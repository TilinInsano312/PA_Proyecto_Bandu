import 'package:flutter/material.dart';
import 'package:flutter_hooks/flutter_hooks.dart';
import '../../domain/servicios/login_service.dart';
import '../../domain/modelos/register_request.dart';

enum RegisterState {
  idle,           
  loading,       
  success,        
  error,          
  validating,    
}

class RegisterHook {
  final TextEditingController usernameController;
  final TextEditingController emailController;
  final TextEditingController passwordController;
  final TextEditingController confirmPasswordController;
  final ValueNotifier<bool> obscurePassword;
  final ValueNotifier<bool> obscureConfirmPassword;
  final ValueNotifier<RegisterState> registerState;
  final ValueNotifier<String?> errorMessage;
  final ValueNotifier<bool> isFormValid;
  final LoginService _loginService;
  
  RegisterRequest? _temporaryUserData;
  String? _temporaryUserId;

  RegisterHook({
    required this.usernameController,
    required this.emailController,
    required this.passwordController,
    required this.confirmPasswordController,
    required this.obscurePassword,
    required this.obscureConfirmPassword,
    required this.registerState,
    required this.errorMessage,
    required this.isFormValid,
  }) : _loginService = LoginService();

  bool get isLoading => registerState.value == RegisterState.loading;
  bool get hasError => registerState.value == RegisterState.error;
  bool get isSuccess => registerState.value == RegisterState.success;
  
  RegisterRequest? get temporaryUserData => _temporaryUserData;
  String? get temporaryUserId => _temporaryUserId;

  void togglePasswordVisibility() {
    obscurePassword.value = !obscurePassword.value;
  }

  void toggleConfirmPasswordVisibility() {
    obscureConfirmPassword.value = !obscureConfirmPassword.value;
  }

  void _setError(String message) {
    registerState.value = RegisterState.error;
    errorMessage.value = message;
  }

  Future<void> register() async {
    if (!isFormValid.value) {
      _setError('Por favor complete todos los campos correctamente');
      return;
    }

    registerState.value = RegisterState.loading;
    errorMessage.value = null;

    try {
      final registerRequest = RegisterRequest(
        nombreUsuario: usernameController.text.trim(),
        email: emailController.text.trim(),
        contrasena: passwordController.text,
      );

      _temporaryUserData = registerRequest;

      final userExists = await _loginService.checkUsernameExists(registerRequest.nombreUsuario);
      
      if (userExists) {
        _setError('El nombre de usuario ya existe. Por favor elige otro.');
        return;
      }

      registerState.value = RegisterState.success;
      errorMessage.value = null;

    } catch (e) {
      _setError('Error inesperado: $e');
    }
  }

  void validateForm() {
    final username = usernameController.text.trim();
    final email = emailController.text.trim();
    final password = passwordController.text;
    final confirmPassword = confirmPasswordController.text;

    final isUsernameValid = username.isNotEmpty && username.length >= 3;
    final isEmailValid = email.isNotEmpty && email.contains('@') && email.contains('.');
    final isPasswordValid = password.isNotEmpty && password.length >= 6;
    final isConfirmPasswordValid = confirmPassword == password;

    isFormValid.value = isUsernameValid && isEmailValid && isPasswordValid && isConfirmPasswordValid;

    if (isFormValid.value && hasError) {
      errorMessage.value = null;
      registerState.value = RegisterState.idle;
    }
  }

  void clearForm() {
    usernameController.clear();
    emailController.clear();
    passwordController.clear();
    confirmPasswordController.clear();
    registerState.value = RegisterState.idle;
    errorMessage.value = null;
    isFormValid.value = false;
  }

  void dispose() {
    usernameController.dispose();
    emailController.dispose();
    passwordController.dispose();
    confirmPasswordController.dispose();
  }
}

RegisterHook useRegister() {
  final usernameController = useTextEditingController();
  final emailController = useTextEditingController();
  final passwordController = useTextEditingController();
  final confirmPasswordController = useTextEditingController();
  
  final obscurePassword = useState(true);
  final obscureConfirmPassword = useState(true);
  final registerState = useState(RegisterState.idle);
  final errorMessage = useState<String?>(null);
  final isFormValid = useState(false);

  final registerHook = useMemoized(
    () => RegisterHook(
      usernameController: usernameController,
      emailController: emailController,
      passwordController: passwordController,
      confirmPasswordController: confirmPasswordController,
      obscurePassword: obscurePassword,
      obscureConfirmPassword: obscureConfirmPassword,
      registerState: registerState,
      errorMessage: errorMessage,
      isFormValid: isFormValid,
    ),
    [usernameController, emailController, passwordController, confirmPasswordController],
  );

  useEffect(() {
    void listener() => registerHook.validateForm();
    
    usernameController.addListener(listener);
    emailController.addListener(listener);
    passwordController.addListener(listener);
    confirmPasswordController.addListener(listener);

    return () {
      usernameController.removeListener(listener);
      emailController.removeListener(listener);
      passwordController.removeListener(listener);
      confirmPasswordController.removeListener(listener);
    };
  }, [usernameController, emailController, passwordController, confirmPasswordController]);

  return registerHook;
}
