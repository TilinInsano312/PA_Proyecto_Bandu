package sistema;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class GestorArchivos {


    private static final ObjectMapper mapper = new ObjectMapper();
    private static final Logger logger = Logger.getLogger(GestorArchivos.class.getName());

    private GestorArchivos() {
    }


    //Revisa si el archivo existe, si existe lo sobreescribe, si no existe lo crea
    public static <T> void guardarArchivo(T objeto) {
        String nombreArchivo = objeto.getClass().getSimpleName();
        if (archivoExiste(nombreArchivo)) {
            sobreEscribirArchivo(objeto, nombreArchivo);
            return;
        }

        List<T> nuevaLista = new ArrayList<>();
        nuevaLista.add(objeto);
        try {
            mapper.writeValue(new File(nombreArchivo), nuevaLista);
        } catch (IOException e) {
            logger.warning("Error al guardar el objeto en el archivo " + nombreArchivo + ": " + e.getMessage());
        }
    }

    //sobre escribre el archivo existente
    public static <T> void sobreEscribirArchivo(T objeto, String nombreArchivo) {
        List<T> datosArchivo = leerListaObjetoArchivo(nombreArchivo, (Class<T>) objeto.getClass());

        if (datosArchivo.isEmpty()) {
            datosArchivo = new ArrayList<>();
        }

        datosArchivo.removeIf(obj -> obj.equals(objeto));
        datosArchivo.add(objeto);

        try {
            mapper.writeValue(new File(nombreArchivo), datosArchivo);
        } catch (IOException e) {
            logger.warning("Error al sobrescribir el archivo " + nombreArchivo + ": " + e.getMessage());
        }

    }


    //lee el archivo y lo convierte a una lista de objetos
    public static <T> List<T> leerListaObjetoArchivo(String nombreArchivo, Class<T> claseTipo) {
        List<T> listaObjetos = new ArrayList<>();
        try {
            JsonNode root = mapper.readTree(new File(nombreArchivo));
            if (root.isArray()) {
                listaObjetos = mapper.convertValue(root, mapper.getTypeFactory().constructCollectionType(List.class, claseTipo));
            } else if (root.isObject()) {
                T objeto = mapper.convertValue(root, claseTipo);
                listaObjetos = new ArrayList<>();
                listaObjetos.add(objeto);
            }
        } catch (IOException e) {
            logger.warning("Error al leer el archivo " + nombreArchivo + ": " + e.getMessage());
        }
        return listaObjetos;
    }

    //lee el archivo y lo convierte a un objeto
    public static <T> T leerArchivo(String nombreArchivo, Class<T> claseTipo) {
        try {
            return mapper.readValue(new File(nombreArchivo), claseTipo);
        } catch (Exception e) {
            logger.warning("Error al leer el personaje desde " + nombreArchivo + ": " + e.getMessage());
            return null;
        }
    }

    //verifica si el archivo existe
    public static boolean archivoExiste(String nombreArchivo) {
        File nuevoArchivo = new File(nombreArchivo);
        return nuevoArchivo.exists();
    }
}