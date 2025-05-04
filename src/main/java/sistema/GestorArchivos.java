package sistema;


import com.fasterxml.jackson.databind.ObjectMapper;
import modelos.usuario.Cliente;


import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Clase GestorArchivos
 * Esta clase se encarga de gestionar la lectura y escritura de archivos JSON
 * utilizando la libreria Jackson.
 *
 * @author Sebastian Sandoval
 * @version 1.0
 */

public class GestorArchivos {


    private static final ObjectMapper mapper = new ObjectMapper();
    private static final Logger logger = Logger.getLogger(GestorArchivos.class.getName());

    private GestorArchivos() {//Constructoir vacio
    }

    /**
     * Metodo para nombrar el un archivo json.
     * @param tipo Clase del objeto a guardar.
     * @return Nombre del archivo en formato JSON.
     * @throws Exception Si ocurre un error al nombrar el archivo.
     */
    //Metodo para nombrar archivos dependiendo el tipo de objeto
    private static <T> String nombrarArchivo(Class<T> tipo) {
        return tipo.getSimpleName() + ".json";
    }

    /**
     *
     * Metodo para escribir una lista de objetos en un archivo JSON.
     *
     * @param listaObjetos
     * @param tipo
     * @return void
     * @throws Exception Si ocurre un error al escribir el archivo.
     */
    //Metodo para escribir una lista de objetos en un archivo JSON.
    public static <T> void escribirListaObjetos(List<T> listaObjetos, Class<T> tipo) {
        String nombreArchivo = nombrarArchivo(tipo);
        try{
            mapper.writeValue(new File(nombreArchivo), listaObjetos);
            logger.info("Archivo guardado de forma satisfactoria");
        }catch (Exception e){
            logger.warning("Error al escribir el archivo: "+ nombreArchivo +": "+ e.getMessage());
        }
    }

    /**
     * Metodo para leer una lista de objetos de un archivo JSON.
     *
     * @param nombreArchivo Nombre del archivo a leer.
     * @param tipo Clase del objeto a leer.
     * @return Lista de objetos leidos del archivo.
     * @throws Exception Si ocurre un error al leer el archivo.
     */
    //Metodo para leer una lista de objetos de un archivo
    public static <T> List<T> leerListaObjetos(String nombreArchivo, Class<T> tipo) {
        try{
            return mapper.readValue(new File(nombreArchivo),
                    mapper.getTypeFactory().constructCollectionType(List.class, tipo));
        }catch(Exception e){
            logger.warning("Error al leer el archivo");
            return new ArrayList<>();
        }
    }

    /**
     * Metodo para escribir un objeto en un archivo JSON.
     *
     * @param nombreArchivo Nombre del archivo del cual se desea eliminar un objeto.
     * @param objeto Objeto a escribir.
     * @param tipo Clase del objeto a escribir.
     * @return void
     * @throws Exception Si ocurre un error al borrar el objeto.
     */
    // Metodo para borrar un objeto de un archivo
    public static <T> void borrarObjeto(String nombreArchivo, Class<T> tipo, T objeto) {
        List<T> listaObjetos = leerListaObjetos(nombreArchivo, tipo);
        try{
            listaObjetos.remove(objeto);
            escribirListaObjetos(listaObjetos, tipo);
            logger.info("Objeto borrado de forma satisfactoria");
        } catch (Exception e) {
            logger.warning("Error al borrar Objeto");
        }

    }

    /**
     * Metodo para modificar un atributo de un objeto en un archivo JSON.
     *
     * @param cliente Objeto a modificar.
     * @param nombreAtributo Nombre del atributo a modificar.
     * @param nuevoValor Nuevo valor del atributo.
     * @return Objeto modificado.
     * @throws Exception Si ocurre un error al modificar el atributo.
     */
    public static Cliente modificarUnAtributoPerfilUsuario(Cliente cliente, String nombreAtributo, Object nuevoValor) {
        String archivo  = "PerfilUsuario.json";
        borrarObjeto(archivo, Cliente.class, cliente);
        List<Cliente> listaPerfiles = leerListaObjetos(archivo, Cliente.class);

        Cliente perfilModificar = cliente;
        try{
            Field atributo = Cliente.class.getDeclaredField(nombreAtributo);
            atributo.setAccessible(true);
            atributo.set(perfilModificar, nuevoValor);

            listaPerfiles.remove(cliente);
            listaPerfiles.add(perfilModificar);

            escribirListaObjetos(listaPerfiles, Cliente.class);
            logger.info("Atributo modificado de forma satisfactoria");
            return cliente;
        }catch (Exception e){
            logger.warning("Error al modificar el atributo: "+ nombreAtributo + ": " + e.getMessage());
        }

        return null;

    }

}