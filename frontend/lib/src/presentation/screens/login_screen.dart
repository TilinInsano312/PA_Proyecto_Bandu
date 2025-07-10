import 'package:flutter/material.dart';
import 'package:flutter_hooks/flutter_hooks.dart';
import '../hooks/use_login.dart';

class LoginScreen extends HookWidget {
  const LoginScreen({super.key});

  @override
  Widget build(BuildContext context) {
    final loginHook = useLogin();
    
    useEffect(() {
      if (loginHook.isSuccess) {
        WidgetsBinding.instance.addPostFrameCallback((_) {
          ScaffoldMessenger.of(context).showSnackBar(
            const SnackBar(
              content: Text('¡Login exitoso! Bienvenido a Banduu'),
              backgroundColor: Colors.green,
              duration: Duration(seconds: 2),
            ),
          );
          Navigator.pushReplacementNamed(context, '/main');
        });
      }
      return null;
    }, [loginHook.isSuccess]);

    return Scaffold(
      backgroundColor: Colors.grey[50],
      body: SafeArea(
        child: Padding(
          padding: const EdgeInsets.symmetric(horizontal: 24.0),
          child: Column(
            children: [
              const Spacer(flex: 2),
              
              const Text(
                'Iniciar Sesión',
                style: TextStyle(
                  fontSize: 32,
                  fontWeight: FontWeight.bold,
                  color: Color(0xFF1565C0),
                ),
              ),
              
              const Spacer(flex: 3),
              
              _buildLoginForm(context, loginHook),
              
              const Spacer(flex: 4),
            ],
          ),
        ),
      ),
    );
  }

