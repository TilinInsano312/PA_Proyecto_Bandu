import 'dart:convert';
import '../../core/app_config.dart';
import '../modelos/profile_request.dart';
import '../modelos/profile_response.dart';
import 'base_service.dart';

class ProfileService extends BaseService {
  static final ProfileService _instance = ProfileService._internal();
  factory ProfileService() => _instance;
  ProfileService._internal() : super(baseUrl: AppConfig.apiBaseUrl);

  Future<ProfileResponse> createProfile(ProfileRequest request) async {
    try {
      final requestBody = request.toJson();
      
      final response = await authenticatedPost('/cliente', requestBody);

      if (response.statusCode == 201 || response.statusCode == 200) {
        final responseData = json.decode(response.body);
        return ProfileResponse(
          exito: true,
          mensaje: 'Perfil creado exitosamente',
          perfilData: responseData,
        );
      } else {
        final errorData = response.body.isNotEmpty ? json.decode(response.body) : {};
        return ProfileResponse(
          exito: false,
          mensaje: errorData['mensaje'] ?? 'Error al crear perfil. Status: ${response.statusCode}',
        );
      }
    } catch (e) {
      return ProfileResponse(
        exito: false,
        mensaje: 'Error de conexión: $e. Verifique su conexión a internet.',
      );
    }
  }

  Future<ProfileResponse> getProfileByUserId(String userId) async {
    try {
      final response = await authenticatedGet('/cliente/usuario/$userId');

      if (response.statusCode == 200) {
        final responseData = json.decode(response.body);
        return ProfileResponse(
          exito: true,
          mensaje: 'Perfil encontrado',
          perfilData: responseData,
        );
      } else if (response.statusCode == 404) {
        return const ProfileResponse(
          exito: false,
          mensaje: 'El usuario no tiene perfil creado',
        );
      } else {
        return const ProfileResponse(
          exito: false,
          mensaje: 'Error al consultar perfil',
        );
      }
    } catch (e) {
      return ProfileResponse(
        exito: false,
        mensaje: 'Error de conexión: $e',
      );
    }
  }
  
  Future<List<Map<String, dynamic>>> getAllProfiles() async {
    try {
      final response = await authenticatedGet('/cliente');

      if (response.statusCode == 200) {
        final data = json.decode(response.body) as List;
        return data.cast<Map<String, dynamic>>();
      } else {
        return [];
      }
    } catch (e) {
      return [];
    }
  }
}
