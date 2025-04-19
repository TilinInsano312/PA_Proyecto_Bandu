package sistema;


import com.fasterxml.jackson.databind.ObjectMapper;


import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public class GestorArchivos {


    private static final ObjectMapper mapper = new ObjectMapper();
    private static final Logger logger = Logger.getLogger(GestorArchivos.class.getName());

    private GestorArchivos() {
    }

    //Metodo para nombrar archivos dependiendo el tipo de objeto
    private static <T> String nombrarArchivo(Class<T> tipo) {
        return tipo.getSimpleName() + ".json";
    }

    //Metodo para escribir una lista de objetos en un archivo
    public static <T> void escribirListaObjetos(List<T> listaObjetos, Class<T> tipo) {
        String nombreArchivo = nombrarArchivo(tipo);
        try{
            mapper.writeValue(new File(nombreArchivo), listaObjetos);
            logger.info("Archivo guardado de forma satisfactoria");
        }catch (Exception e){
            logger.warning("Error al escribir el archivo: "+ nombreArchivo +": "+ e.getMessage());
        }
    }

    //Metodo para leer una lista de objetos de un archivo
    public static <T> List<T> leerListaObjetos(String nombreArchivo, Class<T> tipo) {
        try{
            return mapper.readValue(new File(nombreArchivo),
                    mapper.getTypeFactory().constructCollectionType(List.class, tipo));
        }catch(Exception e){
            logger.warning("Error al leer el archivo");
            return Collections.emptyList();
        }
    }

}