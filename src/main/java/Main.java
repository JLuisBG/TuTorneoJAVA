import MVC.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

public class Main {
    /**
     * Metodo main de la aplicacion
     * @param args
     */
    public static void main(String[] args) {

        Vista vista = new Vista();
        Modelo modelo = new Modelo();
        Login login = new Login();
        Controlador controlador = new Controlador(vista, modelo,login);
        //Login login = new Login();

    }
}
