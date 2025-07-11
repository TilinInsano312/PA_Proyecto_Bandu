import 'package:flutter/material.dart';
import '../../domain/modelos/music_item.dart';

class MusicDisplayWidget extends StatelessWidget {
  final String title;
  final List<MusicItem> items;
  final List<String> fallbackItems;
  final IconData icon;
  final int maxItems;

  const MusicDisplayWidget({
    super.key,
    required this.title,
    required this.items,
    required this.fallbackItems,
    required this.icon,
    this.maxItems = 3,
  });

  @override
  Widget build(BuildContext context) {
    // Usar items enriquecidos si están disponibles, sino usar fallback
    final List<Widget> musicWidgets = [];
    
    if (items.isNotEmpty) {
      // Mostrar items enriquecidos con imágenes
      for (int i = 0; i < items.length && i < maxItems; i++) {
        final item = items[i];
        musicWidgets.add(
          _buildEnrichedMusicItem(item),
        );
      }
    } else if (fallbackItems.isNotEmpty) {
      // Mostrar items simples sin imágenes
      for (int i = 0; i < fallbackItems.length && i < maxItems; i++) {
        final item = fallbackItems[i];
        musicWidgets.add(
          _buildSimpleMusicItem(item),
        );
      }
    }

    if (musicWidgets.isEmpty) {
      return const SizedBox.shrink();
    }

    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        Text(
          title,
          style: const TextStyle(
            fontSize: 14,
            fontWeight: FontWeight.w500,
            color: Colors.black87,
          ),
        ),
        const SizedBox(height: 4),
        ...musicWidgets,
        const SizedBox(height: 8),
      ],
    );
  }

  Widget _buildEnrichedMusicItem(MusicItem item) {
    return Padding(
      padding: const EdgeInsets.symmetric(vertical: 2),
      child: Row(
        children: [
          // Imagen del item musical
          ClipRRect(
            borderRadius: BorderRadius.circular(4),
            child: item.imageUrl != null
                ? Image.network(
                    item.imageUrl!,
                    width: 24,
                    height: 24,
                    fit: BoxFit.cover,
                    errorBuilder: (context, error, stackTrace) {
                      return Container(
                        width: 24,
                        height: 24,
                        color: Colors.grey[300],
                        child: Icon(
                          icon,
                          size: 12,
                          color: Colors.grey[600],
                        ),
                      );
                    },
                  )
                : Container(
                    width: 24,
                    height: 24,
                    decoration: BoxDecoration(
                      color: Colors.grey[200],
                      borderRadius: BorderRadius.circular(4),
                    ),
                    child: Icon(
                      icon,
                      size: 12,
                      color: Colors.grey[600],
                    ),
                  ),
          ),
          const SizedBox(width: 8),
          Expanded(
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Text(
                  item.name,
                  style: const TextStyle(
                    fontSize: 12,
                    color: Colors.black87,
                    fontWeight: FontWeight.w500,
                  ),
                  maxLines: 1,
                  overflow: TextOverflow.ellipsis,
                ),
                if (item.artist != null) ...[
                  const SizedBox(height: 1),
                  Text(
                    'por ${item.artist}',
                    style: TextStyle(
                      fontSize: 10,
                      color: Colors.grey[600],
                    ),
                    maxLines: 1,
                    overflow: TextOverflow.ellipsis,
                  ),
                ],
                if (item.genre != null) ...[
                  const SizedBox(height: 1),
                  Text(
                    item.genre!,
                    style: TextStyle(
                      fontSize: 10,
                      color: Colors.grey[500],
                      fontStyle: FontStyle.italic,
                    ),
                    maxLines: 1,
                    overflow: TextOverflow.ellipsis,
                  ),
                ],
              ],
            ),
          ),
        ],
      ),
    );
  }

  Widget _buildSimpleMusicItem(String item) {
    return Padding(
      padding: const EdgeInsets.symmetric(vertical: 2),
      child: Row(
        children: [
          Icon(icon, size: 16, color: const Color(0xFF1565C0)),
          const SizedBox(width: 4),
          Expanded(
            child: Text(
              item,
              style: const TextStyle(
                fontSize: 12,
                color: Colors.black87,
              ),
              maxLines: 1,
              overflow: TextOverflow.ellipsis,
            ),
          ),
        ],
      ),
    );
  }
}
