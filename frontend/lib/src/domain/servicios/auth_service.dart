import 'dart:convert';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:http/http.dart' as http;
import '../../core/app_config.dart';

class AuthService {
  static final AuthService _instance = AuthService._internal();
  factory AuthService() => _instance;
  AuthService._internal();

  static const _storage = FlutterSecureStorage(
    aOptions: AndroidOptions(
      encryptedSharedPreferences: true,
    ),
  );

  static const String _tokenKey = 'auth_token';
  static const String _userDataKey = 'user_data';
  static const String _credentialsKey = 'user_credentials';
  static const String _isLoggedInKey = 'is_logged_in';

  Map<String, dynamic>? _currentUserData;
  String? _currentToken;

  Map<String, dynamic>? get currentUserData => _currentUserData;
  String? get currentToken => _currentToken;
  bool get isLoggedIn => _currentToken != null && _currentUserData != null;
  String? get currentUsername => _currentUserData?['nombreUsuario'];
  String? get currentEmail => _currentUserData?['email'];
  String? get currentUserId => _currentUserData?['id'];

  Future<void> initialize() async {
    await _loadStoredSession();
  }

  Future<String?> getToken() async {
    if (_currentToken != null) return _currentToken;
    
    try {
      _currentToken = await _storage.read(key: _tokenKey);
      return _currentToken;
    } catch (e) {
      return null;
    }
  }

  Future<Map<String, String>?> getCredentials() async {
    try {
      final credentialsString = await _storage.read(key: _credentialsKey);
      if (credentialsString != null) {
        final credentials = json.decode(credentialsString);
        return {
          'username': credentials['username'],
          'password': credentials['password'],
        };
      }
      return null;
    } catch (e) {
      return null;
    }
  }

  Future<LoginResult> login(String username, String password) async {
    try {
      
      final response = await http.post(
        Uri.parse('${AppConfig.apiBaseUrl}/login'),
        headers: {
          'Content-Type': 'application/json',
        },
        body: json.encode({
          'username': username,
          'password': password,
        }),
      );

      if (response.statusCode == 200) {
        final responseData = json.decode(response.body);
        
        if (responseData['exito'] == true) {
          final token = base64Encode(utf8.encode('$username:$password'));
          
          final userData = {
            'id': username, 
            'nombreUsuario': responseData['nombreUsuario'],
            'email': responseData['email'],
            'rol': responseData['rol'],
          };

          await _saveSession(token, userData, username, password);
          
          return LoginResult(
            success: true,
            message: responseData['mensaje'] ?? 'Login exitoso',
            userData: userData,
          );
        } else {
          return LoginResult(
            success: false,
            message: responseData['mensaje'] ?? 'Error en el login',
          );
        }
      } else if (response.statusCode == 401) {
        final responseData = json.decode(response.body);
        return LoginResult(
          success: false,
          message: responseData['mensaje'] ?? 'Credenciales inválidas',
        );
      } else {
        return LoginResult(
          success: false,
          message: 'Error del servidor: ${response.statusCode}',
        );
      }
    } catch (e) {
      return LoginResult(
        success: false,
        message: 'Error de conexión: $e',
      );
    }
  }

  Future<RegisterResult> register(String username, String email, String password) async {
    try {
      
      final response = await http.post(
        Uri.parse('${AppConfig.apiBaseUrl}/register'),
        headers: {
          'Content-Type': 'application/json',
        },
        body: json.encode({
          'username': username,
          'email': email,
          'password': password,
        }),
      );

      if (response.statusCode == 201 || response.statusCode == 200) {
        final responseData = json.decode(response.body);
        return RegisterResult(
          success: responseData['exito'] ?? true,
          message: responseData['mensaje'] ?? 'Usuario registrado exitosamente',
        );
      } else {
        final errorData = json.decode(response.body);
        return RegisterResult(
          success: false,
          message: errorData['mensaje'] ?? 'Error al registrar usuario',
        );
      }
    } catch (e) {
      return RegisterResult(
        success: false,
        message: 'Error de conexión: $e',
      );
    }
  }

  Future<void> logout() async {
    try {
      _currentUserData = null;
      _currentToken = null;
      
      await _storage.delete(key: _tokenKey);
      await _storage.delete(key: _credentialsKey);
      
      final prefs = await SharedPreferences.getInstance();
      await prefs.remove(_userDataKey);
      await prefs.setBool(_isLoggedInKey, false);
      
    } catch (e) {
      print('DEBUG AUTH - Error al cerrar sesión: $e');
    }
  }

  Future<bool> hasValidSession() async {
    try {
      final token = await getToken();
      final userData = await _getUserData();
      
      return token != null && userData != null;
    } catch (e) {
      return false;
    }
  }

  Future<void> _loadStoredSession() async {
    try {
      final token = await _storage.read(key: _tokenKey);
      final userData = await _getUserData();
      
      if (token != null && userData != null) {
        _currentToken = token;
        _currentUserData = userData;
      }
    } catch (e) {
      print('DEBUG AUTH - Error al cargar sesión: $e');
    }
  }

  Future<Map<String, dynamic>?> _getUserData() async {
    try {
      final prefs = await SharedPreferences.getInstance();
      final userDataString = prefs.getString(_userDataKey);
      
      if (userDataString != null) {
        return json.decode(userDataString);
      }
      return null;
    } catch (e) {
      return null;
    }
  }


  Future<void> _saveSession(String token, Map<String, dynamic> userData, String username, String password) async {
    try {
      await _storage.write(key: _tokenKey, value: token);
      
      await _storage.write(
        key: _credentialsKey,
        value: json.encode({
          'username': username,
          'password': password,
        }),
      );
      
      final prefs = await SharedPreferences.getInstance();
      await prefs.setString(_userDataKey, json.encode(userData));
      await prefs.setBool(_isLoggedInKey, true);

      _currentToken = token;
      _currentUserData = userData;
      
    } catch (e) {
      print('DEBUG AUTH - Error al guardar sesión: $e');
    }
  }

  Future<void> saveUserSession(Map<String, dynamic> userData, String username, String password) async {
    try {

      final token = base64Encode(utf8.encode('$username:$password'));

      await _saveSession(token, userData, username, password);
      
    } catch (e) {
      print('DEBUG AUTH - Error al guardar sesión de usuario: $e');
    }
  }

  Future<bool> checkSavedSession() async {
    try {
      final token = await getToken();
      final userData = await _getUserData();
      
      if (token != null && userData != null) {
        _currentToken = token;
        _currentUserData = userData;
        return true;
      }
      return false;
    } catch (e) {
      return false;
    }
  }

  Future<bool> refreshUserData() async {
    try {
      if (_currentUserData == null) return false;

      final userData = await _getUserData();
      if (userData != null) {
        _currentUserData = userData;
        return true;
      }
      return false;
    } catch (e) {
      return false;
    }
  }
}

class LoginResult {
  final bool success;
  final String message;
  final Map<String, dynamic>? userData;

  LoginResult({
    required this.success,
    required this.message,
    this.userData,
  });
}

class RegisterResult {
  final bool success;
  final String message;

  RegisterResult({
    required this.success,
    required this.message,
  });
}
