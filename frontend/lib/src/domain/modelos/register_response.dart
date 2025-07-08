class RegisterResponse {
  final bool exito;
  final String mensaje;

  const RegisterResponse({
    required this.exito,
    required this.mensaje,
  });

  factory RegisterResponse.fromJson(Map<String, dynamic> json) {
    return RegisterResponse(
      exito: json['exito'] ?? false,
      mensaje: json['mensaje'] ?? '',
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'exito': exito,
      'mensaje': mensaje,
    };
  }

  bool get success => exito;
  String get message => mensaje;
}
