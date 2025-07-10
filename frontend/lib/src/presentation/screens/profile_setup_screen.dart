import 'dart:io';
import 'package:flutter/material.dart';
import 'package:flutter_hooks/flutter_hooks.dart';
import '../hooks/use_profile.dart';
import '../widgets/music_search_widget.dart';
import '../../domain/servicios/login_service.dart';
import '../../domain/servicios/auth_service.dart';
import '../../domain/modelos/register_request.dart';
import '../../core/ufro_careers.dart';
import 'main_screen.dart';

class ProfileSetupScreen extends HookWidget {
  final RegisterRequest? temporaryUserData;
  
  const ProfileSetupScreen({
    super.key,
    this.temporaryUserData,
  });

  @override
  Widget build(BuildContext context) {
    final profileHook = useProfile();
    final loginService = LoginService();
    final selectedCareer = useState<String>('');
    final mismaCarreraPreference = useState<bool>(false);
    
    useEffect(() {
      profileHook.validateForm(career: selectedCareer.value);
      return null;
    }, [selectedCareer.value]);
    
    useEffect(() {
      profileHook.validateForm(career: selectedCareer.value);
      return null;
    }, [profileHook.selectedGender.value, profileHook.selectedOrientation.value]);
    
    useEffect(() {
      if (profileHook.isSuccess) {
        WidgetsBinding.instance.addPostFrameCallback((_) {
          ScaffoldMessenger.of(context).showSnackBar(
            const SnackBar(
              content: Text('¡Perfil creado exitosamente! Bienvenido a Banduu'),
              backgroundColor: Colors.green,
              duration: Duration(seconds: 3),
            ),
          );
          
          Navigator.pushReplacement(
            context,
            MaterialPageRoute(builder: (context) => const MainScreen()),
          );
        });
      }
      return null;
    }, [profileHook.isSuccess]);

    return Scaffold(
      backgroundColor: Colors.grey[50],
      appBar: AppBar(
        title: Row(
          mainAxisSize: MainAxisSize.min,
          children: [
            Container(
              width: 30,
              height: 30,
              decoration: BoxDecoration(
                borderRadius: BorderRadius.circular(6),
              ),
              child: ClipRRect(
                borderRadius: BorderRadius.circular(6),
                child: Image.asset(
                  'assets/imagenes/logo_banduu.png',
                  fit: BoxFit.cover,
                  errorBuilder: (context, error, stackTrace) {
                    return const Icon(
                      Icons.music_note,
                      size: 24,
                      color: Colors.white,
                    );
                  },
                ),
              ),
            ),
            const SizedBox(width: 8),
            const Text('Crear Perfil'),
          ],
        ),
        backgroundColor: const Color(0xFF1565C0),
        foregroundColor: Colors.white,
        elevation: 0,
        actions: [
          IconButton(
            icon: const Icon(Icons.logout),
            onPressed: () async {
              final shouldLogout = await showDialog<bool>(
                context: context,
                builder: (context) => AlertDialog(
                  title: const Text('Cerrar sesión'),
                  content: const Text('¿Estás seguro de que quieres cerrar sesión? Se perderán los datos ingresados.'),
                  actions: [
                    TextButton(
                      onPressed: () => Navigator.of(context).pop(false),
                      child: const Text('Cancelar'),
                    ),
                    TextButton(
                      onPressed: () => Navigator.of(context).pop(true),
                      child: const Text('Sí, cerrar sesión'),
                    ),
                  ],
                ),
              );
              
              if (shouldLogout == true) {
                await AuthService().logout();
                
                Navigator.of(context).pushNamedAndRemoveUntil(
                  '/welcome',
                  (route) => false,
                );
              }
            },
          ),
        ],
      ),
      body: SafeArea(
        child: Padding(
          padding: const EdgeInsets.all(24.0),
          child: SingleChildScrollView(
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.stretch,
              children: [
                const SizedBox(height: 20),

                const Text(
                  'Completa tu Perfil',
                  style: TextStyle(
                    fontSize: 32,
                    fontWeight: FontWeight.bold,
                    color: Color(0xFF1565C0),
                  ),
                  textAlign: TextAlign.center,
                ),
                
                const SizedBox(height: 8),
                
                const Text(
                  'Cuéntanos sobre ti y tus gustos musicales para conectar con personas compatibles',
                  style: TextStyle(
                    fontSize: 16,
                    color: Colors.grey,
                  ),
                  textAlign: TextAlign.center,
                ),
                
                const SizedBox(height: 40),
                
                _buildSectionTitle('Información Personal'),
                const SizedBox(height: 16),
                
                Row(
                  children: [
                    Expanded(
                      child: TextFormField(
                        controller: profileHook.nameController,
                        decoration: InputDecoration(
                          labelText: 'Nombre *',
                          hintText: 'Tu nombre',
                          border: OutlineInputBorder(
                            borderRadius: BorderRadius.circular(12),
                          ),
                          prefixIcon: const Icon(Icons.person),
                        ),
                        enabled: !profileHook.isLoading,
                      ),
                    ),
                    const SizedBox(width: 16),
                    Expanded(
                      child: TextFormField(
                        controller: profileHook.lastNameController,
                        decoration: InputDecoration(
                          labelText: 'Apellido *',
                          hintText: 'Tu apellido',
                          border: OutlineInputBorder(
                            borderRadius: BorderRadius.circular(12),
                          ),
                          prefixIcon: const Icon(Icons.person_outline),
                        ),
                        enabled: !profileHook.isLoading,
                      ),
                    ),
                  ],
                ),
                
                const SizedBox(height: 16),
                
                Row(
                  children: [
                    Expanded(
                      flex: 1,
                      child: TextFormField(
                        controller: profileHook.ageController,
                        keyboardType: TextInputType.number,
                        decoration: InputDecoration(
                          labelText: 'Edad *',
                          hintText: '18+',
                          border: OutlineInputBorder(
                            borderRadius: BorderRadius.circular(12),
                          ),
                          prefixIcon: const Icon(Icons.cake),
                        ),
                        enabled: !profileHook.isLoading,
                      ),
                    ),
                    const SizedBox(width: 16),
                    Expanded(
                      flex: 2,
                      child: DropdownButtonFormField<String>(
                        value: selectedCareer.value.isEmpty ? null : selectedCareer.value,
                        decoration: InputDecoration(
                          labelText: 'Carrera/Profesión *',
                          hintText: 'Selecciona tu carrera',
                          border: OutlineInputBorder(
                            borderRadius: BorderRadius.circular(12),
                          ),
                          prefixIcon: const Icon(Icons.school),
                        ),
                        items: UFROCareers.getAllCareers().map((String career) {
                          return DropdownMenuItem<String>(
                            value: career,
                            child: Text(
                              career,
                              style: const TextStyle(fontSize: 14),
                              overflow: TextOverflow.ellipsis,
                            ),
                          );
                        }).toList(),
                        onChanged: profileHook.isLoading ? null : (String? newValue) {
                          if (newValue != null) {
                            selectedCareer.value = newValue;
                          }
                        },
                        isExpanded: true,
                        menuMaxHeight: 300,
                      ),
                    ),
                  ],
                ),
                
                const SizedBox(height: 16),
                
                _buildImageSelector(profileHook, selectedCareer.value),
                
                const SizedBox(height: 32),
                
                _buildSectionTitle('Preferencias Personales'),
                const SizedBox(height: 16),
                
                _buildDropdownSection(
                  'Género *',
                  profileHook.selectedGender,
                  profileHook.genderOptions,
                  (value) => profileHook.updateGender(value, career: selectedCareer.value),
                  Icons.wc,
                ),
                
                const SizedBox(height: 16),
                
                _buildDropdownSection(
                  'Orientación Sexual *',
                  profileHook.selectedOrientation,
                  profileHook.orientationOptions,
                  (value) => profileHook.updateOrientation(value, career: selectedCareer.value),
                  Icons.favorite,
                ),
                
                const SizedBox(height: 32),
                
                _buildSectionTitle('Géneros Musicales *'),
                const SizedBox(height: 8),
                const Text(
                  'Selecciona al menos un género musical. Nuestro algoritmo usa esta información para encontrar personas compatibles contigo',
                  style: TextStyle(color: Colors.grey, fontSize: 14),
                ),
                const SizedBox(height: 16),
                
                _buildMusicGenreSelector(profileHook, selectedCareer.value),
                
                const SizedBox(height: 32),
                
                _buildSectionTitle('Gustos musicales específicos'),
                const SizedBox(height: 8),
                const Text(
                  'Agrega tus canciones, artistas y álbumes favoritos para mejorar las recomendaciones',
                  style: TextStyle(color: Colors.grey, fontSize: 14),
                ),
                const SizedBox(height: 16),
                
                MusicSearchWidget(
                  title: 'Canciones favoritas',
                  hint: 'Buscar canción favorita',
                  icon: Icons.music_note,
                  selectedItems: profileHook.selectedCanciones,
                  onAdd: profileHook.addEnrichedCancion,
                  onRemove: profileHook.removeCancion,
                  searchType: 'track',
                ),
                
                const SizedBox(height: 16),
                
                MusicSearchWidget(
                  title: 'Artistas favoritos',
                  hint: 'Buscar artista favorito',
                  icon: Icons.person,
                  selectedItems: profileHook.selectedArtistas,
                  onAdd: profileHook.addEnrichedArtista,
                  onRemove: profileHook.removeArtista,
                  searchType: 'artist',
                ),
                
                const SizedBox(height: 16),
                
                MusicSearchWidget(
                  title: 'Álbumes favoritos',
                  hint: 'Buscar álbum favorito',
                  icon: Icons.album,
                  selectedItems: profileHook.selectedAlbums,
                  onAdd: profileHook.addEnrichedAlbum,
                  onRemove: profileHook.removeAlbum,
                  searchType: 'album',
                ),
                
                const SizedBox(height: 32),
                
                _buildSectionTitle('Preferencias de Matching'),
                const SizedBox(height: 8),
                Container(
                  padding: const EdgeInsets.all(12),
                  decoration: BoxDecoration(
                    color: Colors.blue[50],
                    borderRadius: BorderRadius.circular(8),
                    border: Border.all(color: Colors.blue[200]!),
                  ),
                  child: Row(
                    children: [
                      Icon(Icons.info_outline, color: Colors.blue[600], size: 20),
                      const SizedBox(width: 8),
                      const Expanded(
                        child: Text(
                          'Configura si quieres incluir personas de tu misma carrera en los resultados de matching',
                          style: TextStyle(color: Color(0xFF1565C0), fontSize: 13),
                        ),
                      ),
                    ],
                  ),
                ),
                const SizedBox(height: 16),
                
                Card(
                  child: Padding(
                    padding: const EdgeInsets.all(16),
                    child: Row(
                      children: [
                        const Icon(Icons.school, color: Color(0xFF1565C0)),
                        const SizedBox(width: 12),
                        Expanded(
                          child: Column(
                            crossAxisAlignment: CrossAxisAlignment.start,
                            children: [
                              const Text(
                                'Incluir personas de mi misma carrera',
                                style: TextStyle(
                                  fontSize: 16,
                                  fontWeight: FontWeight.w500,
                                ),
                              ),
                              const SizedBox(height: 4),
                              Text(
                                mismaCarreraPreference.value
                                    ? 'Se incluirán personas de ${selectedCareer.value.isNotEmpty ? selectedCareer.value : 'tu carrera'} en tus resultados'
                                    : 'No se mostrarán personas de ${selectedCareer.value.isNotEmpty ? selectedCareer.value : 'tu carrera'}',
                                style: TextStyle(
                                  fontSize: 14,
                                  color: mismaCarreraPreference.value ? Colors.green[600] : Colors.grey,
                                ),
                              ),
                            ],
                          ),
                        ),
                        ValueListenableBuilder<bool>(
                          valueListenable: mismaCarreraPreference,
                          builder: (context, value, _) {
                            return Switch(
                              value: value,
                              onChanged: (newValue) {
                                mismaCarreraPreference.value = newValue;
                              },
                              activeColor: const Color(0xFF1565C0),
                            );
                          },
                        ),
                      ],
                    ),
                  ),
                ),
                
                const SizedBox(height: 32),
                
                // Mensaje de error
                ValueListenableBuilder<String?>(
                  valueListenable: profileHook.errorMessage,
                  builder: (context, errorMessage, _) {
                    if (errorMessage == null) return const SizedBox.shrink();
                    
                    return Container(
                      padding: const EdgeInsets.all(12),
                      margin: const EdgeInsets.only(bottom: 16),
                      decoration: BoxDecoration(
                        color: Colors.red.shade50,
                        border: Border.all(color: Colors.red.shade200),
                        borderRadius: BorderRadius.circular(8),
                      ),
                      child: Row(
                        children: [
                          Icon(Icons.error_outline, color: Colors.red.shade600, size: 20),
                          const SizedBox(width: 8),
                          Expanded(
                            child: Text(
                              errorMessage,
                              style: TextStyle(
                                color: Colors.red.shade700,
                                fontSize: 14,
                              ),
                            ),
                          ),
                        ],
                      ),
                    );
                  },
                ),
                
                ValueListenableBuilder<bool>(
                  valueListenable: profileHook.isFormValid,
                  builder: (context, isFormValid, _) {
                    return SizedBox(
                      height: 56,
                      child: ElevatedButton(
                        onPressed: (isFormValid && !profileHook.isLoading) 
                            ? () => _createProfile(context, profileHook, loginService, selectedCareer.value, mismaCarreraPreference.value)
                            : null,
                        style: ElevatedButton.styleFrom(
                          backgroundColor: const Color(0xFF1565C0),
                          foregroundColor: Colors.white,
                          shape: RoundedRectangleBorder(
                            borderRadius: BorderRadius.circular(12),
                          ),
                          elevation: 2,
                        ),
                        child: profileHook.isLoading
                            ? const SizedBox(
                                height: 20,
                                width: 20,
                                child: CircularProgressIndicator(
                                  strokeWidth: 2,
                                  valueColor: AlwaysStoppedAnimation<Color>(Colors.white),
                                ),
                              )
                            : const Text(
                                'Crear Perfil',
                                style: TextStyle(
                                  fontSize: 18,
                                  fontWeight: FontWeight.w600,
                                ),
                              ),
                      ),
                    );
                  },
                ),
                
                const SizedBox(height: 32),
              ],
            ),
          ),
        ),
      ),
    );
  }

  Widget _buildSectionTitle(String title) {
    return Text(
      title,
      style: const TextStyle(
        fontSize: 20,
        fontWeight: FontWeight.bold,
        color: Color(0xFF1565C0),
      ),
    );
  }

  Widget _buildDropdownSection(
    String label,
    ValueNotifier<String> selectedValue,
    List<String> options,
    Function(String) onChanged,
    IconData icon,
  ) {
    return ValueListenableBuilder<String>(
      valueListenable: selectedValue,
      builder: (context, value, _) {
        return DropdownButtonFormField<String>(
          value: value.isEmpty ? null : value,
          decoration: InputDecoration(
            labelText: label,
            border: OutlineInputBorder(
              borderRadius: BorderRadius.circular(12),
            ),
            prefixIcon: Icon(icon),
          ),
          items: options.map((option) {
            return DropdownMenuItem<String>(
              value: option,
              child: Text(option),
            );
          }).toList(),
          onChanged: (newValue) {
            if (newValue != null) {
              onChanged(newValue);
            }
          },
        );
      },
    );
  }

  Widget _buildMusicGenreSelector(ProfileHook profileHook, String selectedCareer) {
    return ValueListenableBuilder<List<String>>(
      valueListenable: profileHook.selectedMusicGenres,
      builder: (context, selectedGenres, _) {
        return Wrap(
          spacing: 8,
          runSpacing: 8,
          children: profileHook.musicGenreOptions.map((genre) {
            final isSelected = selectedGenres.contains(genre);
            return FilterChip(
              label: Text(genre),
              selected: isSelected,
              onSelected: (_) => profileHook.toggleMusicGenre(genre, career: selectedCareer),
              selectedColor: const Color(0xFF1565C0).withOpacity(0.3),
              checkmarkColor: const Color(0xFF1565C0),
              backgroundColor: Colors.grey[100],
              side: BorderSide(
                color: isSelected ? const Color(0xFF1565C0) : Colors.grey[300]!,
              ),
            );
          }).toList(),
        );
      },
    );
  }

  Widget _buildImageSelector(ProfileHook profileHook, String selectedCareer) {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        _buildSectionTitle('Foto de Perfil *'),
        const SizedBox(height: 12),
        
        ValueListenableBuilder<String?>(
          valueListenable: profileHook.selectedImageUrl,
          builder: (context, selectedImageUrl, _) {
            return ValueListenableBuilder<File?>(
              valueListenable: profileHook.selectedImage,
              builder: (context, selectedImage, _) {
                return Container(
                  width: double.infinity,
                  height: 180,
                  decoration: BoxDecoration(
                    border: Border.all(color: Colors.grey[300]!),
                    borderRadius: BorderRadius.circular(12),
                    color: Colors.grey[50],
                  ),
                  child: (selectedImage != null || selectedImageUrl != null)
                      ? Stack(
                          children: [
                            ClipRRect(
                              borderRadius: BorderRadius.circular(12),
                              child: selectedImageUrl != null
                                  ? Image.network(
                                      selectedImageUrl,
                                      width: double.infinity,
                                      height: double.infinity,
                                      fit: BoxFit.cover,
                                      loadingBuilder: (context, child, loadingProgress) {
                                        if (loadingProgress == null) return child;
                                        return Center(
                                          child: CircularProgressIndicator(
                                            value: loadingProgress.expectedTotalBytes != null
                                                ? loadingProgress.cumulativeBytesLoaded / loadingProgress.expectedTotalBytes!
                                                : null,
                                          ),
                                        );
                                      },
                                      errorBuilder: (context, error, stackTrace) {
                                        return Center(
                                          child: Column(
                                            mainAxisAlignment: MainAxisAlignment.center,
                                            children: [
                                              Icon(
                                                Icons.error_outline,
                                                size: 48,
                                                color: Colors.red[400],
                                              ),
                                              const SizedBox(height: 8),
                                              Text(
                                                'Error al cargar imagen',
                                                style: TextStyle(
                                                  fontSize: 14,
                                                  color: Colors.red[600],
                                                ),
                                              ),
                                            ],
                                          ),
                                        );
                                      },
                                    )
                                  : selectedImage != null
                                      ? Image.file(
                                          selectedImage,
                                          width: double.infinity,
                                          height: double.infinity,
                                          fit: BoxFit.cover,
                                        )
                                      : Container(),
                            ),
                            
                            Positioned(
                              top: 8,
                              right: 8,
                              child: GestureDetector(
                                onTap: () => profileHook.removeSelectedImage(career: selectedCareer),
                                child: Container(
                                  padding: const EdgeInsets.all(6),
                                  decoration: BoxDecoration(
                                    color: Colors.red.withOpacity(0.8),
                                    borderRadius: BorderRadius.circular(20),
                                  ),
                                  child: const Icon(
                                    Icons.close,
                                    color: Colors.white,
                                    size: 18,
                                  ),
                                ),
                              ),
                            ),
                          ],
                        )
                      : Column(
                          mainAxisAlignment: MainAxisAlignment.center,
                          children: [
                            Icon(
                              Icons.add_a_photo,
                              size: 48,
                              color: Colors.grey[400],
                            ),
                            const SizedBox(height: 8),
                            Text(
                              'Agrega tu foto de perfil',
                              style: TextStyle(
                                fontSize: 16,
                                color: Colors.grey[600],
                                fontWeight: FontWeight.w500,
                              ),
                            ),
                            const SizedBox(height: 4),
                            Text(
                              'Una buena foto es esencial para encontrar conexiones',
                              style: TextStyle(
                                fontSize: 14,
                                color: Colors.grey[500],
                              ),
                              textAlign: TextAlign.center,
                            ),
                          ],
                        ),
                );
              },
            );
          },
        ),
        
        const SizedBox(height: 12),
        
        ValueListenableBuilder<String?>(
          valueListenable: profileHook.selectedImageUrl,
          builder: (context, selectedImageUrl, _) {
            return ValueListenableBuilder<File?>(
              valueListenable: profileHook.selectedImage,
              builder: (context, selectedImage, _) {
                final hasImage = selectedImage != null || selectedImageUrl != null;
                
                return Row(
                  children: [
                    Expanded(
                      child: OutlinedButton.icon(
                        onPressed: profileHook.isLoading 
                            ? null 
                            : () => profileHook.selectImage(context, career: selectedCareer),
                        icon: const Icon(Icons.photo_library),
                        label: Text(hasImage ? 'Cambiar foto' : 'Seleccionar foto'),
                        style: OutlinedButton.styleFrom(
                          foregroundColor: const Color(0xFF1565C0),
                          side: const BorderSide(color: Color(0xFF1565C0)),
                          shape: RoundedRectangleBorder(
                            borderRadius: BorderRadius.circular(8),
                          ),
                        ),
                      ),
                    ),
                    if (hasImage) ...[
                      const SizedBox(width: 12),
                      Expanded(
                        child: OutlinedButton.icon(
                          onPressed: profileHook.isLoading 
                              ? null 
                              : () => profileHook.removeSelectedImage(career: selectedCareer),
                          icon: const Icon(Icons.delete_outline),
                          label: const Text('Quitar foto'),
                          style: OutlinedButton.styleFrom(
                            foregroundColor: Colors.red,
                            side: const BorderSide(color: Colors.red),
                            shape: RoundedRectangleBorder(
                              borderRadius: BorderRadius.circular(8),
                            ),
                          ),
                        ),
                      ),
                    ],
                  ],
                );
              },
            );
          },
        ),
      ],
    );
  }

  void _createProfile(BuildContext context, ProfileHook profileHook, LoginService loginService, String selectedCareer, bool mismaCarreraPreference) async {
    if (temporaryUserData == null) {
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(
          content: Text('Error: No se encontraron datos del registro. Por favor, regrese y complete el registro nuevamente.'),
          backgroundColor: Colors.red,
        ),
      );
      return;
    }

    await profileHook.createProfile(
      temporaryUserData,
      career: selectedCareer,
      mismaCarreraPreference: mismaCarreraPreference,
    );
  }
}
