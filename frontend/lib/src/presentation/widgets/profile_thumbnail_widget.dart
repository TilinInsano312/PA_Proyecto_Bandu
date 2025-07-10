import 'package:flutter/material.dart';
import '../../core/cloudinary_config.dart';

class ProfileThumbnailWidget extends StatelessWidget {
  final String imageUrl;
  final double size;
  final BoxFit fit;
  final Widget? placeholder;

  const ProfileThumbnailWidget({
    Key? key,
    required this.imageUrl,
    this.size = 150,
    this.fit = BoxFit.cover,
    this.placeholder,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    if (imageUrl.isEmpty) {
      return placeholder ?? _buildDefaultPlaceholder();
    }

    final optimizedUrl = _optimizeForThumbnail(imageUrl);

    return ClipRRect(
      borderRadius: BorderRadius.circular(8),
      child: Container(
        width: size,
        height: size,
        child: Image.network(
          optimizedUrl,
          fit: fit,
          width: size,
          height: size,
          loadingBuilder: (context, child, loadingProgress) {
            if (loadingProgress == null) return child;
            return Container(
              width: size,
              height: size,
              child: Center(
                child: CircularProgressIndicator(
                  strokeWidth: 2,
                  value: loadingProgress.expectedTotalBytes != null
                      ? loadingProgress.cumulativeBytesLoaded / loadingProgress.expectedTotalBytes!
                      : null,
                ),
              ),
            );
          },
          errorBuilder: (context, error, stackTrace) {
            print('ERROR loading thumbnail: $error');
            print('Thumbnail URL: $optimizedUrl');
            return _buildDefaultPlaceholder();
          },
        ),
      ),
    );
  }

  String _optimizeForThumbnail(String originalUrl) {
    // Si no es de Cloudinary, devolver la original
    if (!originalUrl.contains('cloudinary.com')) {
      return originalUrl;
    }

    return CloudinaryConfig.getThumbnailUrl(originalUrl, size: size.toInt());
  }

  Widget _buildDefaultPlaceholder() {
    return Container(
      width: size,
      height: size,
      decoration: BoxDecoration(
        gradient: LinearGradient(
          begin: Alignment.topCenter,
          end: Alignment.bottomCenter,
          colors: [
            Colors.blue.shade300,
            Colors.blue.shade600,
          ],
        ),
        borderRadius: BorderRadius.circular(8),
      ),
      child: Center(
        child: Icon(
          Icons.person,
          size: size * 0.4,
          color: Colors.white,
        ),
      ),
    );
  }
}
