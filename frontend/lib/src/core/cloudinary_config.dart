/// Configuración de Cloudinary para el almacenamiento de imágenes
/// 
/// Configuración centralizada para el servicio de Cloudinary que permite
/// subir, optimizar y gestionar imágenes de perfiles de usuario.
/// 
/// INSTRUCCIONES DE CONFIGURACIÓN:
/// 1. Crear cuenta en https://cloudinary.com
/// 2. Ir al Dashboard y obtener las credenciales
/// 3. Reemplazar los valores de ejemplo con tus credenciales reales
/// 4. Configurar un Upload Preset para uploads no firmados (recomendado)
/// 
/// IMPORTANTE: En producción, las credenciales deben estar en variables de entorno
class CloudinaryConfig {
  // 🚨 CREDENCIALES DE CLOUDINARY 🚨
  static const String cloudName = 'dohtyaxpv';
  static const String apiKey = '875352165981189';
  static const String apiSecret = 'p_s0QHGgDFLWsqLgrgyP7Quto8U';
  
  // Upload Preset (recomendado para producción)
  // Crear en: Settings > Upload > Upload presets
  static const String uploadPreset = 'banduu_profiles';
  
  // Configuración de carpetas
  static const String profilesFolder = 'banduu/profiles';
  static const String albumsFolder = 'banduu/albums';
  static const String artistsFolder = 'banduu/artists';
  
  // Transformaciones por defecto
  static const int profileImageWidth = 400;
  static const int profileImageHeight = 400;
  static const String imageQuality = 'auto';
  static const String imageFormat = 'auto';
  
  // URLs base
  static String get uploadUrl => 'https://api.cloudinary.com/v1_1/$cloudName/image/upload';
  static String get destroyUrl => 'https://api.cloudinary.com/v1_1/$cloudName/image/destroy';
  
  /// Generar URL optimizada para imagen de perfil
  static String getProfileImageUrl(String originalUrl) {
    if (!originalUrl.contains('cloudinary.com')) {
      return originalUrl;
    }
    
    var parts = originalUrl.split('/upload/');
    if (parts.length == 2) {
      return '${parts[0]}/upload/w_$profileImageWidth,h_$profileImageHeight,c_fill,q_$imageQuality,f_$imageFormat/${parts[1]}';
    }
    
    return originalUrl;
  }
  
  /// Generar URL de thumbnail
  static String getThumbnailUrl(String originalUrl, {int size = 150}) {
    if (!originalUrl.contains('cloudinary.com')) {
      return originalUrl;
    }
    
    var parts = originalUrl.split('/upload/');
    if (parts.length == 2) {
      return '${parts[0]}/upload/w_$size,h_$size,c_fill,q_$imageQuality,f_$imageFormat/${parts[1]}';
    }
    
    return originalUrl;
  }
  
  /// Validar configuración para uploads con preset (más seguro)
  static bool get isConfigured {
    return cloudName != 'TU_CLOUD_NAME_AQUI' &&
           cloudName.isNotEmpty;
  }
  
  /// Validar configuración completa (para uploads con firma)
  static bool get isFullyConfigured {
    return cloudName != 'TU_CLOUD_NAME_AQUI' &&
           apiKey != 'TU_API_KEY_AQUI' &&
           apiSecret != 'TU_API_SECRET_AQUI' &&
           cloudName.isNotEmpty &&
           apiKey.isNotEmpty &&
           apiSecret.isNotEmpty;
  }
}
