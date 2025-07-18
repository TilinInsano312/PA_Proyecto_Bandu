import 'package:flutter/material.dart';
import 'src/presentation/screens/welcome_screen.dart';
import 'src/presentation/screens/login_screen.dart';
import 'src/presentation/screens/register_screen.dart';
import 'src/presentation/screens/profile_setup_screen.dart';
import 'src/presentation/screens/main_screen.dart';
import 'src/core/app_config.dart';
import 'src/domain/servicios/auth_service.dart';

void main() async {
  WidgetsFlutterBinding.ensureInitialized();

  await AuthService().initialize();
  
  runApp(const BanduuApp());
}

class BanduuApp extends StatelessWidget {
  const BanduuApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: AppConfig.appName,
      debugShowCheckedModeBanner: false,
      theme: ThemeData(
        colorScheme: ColorScheme.fromSeed(
          seedColor: const Color(0xFF1565C0),
          brightness: Brightness.light,
        ),
        useMaterial3: true,
        
        fontFamily: 'Roboto',
        
        appBarTheme: const AppBarTheme(
          backgroundColor: Color(0xFF1565C0),
          foregroundColor: Colors.white,
          elevation: 0,
          centerTitle: true,
        ),
        
        elevatedButtonTheme: ElevatedButtonThemeData(
          style: ElevatedButton.styleFrom(
            backgroundColor: const Color(0xFF1565C0),
            foregroundColor: Colors.white,
            elevation: 2,
            shape: RoundedRectangleBorder(
              borderRadius: BorderRadius.circular(8),
            ),
          ),
        ),

        inputDecorationTheme: InputDecorationTheme(
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
        ),
      ),
      
      home: const WelcomeScreen(), 
      
      routes: {
        '/welcome': (context) => const WelcomeScreen(),
        '/login': (context) => const LoginScreen(),
        '/register': (context) => const RegisterScreen(),
        '/profile-setup': (context) => const ProfileSetupScreen(),
        '/main': (context) => const MainScreen(),
      },
    );
  }
}
