package Controller;

import Model.Producto;
import Model.ProductosModel;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ProductosController implements Initializable {
    
    private final ProductosModel modelo = new ProductosModel();
    private Producto selectedProduct = new Producto();
            
    @FXML
    private JFXButton btnAgregar;
    @FXML
    private JFXButton btnModificar;
    @FXML
    private JFXButton btnEliminar;
    @FXML
    private JFXTreeTableView<Producto> table;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        createTableView();
    }    
    
    private Producto getLeadSelect() {
        TreeItem<Producto> selectedItem = (TreeItem<Producto>) table.getSelectionModel().getSelectedItem();
        return selectedItem == null ? null : selectedItem.getValue();
    }
    
    public void createTableView() {

        table.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> giveValues(getLeadSelect()));

        JFXTreeTableColumn<Producto, String> id = new JFXTreeTableColumn("ID");
        id.setPrefWidth(100);
        id.setCellValueFactory((TreeTableColumn.CellDataFeatures<Producto, String> param) -> param.getValue().getValue().id_producto);

        JFXTreeTableColumn<Producto, String> material = new JFXTreeTableColumn("Material");
        material.setPrefWidth(100);
        material.setCellValueFactory((TreeTableColumn.CellDataFeatures<Producto, String> param) -> param.getValue().getValue().material);

        JFXTreeTableColumn<Producto, String> unidad = new JFXTreeTableColumn("Unidad");
        unidad.setPrefWidth(100);
        unidad.setCellValueFactory((TreeTableColumn.CellDataFeatures<Producto, String> param) -> param.getValue().getValue().unidad);

        JFXTreeTableColumn<Producto, String> precio = new JFXTreeTableColumn("Precio");
        precio.setPrefWidth(100);
        precio.setCellValueFactory((TreeTableColumn.CellDataFeatures<Producto, String> param) -> param.getValue().getValue().precio);

        JFXTreeTableColumn<Producto, String> cantidad = new JFXTreeTableColumn("Cantidad");
        cantidad.setPrefWidth(100);
        cantidad.setCellValueFactory((TreeTableColumn.CellDataFeatures<Producto, String> param) -> param.getValue().getValue().cantidad);

        JFXTreeTableColumn<Producto, String> proveedor = new JFXTreeTableColumn("Proveedor");
        proveedor.setPrefWidth(100);
        proveedor.setCellValueFactory((TreeTableColumn.CellDataFeatures<Producto, String> param) -> param.getValue().getValue().proveedor);

        final TreeItem<Producto> root = new RecursiveTreeItem<>(tableInformation(), RecursiveTreeObject::getChildren);

        table.getColumns().setAll(id, material, unidad, precio, cantidad, proveedor);
        table.setRoot(root);
        table.setShowRoot(false);
    }

    public ObservableList<Producto> tableInformation() {
        ObservableList<Producto> productos = FXCollections.observableArrayList();
        modelo.getProductos().forEach((x) -> {
            productos.add(new Producto(x.material, x.unidad, x.precio, x.proveedor, x.cantidad, x.id_producto));
        });
        return productos;
    }
    

    @FXML
    private void btnAgregarAction(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader modal = new FXMLLoader(getClass().getResource("/View/ProductosModal.fxml"));
        Parent root = modal.load();
        stage.setScene(new Scene(root));
        stage.setTitle("Agregar Producto");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(((Node)event.getSource()).getScene().getWindow() );
        stage.show();
        createTableView();
    }

    @FXML
    private void btnModificarAction(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader modal = new FXMLLoader(getClass().getResource("/View/ProductosModal.fxml"));
        Parent root = modal.load();
        ProductosModalController pmc = modal.getController();
        pmc.setValuesToModify(selectedProduct.material.get(), selectedProduct.unidad.get(), selectedProduct.precio.get(), selectedProduct.proveedor.get(), selectedProduct.cantidad.get(), selectedProduct.id_producto.get());
        stage.setScene(new Scene(root));
        stage.setTitle("Agregar Producto");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(((Node)event.getSource()).getScene().getWindow() );
        stage.show();
        createTableView();
    }

    @FXML
    private void btnEliminarAction(ActionEvent event) {
        if(this.selectedProduct.id_producto != null) {
            modelo.eliminarProducto(this.selectedProduct.id_producto.get());
            this.selectedProduct.setId_producto("");
        } else {
            System.out.println("selecciona algo");
        }
        createTableView();
    }
    
    public void giveValues(Producto x) {
        this.selectedProduct.setCantidad(x.cantidad);
        this.selectedProduct.setId_producto(x.id_producto);
        this.selectedProduct.setMaterial(x.material);
        this.selectedProduct.setPrecio(x.precio);
        this.selectedProduct.setProveedor(x.proveedor);
        this.selectedProduct.setUnidad(x.unidad);
    }
    
}
