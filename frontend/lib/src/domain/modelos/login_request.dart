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
      nombreUsuario: json['nombreUsuario'] as String,
      contrasena: json['contrasena'] as String,
    );
  }

}

