class LoginResponse {
  final bool exito;
  final String mensaje;
  final String? nombreUsuario;
  final String? email;
  final String? rol;

  const LoginResponse({
    required this.exito,
    required this.mensaje,
    this.nombreUsuario,
    this.email,
    this.rol,
  });

  factory LoginResponse.fromJson(Map<String, dynamic> json) {
    return LoginResponse(
      exito: json['exito'] ?? false,
      mensaje: json['mensaje'] ?? '',
      nombreUsuario: json['nombreUsuario'],
      email: json['email'],
      rol: json['rol'],
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'exito': exito,
      'mensaje': mensaje,
      'nombreUsuario': nombreUsuario,
      'email': email,
      'rol': rol,
    };
  }

  bool get success => exito;
  String get message => mensaje;
  Map<String, dynamic>? get userData => {
    if (nombreUsuario != null) 'nombreUsuario': nombreUsuario,
    if (email != null) 'email': email,
    if (rol != null) 'rol': rol,
  };
}