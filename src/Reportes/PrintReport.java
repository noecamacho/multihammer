package Reportes;
 
import Model.dbConnection;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JFrame;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.swing.JRViewer;
 
public class PrintReport extends JFrame {
    
    private dbConnection con;
 
    // Muestra el 
    public void showReport(String ReportType, String fechaInicio, String fechaFinal) throws JRException, ClassNotFoundException, SQLException, IOException  {
        String reportSrcFile = null;
        String rootDir = System.getProperty("user.dir");
        
        // Elige el tipo de reporte a mostrar
        switch(ReportType) {    
            case "Por clientes":
                reportSrcFile = rootDir + "\\src\\Reportes\\ReporteClientes.jrxml";
                break;
            case "Por productos":
                reportSrcFile = rootDir + "\\src\\Reportes\\ReporteProductos.jrxml";
                break;
            case "Por pedidos":
                reportSrcFile = rootDir + "\\src\\Reportes\\ReportePedidos.jrxml";
                break;
            default:
        }
        // Compila el archivo .jrxml y hace conexión con la BD
        JasperReport jasperReport = JasperCompileManager.compileReport(reportSrcFile);
        con = new dbConnection();
        Connection reg = con.getConnection();
        
        // Los parametros a reportar
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("fechaInicio", fechaInicio);
        parameters.put("fechaFinal", fechaFinal);
        
        //Cargado de reporte y muestra su vista
        JasperPrint print = JasperFillManager.fillReport(jasperReport, parameters, reg);
        JRViewer viewer = new JRViewer(print);
        viewer.setOpaque(true);
        viewer.setVisible(true);
        this.add(viewer);
        this.setSize(1366, 768);
        this.setVisible(true);
        System.out.print("Done!");
    }
    
    public void showCotizacion(int id_venta) throws JRException, ClassNotFoundException, SQLException, IOException {
        // Compila el archivo .jrxml y hace conexión con la BD
        JasperReport jasperReport = JasperCompileManager.compileReport(System.getProperty("user.dir") + "\\src\\Reportes\\Cotizacion.jrxml");
        con = new dbConnection();
        Connection reg = con.getConnection();
        
        // Los parametros a reportar
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("idVenta", id_venta);

        //Cargado de reporte y muestra su vista
        JasperPrint print = JasperFillManager.fillReport(jasperReport, parameters, reg);
        JRViewer viewer = new JRViewer(print);
        viewer.setOpaque(true);
        viewer.setVisible(true);
        this.add(viewer);
        this.setSize(1366, 768);
        this.setVisible(true);
    }
    
    public void showFacturacion(int id_venta) throws JRException, ClassNotFoundException, SQLException, IOException {
        // Compila el archivo .jrxml y hace conexión con la BD
        JasperReport jasperReport = JasperCompileManager.compileReport(System.getProperty("user.dir") + "\\src\\Reportes\\Facturacion.jrxml");
        con = new dbConnection();
        Connection reg = con.getConnection();
        
        // Los parametros a reportar
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("idVenta", id_venta);

        //Cargado de reporte y muestra su vista
        JasperPrint print = JasperFillManager.fillReport(jasperReport, parameters, reg);
        JRViewer viewer = new JRViewer(print);
        viewer.setOpaque(true);
        viewer.setVisible(true);
        this.add(viewer);
        this.setSize(1366, 768);
        this.setVisible(true);
    }
    
}