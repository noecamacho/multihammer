package Controller;

import Model.Registros;
import Model.PedidosModel;
import Reportes.PrintReport;
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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import net.sf.jasperreports.engine.JRException;

public class DetalleVentasController implements Initializable {

    private final PedidosModel modelo = new PedidosModel();
    private int id_venta;
    
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
        createTable();
    }    
    
    public void createTable() {
        
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
    
    public void createTableView(String id_venta) {
        TreeItem<Registros> root = new RecursiveTreeItem<>(tableInformation(id_venta), RecursiveTreeObject::getChildren);
        table.setRoot(root);
        table.setShowRoot(false);
    }

    public ObservableList<Registros> tableInformation(String id_venta) {
        ObservableList<Registros> registros = FXCollections.observableArrayList();
        modelo.getRegistros(id_venta).forEach((x) -> {
            registros.add(new Registros(x.getMaterial(), x.getUnidad(), x.getCantidad(), x.getPrecio()));
        });
        return registros;
    }
    
    public void setValues(String id_venta, String nombreCliente, String fechaVenta, String total) {
        labelNombre.setText("Cliente: " + nombreCliente);
        labelFecha.setText("Fecha: " + fechaVenta);
        labelTotal.setText("$ " + total);
        createTableView(id_venta);
    }

    public void setId_venta(int id_venta) {
        this.id_venta = id_venta;
    }

    @FXML
    private void CotizarVenta(ActionEvent event) throws JRException, ClassNotFoundException, SQLException, IOException {
        PrintReport facturacion = new PrintReport();
        facturacion.showFacturacion(this.id_venta);
    }
    
}
