import 'package:flutter/material.dart';
import 'package:flutter_hooks/flutter_hooks.dart';
import 'dart:io';
import '../../domain/servicios/image_upload_service.dart';
import '../../domain/servicios/profile_service.dart';
import '../../domain/servicios/login_service.dart';
import '../../domain/servicios/music_service.dart';
import '../../domain/modelos/profile_request.dart';
import '../../domain/modelos/music_item.dart';
import '../../domain/modelos/register_request.dart';
import '../../domain/modelos/login_request.dart';

enum ProfileState {
  idle,           
  loading,       
  success,        
  error,          
  validating,    
}

class ProfileHook {
  final TextEditingController nameController;
  final TextEditingController lastNameController;
  final TextEditingController ageController;
  final ValueNotifier<String> selectedGender;
  final ValueNotifier<String> selectedOrientation;
  final ValueNotifier<List<String>> selectedMusicGenres;
  final ValueNotifier<List<String>> selectedCanciones;
  final ValueNotifier<List<String>> selectedArtistas;
  final ValueNotifier<List<String>> selectedAlbums;
  final ValueNotifier<List<MusicItem>> enrichedCanciones;
  final ValueNotifier<List<MusicItem>> enrichedArtistas;
  final ValueNotifier<List<MusicItem>> enrichedAlbums;
  final ValueNotifier<ProfileState> profileState;
  final ValueNotifier<String?> errorMessage;
  final ValueNotifier<bool> isFormValid;
  final ValueNotifier<File?> selectedImage;
  final ValueNotifier<String?> selectedImageUrl;
  final LoginService _loginService = LoginService();
  final MusicService _musicService = MusicService();

  ProfileHook({
    required this.nameController,
    required this.lastNameController,
    required this.ageController,
    required this.selectedGender,
    required this.selectedOrientation,
    required this.selectedMusicGenres,
    required this.selectedCanciones,
    required this.selectedArtistas,
    required this.selectedAlbums,
    required this.enrichedCanciones,
    required this.enrichedArtistas,
    required this.enrichedAlbums,
    required this.profileState,
    required this.errorMessage,
    required this.isFormValid,
    required this.selectedImage,
    required this.selectedImageUrl,
  });

  bool get isLoading => profileState.value == ProfileState.loading;
  bool get hasError => profileState.value == ProfileState.error;
  bool get isSuccess => profileState.value == ProfileState.success;

  void _setError(String message) {
    profileState.value = ProfileState.error;
    errorMessage.value = message;
  }

  List<String> get genderOptions => ['Masculino', 'Femenino', 'No binario', 'Prefiero no decir'];
  
  List<String> get orientationOptions => ['Heterosexual', 'Homosexual', 'Bisexual', 'Pansexual', 'Asexual', 'Prefiero no decir'];
  
  List<String> get musicGenreOptions => [
    'Rock', 'Pop', 'Jazz', 'Blues', 'Country', 'Electronic', 'Hip Hop', 'R&B',
    'Reggae', 'Classical', 'Folk', 'Punk', 'Metal', 'Alternative', 'Indie',
    'Funk', 'Soul', 'Gospel', 'Latin', 'World Music'
  ];

  void updateGender(String gender, {String? career}) {
    selectedGender.value = gender;
    validateForm(career: career);
  }

  void updateOrientation(String orientation, {String? career}) {
    selectedOrientation.value = orientation;
    validateForm(career: career);
  }

  void toggleMusicGenre(String genre, {String? career}) {
    final currentGenres = List<String>.from(selectedMusicGenres.value);
    if (currentGenres.contains(genre)) {
      currentGenres.remove(genre);
    } else {
      currentGenres.add(genre);
    }
    selectedMusicGenres.value = currentGenres;
    validateForm(career: career);
  }

