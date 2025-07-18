import 'package:flutter/material.dart';
import 'package:flutter_hooks/flutter_hooks.dart';
import '../../domain/servicios/login_service.dart';
import '../../domain/modelos/login_request.dart';

/// Estados posibles del proceso de login
enum LoginState {
  idle,           // Estado inicial
  loading,        // Procesando login
  success,        // Login exitoso
  error,          // Error en login
  validating,     // Validando formulario
}

class LoginHook {
  final TextEditingController usernameController;
  final TextEditingController passwordController;
  final ValueNotifier<bool> obscurePassword;
  final ValueNotifier<LoginState> loginState;
  final ValueNotifier<String?> errorMessage;
  final ValueNotifier<bool> isFormValid;
  final LoginService _loginService;

  LoginHook({
    required this.usernameController,
    required this.passwordController,
    required this.obscurePassword,
    required this.loginState,
    required this.errorMessage,
    required this.isFormValid,
    required LoginService loginService,
  }) : _loginService = loginService;

  bool get isLoading => loginState.value == LoginState.loading;
  
  bool get hasError => loginState.value == LoginState.error;
  
  bool get isSuccess => loginState.value == LoginState.success;

  void togglePasswordVisibility() {
    obscurePassword.value = !obscurePassword.value;
  }

  Future<void> login() async {
    if (!isFormValid.value) {
      _setError('Por favor complete todos los campos correctamente');
      return;
    }

    loginState.value = LoginState.loading;
    errorMessage.value = null;

    try {
      final loginRequest = LoginRequest(
        nombreUsuario: usernameController.text.trim(),
        contrasena: passwordController.text,
      );

      final response = await _loginService.login(loginRequest).timeout(
        const Duration(seconds: 30),
        onTimeout: () {
          throw Exception('La conexión tardó demasiado. Inténtelo nuevamente.');
        },
      );

      if (response.success) {
        loginState.value = LoginState.success;
        errorMessage.value = null;
      } else {
        _setError(response.message);
      }
    } catch (e) {
      _setError('Error inesperado: $e');
    }
  }

  void validateForm() {
    final username = usernameController.text.trim();
    final password = passwordController.text;

    final isUsernameValid = username.isNotEmpty && username.length >= 3;
    final isPasswordValid = password.isNotEmpty && password.length >= 4;

    isFormValid.value = isUsernameValid && isPasswordValid;

    if (isFormValid.value && hasError) {
      errorMessage.value = null;
      loginState.value = LoginState.idle;
    }
  }

  Future<void> checkUsernameExists() async {
    final username = usernameController.text.trim();
    if (username.isEmpty || username.length < 3) return;

    loginState.value = LoginState.validating;
    
    try {
      final exists = await _loginService.checkUsernameExists(username);
      if (!exists) {
        _setError('El usuario "$username" no existe');
      } else {
        loginState.value = LoginState.idle;
        errorMessage.value = null;
      }
    } catch (e) {
      loginState.value = LoginState.idle;
    }
  }

  void clearForm() {
    usernameController.clear();
    passwordController.clear();
    loginState.value = LoginState.idle;
    errorMessage.value = null;
    isFormValid.value = false;
  }

  void _setError(String message) {
    loginState.value = LoginState.error;
    errorMessage.value = message;
  }
}

LoginHook useLogin() {
  final usernameController = useTextEditingController();
  final passwordController = useTextEditingController();
  
  final obscurePassword = useState(true);
  final loginState = useState<LoginState>(LoginState.idle);
  final errorMessage = useState<String?>(null);
  final isFormValid = useState(false);
  
  final loginService = useMemoized(() => LoginService(), []);
  
  final loginHook = useMemoized(
    () => LoginHook(
      usernameController: usernameController,
      passwordController: passwordController,
      obscurePassword: obscurePassword,
      loginState: loginState,
      errorMessage: errorMessage,
      isFormValid: isFormValid,
      loginService: loginService,
    ),
    [usernameController, passwordController],
  );

  useEffect(() {
    void listener() => loginHook.validateForm();
    
    usernameController.addListener(listener);
    passwordController.addListener(listener);
    
    return () {
      usernameController.removeListener(listener);
      passwordController.removeListener(listener);
    };
  }, [usernameController, passwordController]);

  useEffect(() {
    return () {
    };
  }, []);

  return loginHook;
}
