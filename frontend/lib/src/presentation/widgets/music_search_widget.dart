import 'dart:async';
import 'package:flutter/material.dart';
import 'package:flutter_hooks/flutter_hooks.dart';
import '../hooks/use_music_search.dart';
import '../../domain/servicios/music_service.dart';

class MusicSearchWidget extends HookWidget {
  final String title;
  final String hint;
  final IconData icon;
  final ValueNotifier<List<String>> selectedItems;
  final Future<void> Function(String) onAdd;
  final void Function(String) onRemove;
  final String searchType; // 'track', 'artist', 'album'

  const MusicSearchWidget({
    super.key,
    required this.title,
    required this.hint,
    required this.icon,
    required this.selectedItems,
    required this.onAdd,
    required this.onRemove,
    required this.searchType,
  });

  @override
  Widget build(BuildContext context) {
    final musicSearch = useMusicSearch();
    final searchController = useTextEditingController();
    final showSuggestions = useState<bool>(false);
    final debounceTimer = useRef<Timer?>(null);

    // Limpiar el timer cuando se desmonte el widget
    useEffect(() {
      return () => debounceTimer.value?.cancel();
    }, []);

    void onSearchChanged(String query) {
      print('DEBUG MUSIC_WIDGET - Search changed: $query');
      debounceTimer.value?.cancel();
      
      if (query.isEmpty) {
        musicSearch.clearSearch();
        showSuggestions.value = false;
        return;
      }

      debounceTimer.value = Timer(const Duration(milliseconds: 500), () {
        print('DEBUG MUSIC_WIDGET - Executing search after debounce: $query');
        showSuggestions.value = true;
        switch (searchType) {
          case 'track':
            print('DEBUG MUSIC_WIDGET - Searching track: $query');
            musicSearch.searchTrack(query);
            break;
          case 'artist':
            print('DEBUG MUSIC_WIDGET - Searching artist: $query');
            musicSearch.searchArtist(query);
            break;
          case 'album':
            print('DEBUG MUSIC_WIDGET - Searching album: $query');
            musicSearch.searchAlbum(query);
            break;
        }
      });
    }

    void addItem(String item) async {
      print('DEBUG MUSIC_WIDGET - Adding item: $item');
      try {
        await onAdd(item);
        print('DEBUG MUSIC_WIDGET - Item added successfully: $item');
        searchController.clear();
        musicSearch.clearSearch();
        showSuggestions.value = false;
      } catch (e) {
        print('DEBUG MUSIC_WIDGET - Error adding item: $e');
        // Si hay un error, manejar de manera silenciosa
        // El usuario seguirá viendo el item agregado en la lista
      }
    }

    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        Text(
          title,
          style: const TextStyle(
            fontSize: 16,
            fontWeight: FontWeight.w600,
            color: Color(0xFF1565C0),
          ),
        ),
        const SizedBox(height: 8),
        
        // Input de búsqueda
        TextField(
          controller: searchController,
          decoration: InputDecoration(
            hintText: hint,
            border: OutlineInputBorder(
              borderRadius: BorderRadius.circular(8),
            ),
            prefixIcon: Icon(icon, color: const Color(0xFF1565C0)),
            suffixIcon: ValueListenableBuilder<bool>(
              valueListenable: musicSearch.isSearching,
              builder: (context, isSearching, _) {
                if (isSearching) {
                  return const Padding(
                    padding: EdgeInsets.all(12),
                    child: SizedBox(
                      width: 16,
                      height: 16,
                      child: CircularProgressIndicator(
                        strokeWidth: 2,
                        color: Color(0xFF1565C0),
                      ),
                    ),
                  );
                }
                return const SizedBox.shrink();
              },
            ),
            contentPadding: const EdgeInsets.symmetric(horizontal: 12, vertical: 8),
          ),
          onChanged: onSearchChanged,
          onSubmitted: (value) {
            if (value.trim().isNotEmpty) {
              addItem(value.trim());
            }
          },
        ),
        
        // Sugerencias de búsqueda
        ValueListenableBuilder<bool>(
          valueListenable: showSuggestions,
          builder: (context, shouldShow, _) {
            if (!shouldShow) return const SizedBox.shrink();
            
            return ValueListenableBuilder<List<MusicSearchResult>>(
              valueListenable: musicSearch.searchResults,
              builder: (context, results, _) {
                if (results.isEmpty) {
                  return ValueListenableBuilder<bool>(
                    valueListenable: musicSearch.isSearching,
                    builder: (context, isSearching, _) {
                      if (isSearching) return const SizedBox.shrink();
                      
                      return Container(
                        margin: const EdgeInsets.only(top: 4),
                        padding: const EdgeInsets.all(8),
                        decoration: BoxDecoration(
                          color: Colors.grey[100],
                          borderRadius: BorderRadius.circular(8),
                          border: Border.all(color: Colors.grey[300]!),
                        ),
                        child: Row(
                          children: [
                            Icon(Icons.search_off, color: Colors.grey[600], size: 16),
                            const SizedBox(width: 8),
                            Text(
                              'No se encontraron resultados',
                              style: TextStyle(
                                color: Colors.grey[600],
                                fontSize: 12,
                              ),
                            ),
                          ],
                        ),
                      );
                    },
                  );
                }
                
                return Container(
                  margin: const EdgeInsets.only(top: 4),
                  decoration: BoxDecoration(
                    color: Colors.white,
                    borderRadius: BorderRadius.circular(8),
                    border: Border.all(color: Colors.grey[300]!),
                    boxShadow: [
                      BoxShadow(
                        color: Colors.grey.withOpacity(0.1),
                        blurRadius: 4,
                        offset: const Offset(0, 2),
                      ),
                    ],
                  ),
                  child: ListView.builder(
                    shrinkWrap: true,
                    itemCount: results.length,
                    itemBuilder: (context, index) {
                      final result = results[index];
                      return ListTile(
                        dense: true,
                        leading: result.imageUrl != null
                            ? ClipRRect(
                                borderRadius: BorderRadius.circular(4),
                                child: Image.network(
                                  result.imageUrl!,
                                  width: 40,
                                  height: 40,
                                  fit: BoxFit.cover,
                                  errorBuilder: (context, error, stackTrace) {
                                    return Container(
                                      width: 40,
                                      height: 40,
                                      color: Colors.grey[300],
                                      child: Icon(
                                        icon,
                                        color: Colors.grey[600],
                                        size: 20,
                                      ),
                                    );
                                  },
                                ),
                              )
                            : Container(
                                width: 40,
                                height: 40,
                                decoration: BoxDecoration(
                                  color: Colors.grey[200],
                                  borderRadius: BorderRadius.circular(4),
                                ),
                                child: Icon(
                                  icon,
                                  color: Colors.grey[600],
                                  size: 20,
                                ),
                              ),
                        title: Text(
                          result.name,
                          style: const TextStyle(
                            fontSize: 14,
                            fontWeight: FontWeight.w500,
                          ),
                        ),
                        subtitle: Column(
                          crossAxisAlignment: CrossAxisAlignment.start,
                          children: [
                            if (result.artist != null)
                              Text(
                                'Artista: ${result.artist}',
                                style: TextStyle(
                                  fontSize: 12,
                                  color: Colors.grey[600],
                                ),
                              ),
                            if (result.genre != null)
                              Text(
                                'Género: ${result.genre}',
                                style: TextStyle(
                                  fontSize: 12,
                                  color: Colors.grey[600],
                                ),
                              ),
                          ],
                        ),
                        trailing: IconButton(
                          icon: const Icon(Icons.add, color: Color(0xFF1565C0)),
                          onPressed: () => addItem(result.displayName),
                        ),
                        onTap: () => addItem(result.displayName),
                      );
                    },
                  ),
                );
              },
            );
          },
        ),
        
        // Error de búsqueda
        ValueListenableBuilder<String?>(
          valueListenable: musicSearch.searchError,
          builder: (context, error, _) {
            if (error == null) return const SizedBox.shrink();
            
            return Container(
              margin: const EdgeInsets.only(top: 4),
              padding: const EdgeInsets.all(8),
              decoration: BoxDecoration(
                color: Colors.red[50],
                borderRadius: BorderRadius.circular(8),
                border: Border.all(color: Colors.red[200]!),
              ),
              child: Row(
                children: [
                  Icon(Icons.error_outline, color: Colors.red[600], size: 16),
                  const SizedBox(width: 8),
                  Expanded(
                    child: Text(
                      error,
                      style: TextStyle(
                        color: Colors.red[600],
                        fontSize: 12,
                      ),
                    ),
                  ),
                ],
              ),
            );
          },
        ),
        
        const SizedBox(height: 12),
        
        // Lista de items seleccionados
        ValueListenableBuilder<List<String>>(
          valueListenable: selectedItems,
          builder: (context, itemsList, _) {
            if (itemsList.isEmpty) {
              return Container(
                padding: const EdgeInsets.all(16),
                decoration: BoxDecoration(
                  color: Colors.grey[100],
                  borderRadius: BorderRadius.circular(8),
                  border: Border.all(color: Colors.grey[300]!),
                ),
                child: Center(
                  child: Text(
                    'No hay ${title.toLowerCase()} agregados',
                    style: const TextStyle(
                      color: Colors.grey,
                      fontSize: 14,
                    ),
                  ),
                ),
              );
            }
            
            return Container(
              padding: const EdgeInsets.all(12),
              decoration: BoxDecoration(
                color: Colors.grey[50],
                borderRadius: BorderRadius.circular(8),
                border: Border.all(color: Colors.grey[300]!),
              ),
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Text(
                    '${itemsList.length} ${title.toLowerCase()}',
                    style: const TextStyle(
                      fontSize: 14,
                      fontWeight: FontWeight.w500,
                      color: Colors.grey,
                    ),
                  ),
                  const SizedBox(height: 8),
                  Wrap(
                    spacing: 8,
                    runSpacing: 4,
                    children: itemsList.map((item) {
                      return Chip(
                        label: Text(
                          item,
                          style: const TextStyle(fontSize: 12),
                        ),
                        onDeleted: () => onRemove(item),
                        deleteIcon: const Icon(Icons.close, size: 16),
                        backgroundColor: const Color(0xFF1565C0).withOpacity(0.1),
                        side: BorderSide(
                          color: const Color(0xFF1565C0).withOpacity(0.3),
                        ),
                      );
                    }).toList(),
                  ),
                ],
              ),
            );
          },
        ),
      ],
    );
  }
}
