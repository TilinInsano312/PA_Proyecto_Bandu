import 'dart:io';
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';
import '../servicios/image_service.dart';
import '../../core/cloudinary_config.dart';
import 'package:image_picker/image_picker.dart';

class ImageUploadService {
  static final ImageUploadService _instance = ImageUploadService._internal();
  factory ImageUploadService() => _instance;
  ImageUploadService._internal();

  final ImageService _imageService = ImageService();

  Future<String?> showImagePickerDialog(BuildContext context) async {
    if (!CloudinaryConfig.isConfigured) {
      return null;
    }

    final ImageSource? source = await showDialog<ImageSource>(
      context: context,
      builder: (BuildContext context) {
        return AlertDialog(
          title: const Text('Seleccionar imagen'),
          content: const Text('¿Cómo deseas obtener la imagen?'),
          actions: [
            TextButton(
              onPressed: () => Navigator.of(context).pop(),
              child: const Text('Cancelar'),
            ),
            TextButton(
              onPressed: () => Navigator.of(context).pop(ImageSource.gallery),
              child: const Text('Galería'),
            ),
            TextButton(
              onPressed: () => Navigator.of(context).pop(ImageSource.camera),
              child: const Text('Cámara'),
            ),
          ],
        );
      },
    );

    if (source == null) {
      return null;
    }

    if (context.mounted) {
      showDialog(
        context: context,
        barrierDismissible: false,
        builder: (BuildContext context) {
          return const AlertDialog(
            content: Column(
              mainAxisSize: MainAxisSize.min,
              children: [
                CircularProgressIndicator(),
                SizedBox(height: 16),
                Text('Subiendo imagen...'),
              ],
            ),
          );
        },
      );
    }

    try {
      final imageUrl = await _handleImageSelection(source);
      
      if (context.mounted) {
        Navigator.of(context).pop();
      }
      
      return imageUrl;
    } catch (e) {
      if (context.mounted) {
        Navigator.of(context).pop();
      }
      
      return null;
    }
  }

  Future<String?> _handleImageSelection(ImageSource source) async {
    try {
      File? imageFile;
      
      if (source == ImageSource.gallery) {
        imageFile = await _imageService.pickImageFromGallery();
      } else {
        imageFile = await _imageService.takePhotoWithCamera();
      }
      
      if (imageFile == null) {
        return null;
      }

      final imageUrl = await _uploadToCloudinary(imageFile);
      
      if (imageUrl != null) {
        return imageUrl;
      } else {
        return null;
      }
    } catch (e) {
      return null;
    }
  }

  Future<String?> _uploadToCloudinary(File imageFile) async {
    try {
  
      var uri = Uri.parse(CloudinaryConfig.uploadUrl);
      
      var request = http.MultipartRequest('POST', uri);
      request.fields['upload_preset'] = CloudinaryConfig.uploadPreset;
      request.fields['folder'] = CloudinaryConfig.profilesFolder;
      
      var fileStream = http.ByteStream(imageFile.openRead());
      var length = await imageFile.length();
      var multipartFile = http.MultipartFile(
        'file',
        fileStream,
        length,
        filename: 'profile_${DateTime.now().millisecondsSinceEpoch}.jpg',
      );
      
      request.files.add(multipartFile);

      var response = await request.send();
      
      if (response.statusCode == 200) {
        var responseBody = await response.stream.bytesToString();
        
        var jsonResponse = json.decode(responseBody);
        
        String imageUrl = jsonResponse['secure_url'];
        return imageUrl;
      } else {
        return null;
      }
    } catch (e) {
      return null;
    }
  }

  Future<String?> pickAndUploadImage(ImageSource source) async {
    return await _handleImageSelection(source);
  }
}
