class Profile {
  final String? id;
  final String? idUsuario;
  final String nombre;
  final String apellido;
  final int edad;
  final String imagen;
  final String carrera;
  final String orientacion;
  final String genero;
  final List<String> generosMusicales;
  final List<dynamic> canciones;
  final List<dynamic> artistas;
  final List<dynamic> albums;

  const Profile({
    this.id,
    this.idUsuario,
    required this.nombre,
    required this.apellido,
    required this.edad,
    required this.imagen,
    required this.carrera,
    required this.orientacion,
    required this.genero,
    required this.generosMusicales,
    this.canciones = const [],
    this.artistas = const [],
    this.albums = const [],
  });

  factory Profile.fromJson(Map<String, dynamic> json) {
    return Profile(
      id: json['id'] as String?,
      idUsuario: json['idUsuario'] as String?,
      nombre: json['nombre'] as String,
      apellido: json['apellido'] as String,
      edad: json['edad'] as int,
      imagen: json['imagen'] as String,
      carrera: json['carrera'] as String,
      orientacion: json['orientacion'] as String,
      genero: json['genero'] as String,
      generosMusicales: List<String>.from(json['generosMusicales'] ?? []),
      canciones: json['canciones'] ?? [],
      artistas: json['artistas'] ?? [],
      albums: json['albums'] ?? [],
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'idUsuario': idUsuario,
      'nombre': nombre,
      'apellido': apellido,
      'edad': edad,
      'imagen': imagen,
      'carrera': carrera,
      'orientacion': orientacion,
      'genero': genero,
      'generosMusicales': generosMusicales,
      'canciones': canciones,
      'artistas': artistas,
      'albums': albums,
    };
  }

  String get fullName => '$nombre $apellido';
  
  String get displayGenerosMusicales => generosMusicales.join(', ');
}
