class LoginRequest{

  final String nombreUsuario;
  final String contrasena;

  const LoginRequest({
    required this.nombreUsuario,
    required this.contrasena,
  });

  Map<String, dynamic> toJson() {
    return {
      'username': nombreUsuario,
      'password': contrasena,
    };
  }
  
  factory LoginRequest.fromJson(Map<String, dynamic> json) {
    return LoginRequest(
      nombreUsuario: json['username'] as String,
      contrasena: json['password'] as String,
    );
  }

}

