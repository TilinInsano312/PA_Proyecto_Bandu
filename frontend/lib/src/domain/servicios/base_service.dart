import 'dart:convert';
import 'package:http/http.dart' as http;
import 'auth_service.dart';

abstract class BaseService {
  final String baseUrl;
  final AuthService _authService = AuthService();

  BaseService({
    required this.baseUrl,
  });

  Future<Map<String, String>> getAuthHeaders() async {
    final token = await _authService.getToken();
    if (token == null || token.isEmpty) {
      throw Exception('Token de autenticación no encontrado');
    }

    return {
      'Content-Type': 'application/json',
      'Accept': 'application/json',
      'Authorization': 'Basic $token',
    };
  }

  String _buildUrl(String endpoint) {
    if (endpoint.startsWith('http')) {
      return endpoint;
    } else {
      String path = endpoint;
      if (endpoint.startsWith('/') && baseUrl.endsWith('/')) {
        path = endpoint.substring(1);
      } else if (!endpoint.startsWith('/') && !baseUrl.endsWith('/')) {
        path = '/$endpoint';
      }
      return baseUrl + path;
    }
  }

  Future<http.Response> authenticatedGet(String endpoint) async {
    try {
      final headers = await getAuthHeaders();
      final url = _buildUrl(endpoint);
      final uri = Uri.parse(url);
      
      final response = await http.get(uri, headers: headers);

      return response;
    } catch (e) {
      rethrow;
    }
  }

  Future<http.Response> authenticatedPost(String endpoint, dynamic body) async {
    try {
      final headers = await getAuthHeaders();
      final url = _buildUrl(endpoint);
      final uri = Uri.parse(url);
      
      final requestBody = body is String ? body : json.encode(body);
      
      final response = await http.post(uri, headers: headers, body: requestBody);
    
      return response;
    } catch (e) {
      rethrow;
    }
  }

  Future<http.Response> authenticatedPut(String endpoint, dynamic body) async {
    try {
      final headers = await getAuthHeaders();
      final url = _buildUrl(endpoint);
      final uri = Uri.parse(url);
      
      final requestBody = body is String ? body : json.encode(body);
      
      final response = await http.put(uri, headers: headers, body: requestBody);
      return response;
    } catch (e) {
      rethrow;
    }
  }

  Future<http.Response> authenticatedDelete(String endpoint) async {
    try {
      final headers = await getAuthHeaders();
      final url = _buildUrl(endpoint);
      final uri = Uri.parse(url);
      
      final response = await http.delete(uri, headers: headers);
      
      return response;
    } catch (e) {
      rethrow;
    }
  }

  Map<String, String> getBasicHeaders() {
    return {
      'Content-Type': 'application/json',
      'Accept': 'application/json',
    };
  }

  Future<http.Response> publicGet(String endpoint) async {
    try {
      final headers = getBasicHeaders();
      final url = _buildUrl(endpoint);
      final uri = Uri.parse(url);
      
      final response = await http.get(uri, headers: headers);
      
      return response;
    } catch (e) {
      rethrow;
    }
  }

  Future<http.Response> publicPost(String endpoint, dynamic body) async {
    try {
      final headers = getBasicHeaders();
      final url = _buildUrl(endpoint);
      final uri = Uri.parse(url);
      
      final requestBody = body is String ? body : json.encode(body);
      
      final response = await http.post(uri, headers: headers, body: requestBody);
      
      return response;
    } catch (e) {
      rethrow;
    }
  }
}
