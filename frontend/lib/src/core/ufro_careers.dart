class UFROCareers {
  static const Map<String, List<String>> facultades = {
    'Facultad de Ciencias Agropecuarias y Medioambiente': [
      'Agronomía',
      'Biotecnología',
      'Ingeniería en Recursos Naturales',
      'Medicina Veterinaria',
    ],
    
    'Facultad de Ciencias Jurídicas y Empresariales': [
      'Contador Público y Auditor',
      'Derecho',
      'Ingeniería Comercial',
    ],
    
    'Facultad de Educación, Ciencias Sociales y Humanidades': [
      'Bachillerato en Ciencias Sociales',
      'Pedagogía en Castellano y Comunicación',
      'Pedagogía en Ciencias Mención Biología, Química o Física',
      'Pedagogía en Educación Diferencial',
      'Pedagogía en Educación Física, Deportes y Recreación',
      'Pedagogía en Historia, Geografía y Educación Cívica',
      'Pedagogía en Inglés',
      'Pedagogía en Matemática',
      'Periodismo',
      'Psicología',
      'Sociología',
      'Trabajo Social',
    ],
    
    'Facultad de Ingeniería y Ciencias': [
      'Bioquímica',
      'Ingeniería Civil',
      'Ingeniería Civil Ambiental',
      'Ingeniería Civil Eléctrica',
      'Ingeniería Civil Electrónica',
      'Ingeniería Civil en Biotecnología',
      'Ingeniería Civil Industrial',
      'Ingeniería Civil Informática',
      'Ingeniería Civil Física',
      'Ingeniería Civil Matemática',
      'Ingeniería Civil Mecánica',
      'Ingeniería Civil Química',
      'Ingeniería Civil Telemática',
      'Ingeniería en Construcción',
      'Ingeniería Informática',
      'Plan Común Ingeniería Civil',
    ],
    
    'Facultad de Medicina': [
      'Enfermería',
      'Fonoaudiología',
      'Kinesiología',
      'Medicina',
      'Nutrición y Dietética',
      'Obstetricia y Puericultura',
      'Química y Farmacia',
      'Tecnología Médica',
      'Terapia Ocupacional',
    ],
    
    'Facultad de Odontología': [
      'Odontología',
    ],
    
    'Carreras Técnicas Campus Pucón': [
      'Técnico Guía de Turismo Aventura',
      'Técnico Superior en Turismo',
      'Técnico Universitario en Enfermería',
    ],
  };

  /// Obtiene todas las carreras en una lista plana
  static List<String> getAllCareers() {
    List<String> todasLasCarreras = [];
    facultades.values.forEach((carreras) {
      todasLasCarreras.addAll(carreras);
    });
    todasLasCarreras.sort(); // Ordenar alfabéticamente
    return todasLasCarreras;
  }

  /// Obtiene carreras por facultad
  static List<String> getCareersByFaculty(String facultad) {
    return facultades[facultad] ?? [];
  }

  /// Obtiene todas las facultades
  static List<String> getFaculties() {
    return facultades.keys.toList();
  }
}
