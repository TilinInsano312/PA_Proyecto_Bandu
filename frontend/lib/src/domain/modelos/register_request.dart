class RegisterRequest {
  final String nombreUsuario;
  final String email;
  final String contrasena;

  const RegisterRequest({
    required this.nombreUsuario,
    required this.email,
    required this.contrasena,
  });

  Map<String, dynamic> toJson() {
    return {
      'username': nombreUsuario,
      'email': email,
      'password': contrasena,     
    };
  }
  
  factory RegisterRequest.fromJson(Map<String, dynamic> json) {
    return RegisterRequest(
      nombreUsuario: json['nombreUsuario'] as String,
      email: json['email'] as String,
      contrasena: json['contrasena'] as String,
    );
  }
}
