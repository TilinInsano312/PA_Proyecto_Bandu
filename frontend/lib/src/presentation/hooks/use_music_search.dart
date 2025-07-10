import 'package:flutter_hooks/flutter_hooks.dart';
import 'package:flutter/material.dart';
import '../../domain/servicios/music_service.dart';

class MusicSearchHook {
  final MusicService _musicService = MusicService();
  final ValueNotifier<List<MusicSearchResult>> searchResults;
  final ValueNotifier<bool> isSearching;
  final ValueNotifier<String?> searchError;

  MusicSearchHook({
    required this.searchResults,
    required this.isSearching,
    required this.searchError,
  });

  Future<void> searchTrack(String query) async {
    if (query.trim().isEmpty) {
      searchResults.value = [];
      return;
    }

    isSearching.value = true;
    searchError.value = null;

    try {
      print('DEBUG MUSIC_HOOK - Searching track: $query');
      final result = await _musicService.searchTrack(query);
      if (result != null) {
        print('DEBUG MUSIC_HOOK - Track found: ${result.name}');
        searchResults.value = [result];
      } else {
        print('DEBUG MUSIC_HOOK - No track found for: $query');
        searchResults.value = [];
      }
    } catch (e) {
      print('DEBUG MUSIC_HOOK - Error searching track: $e');
      searchError.value = 'Error al buscar canción: $e';
      searchResults.value = [];
    } finally {
      isSearching.value = false;
    }
  }

  Future<void> searchArtist(String query) async {
    if (query.trim().isEmpty) {
      searchResults.value = [];
      return;
    }

    isSearching.value = true;
    searchError.value = null;

    try {
      print('DEBUG MUSIC_HOOK - Searching artist: $query');
      final result = await _musicService.searchArtist(query);
      if (result != null) {
        print('DEBUG MUSIC_HOOK - Artist found: ${result.name}');
        searchResults.value = [result];
      } else {
        print('DEBUG MUSIC_HOOK - No artist found for: $query');
        searchResults.value = [];
      }
    } catch (e) {
      print('DEBUG MUSIC_HOOK - Error searching artist: $e');
      searchError.value = 'Error al buscar artista: $e';
      searchResults.value = [];
    } finally {
      isSearching.value = false;
    }
  }

  Future<void> searchAlbum(String query) async {
    if (query.trim().isEmpty) {
      searchResults.value = [];
      return;
    }

    isSearching.value = true;
    searchError.value = null;

    try {
      print('DEBUG MUSIC_HOOK - Searching album: $query');
      final result = await _musicService.searchAlbum(query);
      if (result != null) {
        print('DEBUG MUSIC_HOOK - Album found: ${result.name}');
        searchResults.value = [result];
      } else {
        print('DEBUG MUSIC_HOOK - No album found for: $query');
        searchResults.value = [];
      }
    } catch (e) {
      print('DEBUG MUSIC_HOOK - Error searching album: $e');
      searchError.value = 'Error al buscar álbum: $e';
      searchResults.value = [];
    } finally {
      isSearching.value = false;
    }
  }

  void clearSearch() {
    searchResults.value = [];
    searchError.value = null;
    isSearching.value = false;
  }
}

MusicSearchHook useMusicSearch() {
  final searchResults = useState<List<MusicSearchResult>>([]);
  final isSearching = useState<bool>(false);
  final searchError = useState<String?>(null);

  return useMemoized(() => MusicSearchHook(
    searchResults: searchResults,
    isSearching: isSearching,
    searchError: searchError,
  ), []);
}
