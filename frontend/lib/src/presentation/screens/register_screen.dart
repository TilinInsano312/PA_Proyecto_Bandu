import 'package:flutter/material.dart';
import 'package:flutter_hooks/flutter_hooks.dart';
import '../hooks/use_register.dart';
import 'profile_setup_screen.dart';

class RegisterScreen extends HookWidget {
  const RegisterScreen({super.key});

  @override
  Widget build(BuildContext context) {

    final registerHook = useRegister();
    
    useEffect(() {
      if (registerHook.isSuccess) {
        WidgetsBinding.instance.addPostFrameCallback((_) {
          ScaffoldMessenger.of(context).showSnackBar(
            const SnackBar(
              content: Text('¡Registro validado! Ahora crea tu perfil'),
              backgroundColor: Colors.green,
              duration: Duration(seconds: 2),
            ),
          );
          
          Navigator.pushReplacement(
            context,
            MaterialPageRoute(
              builder: (context) => ProfileSetupScreen(
                temporaryUserData: registerHook.temporaryUserData,
              ),
            ),
          );
        });
      }
      return null;
    }, [registerHook.isSuccess]);

    return Scaffold(
      backgroundColor: Colors.grey[50],
      appBar: AppBar(
        title: const Text('Crear Cuenta'),
        backgroundColor: Colors.transparent,
        elevation: 0,
        foregroundColor: const Color(0xFF1565C0),
        iconTheme: const IconThemeData(
          color: Color(0xFF1565C0),
          size: 28,
        ),
      ),
      body: SafeArea(
        child: Padding(
          padding: const EdgeInsets.all(24.0),
          child: SingleChildScrollView(
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.stretch,
              children: [
                const SizedBox(height: 10),
                
                Center(
                  child: Container(
                    width: 100,
                    height: 100,
                    decoration: BoxDecoration(
                      borderRadius: BorderRadius.circular(15),
                      boxShadow: [
                        BoxShadow(
                          color: Colors.black.withOpacity(0.1),
                          blurRadius: 8,
                          offset: const Offset(0, 3),
                        ),
                      ],
                    ),
                    child: ClipRRect(
                      borderRadius: BorderRadius.circular(15),
                      child: Image.asset(
                        'assets/imagenes/logo_banduu.png',
                        fit: BoxFit.cover,
                        errorBuilder: (context, error, stackTrace) {
                          // Debug: Imprimir error
                          print('Error cargando logo: $error');
                          return Container(
                            decoration: BoxDecoration(
                              color: const Color(0xFF1565C0),
                              borderRadius: BorderRadius.circular(15),
                            ),
                            child: const Icon(
                              Icons.music_note,
                              size: 50,
                              color: Colors.white,
                            ),
                          );
                        },
                      ),
                    ),
                  ),
                ),
                
                const SizedBox(height: 24),
                
                const Text(
                  'Únete a Banduu',
                  style: TextStyle(
                    fontSize: 32,
                    fontWeight: FontWeight.bold,
                    color: Colors.black87,
                  ),
                  textAlign: TextAlign.center,
                ),
                
                const SizedBox(height: 40),
                
                TextFormField(
                  controller: registerHook.usernameController,
                  decoration: InputDecoration(
                    labelText: 'Nombre de Usuario',
                    hintText: 'Elige tu nombre de usuario',
                    border: OutlineInputBorder(
                      borderRadius: BorderRadius.circular(12),
                    ),
                    prefixIcon: const Icon(Icons.person_outline),
                  ),
                  enabled: !registerHook.isLoading,
                ),
                
                const SizedBox(height: 16),
                
                TextFormField(
                  controller: registerHook.emailController,
                  keyboardType: TextInputType.emailAddress,
                  decoration: InputDecoration(
                    labelText: 'Correo Electrónico',
                    hintText: 'tu.email@ejemplo.com',
                    border: OutlineInputBorder(
                      borderRadius: BorderRadius.circular(12),
                    ),
                    prefixIcon: const Icon(Icons.email_outlined),
                  ),
                  enabled: !registerHook.isLoading,
                ),
                
                const SizedBox(height: 16),
                
                ValueListenableBuilder<bool>(
                  valueListenable: registerHook.obscurePassword,
                  builder: (context, obscurePassword, _) {
                    return TextFormField(
                      controller: registerHook.passwordController,
                      obscureText: obscurePassword,
                      decoration: InputDecoration(
                        labelText: 'Contraseña',
                        hintText: 'Mínimo 6 caracteres',
                        border: OutlineInputBorder(
                          borderRadius: BorderRadius.circular(12),
                        ),
                        prefixIcon: const Icon(Icons.lock_outline),
                        suffixIcon: IconButton(
                          onPressed: registerHook.togglePasswordVisibility,
                          icon: Icon(
                            obscurePassword ? Icons.visibility : Icons.visibility_off,
                          ),
                        ),
                      ),
                      enabled: !registerHook.isLoading,
                    );
                  },
                ),
                
                const SizedBox(height: 16),
                
                ValueListenableBuilder<bool>(
                  valueListenable: registerHook.obscureConfirmPassword,
                  builder: (context, obscureConfirmPassword, _) {
                    return TextFormField(
                      controller: registerHook.confirmPasswordController,
                      obscureText: obscureConfirmPassword,
                      decoration: InputDecoration(
                        labelText: 'Confirmar Contraseña',
                        hintText: 'Repite tu contraseña',
                        border: OutlineInputBorder(
                          borderRadius: BorderRadius.circular(12),
                        ),
                        prefixIcon: const Icon(Icons.lock_outline),
                        suffixIcon: IconButton(
                          onPressed: registerHook.toggleConfirmPasswordVisibility,
                          icon: Icon(
                            obscureConfirmPassword ? Icons.visibility : Icons.visibility_off,
                          ),
                        ),
                      ),
                      enabled: !registerHook.isLoading,
                    );
                  },
                ),
                
                const SizedBox(height: 24),
                
                ValueListenableBuilder<String?>(
                  valueListenable: registerHook.errorMessage,
                  builder: (context, errorMessage, _) {
                    if (errorMessage == null) return const SizedBox.shrink();
                    
                    return Container(
                      padding: const EdgeInsets.all(12),
                      margin: const EdgeInsets.only(bottom: 16),
                      decoration: BoxDecoration(
                        color: Colors.red.shade50,
                        border: Border.all(color: Colors.red.shade200),
                        borderRadius: BorderRadius.circular(8),
                      ),
                      child: Row(
                        children: [
                          Icon(Icons.error_outline, color: Colors.red.shade600, size: 20),
                          const SizedBox(width: 8),
                          Expanded(
                            child: Text(
                              errorMessage,
                              style: TextStyle(
                                color: Colors.red.shade700,
                                fontSize: 14,
                              ),
                            ),
                          ),
                        ],
                      ),
                    );
                  },
                ),
                
                ValueListenableBuilder<bool>(
                  valueListenable: registerHook.isFormValid,
                  builder: (context, isFormValid, _) {
                    return SizedBox(
                      height: 56,
                      child: ElevatedButton(
                        onPressed: (isFormValid && !registerHook.isLoading) 
                            ? registerHook.register 
                            : null,
                        style: ElevatedButton.styleFrom(
                          backgroundColor: const Color(0xFF1E88E5),
                          foregroundColor: Colors.white,
                          shape: RoundedRectangleBorder(
                            borderRadius: BorderRadius.circular(12),
                          ),
                          elevation: 2,
                        ),
                        child: registerHook.isLoading
                            ? const SizedBox(
                                height: 20,
                                width: 20,
                                child: CircularProgressIndicator(
                                  strokeWidth: 2,
                                  valueColor: AlwaysStoppedAnimation<Color>(Colors.white),
                                ),
                              )
                            : const Text(
                                'Crear Cuenta',
                                style: TextStyle(
                                  fontSize: 18,
                                  fontWeight: FontWeight.w600,
                                ),
                              ),
                      ),
                    );
                  },
                ),
                
                const SizedBox(height: 24),
                
                Row(
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: [
                    const Text(
                      '¿Ya tienes cuenta? ',
                      style: TextStyle(
                        color: Colors.grey,
                        fontSize: 16,
                      ),
                    ),
                    TextButton(
                      onPressed: () {
                        Navigator.pushNamed(context, '/login');
                      },
                      child: const Text(
                        'Iniciar Sesión',
                        style: TextStyle(
                          color: Color(0xFF1565C0),
                          fontSize: 16,
                          fontWeight: FontWeight.w600,
                        ),
                      ),
                    ),
                  ],
                ),
              ],
            ),
          ),
        ),
      ),
    );
  }
}
