import 'dart:convert';
import 'base_service.dart';
import '../../core/app_config.dart';

class MusicSearchResult {
  final String id;
  final String name;
  final String? artist;
  final String? genre;
  final String? imageUrl;
  final String type;

  MusicSearchResult({
    required this.id,
    required this.name,
    this.artist,
    this.genre,
    this.imageUrl,
    required this.type,
  });

  factory MusicSearchResult.fromJson(Map<String, dynamic> json, String type) {
   
    try {
      switch (type) {
        case 'track':
          return MusicSearchResult(
            id: json['nombreCancion']?.toString() ?? '',
            name: json['nombreCancion']?.toString() ?? '',
            artist: json['artistaCancion']?.toString(),
            genre: json['generoCancion']?.toString(),
            imageUrl: json['imagenCancion']?.toString(),
            type: type,
          );
        case 'artist':
          return MusicSearchResult(
            id: json['nombreArtista']?.toString() ?? '',
            name: json['nombreArtista']?.toString() ?? '',
            artist: null,
            genre: json['generoArtista']?.toString(),
            imageUrl: json['imagenArtista']?.toString(),
            type: type,
          );
        case 'album':
          return MusicSearchResult(
            id: json['nombreAlbum']?.toString() ?? '',
            name: json['nombreAlbum']?.toString() ?? '',
            artist: json['artistaAlbum']?.toString(),
            genre: json['generoAlbum']?.toString(),
            imageUrl: json['imagenAlbum']?.toString(),
            type: type,
          );
        default:
          throw ArgumentError('Unknown type: $type');
      }
    } catch (e) {
      rethrow;
    }
  }

  String get displayName {
    if (artist != null && artist!.isNotEmpty) {
      return '$name - $artist';
    }
    return name;
  }
}

class MusicService extends BaseService {
  MusicService() : super(baseUrl: '${AppConfig.apiBaseUrl}/musica');

  Future<MusicSearchResult?> searchTrack(String query) async {
    try {
      final encodedQuery = Uri.encodeComponent(query);
      final endpoint = '/cancion/$encodedQuery';
    
      final response = await publicGet(endpoint);

      if (response.statusCode == 200) {
        // Verificar si la respuesta no está vacía
        if (response.body.trim().isEmpty) {
          return null;
        }
        
        final data = json.decode(response.body);
        return MusicSearchResult.fromJson(data, 'track');
      } else if (response.statusCode == 404) {
        return null;
      } else {
        return null;
      }
    } catch (e) {
      return null;
    }
  }

  Future<MusicSearchResult?> searchArtist(String query) async {
    try {
      final encodedQuery = Uri.encodeComponent(query);
      final endpoint = '/artista/$encodedQuery';
      
      final response = await publicGet(endpoint);

      if (response.statusCode == 200) {
        // Verificar si la respuesta no está vacía
        if (response.body.trim().isEmpty) {
          return null;
        }
        
        final data = json.decode(response.body);
        return MusicSearchResult.fromJson(data, 'artist');
      } else if (response.statusCode == 404) {

        return null;
      } else {
        return null;
      }
    } catch (e) {
      return null;
    }
  }

  Future<MusicSearchResult?> searchAlbum(String query) async {
    try {
      final encodedQuery = Uri.encodeComponent(query);
      final endpoint = '/album/$encodedQuery';
      
      final response = await publicGet(endpoint);
      
      if (response.statusCode == 200) {
        // Verificar si la respuesta no está vacía
        if (response.body.trim().isEmpty) {
          return null;
        }
        
        final data = json.decode(response.body);
        return MusicSearchResult.fromJson(data, 'album');
      } else if (response.statusCode == 404) {
        return null;
      } else {
        return null;
      }
    } catch (e) {
      return null;
    }
  }
}
