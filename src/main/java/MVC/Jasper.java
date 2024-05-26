package MVC;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 * Clase responsable de generar un ticket de compra a partir de un numero de pedido utilizando JasperReports.
 */

public class Jasper {
public static void reportPlayers(){
    Modelo modelo = new Modelo();
    try {
        JasperDesign design = JRXmlLoader.load("src/main/resources/Players.jrxml");
        JasperReport report = JasperCompileManager.compileReport(design);
        JasperPrint print = JasperFillManager.fillReport(report,null,modelo.conexion());
        JasperViewer.viewReport(print,false);
    } catch (JRException e) {
        throw new RuntimeException(e);
    }
}
    public static void reportTeams(){
        Modelo modelo = new Modelo();
        try {
            JasperDesign design = JRXmlLoader.load("src/main/resources/teams.jrxml");
            JasperReport report = JasperCompileManager.compileReport(design);
            JasperPrint print = JasperFillManager.fillReport(report,null,modelo.conexion());
            JasperViewer.viewReport(print,false);
        } catch (JRException e) {
            throw new RuntimeException(e);
        }
    }


}
