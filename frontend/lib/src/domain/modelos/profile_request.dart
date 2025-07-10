class ProfileRequest {
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
  final List<String> canciones;
  final List<String> artistas;
  final List<String> albums;

  const ProfileRequest({
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
      'canciones': <Map<String, dynamic>>[],
      'artistas': <Map<String, dynamic>>[],
      'albums': <Map<String, dynamic>>[],
    };
  }
  
  factory ProfileRequest.fromJson(Map<String, dynamic> json) {
    return ProfileRequest(
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
      canciones: List<String>.from(json['canciones'] ?? []),
      artistas: List<String>.from(json['artistas'] ?? []),
      albums: List<String>.from(json['albums'] ?? []),
    );
  }
}
