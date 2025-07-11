import 'dart:convert';
import 'dart:io';
import 'dart:typed_data';
import 'package:flutter/material.dart';
import 'package:image_picker/image_picker.dart';
import 'package:path_provider/path_provider.dart';

class ImageService {
  static final ImageService _instance = ImageService._internal();
  factory ImageService() => _instance;
  ImageService._internal();

  final ImagePicker _picker = ImagePicker();

  Future<File?> pickImageFromGallery() async {
    try {
      final XFile? image = await _picker.pickImage(
        source: ImageSource.gallery,
        maxWidth: 800,
        maxHeight: 800,
        imageQuality: 85,
      );
      
      if (image != null) {
        return File(image.path);
      }
      return null;
    } catch (e) {
      return null;
    }
  }

  Future<File?> takePhotoWithCamera() async {
    try {
      final XFile? image = await _picker.pickImage(
        source: ImageSource.camera,
        maxWidth: 800,
        maxHeight: 800,
        imageQuality: 85,
      );
      
      if (image != null) {
        return File(image.path);
      }
      return null;
    } catch (e) {
      return null;
    }
  }

  Future<String?> convertImageToBase64(File imageFile) async {
    try {
      final Uint8List imageBytes = await imageFile.readAsBytes();
      final String base64String = base64Encode(imageBytes);

      String extension = imageFile.path.split('.').last.toLowerCase();
      String mimeType = 'image/jpeg';
      
      switch (extension) {
        case 'png':
          mimeType = 'image/png';
          break;
        case 'gif':
          mimeType = 'image/gif';
          break;
        case 'webp':
          mimeType = 'image/webp';
          break;
        default:
          mimeType = 'image/jpeg';
      }
      
      return 'data:$mimeType;base64,$base64String';
    } catch (e) {
      return null;
    }
  }

  Future<String?> saveImageLocally(File imageFile, String fileName) async {
    try {
      final Directory appDir = await getApplicationDocumentsDirectory();
      final String imagePath = '${appDir.path}/profile_images';
      final Directory imageDir = Directory(imagePath);
      
      if (!await imageDir.exists()) {
        await imageDir.create(recursive: true);
      }
      
      // Guardar imagen
      final String extension = imageFile.path.split('.').last;
      final File savedImage = await imageFile.copy('$imagePath/$fileName.$extension');
      
      return savedImage.path;
    } catch (e) {
      return null;
    }
  }

  Future<File?> showImageSourceDialog(BuildContext context) async {
    final String? choice = await showDialog<String?>(
      context: context,
      builder: (BuildContext dialogContext) {
        return AlertDialog(
          title: const Text('Seleccionar imagen'),
          content: Column(
            mainAxisSize: MainAxisSize.min,
            children: [
              ListTile(
                leading: const Icon(Icons.photo_library),
                title: const Text('Galería'),
                onTap: () async {
                  Navigator.pop(dialogContext, 'gallery');
                },
              ),
              ListTile(
                leading: const Icon(Icons.camera_alt),
                title: const Text('Cámara'),
                onTap: () async {
                  Navigator.pop(dialogContext, 'camera');
                },
              ),
            ],
          ),
          actions: [
            TextButton(
              onPressed: () => Navigator.pop(dialogContext, null),
              child: const Text('Cancelar'),
            ),
          ],
        );
      },
    );

    if (choice == 'gallery') {
      return await pickImageFromGallery();
    } else if (choice == 'camera') {
      return await takePhotoWithCamera();
    }
    return null;
  }
}
