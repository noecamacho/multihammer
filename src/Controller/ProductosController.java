package Controller;

import Model.Dialogs;
import Model.Producto;
import Model.ProductosModel;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ProductosController implements Initializable {
    // Declaración/Instanciación de variables
    private final ProductosModel modelo = new ProductosModel();
    final private Dialogs dialogs = new Dialogs();
    // Variable auxiliar
    private Producto selectedProduct = new Producto();
    // Declaración de componentes
    @FXML
    private JFXButton btnAgregar;
    @FXML
    private JFXButton btnModificar;
    @FXML
    private JFXButton btnEliminar;
    @FXML
    private JFXTreeTableView<Producto> table;
    @FXML
    private JFXTextField searchField; 

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Se crea la tabla
        createTable();
        createTableView();
        // Se desactivan los botones modificar/eliminar si el campo de búsqueda recibe el focus
        searchField.focusedProperty().addListener((ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) -> {
            if(newPropertyValue) {
                disableButtons(true);
            }
        });
    }    
    // Función para cambiar el estado de los botones
    public void disableButtons(boolean value) {
        btnModificar.setDisable(value);
        btnEliminar.setDisable(value);
    }
    // Se asigna a la variable auxiliar los datos de la fila seleccionada y se habilitan los botones
    private Producto getLeadSelect() {
        TreeItem<Producto> selectedItem = (TreeItem<Producto>) table.getSelectionModel().getSelectedItem();
        disableButtons(false);
        return selectedItem == null ? null : selectedItem.getValue();
    }
    // Función de funcionalidad de campo de búsqueda
    private ChangeListener<String> setupSearchField(final JFXTreeTableView<Producto> tableView) {
        return (o, oldVal, newVal) ->
            tableView.setPredicate(personProp -> {
                final Producto prod = personProp.getValue();
                return prod.material.get().contains(newVal)
                    || prod.unidad.get().contains(newVal)
                    || prod.proveedor.get().contains(newVal);
            });
    }
    
    public void createTable() {
        
        // Evento al seleccionar una fila
        table.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> this.selectedProduct = getLeadSelect());
        
        searchField.textProperty().addListener(setupSearchField(table));
        // Estructura de la columna
        JFXTreeTableColumn<Producto, String> id = new JFXTreeTableColumn("ID");
        id.setPrefWidth(100);
        id.setCellValueFactory((TreeTableColumn.CellDataFeatures<Producto, String> param) -> param.getValue().getValue().id_producto);

        JFXTreeTableColumn<Producto, String> material = new JFXTreeTableColumn("Material");
        material.setPrefWidth(200);
        material.setCellValueFactory((TreeTableColumn.CellDataFeatures<Producto, String> param) -> param.getValue().getValue().material);

        JFXTreeTableColumn<Producto, String> unidad = new JFXTreeTableColumn("Unidad");
        unidad.setPrefWidth(200);
        unidad.setCellValueFactory((TreeTableColumn.CellDataFeatures<Producto, String> param) -> param.getValue().getValue().unidad);

        JFXTreeTableColumn<Producto, String> precio = new JFXTreeTableColumn("Precio");
        precio.setPrefWidth(200);
        precio.setCellValueFactory((TreeTableColumn.CellDataFeatures<Producto, String> param) -> param.getValue().getValue().precio);

        JFXTreeTableColumn<Producto, String> cantidad = new JFXTreeTableColumn("Cantidad");
        cantidad.setPrefWidth(200);
        cantidad.setCellValueFactory((TreeTableColumn.CellDataFeatures<Producto, String> param) -> param.getValue().getValue().cantidad);

        JFXTreeTableColumn<Producto, String> proveedor = new JFXTreeTableColumn("Proveedor");
        proveedor.setPrefWidth(250);
        proveedor.setCellValueFactory((TreeTableColumn.CellDataFeatures<Producto, String> param) -> param.getValue().getValue().proveedor);

        table.getColumns().setAll(id, material, unidad, precio, cantidad, proveedor);
    }
    // Se agrega información a la tabla
    public void createTableView() {
        TreeItem<Producto> root = new RecursiveTreeItem<>(tableInformation(), RecursiveTreeObject::getChildren);
        table.setRoot(root);
        table.setShowRoot(false);
        disableButtons(true);
    }
    // Se obtiene la información
    public ObservableList<Producto> tableInformation() {
        ObservableList<Producto> productos = FXCollections.observableArrayList();
        modelo.getProductos().forEach((x) -> {
            productos.add(new Producto(x.material, x.unidad, x.precio, x.proveedor, x.cantidad, x.id_producto));
        });
        return productos;
    }
    
    // Función agregar
    @FXML
    private void btnAgregarAction(ActionEvent event) throws IOException {
        // Se prepara el modal
        Stage stage = new Stage();
        FXMLLoader modal = new FXMLLoader(getClass().getResource("/View/ProductosModal.fxml"));
        Parent root = modal.load();
        root.getStylesheets().add("/Resources/main.css");
        stage.setScene(new Scene(root));
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.getScene().setFill(Color.TRANSPARENT);
        stage.setTitle("Agregar Producto");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(((Node)event.getSource()).getScene().getWindow() );
        stage.showAndWait();
        createTableView();
    }
    // Función modificar
    @FXML
    private void btnModificarAction() throws IOException {
        // Se prepara el modal
        Stage stage = new Stage();
        FXMLLoader modal = new FXMLLoader(getClass().getResource("/View/ProductosModal.fxml"));
        Parent root = modal.load();
        root.getStylesheets().add("/Resources/main.css");
        ProductosModalController pmc = modal.getController();
        // Se mandan los valores del producto a modificar al controlador
        pmc.setValuesToModify(selectedProduct.material.get(), selectedProduct.unidad.get(), selectedProduct.precio.get(), selectedProduct.proveedor.get(), selectedProduct.cantidad.get(), selectedProduct.id_producto.get());
        stage.setScene(new Scene(root));
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.getScene().setFill(Color.TRANSPARENT);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(((Node)btnModificar).getScene().getWindow());
        stage.showAndWait();
        createTableView();
    }
    // Función eliminar
    @FXML
    private void btnEliminarAction() {
        if(dialogs.displayMessage((Stage) btnEliminar.getScene().getWindow(), "Eliminar producto", "¿Estás seguro que deseas eliminar este producto?", "Si", "No")) {
            if(this.selectedProduct.id_producto != null) {
                // Se da baja lógica al producto seleccionado
                modelo.eliminarProducto(this.selectedProduct.id_producto.get());
                this.selectedProduct.setId_producto("");
            } else {
                System.out.println("selecciona algo");
            }
            createTableView();
        }
    }
    // Función para agregar unidad
    @FXML
    void btnUnidadAction(ActionEvent event) throws IOException {
        // Se prepara el modal
        Stage stage = new Stage();
        FXMLLoader modal = new FXMLLoader(getClass().getResource("/View/ProductosModal.fxml"));
        Parent root = modal.load();
        root.getStylesheets().add("/Resources/main.css");
        ProductosModalController pmc = modal.getController();
        pmc.agregarUnidad();
        stage.setScene(new Scene(root));
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.getScene().setFill(Color.TRANSPARENT);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(((Node)btnModificar).getScene().getWindow());
        stage.showAndWait();
        createTableView();
    }
    
}