  Future<void> createProfile(RegisterRequest? userInfo, {String? career, bool? mismaCarreraPreference}) async {
    if (!isFormValid.value) {
      String missingFields = '';
      if (nameController.text.trim().length < 2) missingFields += '• Nombre completo\n';
      if (lastNameController.text.trim().length < 2) missingFields += '• Apellido completo\n';
      if (ageController.text.trim().isEmpty || int.tryParse(ageController.text.trim()) == null) missingFields += '• Edad válida\n';
      if (career?.isEmpty ?? true) missingFields += '• Carrera/Profesión\n';
      if (selectedGender.value.isEmpty) missingFields += '• Género\n';
      if (selectedOrientation.value.isEmpty) missingFields += '• Orientación sexual\n';
      if (selectedMusicGenres.value.isEmpty) missingFields += '• Al menos un género musical\n';
      if (selectedImage.value == null && selectedImageUrl.value == null) missingFields += '• Foto de perfil\n';
      
      _setError('Por favor completa los siguientes campos:\n$missingFields');
      return;
    }

    if (userInfo == null) {
      _setError('Error: No se encontraron datos del usuario. Regrese al registro.');
      return;
    }

    if (career == null || career.isEmpty) {
      _setError('Por favor seleccione una carrera/profesión');
      return;
    }

    profileState.value = ProfileState.loading;
    errorMessage.value = null;

    try {
  
      final registerResponse = await _loginService.register(userInfo);
      
      if (!registerResponse.exito) {
        _setError('Error al registrar usuario: ${registerResponse.mensaje}');
        return;
      }
    
      final loginRequest = LoginRequest(
        nombreUsuario: userInfo.nombreUsuario,
        contrasena: userInfo.contrasena,
      );
      
      final loginResponse = await _loginService.login(loginRequest);
      
      if (!loginResponse.success) {
        _setError('Usuario registrado pero error al iniciar sesión: ${loginResponse.message}');
        return;
      }

      final userId = _loginService.currentUserId ?? _loginService.currentUsername ?? userInfo.nombreUsuario;
 
      final profileRequest = ProfileRequest(
        idUsuario: userId,
        nombre: nameController.text.trim(),
        apellido: lastNameController.text.trim(),
        edad: int.parse(ageController.text.trim()),
        imagen: selectedImageUrl.value ?? 'default_avatar.png',
        carrera: career,
        orientacion: selectedOrientation.value,
        genero: selectedGender.value,
        generosMusicales: selectedMusicGenres.value,
        canciones: selectedCanciones.value,
        artistas: selectedArtistas.value,
        albums: selectedAlbums.value,
        mismaCarrera: mismaCarreraPreference ?? false,
      );

      final profileService = ProfileService();
      final profileResponse = await profileService.createProfile(profileRequest);


      if (profileResponse.exito) {

        final isProfileCreated = await verifyProfileCreated(userId);
        if (isProfileCreated) {
          profileState.value = ProfileState.success;
          errorMessage.value = null;
        } else {
          _setError('El perfil se creó pero no se pudo verificar. Intente iniciar sesión nuevamente.');
        }
      } else {
        _setError('Usuario registrado exitosamente, pero error al crear perfil: ${profileResponse.mensaje}. Puede intentar nuevamente.');
      }
      
    } catch (e) {
      _setError('Error inesperado: $e');
    }
  }

  Future<void> selectImage(BuildContext context, {String? career}) async {
    try {
      
      final imageUploadService = ImageUploadService();

      final String? imageUrl = await imageUploadService.showImagePickerDialog(context);
      
      if (imageUrl != null) {

        selectedImageUrl.value = imageUrl;

        selectedImage.value = File('cloudinary_image_${DateTime.now().millisecondsSinceEpoch}.jpg');

        validateForm(career: career);
      
      } else {
        print('DEBUG - No se obtuvo URL de imagen (usuario canceló o error)');
      }
    } catch (e) {
      _setError('Error seleccionando imagen: $e');
    }
  }

  void removeSelectedImage({String? career}) {
    selectedImage.value = null;
    selectedImageUrl.value = null;
    validateForm(career: career);
  }

