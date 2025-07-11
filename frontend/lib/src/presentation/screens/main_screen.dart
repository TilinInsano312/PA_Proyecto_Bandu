import 'package:flutter/material.dart';
import 'package:flutter_hooks/flutter_hooks.dart';
import '../hooks/use_matching.dart';
import '../widgets/profile_image_widget.dart';
import '../widgets/music_display_widget.dart';
import '../../domain/modelos/profile.dart';

class MainScreen extends HookWidget {
  const MainScreen({super.key});

  @override
  Widget build(BuildContext context) {
    final matchingHook = useMatching();
    final currentProfileIndex = useState<int>(0);
    final swipeAnimation = useAnimationController(
      duration: const Duration(milliseconds: 300),
    );
    final likeAnimation = useAnimationController(
      duration: const Duration(milliseconds: 500),
    );
    final dislikeAnimation = useAnimationController(
      duration: const Duration(milliseconds: 500),
    );

    // Animaciones de deslizamiento
    final swipeOffset = useAnimation(
      Tween<Offset>(begin: Offset.zero, end: const Offset(1.5, 0))
          .animate(CurvedAnimation(
        parent: swipeAnimation,
        curve: Curves.easeInOut,
      )),
    );

    // Animaciones de like/dislike
    final likeScale = useAnimation(
      Tween<double>(begin: 0.0, end: 1.0)
          .animate(CurvedAnimation(
        parent: likeAnimation,
        curve: Curves.elasticOut,
      )),
    );

    final dislikeScale = useAnimation(
      Tween<double>(begin: 0.0, end: 1.0)
          .animate(CurvedAnimation(
        parent: dislikeAnimation,
        curve: Curves.elasticOut,
      )),
    );

    // Función para mover al siguiente perfil
    void moveToNextProfile() {
      if (matchingHook.profiles.isNotEmpty) {
        if (currentProfileIndex.value < matchingHook.profiles.length - 1) {
          currentProfileIndex.value++;
        } else {
          // Si llegamos al final, recargar perfiles
          currentProfileIndex.value = 0;
          matchingHook.refreshProfiles();
        }
      }
    }

    // Función para manejar like
    Future<void> handleLike() async {
      if (matchingHook.profiles.isNotEmpty && 
          currentProfileIndex.value < matchingHook.profiles.length) {
        final profile = matchingHook.profiles[currentProfileIndex.value];
        
        // Animación de like
        likeAnimation.forward().then((_) {
          likeAnimation.reset();
        });
        
        // Animación de deslizamiento hacia la derecha
        swipeAnimation.forward().then((_) {
          swipeAnimation.reset();
          moveToNextProfile();
        });
        
        // Llamar al hook de matching
        await matchingHook.likeProfile(profile.id ?? '');
      }
    }

    // Función para manejar dislike
    Future<void> handleDislike() async {
      if (matchingHook.profiles.isNotEmpty && 
          currentProfileIndex.value < matchingHook.profiles.length) {
        final profile = matchingHook.profiles[currentProfileIndex.value];
        
        // Animación de dislike
        dislikeAnimation.forward().then((_) {
          dislikeAnimation.reset();
        });
        
        // Animación de deslizamiento hacia la izquierda
        swipeAnimation.forward().then((_) {
          swipeAnimation.reset();
          moveToNextProfile();
        });
        
        // Llamar al hook de matching
        await matchingHook.passProfile(profile.id ?? '');
      }
    }

    // Validar índice actual
    if (currentProfileIndex.value >= matchingHook.profiles.length) {
      currentProfileIndex.value = 0;
    }

    // Si está cargando
    if (matchingHook.isLoading) {
      return Scaffold(
        backgroundColor: const Color(0xFF1565C0),
        body: const Center(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              CircularProgressIndicator(color: Colors.white),
              SizedBox(height: 16),
              Text(
                'Cargando perfiles...',
                style: TextStyle(color: Colors.white, fontSize: 16),
              ),
            ],
          ),
        ),
      );
    }

    // Si hay error
    if (matchingHook.error != null) {
      return Scaffold(
        backgroundColor: const Color(0xFF1565C0),
        appBar: AppBar(
          title: const Text('Banduu'),
          backgroundColor: const Color(0xFF1565C0),
          foregroundColor: Colors.white,
          elevation: 0,
        ),
        body: Center(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              const Icon(
                Icons.error_outline,
                size: 80,
                color: Colors.white,
              ),
              const SizedBox(height: 16),
              Text(
                'Error: ${matchingHook.error}',
                style: const TextStyle(color: Colors.white, fontSize: 16),
                textAlign: TextAlign.center,
              ),
              const SizedBox(height: 24),
              ElevatedButton(
                onPressed: matchingHook.refreshProfiles,
                style: ElevatedButton.styleFrom(
                  backgroundColor: Colors.white,
                  foregroundColor: const Color(0xFF1565C0),
                ),
                child: const Text('Reintentar'),
              ),
            ],
          ),
        ),
      );
    }

    // Si no hay perfiles disponibles
    if (matchingHook.profiles.isEmpty) {
      return Scaffold(
        backgroundColor: const Color(0xFF1565C0),
        appBar: AppBar(
          title: const Text('Banduu'),
          backgroundColor: const Color(0xFF1565C0),
          foregroundColor: Colors.white,
          elevation: 0,
          actions: [
            IconButton(
              icon: const Icon(Icons.refresh),
              onPressed: matchingHook.refreshProfiles,
            ),
          ],
        ),
        body: Center(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              const Icon(
                Icons.people_outline,
                size: 80,
                color: Colors.white,
              ),
              const SizedBox(height: 16),
              const Text(
                'No hay más perfiles disponibles',
                style: TextStyle(color: Colors.white, fontSize: 16),
              ),
              const SizedBox(height: 24),
              ElevatedButton(
                onPressed: matchingHook.refreshProfiles,
                style: ElevatedButton.styleFrom(
                  backgroundColor: Colors.white,
                  foregroundColor: const Color(0xFF1565C0),
                ),
                child: const Text('Buscar más perfiles'),
              ),
            ],
          ),
        ),
      );
    }

    if (matchingHook.profiles.isEmpty || currentProfileIndex.value >= matchingHook.profiles.length) {
      return Scaffold(
        backgroundColor: const Color(0xFF1565C0),
        appBar: AppBar(
          title: const Text('Banduu'),
          backgroundColor: const Color(0xFF1565C0),
          foregroundColor: Colors.white,
          elevation: 0,
        ),
        body: const Center(
          child: Text(
            'No hay perfiles disponibles',
            style: TextStyle(color: Colors.white, fontSize: 16),
          ),
        ),
      );
    }
    
    final currentProfile = matchingHook.profiles[currentProfileIndex.value];

    // Adjust the design to match Tinder-like functionality
    return Scaffold(
      backgroundColor: Colors.white,
      appBar: AppBar(
        title: Row(
          mainAxisSize: MainAxisSize.min,
          children: [
            Image.asset(
              'assets/imagenes/logo_banduu.png',
              width: 30,
              height: 30,
            ),
            const SizedBox(width: 8),
            const Text('Banduu', style: TextStyle(fontWeight: FontWeight.bold)),
          ],
        ),
        backgroundColor: Colors.white,
        foregroundColor: const Color(0xFF1565C0),
        elevation: 0,
      ),      body: SafeArea(
    child: Column(
      crossAxisAlignment: CrossAxisAlignment.stretch,
      children: [
            Expanded(
              child: Padding(
                padding: const EdgeInsets.symmetric(horizontal: 16.0),
                child: Stack(
                  children: [
                    Transform.translate(
                      offset: swipeOffset,
                      child: GestureDetector(
                        onPanUpdate: (details) {
                          // Logic for swipe tracking
                        },
                        onPanEnd: (details) {
                          if (details.velocity.pixelsPerSecond.dx > 500) {
                            handleLike();
                          } else if (details.velocity.pixelsPerSecond.dx < -500) {
                            handleDislike();
                          }
                        },
                        child: Container(
                          margin: const EdgeInsets.all(4), // Reducido aún más para aprovechar espacio
                          child: _buildProfileCard(currentProfile, matchingHook),
                        ),
                      ),
                    ),
                    if (likeScale > 0)
                      Positioned.fill(
                        child: Transform.scale(
                          scale: likeScale,
                          child: Container(
                            margin: const EdgeInsets.all(16),
                            decoration: BoxDecoration(
                              borderRadius: BorderRadius.circular(20),
                              color: Colors.green.withOpacity(0.1),
                            ),
                            child: Stack(
                              children: [
                                Center(
                                  child: Image.asset(
                                    'assets/imagenes/Bandurria para el si.png',
                                    width: 100,
                                    height: 100,
                                  ),
                                ),
                                Positioned(
                                  top: 30,
                                  right: 30,
                                  child: Container(
                                    padding: const EdgeInsets.symmetric(horizontal: 16, vertical: 8),
                                    decoration: BoxDecoration(
                                      color: Colors.green,
                                      borderRadius: BorderRadius.circular(20),
                                    ),
                                    child: const Text(
                                      'Like',
                                      style: TextStyle(
                                        color: Colors.white,
                                        fontSize: 20,
                                        fontWeight: FontWeight.bold,
                                      ),
                                    ),
                                  ),
                                ),
                              ],
                            ),
                          ),
                        ),
                      ),
                    if (dislikeScale > 0)
                      Positioned.fill(
                        child: Transform.scale(
                          scale: dislikeScale,
                          child: Container(
                            margin: const EdgeInsets.all(16),
                            decoration: BoxDecoration(
                              borderRadius: BorderRadius.circular(20),
                              color: Colors.red.withOpacity(0.1),
                            ),
                            child: Stack(
                              children: [
                                Center(
                                  child: Image.asset(
                                    'assets/imagenes/Bandurria para el no.png',
                                    width: 100,
                                    height: 100,
                                  ),
                                ),
                                Positioned(
                                  top: 30,
                                  left: 30,
                                  child: Container(
                                    padding: const EdgeInsets.symmetric(horizontal: 16, vertical: 8),
                                    decoration: BoxDecoration(
                                      color: Colors.red,
                                      borderRadius: BorderRadius.circular(20),
                                    ),
                                    child: const Text(
                                      'Nope',
                                      style: TextStyle(
                                        color: Colors.white,
                                        fontSize: 20,
                                        fontWeight: FontWeight.bold,
                                      ),
                                    ),
                                  ),
                                ),
                              ],
                            ),
                          ),
                        ),
                      ),
                  ],
                ),
              ),
            ),
      Container(
          padding: const EdgeInsets.all(24),
          child: Row(
            mainAxisAlignment: MainAxisAlignment.spaceEvenly,
            children: [
              GestureDetector(
                onTap: handleDislike,
                child: Container(
                  width: 80, // Más grande
                  height: 80, // Más grande
                  decoration: BoxDecoration(
                    color: Colors.white,
                    shape: BoxShape.circle,
                    boxShadow: [
                      BoxShadow(
                        color: Colors.black.withOpacity(0.1),
                        blurRadius: 10,
                        offset: const Offset(0, 4),
                      ),
                    ],
                  ),
                  child: const Icon(Icons.close, color: Colors.red, size: 45),
                ),
              ),
              GestureDetector(
                onTap: handleLike,
                child: Container(
                  width: 80, // Más grande
                  height: 80, // Más grande
                  decoration: BoxDecoration(
                    color: Colors.white,
                    shape: BoxShape.circle,
                    boxShadow: [
                      BoxShadow(
                        color: Colors.black.withOpacity(0.1),
                        blurRadius: 10,
                        offset: const Offset(0, 4),
                      ),
                    ],
                  ),
                  child: const Icon(Icons.favorite, color: Colors.green, size: 45),
                ),
              ),
                ],
              ),
            ),
          ],
        ),
      ),
      bottomNavigationBar: BottomNavigationBar(
        backgroundColor: const Color(0xFF1565C0),
        selectedItemColor: Colors.white,
        unselectedItemColor: Colors.white70,
        items: const [
          BottomNavigationBarItem(
            icon: Icon(Icons.home),
            label: 'Inicio',
          ),
          BottomNavigationBarItem(
            icon: Icon(Icons.chat),
            label: 'Mensajes',
          ),
          BottomNavigationBarItem(
            icon: Icon(Icons.person),
            label: 'Perfil',
          ),
        ],
        onTap: (index) {
          // Handle navigation
          if (index == 0) {
            Navigator.pushNamed(context, '/main');
          } else if (index == 1) {
            Navigator.pushNamed(context, '/messages');
          } else if (index == 2) {
            Navigator.pushNamed(context, '/profile');
          }
        },
      ),
    );
  }

  Widget _buildProfileCard(Profile profile, UseMatchingResult matchingHook) {
    return Card(
      elevation: 10,
      margin: EdgeInsets.zero,
      shape: RoundedRectangleBorder(
        borderRadius: BorderRadius.circular(20),
      ),
      child: ClipRRect(
        borderRadius: BorderRadius.circular(20),
        child: Column(
          children: [
            // Imagen de perfil (ahora más grande)
            Expanded(
              flex: 6,
              child: Container(
                width: double.infinity,
                child: Stack(
                  fit: StackFit.expand,
                  children: [
                    ProfileImageWidget(
                      imageData: profile.imagen,
                      fit: BoxFit.cover,
                      placeholder: _buildProfilePlaceholder(profile),
                    ),
                    // Gradiente en la parte inferior
                    Positioned(
                      bottom: 0,
                      left: 0,
                      right: 0,
                      child: Container(
                        height: 100,
                        decoration: BoxDecoration(
                          gradient: LinearGradient(
                            begin: Alignment.topCenter,
                            end: Alignment.bottomCenter,
                            colors: [
                              Colors.transparent,
                              Colors.black.withOpacity(0.8),
                            ],
                          ),
                        ),
                      ),
                    ),
                    // Nombre y edad sobre la imagen
                    Positioned(
                      bottom: 20,
                      left: 20,
                      right: 20,
                      child: Row(
                        children: [
                          Expanded(
                            child: Column(
                              crossAxisAlignment: CrossAxisAlignment.start,
                              children: [
                                Text(
                                  '${profile.nombre} ${profile.apellido}',
                                  style: const TextStyle(
                                    fontSize: 28,
                                    fontWeight: FontWeight.bold,
                                    color: Colors.white,
                                    shadows: [
                                      Shadow(
                                        blurRadius: 10.0,
                                        color: Colors.black,
                                        offset: Offset(2.0, 2.0),
                                      ),
                                    ],
                                  ),
                                ),
                                const SizedBox(height: 4),
                                Text(
                                  profile.carrera,
                                  style: const TextStyle(
                                    fontSize: 16,
                                    color: Colors.white,
                                    fontWeight: FontWeight.w500,
                                    shadows: [
                                      Shadow(
                                        blurRadius: 8.0,
                                        color: Colors.black,
                                        offset: Offset(1.0, 1.0),
                                      ),
                                    ],
                                  ),
                                ),
                              ],
                            ),
                          ),
                          Container(
                            padding: const EdgeInsets.symmetric(horizontal: 16, vertical: 8),
                            decoration: BoxDecoration(
                              color: Colors.white,
                              borderRadius: BorderRadius.circular(15),
                              boxShadow: [
                                BoxShadow(
                                  color: Colors.black.withOpacity(0.2),
                                  blurRadius: 8,
                                  offset: const Offset(0, 2),
                                ),
                              ],
                            ),
                            child: Text(
                              '${profile.edad}',
                              style: const TextStyle(
                                color: Color(0xFF1565C0),
                                fontWeight: FontWeight.bold,
                                fontSize: 20,
                              ),
                            ),
                          ),
                        ],
                      ),
                    ),
                  ],
                ),
              ),
            ),
            
            // Información del perfil
            Expanded(
              flex: 3,
              child: Container(
                padding: const EdgeInsets.symmetric(horizontal: 24, vertical: 16),
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Row(
                      children: [
                        const Icon(Icons.favorite, color: Color(0xFF1565C0), size: 22),
                        const SizedBox(width: 10),
                        Expanded(
                          child: Text(
                            "Orientación: ${profile.orientacion}",
                            style: const TextStyle(
                              fontSize: 16,
                              color: Colors.black87,
                              fontWeight: FontWeight.w500,
                            ),
                          ),
                        ),
                      ],
                    ),
                    
                    const SizedBox(height: 12),
                    
                    // Gustos musicales
                    Row(
                      children: [
                        const Icon(Icons.music_note, color: Color(0xFF1565C0), size: 22),
                        const SizedBox(width: 10),
                        const Text(
                          'Gustos musicales:',
                          style: TextStyle(
                            fontSize: 16,
                            fontWeight: FontWeight.w600,
                            color: Color(0xFF1565C0),
                          ),
                        ),
                      ],
                    ),
                    const SizedBox(height: 8),
                    Expanded(
                      child: SingleChildScrollView(
                        child: Column(
                          crossAxisAlignment: CrossAxisAlignment.start,
                          children: [
                            // Géneros musicales
                            if (profile.generosMusicales.isNotEmpty) 
                              Wrap(
                                spacing: 8,
                                runSpacing: 8,
                                children: profile.generosMusicales.map((genre) => 
                                  Container(
                                    padding: const EdgeInsets.symmetric(horizontal: 12, vertical: 6),
                                    decoration: BoxDecoration(
                                      color: const Color(0xFF1565C0).withOpacity(0.1),
                                      borderRadius: BorderRadius.circular(20),
                                      border: Border.all(
                                        color: const Color(0xFF1565C0).withOpacity(0.3),
                                      ),
                                    ),
                                    child: Text(
                                      genre,
                                      style: const TextStyle(
                                        fontSize: 14,
                                        color: Color(0xFF1565C0),
                                        fontWeight: FontWeight.w500,
                                      ),
                                    ),
                                  )
                                ).toList(),
                              ),
                            
                            const SizedBox(height: 10),
                            
                            // Compatibilidad
                            if (matchingHook.matchingScores.isNotEmpty && 
                                profile.id != null && 
                                matchingHook.matchingScores.containsKey(profile.id))
                              Container(
                                margin: const EdgeInsets.only(top: 8),
                                padding: const EdgeInsets.symmetric(horizontal: 12, vertical: 8),
                                decoration: BoxDecoration(
                                  color: Colors.amber.withOpacity(0.1),
                                  borderRadius: BorderRadius.circular(12),
                                  border: Border.all(
                                    color: Colors.amber.withOpacity(0.5),
                                  ),
                                ),
                                child: Row(
                                  mainAxisSize: MainAxisSize.min,
                                  children: [
                                    const Icon(Icons.star, color: Colors.amber, size: 20),
                                    const SizedBox(width: 6),
                                    Text(
                                      'Compatibilidad: ${matchingHook.matchingScores[profile.id] ?? 0}%',
                                      style: const TextStyle(
                                        fontSize: 16,
                                        fontWeight: FontWeight.w600,
                                        color: Colors.amber,
                                      ),
                                    ),
                                  ],
                                ),
                              ),
                          ],
                        ),
                      ),
                    ),
                  ],
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }

  Widget _buildProfilePlaceholder(Profile profile) {
    return Container(
      decoration: BoxDecoration(
        gradient: LinearGradient(
          begin: Alignment.topCenter,
          end: Alignment.bottomCenter,
          colors: [
            Colors.blue.shade300,
            Colors.blue.shade600,
          ],
        ),
      ),
      child: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            const Icon(
              Icons.person,
              size: 80,
              color: Colors.white,
            ),
            const SizedBox(height: 16),
            Text(
              '${profile.nombre} ${profile.apellido}',
              style: const TextStyle(
                color: Colors.white,
                fontSize: 18,
                fontWeight: FontWeight.bold,
              ),
            ),
          ],
        ),
      ),
    );
  }
}
