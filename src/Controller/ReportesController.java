package Controller;

import Model.Dialogs;
import Model.Pedidos;
import Model.ReporteClientes;
import Model.ReporteProductos;
import Model.ReportesModel;
import Reportes.PrintReport;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JRException;
import static org.apache.batik.anim.timing.Trace.print;

public class ReportesController implements Initializable {

    private final Dialogs dialogs = new Dialogs();
    private final ReportesModel modelo = new ReportesModel();
    
    @FXML
    private JFXComboBox<String> txtReporte;
    @FXML
    private JFXDatePicker txtFechaInicial;
    @FXML
    private JFXDatePicker txtFechaFinal;
    @FXML
    private JFXButton btnReporte;
    @FXML
    private JFXButton btnPDF;
    @FXML
    private JFXTreeTableView<ReporteClientes> table;
    @FXML
    private JFXTreeTableView<ReporteProductos> table1;
    @FXML
    private JFXTreeTableView<Pedidos> table2;
    @FXML
    private Label labelTotal;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        labelTotal.setVisible(false);
        hideTables();
        ObservableList<String> data = FXCollections.observableArrayList("Por clientes", "Por productos", "Por pedidos");
        txtReporte.setItems(data);
        createTable();
        createTable1();
        createTable2();
    }    
    
    public void createTable() {
        
        JFXTreeTableColumn<ReporteClientes, String> nombre = new JFXTreeTableColumn("Nombre");
        nombre.setPrefWidth(350);
        nombre.setCellValueFactory((TreeTableColumn.CellDataFeatures<ReporteClientes, String> param) -> param.getValue().getValue().getNombre());

        JFXTreeTableColumn<ReporteClientes, String> num_ventas = new JFXTreeTableColumn("Número de Ventas");
        num_ventas.setPrefWidth(300);
        num_ventas.setCellValueFactory((TreeTableColumn.CellDataFeatures<ReporteClientes, String> param) -> param.getValue().getValue().getNum_ventas());

        JFXTreeTableColumn<ReporteClientes, String> total = new JFXTreeTableColumn("Total");
        total.setPrefWidth(350);
        total.setCellValueFactory((TreeTableColumn.CellDataFeatures<ReporteClientes, String> param) -> param.getValue().getValue().getTotal());

        table.getColumns().setAll(nombre, num_ventas, total);
    }
    
    public void createTableView(String fechaInicial, String fechaFinal) {
        TreeItem<ReporteClientes> root = new RecursiveTreeItem<>(tableInformation(fechaInicial, fechaFinal), RecursiveTreeObject::getChildren);
        table.setRoot(root);
        table.setShowRoot(false);
        hideTables();
        table.setVisible(true);
    }

    public ObservableList<ReporteClientes> tableInformation(String fechaInicial, String fechaFinal) {
        ObservableList<ReporteClientes> rc = FXCollections.observableArrayList();
        modelo.getInfoClientes(fechaInicial, fechaFinal).forEach((x) -> {
            rc.add(new ReporteClientes(x.getNombre(), x.getNum_ventas(), x.getTotal()));
        });
        return rc;
    }
    
    public void createTable1() {
        
        JFXTreeTableColumn<ReporteProductos, String> material = new JFXTreeTableColumn("Material");
        material.setPrefWidth(200);
        material.setCellValueFactory((TreeTableColumn.CellDataFeatures<ReporteProductos, String> param) -> param.getValue().getValue().getMaterial());

        JFXTreeTableColumn<ReporteProductos, String> unidad = new JFXTreeTableColumn("Unidad");
        unidad.setPrefWidth(200);
        unidad.setCellValueFactory((TreeTableColumn.CellDataFeatures<ReporteProductos, String> param) -> param.getValue().getValue().getUnidad());
        
        JFXTreeTableColumn<ReporteProductos, String> cantidad = new JFXTreeTableColumn("Cantidad");
        cantidad.setPrefWidth(200);
        cantidad.setCellValueFactory((TreeTableColumn.CellDataFeatures<ReporteProductos, String> param) -> param.getValue().getValue().getCantidad());
        
        JFXTreeTableColumn<ReporteProductos, String> precio = new JFXTreeTableColumn("Precio");
        precio.setPrefWidth(200);
        precio.setCellValueFactory((TreeTableColumn.CellDataFeatures<ReporteProductos, String> param) -> param.getValue().getValue().getPrecio());

        JFXTreeTableColumn<ReporteProductos, String> total = new JFXTreeTableColumn("Total");
        total.setPrefWidth(200);
        total.setCellValueFactory((TreeTableColumn.CellDataFeatures<ReporteProductos, String> param) -> param.getValue().getValue().getTotal());

        table1.getColumns().setAll(material, unidad, cantidad, precio, total);
    }
    
    public void createTableView1(String fechaInicial, String fechaFinal) {
        TreeItem<ReporteProductos> root = new RecursiveTreeItem<>(tableInformation1(fechaInicial, fechaFinal), RecursiveTreeObject::getChildren);
        table1.setRoot(root);
        table1.setShowRoot(false);
        hideTables();
        table1.setVisible(true);
    }

    public ObservableList<ReporteProductos> tableInformation1(String fechaInicial, String fechaFinal) {
        ObservableList<ReporteProductos> rp = FXCollections.observableArrayList();
        modelo.getInfoProductos(fechaInicial, fechaFinal).forEach((x) -> {
            rp.add(new ReporteProductos(x.getMaterial(), x.getUnidad(), x.getCantidad(), x.getPrecio(), x.getTotal()));
        });
        return rp;
    }
    
    public void createTable2() {
        
        JFXTreeTableColumn<Pedidos, String> id = new JFXTreeTableColumn("ID");
        id.setPrefWidth(100);
        id.setCellValueFactory((TreeTableColumn.CellDataFeatures<Pedidos, String> param) -> param.getValue().getValue().id_pedido);

        JFXTreeTableColumn<Pedidos, String> nombre = new JFXTreeTableColumn("Nombre");
        nombre.setPrefWidth(350);
        nombre.setCellValueFactory((TreeTableColumn.CellDataFeatures<Pedidos, String> param) -> param.getValue().getValue().nombre);

        JFXTreeTableColumn<Pedidos, String> fecha = new JFXTreeTableColumn("Fecha");
        fecha.setPrefWidth(250);
        fecha.setCellValueFactory((TreeTableColumn.CellDataFeatures<Pedidos, String> param) -> param.getValue().getValue().fecha);

        JFXTreeTableColumn<Pedidos, String> total = new JFXTreeTableColumn("Total");
        total.setPrefWidth(300);
        total.setCellValueFactory((TreeTableColumn.CellDataFeatures<Pedidos, String> param) -> param.getValue().getValue().total);

        table2.getColumns().setAll(id, nombre, fecha, total);
    }
    
    public void createTableView2(String fechaInicio, String fechaFinal) {
        TreeItem<Pedidos> root = new RecursiveTreeItem<>(tableInformation2(fechaInicio, fechaFinal), RecursiveTreeObject::getChildren);
        table2.setRoot(root);
        table2.setShowRoot(false);
        hideTables();
        table2.setVisible(true);
    }

    public ObservableList<Pedidos> tableInformation2(String fechaInicio, String fechaFinal) {
        float total = 0;
        ObservableList<Pedidos> pedidos = FXCollections.observableArrayList();
        modelo.getPedidos(fechaInicio, fechaFinal).forEach((x) -> {
            pedidos.add(new Pedidos(x.getId_pedido(), x.getNombre(), x.getFecha(), x.total));
        });
        return pedidos;
    }
    
    public void hideTables() {
        table.setVisible(false);
        table1.setVisible(false);
        table2.setVisible(false);
    }
    
    @FXML
    private void btnReporteAction() {
        if(txtFechaInicial.getValue() != null && txtFechaFinal.getValue() != null && txtReporte.getValue() != null) {
            if(txtFechaInicial.getValue().isBefore(txtFechaFinal.getValue())) {
                switch(txtReporte.getValue()) {
                    case "Por clientes":
                        createTableView(txtFechaInicial.getValue().toString(), txtFechaFinal.getValue().toString());
                        break;
                    case "Por productos":
                        createTableView1(txtFechaInicial.getValue().toString(), txtFechaFinal.getValue().toString());
                        break;
                    case "Por pedidos":
                        createTableView2(txtFechaInicial.getValue().toString(), txtFechaFinal.getValue().toString());
                        break;
                }
            } else {
                dialogs.displayMessage((Stage) btnReporte.getScene().getWindow(), "Advertencia", "La fecha final no puede ser antes que la fecha inicial", "Ok");
            }
        } else {
            dialogs.displayMessage((Stage) btnReporte.getScene().getWindow(), "Advertencia", "Es necesario escoger el tipo de reporte y el rango de fechas antes de generar el reporte", "Ok");
        }
    }
    
    @FXML
    private void btnPDFAction() throws JRException, ClassNotFoundException, SQLException, IOException {
        if(txtFechaInicial.getValue() != null && txtFechaFinal.getValue() != null && txtReporte.getValue() != null) {
            if(txtFechaInicial.getValue().isBefore(txtFechaFinal.getValue())) {
                String fechaInicio = txtFechaInicial.getValue().toString();
                String fechaFinal = txtFechaFinal.getValue().toString();
                String ReportType = txtReporte.getValue(); 
                PrintReport report = new PrintReport();
                report.showReport(ReportType, fechaInicio, fechaFinal);
            }
        }
    }
}