  void validateForm({String? career}) {
    final name = nameController.text.trim();
    final lastName = lastNameController.text.trim();
    final ageText = ageController.text.trim();

    final isNameValid = name.isNotEmpty && name.length >= 2;
    final isLastNameValid = lastName.isNotEmpty && lastName.length >= 2;
    final isAgeValid = ageText.isNotEmpty && int.tryParse(ageText) != null && 
                      int.parse(ageText) >= 18 && int.parse(ageText) <= 100;
    final isCareerValid = (career?.isNotEmpty ?? false);
    final isGenderValid = selectedGender.value.isNotEmpty;
    final isOrientationValid = selectedOrientation.value.isNotEmpty;
    final isMusicGenresValid = selectedMusicGenres.value.isNotEmpty;
    final isImageValid = selectedImage.value != null || selectedImageUrl.value != null;

    final isValid = isNameValid && isLastNameValid && isAgeValid && 
                   isCareerValid && isGenderValid && isOrientationValid && 
                   isMusicGenresValid && isImageValid;

    isFormValid.value = isValid;

    if (isValid && hasError) {
      profileState.value = ProfileState.idle;
      errorMessage.value = null;
    }
  }

  Future<bool> verifyProfileCreated(String userId) async {
    try {
      final profileService = ProfileService();
      final response = await profileService.getProfileByUserId(userId);
      
      return response.exito;
    } catch (e) {
      return false;
    }
  }

  void dispose() {
    nameController.dispose();
    lastNameController.dispose();
    ageController.dispose();
    selectedGender.dispose();
    selectedOrientation.dispose();
    selectedMusicGenres.dispose();
    selectedCanciones.dispose();
    selectedArtistas.dispose();
    selectedAlbums.dispose();
    enrichedCanciones.dispose();
    enrichedArtistas.dispose();
    enrichedAlbums.dispose();
    profileState.dispose();
    errorMessage.dispose();
    isFormValid.dispose();
    selectedImage.dispose();
    selectedImageUrl.dispose();
  }

  void addCancion(String cancion) {
    final current = List<String>.from(selectedCanciones.value);
    if (!current.contains(cancion) && cancion.trim().isNotEmpty) {
      current.add(cancion.trim());
      selectedCanciones.value = current;
    }
  }

  void removeCancion(String cancion) {
    final current = List<String>.from(selectedCanciones.value);
    current.remove(cancion);
    selectedCanciones.value = current;
    

    final enrichedCurrent = List<MusicItem>.from(enrichedCanciones.value);
    enrichedCurrent.removeWhere((item) => item.displayName == cancion);
    enrichedCanciones.value = enrichedCurrent;
  }

  void addArtista(String artista) {
    final current = List<String>.from(selectedArtistas.value);
    if (!current.contains(artista) && artista.trim().isNotEmpty) {
      current.add(artista.trim());
      selectedArtistas.value = current;
    }
  }

  void removeArtista(String artista) {
    final current = List<String>.from(selectedArtistas.value);
    current.remove(artista);
    selectedArtistas.value = current;

    final enrichedCurrent = List<MusicItem>.from(enrichedArtistas.value);
    enrichedCurrent.removeWhere((item) => item.displayName == artista);
    enrichedArtistas.value = enrichedCurrent;
  }

  void addAlbum(String album) {
    final current = List<String>.from(selectedAlbums.value);
    if (!current.contains(album) && album.trim().isNotEmpty) {
      current.add(album.trim());
      selectedAlbums.value = current;
    }
  }

  void removeAlbum(String album) {
    final current = List<String>.from(selectedAlbums.value);
    current.remove(album);
    selectedAlbums.value = current;
    
    final enrichedCurrent = List<MusicItem>.from(enrichedAlbums.value);
    enrichedCurrent.removeWhere((item) => item.displayName == album);
    enrichedAlbums.value = enrichedCurrent;
  }

