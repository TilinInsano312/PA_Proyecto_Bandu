class ProfileResponse {
  final bool exito;
  final String mensaje;
  final Map<String, dynamic>? perfilData;

  const ProfileResponse({
    required this.exito,
    required this.mensaje,
    this.perfilData,
  });

  factory ProfileResponse.fromJson(Map<String, dynamic> json) {
    return ProfileResponse(
      exito: json['exito'] ?? json['success'] ?? false,
      mensaje: json['mensaje'] ?? json['message'] ?? '',
      perfilData: json['perfilData'] ?? json['data'],
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'exito': exito,
      'mensaje': mensaje,
      'perfilData': perfilData,
    };
  }

  bool get success => exito;
  String get message => mensaje;
}
