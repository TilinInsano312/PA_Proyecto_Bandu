class MusicItem {
  final String name;
  final String? artist;
  final String? genre;
  final String? imageUrl;
  final String type; // 'track', 'artist', 'album'

  const MusicItem({
    required this.name,
    this.artist,
    this.genre,
    this.imageUrl,
    required this.type,
  });

  Map<String, dynamic> toJson() {
    return {
      'name': name,
      'artist': artist,
      'genre': genre,
      'imageUrl': imageUrl,
      'type': type,
    };
  }

  factory MusicItem.fromJson(Map<String, dynamic> json) {
    return MusicItem(
      name: json['name'] as String,
      artist: json['artist'] as String?,
      genre: json['genre'] as String?,
      imageUrl: json['imageUrl'] as String?,
      type: json['type'] as String,
    );
  }

  String get displayName {
    if (artist != null && artist!.isNotEmpty) {
      return '$name - $artist';
    }
    return name;
  }

  @override
  bool operator ==(Object other) {
    if (identical(this, other)) return true;
    return other is MusicItem &&
        other.name == name &&
        other.artist == artist &&
        other.type == type;
  }

  @override
  int get hashCode => Object.hash(name, artist, type);
}

class EnrichedProfileRequest {
  final String? idUsuario;
  final String nombre;
  final String apellido;
  final int edad;
  final String imagen;
  final String carrera;
  final String orientacion;
  final String genero;
  final List<String> generosMusicales;
  final bool mismaCarrera;
  final List<MusicItem> canciones;
  final List<MusicItem> artistas;
  final List<MusicItem> albums;

  const EnrichedProfileRequest({
    this.idUsuario,
    required this.nombre,
    required this.apellido,
    required this.edad,
    required this.imagen,
    required this.carrera,
    required this.orientacion,
    required this.genero,
    required this.generosMusicales,
    this.mismaCarrera = false,
    this.canciones = const [],
    this.artistas = const [],
    this.albums = const [],
  });

  Map<String, dynamic> toJson() {
    return {
      'id': null,
      'idUsuario': idUsuario ?? '',
      'nombre': nombre,
      'apellido': apellido,
      'edad': edad,
      'imagen': imagen,
      'carrera': carrera,
      'orientacion': orientacion,
      'genero': genero,
      'generosMusicales': generosMusicales,
      'mismaCarrera': mismaCarrera,
      // Para compatibilidad con el backend actual, enviamos solo los nombres
      'canciones': canciones.map((e) => e.displayName).toList(),
      'artistas': artistas.map((e) => e.displayName).toList(),
      'albums': albums.map((e) => e.displayName).toList(),
      // Información enriquecida adicional (para futuras mejoras del backend)
      'enrichedCanciones': canciones.map((e) => e.toJson()).toList(),
      'enrichedArtistas': artistas.map((e) => e.toJson()).toList(),
      'enrichedAlbums': albums.map((e) => e.toJson()).toList(),
    };
  }

  factory EnrichedProfileRequest.fromJson(Map<String, dynamic> json) {
    return EnrichedProfileRequest(
      idUsuario: json['idUsuario'] as String?,
      nombre: json['nombre'] as String,
      apellido: json['apellido'] as String,
      edad: json['edad'] as int,
      imagen: json['imagen'] as String,
      carrera: json['carrera'] as String,
      orientacion: json['orientacion'] as String,
      genero: json['genero'] as String,
      generosMusicales: List<String>.from(json['generosMusicales'] ?? []),
      mismaCarrera: json['mismaCarrera'] as bool? ?? false,
      canciones: (json['enrichedCanciones'] as List?)
          ?.map((e) => MusicItem.fromJson(e as Map<String, dynamic>))
          .toList() ?? [],
      artistas: (json['enrichedArtistas'] as List?)
          ?.map((e) => MusicItem.fromJson(e as Map<String, dynamic>))
          .toList() ?? [],
      albums: (json['enrichedAlbums'] as List?)
          ?.map((e) => MusicItem.fromJson(e as Map<String, dynamic>))
          .toList() ?? [],
    );
  }
}