  Widget _buildLoginForm(BuildContext context, LoginHook loginHook) {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        _buildUsernameField(loginHook),
        
        const SizedBox(height: 24),
        
        _buildPasswordField(loginHook),
        
        const SizedBox(height: 16),
        
        _buildErrorMessage(loginHook),
        
        const SizedBox(height: 32),
        
        _buildLoginButton(context, loginHook),
        
        const SizedBox(height: 24),
        
        _buildRegisterLink(context),
      ],
    );
  }

  Widget _buildUsernameField(LoginHook loginHook) {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        const Text(
          'Usuario',
          style: TextStyle(
            fontSize: 16,
            fontWeight: FontWeight.w500,
            color: Color(0xFF1565C0),
          ),
        ),
        const SizedBox(height: 8),
        
        ValueListenableBuilder<LoginState>(
          valueListenable: loginHook.loginState,
          builder: (context, state, child) {
            return TextFormField(
              controller: loginHook.usernameController,
              enabled: !loginHook.isLoading,
              decoration: InputDecoration(
                hintText: 'Ingresa tu usuario',
                hintStyle: TextStyle(
                  color: Colors.grey[400],
                  fontSize: 16,
                ),
                filled: true,
                fillColor: Colors.white,
                border: OutlineInputBorder(
                  borderRadius: BorderRadius.circular(8),
                  borderSide: BorderSide(color: Colors.grey[300]!),
                ),
                enabledBorder: OutlineInputBorder(
                  borderRadius: BorderRadius.circular(8),
                  borderSide: BorderSide(color: Colors.grey[300]!),
                ),
                focusedBorder: OutlineInputBorder(
                  borderRadius: BorderRadius.circular(8),
                  borderSide: const BorderSide(color: Color(0xFF1565C0), width: 2),
                ),
                errorBorder: OutlineInputBorder(
                  borderRadius: BorderRadius.circular(8),
                  borderSide: const BorderSide(color: Colors.red, width: 2),
                ),
                contentPadding: const EdgeInsets.symmetric(
                  horizontal: 16,
                  vertical: 16,
                ),
              ),
              textInputAction: TextInputAction.next,
              autocorrect: false,
            );
          },
        ),
      ],
    );
  }

  Widget _buildPasswordField(LoginHook loginHook) {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        const Text(
          'Contraseña',
          style: TextStyle(
            fontSize: 16,
            fontWeight: FontWeight.w500,
            color: Color(0xFF1565C0),
          ),
        ),
        const SizedBox(height: 8),
        
        ValueListenableBuilder<bool>(
          valueListenable: loginHook.obscurePassword,
          builder: (context, obscurePassword, child) {
            return TextFormField(
              controller: loginHook.passwordController,
              enabled: !loginHook.isLoading,
              obscureText: obscurePassword,
              decoration: InputDecoration(
                hintText: 'Ingresa tu contraseña',
                hintStyle: TextStyle(
                  color: Colors.grey[400],
                  fontSize: 16,
                ),
                filled: true,
                fillColor: Colors.white,
                border: OutlineInputBorder(
                  borderRadius: BorderRadius.circular(8),
                  borderSide: BorderSide(color: Colors.grey[300]!),
                ),
                enabledBorder: OutlineInputBorder(
                  borderRadius: BorderRadius.circular(8),
                  borderSide: BorderSide(color: Colors.grey[300]!),
                ),
                focusedBorder: OutlineInputBorder(
                  borderRadius: BorderRadius.circular(8),
                  borderSide: const BorderSide(color: Color(0xFF1565C0), width: 2),
                ),
                errorBorder: OutlineInputBorder(
                  borderRadius: BorderRadius.circular(8),
                  borderSide: const BorderSide(color: Colors.red, width: 2),
                ),
                contentPadding: const EdgeInsets.symmetric(
                  horizontal: 16,
                  vertical: 16,
                ),
                suffixIcon: IconButton(
                  icon: Icon(
                    obscurePassword ? Icons.visibility : Icons.visibility_off,
                    color: Colors.grey[600],
                  ),
                  onPressed: loginHook.togglePasswordVisibility,
                ),
              ),
              textInputAction: TextInputAction.done,
              onFieldSubmitted: (_) => loginHook.login(),
            );
          },
        ),
      ],
    );
  }

  Widget _buildErrorMessage(LoginHook loginHook) {
    return ValueListenableBuilder<String?>(
      valueListenable: loginHook.errorMessage,
      builder: (context, errorMessage, child) {
        if (errorMessage == null || errorMessage.isEmpty) {
          return const SizedBox.shrink();
        }

        return Container(
          width: double.infinity,
          padding: const EdgeInsets.all(12),
          decoration: BoxDecoration(
            color: Colors.red[50],
            borderRadius: BorderRadius.circular(8),
            border: Border.all(color: Colors.red[300]!),
          ),
          child: Row(
            children: [
              Icon(
                Icons.error_outline,
                color: Colors.red[700],
                size: 20,
              ),
              const SizedBox(width: 8),
              Expanded(
                child: Text(
                  errorMessage,
                  style: TextStyle(
                    color: Colors.red[700],
                    fontSize: 14,
                    fontWeight: FontWeight.w500,
                  ),
                ),
              ),
            ],
          ),
        );
      },
    );
  }

  Widget _buildLoginButton(BuildContext context, LoginHook loginHook) {
    return ValueListenableBuilder<bool>(
      valueListenable: loginHook.isFormValid,
      builder: (context, isFormValid, child) {
        return ValueListenableBuilder<LoginState>(
          valueListenable: loginHook.loginState,
          builder: (context, loginState, child) {
            final isEnabled = isFormValid && !loginHook.isLoading;
            
            return SizedBox(
              width: double.infinity,
              height: 56,
              child: ElevatedButton(
                onPressed: isEnabled ? loginHook.login : null,
                style: ElevatedButton.styleFrom(
                  backgroundColor: const Color(0xFF1565C0),
                  foregroundColor: Colors.white,
                  disabledBackgroundColor: Colors.grey[300],
                  disabledForegroundColor: Colors.grey[600],
                  elevation: isEnabled ? 2 : 0,
                  shadowColor: const Color(0xFF1565C0).withOpacity(0.3),
                  shape: RoundedRectangleBorder(
                    borderRadius: BorderRadius.circular(8),
                  ),
                ),
                child: loginHook.isLoading
                    ? const SizedBox(
                        width: 24,
                        height: 24,
                        child: CircularProgressIndicator(
                          strokeWidth: 2,
                          valueColor: AlwaysStoppedAnimation<Color>(Colors.white),
                        ),
                      )
                    : const Text(
                        'Iniciar Sesión',
                        style: TextStyle(
                          fontSize: 18,
                          fontWeight: FontWeight.w600,
                        ),
                      ),
              ),
            );
          },
        );
      },
    );
  }

  Widget _buildRegisterLink(BuildContext context) {
    return Row(
      mainAxisAlignment: MainAxisAlignment.center,
      children: [
        const Text(
          '¿No tienes cuenta? ',
          style: TextStyle(
            color: Colors.grey,
            fontSize: 16,
          ),
        ),
        TextButton(
          onPressed: () => Navigator.pushNamed(context, '/register'),
          child: const Text(
            'Regístrate',
            style: TextStyle(
              color: Color(0xFF1565C0),
              fontSize: 16,
              fontWeight: FontWeight.w600,
            ),
          ),
        ),
      ],
    );
  }
}

