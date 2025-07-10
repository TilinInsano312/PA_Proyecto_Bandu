import 'dart:convert';
import '../modelos/profile.dart';
import '../../core/app_config.dart';
import 'base_service.dart';

class MatchingService extends BaseService {
  static final MatchingService _instance = MatchingService._internal();
  factory MatchingService() => _instance;
  MatchingService._internal() : super(baseUrl: AppConfig.apiBaseUrl);

  Future<List<Profile>> getAllProfiles() async {
    try {
      
      final response = await authenticatedGet('/cliente');

      if (response.statusCode == 200) {
        final List<dynamic> jsonList = json.decode(response.body);
        final profiles = jsonList.map((json) => Profile.fromJson(json)).toList();
        return profiles;
      } else {
        throw Exception('Error al obtener perfiles: ${response.statusCode}');
      }
    } catch (e) {
      throw Exception('Error de conexión: $e');
    }
  }

  Future<Map<String, int>> getMatchingByCarrera(String idUsuario) async {
    try {
      
      final response = await authenticatedGet('/emparejamiento/carrera/$idUsuario');

      if (response.statusCode == 200) {
        final Map<String, dynamic> jsonMap = json.decode(response.body);
        final matchingMap = jsonMap.map((key, value) => MapEntry(key, value as int));
        return matchingMap;
      } else if (response.statusCode == 500) {
        return <String, int>{};
      } else {
        throw Exception('Error al obtener matching por carrera: ${response.statusCode}');
      }
    } catch (e) {
      if (e.toString().contains('500')) {
        return <String, int>{};
      }
      throw Exception('Error de conexión: $e');
    }
  }

  Future<Map<String, int>> getMatchingByGenerosMusicales(String idUsuario) async {
    try {
      
      final response = await authenticatedGet('/emparejamiento/generosmusicales/$idUsuario');

      if (response.statusCode == 200) {
        final Map<String, dynamic> jsonMap = json.decode(response.body);
        final matchingMap = jsonMap.map((key, value) => MapEntry(key, value as int));
        return matchingMap;
      } else if (response.statusCode == 500) {
        return <String, int>{};
      } else {
        throw Exception('Error al obtener matching por géneros musicales: ${response.statusCode}');
      }
    } catch (e) {
      if (e.toString().contains('500')) {
        return <String, int>{};
      }
      throw Exception('Error de conexión: $e');
    }
  }

  Future<Map<String, int>> getMatchingByOrientacion(String idUsuario) async {
    try {
      
      final response = await authenticatedGet('/emparejamiento/orientacion/$idUsuario');

      if (response.statusCode == 200) {
        final Map<String, dynamic> jsonMap = json.decode(response.body);
        final matchingMap = jsonMap.map((key, value) => MapEntry(key, value as int));
        return matchingMap;
      } else if (response.statusCode == 500) {
        return <String, int>{};
      } else {
        throw Exception('Error al obtener matching por orientación: ${response.statusCode}');
      }
    } catch (e) {
      if (e.toString().contains('500')) {
        return <String, int>{};
      }
      throw Exception('Error de conexión: $e');
    }
  }

  Future<List<String>> getBlacklistPorCarrera(String idUsuario) async {
    try {
      
      final response = await authenticatedGet('/emparejamiento/descartar/$idUsuario');

      if (response.statusCode == 200) {
        final List<dynamic> jsonList = json.decode(response.body);
        final blacklist = jsonList.cast<String>();
        return blacklist;
      } else if (response.statusCode == 500) {
        return <String>[];
      } else {
        throw Exception('Error al obtener blacklist: ${response.statusCode}');
      }
    } catch (e) {
      if (e.toString().contains('500')) {
        return <String>[];
      }
      throw Exception('Error de conexión: $e');
    }
  }
}
