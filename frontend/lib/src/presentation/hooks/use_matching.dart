import 'package:flutter_hooks/flutter_hooks.dart';
import '../../domain/servicios/matching_service.dart';
import '../../domain/modelos/profile.dart';
import '../../domain/servicios/login_service.dart';

class UseMatchingResult {
  final List<Profile> profiles;
  final bool isLoading;
  final String? error;
  final Future<void> Function() refreshProfiles;
  final Future<void> Function(String profileId) likeProfile;
  final Future<void> Function(String profileId) passProfile;
  final Map<String, int> matchingScores;
  final bool isLoadingScores;

  UseMatchingResult({
    required this.profiles,
    required this.isLoading,
    this.error,
    required this.refreshProfiles,
    required this.likeProfile,
    required this.passProfile,
    required this.matchingScores,
    required this.isLoadingScores,
  });
}

UseMatchingResult useMatching() {
  final matchingService = useMemoized(() => MatchingService());
  final loginService = useMemoized(() => LoginService());
  
  final profiles = useState<List<Profile>>([]);
  final isLoading = useState<bool>(false);
  final error = useState<String?>('');
  final matchingScores = useState<Map<String, int>>({});
  final isLoadingScores = useState<bool>(false);
  final currentProfileIndex = useState<int>(0);
  final likedProfiles = useState<Set<String>>({});
  final passedProfiles = useState<Set<String>>({});

  Future<void> loadMatchingScores(String userId) async {
    if (isLoadingScores.value) return; 
    
    isLoadingScores.value = true;
    
    try {
      final carreraScores = await matchingService.getMatchingByCarrera(userId);
      final generoScores = await matchingService.getMatchingByGenerosMusicales(userId);
      final orientacionScores = await matchingService.getMatchingByOrientacion(userId);
  
      if (profiles.value.isNotEmpty) {
        final Map<String, int> combinedScores = {};
        
        for (final profile in profiles.value) {
          final carrera = carreraScores[profile.id] ?? 0;
          final generos = generoScores[profile.id] ?? 0;
          final orientacion = orientacionScores[profile.id] ?? 0;

          final totalScore = (carrera * 0.3).round() + 
                            (generos * 0.5).round() + 
                            (orientacion * 0.2).round();
          
          combinedScores[profile.id ?? ''] = totalScore;
        }
        
        matchingScores.value = combinedScores;
      }
      
    } catch (e) {
      try {
        error.value = 'Error al calcular compatibilidad: $e';
      } catch (_) {
      }
    } finally {
      try {
        isLoadingScores.value = false;
      } catch (_) {
      }
    }
  }

  void moveToNextProfile() {
    try {
      if (currentProfileIndex.value < profiles.value.length - 1) {
        currentProfileIndex.value++;
      } else {

      }
    } catch (_) {

    }
  }


  Future<void> refreshProfiles() async {
    try {
      isLoading.value = true;
      error.value = null;
    } catch (_) {

      return;
    }

    try {
      final currentUser = loginService.currentUserData;
      if (currentUser == null) {
        throw Exception('Usuario no autenticado');
      }

      final allProfiles = await matchingService.getAllProfiles();
      
      // Filtrar para excluir el perfil del usuario actual
      final filteredProfiles = allProfiles.where((profile) => 
          profile.idUsuario != currentUser['id']).toList();

      try {
        profiles.value = filteredProfiles;
      } catch (_) {

        return;
      }
      

      if (filteredProfiles.isNotEmpty) {
        try {
          await loadMatchingScores(currentUser['id']);
        } catch (matchingError) {
          print('DEBUG MATCHING - Error al cargar scores, continuando sin ellos: $matchingError');

        }
      }
      
    } catch (e) {

      try {
        error.value = 'Error al cargar perfiles: $e';
      } catch (_) {

      }
    } finally {
      try {
        isLoading.value = false;
      } catch (_) {

      }
    }
  }


  Future<void> likeProfile(String profileId) async {
    try {
      likedProfiles.value = {...likedProfiles.value, profileId};
      
      moveToNextProfile();
    } catch (_) {

    }
  }

  Future<void> passProfile(String profileId) async {
    try {
      passedProfiles.value = {...passedProfiles.value, profileId};
      
      moveToNextProfile();
    } catch (_) {

    }
  }

  useEffect(() {
    refreshProfiles();
    return null;
  }, []);

  final availableProfiles = useMemoized(() {
    return profiles.value.where((profile) => 
        !likedProfiles.value.contains(profile.id ?? '') &&
        !passedProfiles.value.contains(profile.id ?? '')
    ).toList();
  }, [profiles.value, likedProfiles.value, passedProfiles.value]);

  return UseMatchingResult(
    profiles: availableProfiles,
    isLoading: isLoading.value,
    error: error.value,
    refreshProfiles: refreshProfiles,
    likeProfile: likeProfile,
    passProfile: passProfile,
    matchingScores: matchingScores.value,
    isLoadingScores: isLoadingScores.value,
  );
}