class LoginTestWidget extends HookWidget {
  const LoginTestWidget({super.key});

  @override
  Widget build(BuildContext context) {
    final loginHook = useLogin();
    
    return Card(
      margin: const EdgeInsets.all(16),
      child: Padding(
        padding: const EdgeInsets.all(16),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            const Text(
              'Login Test - Banduu Backend',
              style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
            ),
            const SizedBox(height: 16),
            
            // Botones de prueba rápida
            Wrap(
              spacing: 8,
              children: [
                ElevatedButton(
                  onPressed: () {
                    loginHook.usernameController.text = 'testUser';
                    loginHook.passwordController.text = 'testPass';
                  },
                  child: const Text('Datos Test'),
                ),
                ElevatedButton(
                  onPressed: loginHook.clearForm,
                  child: const Text('Limpiar'),
                ),
              ],
            ),
            
            const SizedBox(height: 16),
            
            ValueListenableBuilder<LoginState>(
              valueListenable: loginHook.loginState,
              builder: (context, state, child) {
                return Container(
                  padding: const EdgeInsets.all(12),
                  decoration: BoxDecoration(
                    color: _getStateColor(state).withOpacity(0.1),
                    borderRadius: BorderRadius.circular(8),
                    border: Border.all(color: _getStateColor(state)),
                  ),
                  child: Row(
                    children: [
                      Icon(_getStateIcon(state), color: _getStateColor(state)),
                      const SizedBox(width: 8),
                      Text(
                        'Estado: ${_getStateText(state)}',
                        style: TextStyle(
                          color: _getStateColor(state),
                          fontWeight: FontWeight.w500,
                        ),
                      ),
                    ],
                  ),
                );
              },
            ),
          ],
        ),
      ),
    );
  }

  Color _getStateColor(LoginState state) {
    switch (state) {
      case LoginState.idle:
        return Colors.grey;
      case LoginState.loading:
        return Colors.blue;
      case LoginState.success:
        return Colors.green;
      case LoginState.error:
        return Colors.red;
      case LoginState.validating:
        return Colors.orange;
    }
  }

  IconData _getStateIcon(LoginState state) {
    switch (state) {
      case LoginState.idle:
        return Icons.radio_button_unchecked;
      case LoginState.loading:
        return Icons.refresh;
      case LoginState.success:
        return Icons.check_circle;
      case LoginState.error:
        return Icons.error;
      case LoginState.validating:
        return Icons.search;
    }
  }

  String _getStateText(LoginState state) {
    switch (state) {
      case LoginState.idle:
        return 'Inactivo';
      case LoginState.loading:
        return 'Cargando...';
      case LoginState.success:
        return 'Éxito';
      case LoginState.error:
        return 'Error';
      case LoginState.validating:
        return 'Validando...';
    }
  }
}
