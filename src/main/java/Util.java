import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.security.MessageDigest;

/**
 * Esta es una clase en la que tengo métodos estáticos para crear una ventana con un mensaje.
 * Cada método se refiere a un tipo distinto de mensaje.
 */
public class Util {
    /**
     * Este método me muestra un mensaje de error con el texto recibido
     *
     * @param message Texto del mensaje de error
     */
    public static void showErrorAlert(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Este método me muestra un mensaje de aviso con el texto recibido
     *
     * @param message Texto del mensaje de aviso
     */
    public static void showWarningAlert(String message) {
        JOptionPane.showMessageDialog(null, message, "Aviso", JOptionPane.WARNING_MESSAGE);
    }

    /**
     * Este método me muestra un mensaje de información con el texto recibido
     *
     * @param message Texto del mensaje de información
     */
    public static void showInfoAlert(String message) {
        JOptionPane.showMessageDialog(null, message, "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Este método me muestra un mensaje de confirmación con el texto recibido
     *
     * @param mensaje Texto del mensaje de confirmación
     * @param titulo  Texto del mensaje de confirmación
     * @return Devuelve un entero que indica la opción seleccionada
     */
    public static int mensajeConfirmacion(String mensaje, String titulo) {
        return JOptionPane.showConfirmDialog(null, mensaje, titulo, JOptionPane.YES_NO_OPTION);
    }

    /**
     * Este método me crea un selector de ficheros con la ruta por defecto, el tipo de archivos y la extensión
     *
     * @param rutaDefecto  Ruta por defecto
     * @param tipoArchivos Tipo de archivos
     * @param extension    Extensión de los archivos
     * @return Devuelve un JFileChooser con la ruta por defecto, el tipo de archivos y la extensión
     */
    public static JFileChooser crearSelectorFicheros(File rutaDefecto, String tipoArchivos, String extension) {
        JFileChooser selectorFichero = new JFileChooser();
        if (rutaDefecto != null) {
            selectorFichero.setCurrentDirectory(rutaDefecto);
        }
        if (extension != null) {
            FileNameExtensionFilter filtro = new FileNameExtensionFilter(tipoArchivos, extension);
            selectorFichero.setFileFilter(filtro);
        }
        return selectorFichero;
    }

    /**
     * Este método me muestra un mensaje de éxito con el texto recibido
     *
     * @param mensaje Texto del mensaje de éxito
     */
    public static void mensajeExitoso(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, "Exito", JOptionPane.INFORMATION_MESSAGE);
    }

/**
     * Este método me devuelve el valor de un hash SHA-256 de un valor
     *
     * @param value Valor a hashear
     * @return Devuelve un String con el valor hasheado
 */
    public static String getSha256(String value) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(value.getBytes());
            return bytesToHex(md.digest());
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
/**
     * Este método me convierte un array de bytes en un String hexadecimal
     *
     * @param bytes Array de bytes
     * @return Devuelve un String con el valor hexadecimal
     */
    private static String bytesToHex(byte[] bytes) {
        StringBuffer result = new StringBuffer();
        for (byte b : bytes) result.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
        return result.toString();
    }

}