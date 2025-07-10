import 'dart:convert';
import 'dart:typed_data';
import 'package:flutter/material.dart';
import '../../core/cloudinary_config.dart';

class ProfileImageWidget extends StatelessWidget {
  final String imageData;
  final BoxFit fit;
  final Widget? placeholder;
  final Widget Function(BuildContext, Object, StackTrace?)? errorBuilder;
  final double? width;
  final double? height;

  const ProfileImageWidget({
    Key? key,
    required this.imageData,
    this.fit = BoxFit.cover,
    this.placeholder,
    this.errorBuilder,
    this.width,
    this.height,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    if (imageData.isEmpty) {
      return placeholder ?? _buildDefaultPlaceholder();
    }

    if (imageData.startsWith('http://') || imageData.startsWith('https://')) {
      final optimizedUrl = _optimizeCloudinaryUrl(imageData);
      
      return Image.network(
        optimizedUrl,
        fit: fit,
        width: width,
        height: height,
        loadingBuilder: (context, child, loadingProgress) {
          if (loadingProgress == null) return child;
          return Container(
            width: width,
            height: height,
            child: Center(
              child: CircularProgressIndicator(
                value: loadingProgress.expectedTotalBytes != null
                    ? loadingProgress.cumulativeBytesLoaded / loadingProgress.expectedTotalBytes!
                    : null,
              ),
            ),
          );
        },
        errorBuilder: errorBuilder ?? (context, error, stackTrace) {
          print('ERROR loading image: $error');
          print('Image URL: $optimizedUrl');
          return _buildDefaultPlaceholder();
        },
      );
    }

    if (imageData.startsWith('data:image/') || _isBase64String(imageData)) {
      return _buildBase64Image();
    }

    return placeholder ?? _buildDefaultPlaceholder();
  }

  String _optimizeCloudinaryUrl(String originalUrl) {

    if (!originalUrl.contains('cloudinary.com')) {
      return originalUrl;
    }

    return CloudinaryConfig.getProfileImageUrl(originalUrl);
  }

  bool _isBase64String(String str) {
    if (str.length < 4) return false;
    
    final base64RegExp = RegExp(r'^[A-Za-z0-9+/]+={0,2}$');
    
    final sample = str.length > 100 ? str.substring(0, 100) : str;
    
    return base64RegExp.hasMatch(sample) && str.length % 4 == 0;
  }

  Widget _buildBase64Image() {
    try {
      if (!imageData.contains(',')) {
        final Uint8List bytes = base64Decode(imageData);
        return Image.memory(
          bytes,
          fit: fit,
          width: width,
          height: height,
          errorBuilder: errorBuilder ?? (context, error, stackTrace) => _buildDefaultPlaceholder(),
        );
      }

      final parts = imageData.split(',');
      if (parts.length != 2) {
        throw Exception('Formato base64 inválido');
      }

      final base64String = parts[1];
      final Uint8List bytes = base64Decode(base64String);

      return Image.memory(
        bytes,
        fit: fit,
        width: width,
        height: height,
        errorBuilder: errorBuilder ?? (context, error, stackTrace) => _buildDefaultPlaceholder(),
      );
    } catch (e) {
      return placeholder ?? _buildDefaultPlaceholder();
    }
  }

  Widget _buildDefaultPlaceholder() {
    return Container(
      width: width,
      height: height,
      decoration: BoxDecoration(
        gradient: LinearGradient(
          begin: Alignment.topCenter,
          end: Alignment.bottomCenter,
          colors: [
            Colors.blue.shade300,
            Colors.blue.shade600,
          ],
        ),
      ),
      child: const Center(
        child: Icon(
          Icons.person,
          size: 60,
          color: Colors.white,
        ),
      ),
    );
  }
}
