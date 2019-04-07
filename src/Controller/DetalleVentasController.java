package Controller;

import Reportes.PrintReport;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import net.sf.jasperreports.engine.JRException;
import Model.Dialogs;
import Model.Registros;
import Model.PedidosModel;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.stage.Stage;

public class DetalleVentasController implements Initializable {
    // Instanciación de clase de modelo y dialogo
    private final PedidosModel modelo = new PedidosModel();
    private int id_venta;

    final private Dialogs dialogs = new Dialogs();
    // Declaración de componentes
    @FXML
    private Label labelNombre;
    @FXML
    private Label labelFecha;
    @FXML
    private Label labelTotal;
    @FXML
    private JFXTreeTableView table;
    @FXML
    private JFXButton btnCotizar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Se crea la tabla
        createTable();
    }    
    
    public void createTable() {
        // Estructura de la tabla
        JFXTreeTableColumn<Registros, String> material = new JFXTreeTableColumn("Material");
        material.setPrefWidth(150);
        material.setCellValueFactory((TreeTableColumn.CellDataFeatures<Registros, String> param) -> param.getValue().getValue().getMaterial());

        JFXTreeTableColumn<Registros, String> unidad = new JFXTreeTableColumn("Unidad");
        unidad.setPrefWidth(150);
        unidad.setCellValueFactory((TreeTableColumn.CellDataFeatures<Registros, String> param) -> param.getValue().getValue().getUnidad());

        JFXTreeTableColumn<Registros, String> cantidad = new JFXTreeTableColumn("Cantidad");
        cantidad.setPrefWidth(150);
        cantidad.setCellValueFactory((TreeTableColumn.CellDataFeatures<Registros, String> param) -> param.getValue().getValue().getCantidad());

        JFXTreeTableColumn<Registros, String> precio = new JFXTreeTableColumn("Precio");
        precio.setPrefWidth(150);
        precio.setCellValueFactory((TreeTableColumn.CellDataFeatures<Registros, String> param) -> param.getValue().getValue().getPrecio());

        table.getColumns().setAll(material, unidad, cantidad, precio);
    }
    // Función para mostrar valores en la tabla
    public void createTableView(String id_venta) {
        TreeItem<Registros> root = new RecursiveTreeItem<>(tableInformation(id_venta), RecursiveTreeObject::getChildren);
        table.setRoot(root);
        table.setShowRoot(false);
    }
    // Función para obtener los datos a mostrar
    public ObservableList<Registros> tableInformation(String id_venta) {
        ObservableList<Registros> registros = FXCollections.observableArrayList();
        modelo.getRegistros(id_venta).forEach((x) -> {
            registros.add(new Registros(x.getMaterial(), x.getUnidad(), x.getCantidad(), x.getPrecio()));
        });
        return registros;
    }
    // Función para pasar los valores a los componentes 
    public void setValues(String id_venta, String nombreCliente, String fechaVenta, String total) {
        labelNombre.setText("Cliente: " + nombreCliente);
        labelFecha.setText("Fecha: " + fechaVenta);
        labelTotal.setText("$ " + total);
        createTableView(id_venta);
    }
    // Asigna el ID del pedido
    public void setId_venta(int id_venta) {
        this.id_venta = id_venta;
    }

    //Función para cerrar el modal
    @FXML
    private void closeModal() {
        if(dialogs.displayMessage((Stage) labelNombre.getScene().getWindow(), "Advertencia", "¿Estás seguro que deseas cerrar esta ventana?", "Si", "No")) {
            Stage stage = (Stage) labelNombre.getScene().getWindow();
            stage.close();
        }
    }
    @FXML
    private void CotizarVenta(ActionEvent event) throws JRException, ClassNotFoundException, SQLException, IOException {
        PrintReport facturacion = new PrintReport();
        facturacion.showFacturacion(this.id_venta);
    }
}