import 'dart:convert';
import 'package:http/http.dart' as http;
import '../../core/app_config.dart';
import '../modelos/login_request.dart';
import '../modelos/login_response.dart';
import '../modelos/register_request.dart';
import '../modelos/register_response.dart';
import 'auth_service.dart';

class LoginService {
  static final LoginService _instance = LoginService._internal();
  factory LoginService() => _instance;
  LoginService._internal();

  final AuthService _authService = AuthService();

  Map<String, dynamic>? get currentUserData => _authService.currentUserData;
  bool get isLoggedIn => _authService.isLoggedIn;
  String? get currentUsername => _authService.currentUsername;
  String? get currentEmail => _authService.currentEmail;
  String? get currentUserId => _authService.currentUserId;

  Future<LoginResponse> login(LoginRequest request) async {
    try {

      final response = await http.post(
        Uri.parse('${AppConfig.apiBaseUrl}/login'),
        headers: {
          'Content-Type': 'application/json',
        },
        body: json.encode(request.toJson()),
      );

      if (response.statusCode == 200) {
        final responseData = json.decode(response.body);
        
        if (responseData['exito'] == true) {
          final userId = await getUserIdByUsername(request.nombreUsuario);

          final userData = {
            'id': userId,
            'nombreUsuario': responseData['nombreUsuario'],
            'email': responseData['email'],
            'rol': responseData['rol'],
          };

          await _authService.saveUserSession(
            userData,
            request.nombreUsuario,
            request.contrasena,
          );
          
          return LoginResponse(
            success: true,
            message: responseData['mensaje'] ?? 'Login exitoso. ¡Bienvenido a Banduu!',
            userData: userData,
          );
        } else {
          return LoginResponse(
            success: false,
            message: responseData['mensaje'] ?? 'Error en el login',
          );
        }
      } else if (response.statusCode == 401) {
        final responseData = json.decode(response.body);
        return LoginResponse(
          success: false,
          message: responseData['mensaje'] ?? 'Usuario o contraseña incorrectos',
        );
      } else {
        return const LoginResponse(
          success: false,
          message: 'Error al conectar con el servidor. Verifique su conexión.',
        );
      }

    } catch (e) {

      String errorMessage;
      if (e.toString().contains('SocketException') || 
          e.toString().contains('NetworkException') ||
          e.toString().contains('Failed host lookup')) {
        errorMessage = 'No se pudo conectar al servidor. Verifique su conexión a internet y que el servidor esté funcionando.';
      } else if (e.toString().contains('TimeoutException')) {
        errorMessage = 'La conexión tardó demasiado. Inténtelo nuevamente.';
      } else if (e.toString().contains('FormatException')) {
        errorMessage = 'Error en el formato de respuesta del servidor.';
      } else {
        errorMessage = 'Error inesperado: ${e.toString()}';
      }
      
      return LoginResponse(
        success: false,
        message: errorMessage,
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
        body: json.encode({
          'username': request.nombreUsuario,
          'email': request.email,
          'password': request.contrasena,
        }),
      );

      if (response.statusCode == 201 || response.statusCode == 200) {
        final responseData = json.decode(response.body);
        return RegisterResponse(
          exito: responseData['exito'] ?? true,
          mensaje: responseData['mensaje'] ?? 'Usuario registrado exitosamente',
        );
      } else {
        final errorData = json.decode(response.body);
        return RegisterResponse(
          exito: false,
          mensaje: errorData['mensaje'] ?? 'Error al registrar usuario',
        );
      }
    } catch (e) {
      return RegisterResponse(
        exito: false,
        mensaje: 'Error de conexión: $e. Verifique su conexión a internet.',
      );
    }
  }

  Future<bool> checkUsernameExists(String nombreUsuario) async {
    try {
      return false;
    } catch (e) {
      return false;
    }
  }

  Future<void> logout() async {
    await _authService.logout();
  }

  Future<bool> checkSavedSession() async {
    return await _authService.checkSavedSession();
  }

  Future<bool> refreshUserData() async {
    return await _authService.refreshUserData();
  }

  Future<String?> getUserIdByUsername(String nombreUsuario) async {
    try {
      return nombreUsuario;
    } catch (e) {
      return null;
    }
  }
}
