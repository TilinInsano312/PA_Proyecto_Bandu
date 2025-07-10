class LoginResponse {
  final bool success;
  final String message;
  final Map<String, dynamic>? userData;

  const LoginResponse({
    required this.success,
    required this.message,
    this.userData,
  });

  factory LoginResponse.fromJson(Map<String, dynamic> json) {
    return LoginResponse(
      success: json['success'] ?? false,
      message: json['message'] ?? '',
      userData: json['userData'],
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'success': success,
      'message': message,
      'userData': userData,
    };
  }
}