  Future<void> addEnrichedCancion(String query) async {
    try {
      final result = await _musicService.searchTrack(query);
      if (result != null) {
        final musicItem = MusicItem(
          name: result.name,
          artist: result.artist,
          genre: result.genre,
          imageUrl: result.imageUrl,
          type: 'track',
        );
        
        final enrichedCurrent = List<MusicItem>.from(enrichedCanciones.value);
        if (!enrichedCurrent.any((item) => item.name == musicItem.name && item.artist == musicItem.artist)) {
          enrichedCurrent.add(musicItem);
          enrichedCanciones.value = enrichedCurrent;
        }
      }
   
      addCancion(query);
    } catch (e) {
      addCancion(query);
    }
  }

  Future<void> addEnrichedArtista(String query) async {
    try {
      final result = await _musicService.searchArtist(query);
      if (result != null) {
        final musicItem = MusicItem(
          name: result.name,
          artist: null,
          genre: result.genre,
          imageUrl: result.imageUrl,
          type: 'artist',
        );
        
        final enrichedCurrent = List<MusicItem>.from(enrichedArtistas.value);
        if (!enrichedCurrent.any((item) => item.name == musicItem.name)) {
          enrichedCurrent.add(musicItem);
          enrichedArtistas.value = enrichedCurrent;
        }
      }
      
      addArtista(query);
    } catch (e) {
      addArtista(query);
    }
  }

  Future<void> addEnrichedAlbum(String query) async {
    try {
      final result = await _musicService.searchAlbum(query);
      if (result != null) {
        final musicItem = MusicItem(
          name: result.name,
          artist: result.artist,
          genre: result.genre,
          imageUrl: result.imageUrl,
          type: 'album',
        );
        
        final enrichedCurrent = List<MusicItem>.from(enrichedAlbums.value);
        if (!enrichedCurrent.any((item) => item.name == musicItem.name && item.artist == musicItem.artist)) {
          enrichedCurrent.add(musicItem);
          enrichedAlbums.value = enrichedCurrent;
        }
      }
  
      addAlbum(query);
    } catch (e) {
      addAlbum(query);
    }
  }
}

ProfileHook useProfile() {
  final nameController = useTextEditingController();
  final lastNameController = useTextEditingController();
  final ageController = useTextEditingController();
  
  final selectedGender = useState<String>('');
  final selectedOrientation = useState<String>('');
  final selectedMusicGenres = useState<List<String>>([]);
  final selectedCanciones = useState<List<String>>([]);
  final selectedArtistas = useState<List<String>>([]);
  final selectedAlbums = useState<List<String>>([]);
  final enrichedCanciones = useState<List<MusicItem>>([]);
  final enrichedArtistas = useState<List<MusicItem>>([]);
  final enrichedAlbums = useState<List<MusicItem>>([]);
  final profileState = useState<ProfileState>(ProfileState.idle);
  final errorMessage = useState<String?>(null);
  final isFormValid = useState<bool>(false);
  final selectedImage = useState<File?>(null);
  final selectedImageUrl = useState<String?>(null); 

  final profileHook = useMemoized(() => ProfileHook(
    nameController: nameController,
    lastNameController: lastNameController,
    ageController: ageController,
    selectedGender: selectedGender,
    selectedOrientation: selectedOrientation,
    selectedMusicGenres: selectedMusicGenres,
    selectedCanciones: selectedCanciones,
    selectedArtistas: selectedArtistas,
    selectedAlbums: selectedAlbums,
    enrichedCanciones: enrichedCanciones,
    enrichedArtistas: enrichedArtistas,
    enrichedAlbums: enrichedAlbums,
    profileState: profileState,
    errorMessage: errorMessage,
    isFormValid: isFormValid,
    selectedImage: selectedImage,
    selectedImageUrl: selectedImageUrl,
  ), []);

  useEffect(() {
    void listener() => profileHook.validateForm();
    
    nameController.addListener(listener);
    lastNameController.addListener(listener);
    ageController.addListener(listener);
    
    return () {
      nameController.removeListener(listener);
      lastNameController.removeListener(listener);
      ageController.removeListener(listener);
    };
  }, [nameController, lastNameController, ageController]);

  useEffect(() {
    return () => profileHook.dispose();
  }, []);

  return profileHook;
}
