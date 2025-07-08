import 'dart:convert';
import 'package:http/http.dart' as http;
import 'package:shared_preferences/shared_preferences.dart';
import '../../core/app_config.dart';
import '../modelos/login_request.dart';
import '../modelos/login_response.dart';
import '../modelos/register_request.dart';
import '../modelos/register_response.dart';


class LoginService {
  static final LoginService _instance = LoginService._internal();
  factory LoginService() => _instance;
  LoginService._internal();

  static const String _userDataKey = 'user_data';
  static const String _isLoggedInKey = 'is_logged_in';

  Map<String, dynamic>? _currentUserData;
  
  Map<String, dynamic>? get currentUserData => _currentUserData;
  
  bool get isLoggedIn => _currentUserData != null;
  
  String? get currentUsername => _currentUserData?['nombreUsuario'];
  
  String? get currentEmail => _currentUserData?['email'];
  
  String? get currentRole => _currentUserData?['rol'];

  Future<LoginResponse> login(LoginRequest request) async {
    try {
      final response = await http.post(
        Uri.parse('${AppConfig.apiBaseUrl}/login'),
        headers: {
          'Content-Type': 'application/json',
        },
        body: json.encode(request.toJson()),
      );

      final responseData = json.decode(response.body) as Map<String, dynamic>;

      if (response.statusCode == 200) {
        final loginResponse = LoginResponse.fromJson(responseData);
        
        if (loginResponse.exito) {
        
          _currentUserData = {
            'nombreUsuario': loginResponse.nombreUsuario,
            'email': loginResponse.email,
            'rol': loginResponse.rol,
          };
          await _saveUserDataLocally(_currentUserData!);
        }
        
        return loginResponse;
      } else {
        return LoginResponse.fromJson(responseData);
      }

    } catch (e) {
      return LoginResponse(
        exito: false,
        mensaje: 'Error de conexión: $e. Verifique su conexión a internet.',
      );
    }
  }

  Future<RegisterResponse> register(RegisterRequest request) async {
    try {
      final response = await http.post(
        Uri.parse('${AppConfig.apiBaseUrl}/register'),
        headers: {
          'Content-Type': 'application/json',
        },
        body: json.encode(request.toJson()),
      );

      final responseData = json.decode(response.body) as Map<String, dynamic>;
      return RegisterResponse.fromJson(responseData);

    } catch (e) {
      return RegisterResponse(
        exito: false,
        mensaje: 'Error de conexión: $e. Verifique su conexión a internet.',
      );
    }
  }

  Future<void> logout() async {
    _currentUserData = null;
    final prefs = await SharedPreferences.getInstance();
    await prefs.remove(_userDataKey);
    await prefs.setBool(_isLoggedInKey, false);
  }

  Future<bool> checkSavedSession() async {
    try {
      final prefs = await SharedPreferences.getInstance();
      final isLoggedIn = prefs.getBool(_isLoggedInKey) ?? false;
      
      if (isLoggedIn) {
        final userDataString = prefs.getString(_userDataKey);
        if (userDataString != null) {
          _currentUserData = json.decode(userDataString);
          return true;
        }
      }
      return false;
    } catch (e) {
      return false;
    }
  }

  Future<void> _saveUserDataLocally(Map<String, dynamic> userData) async {
    try {
      final prefs = await SharedPreferences.getInstance();
      await prefs.setString(_userDataKey, json.encode(userData));
      await prefs.setBool(_isLoggedInKey, true);
    } catch (e) {
      print('Error guardando datos localmente: $e');
    }
  }
}
