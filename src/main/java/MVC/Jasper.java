package MVC;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.InputStream;

/**
 * Clase responsable de generar un ticket de compra a partir de un numero de pedido utilizando JasperReports.
 */

public class Jasper {
    /**
     * Genera un ticket de compra en formato PDF para el numero de pedido proporcionado.
     */
    public  void reportPlayers() {
        Modelo modelo = new Modelo();
        try {
           /* JasperDesign design = JRXmlLoader.load(".jrxml");
            JasperReport report = JasperCompileManager.compileReport(design);
            JasperPrint print = JasperFillManager.fillReport(report, null, modelo.conexion());
            JasperViewer.viewReport(print, false);*/
            // Load the .jrxml file as a stream from the classpath
            InputStream jrxmlStream = getClass().getResourceAsStream("/Players.jrxml");


            // Load the JasperDesign from the InputStream
            JasperDesign design = JRXmlLoader.load(jrxmlStream);

            // Compile the JasperDesign to get a JasperReport
            JasperReport report = JasperCompileManager.compileReport(design);

            // Fill the report with data
            JasperPrint print = JasperFillManager.fillReport(report, null, modelo.conexion());

            // View the report
            JasperViewer.viewReport(print, false);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        } catch (JRException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Genera un ticket de compra en formato PDF para el numero de pedido proporcionado.
     */
    public void reportTeams() {
        Modelo modelo = new Modelo();
        try {/*
            JasperDesign design = JRXmlLoader.load("teams.jrxml");
            JasperReport report = JasperCompileManager.compileReport(design);
            JasperPrint print = JasperFillManager.fillReport(report, null, modelo.conexion());
            JasperViewer.viewReport(print, false);*/

                // Load the .jrxml file as a stream from the classpath
                InputStream jrxmlStream = getClass().getResourceAsStream("/teams.jrxml");


                // Load the JasperDesign from the InputStream
                JasperDesign design = JRXmlLoader.load(jrxmlStream);

                // Compile the JasperDesign to get a JasperReport
                JasperReport report = JasperCompileManager.compileReport(design);

                // Fill the report with data
                JasperPrint print = JasperFillManager.fillReport(report, null, modelo.conexion());

                // View the report
                JasperViewer.viewReport(print, false);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        } catch (JRException e) {
            throw new RuntimeException(e);
        }
    }
}
