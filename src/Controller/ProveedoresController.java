package Controller;

import Model.Dialogs;
import Model.Producto;
import Model.ProductosModel;
import Model.Proveedor;
import Model.ProveedorModel;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
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
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

public class ProveedoresController implements Initializable {
    // Declaraciòn/Instanciaciòn de variables
    private final ProveedorModel modelo = new ProveedorModel();
    final private Dialogs dialogs = new Dialogs();
    // Variable auxiliar
    private Proveedor selectedProveedor = new Proveedor();
    // Declaraciòn de componentes
    @FXML
    private JFXButton btnAgregar;
    @FXML
    private JFXButton btnModificar;
    @FXML
    private JFXTreeTableView<Proveedor> table;
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
        // Se desactivan los botones cuando el campo de bùsqueda tiene el focus
        searchField.focusedProperty().addListener((ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) -> {
            if(newPropertyValue) {
                disableButtons(true);
            }
        });
    }    
    // Funciòn para desactivar los botones
    public void disableButtons(boolean value) {
        btnModificar.setDisable(value);
    }
    // Se dan los valores a la variable auxiliar de la fila seleccionada
    private Proveedor getLeadSelect() {
        TreeItem<Proveedor> selectedItem = (TreeItem<Proveedor>) table.getSelectionModel().getSelectedItem();
        disableButtons(false);
        return selectedItem == null ? null : selectedItem.getValue();
    }
    // Funciòn para que el campo de bùsqueda funcione
    private ChangeListener<String> setupSearchField(final JFXTreeTableView<Proveedor> tableView) {
        return (o, oldVal, newVal) ->
            tableView.setPredicate(personProp -> {
                final Proveedor prod = personProp.getValue();
                return prod.razon_social.get().contains(newVal);
            });
    }
    // Funciòn para crear la tabla
    public void createTable() {
        // Se agrega el evento de click a la fila
        table.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> this.selectedProveedor = getLeadSelect());
        // Se agrega el evento al campo de bùsqueda
        searchField.textProperty().addListener(setupSearchField(table));
        // Estructura de la tabla

        JFXTreeTableColumn<Proveedor, String> razonSocial = new JFXTreeTableColumn("Razón Social");
        razonSocial.setPrefWidth(200);
        razonSocial.setCellValueFactory((TreeTableColumn.CellDataFeatures<Proveedor, String> param) -> param.getValue().getValue().razon_social);

        JFXTreeTableColumn<Proveedor, String> domicilio = new JFXTreeTableColumn("Domicilio");
        domicilio.setPrefWidth(290);
        domicilio.setCellValueFactory((TreeTableColumn.CellDataFeatures<Proveedor, String> param) -> param.getValue().getValue().domicilio);

        JFXTreeTableColumn<Proveedor, String> telefono = new JFXTreeTableColumn("Teléfono");
        telefono.setPrefWidth(195);
        telefono.setCellValueFactory((TreeTableColumn.CellDataFeatures<Proveedor, String> param) -> param.getValue().getValue().telefono);

        JFXTreeTableColumn<Proveedor, String> rfc = new JFXTreeTableColumn("RFC");
        rfc.setPrefWidth(195);
        rfc.setCellValueFactory((TreeTableColumn.CellDataFeatures<Proveedor, String> param) -> param.getValue().getValue().rfc);

        // Crea columna con un JFXToggleButton que indica el estado de existencia del producto
        JFXTreeTableColumn<Proveedor, String> estado = new JFXTreeTableColumn<>("Estado");
        estado.setPrefWidth(200);
        Callback<TreeTableColumn<Proveedor, String>, TreeTableCell<Proveedor, String>> cellFactory
                =                 
        (final TreeTableColumn<Proveedor, String> param) -> {
            final TreeTableCell<Proveedor, String> cell = new TreeTableCell<Proveedor, String>() {
                // Declara el ToggleButton
                final JFXToggleButton btn = new JFXToggleButton();
                
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        // Establece el estado en que aparece el botón segun su existencia
                        if(getTreeTableRow().getItem().estado.get().equals("1")) {
                            btn.setSelected(true);
                        } else {
                            btn.setSelected(false);
                        }
                        // Acción al activar
                        btn.setOnAction(event -> {
                            // Cambia el estado del producto
                            modelo.cambiarEstado(getTreeTableRow().getItem().id_proveedor.get());
                        });
                        setGraphic(btn);
                        setText(null);
                    }
                }
            };
            return cell;
        };
        estado.setCellFactory(cellFactory);
        
        table.getColumns().setAll(razonSocial, domicilio, telefono, rfc, estado);
    }
    // Asigna los valores a la tabla
    public void createTableView() {
        TreeItem<Proveedor> root = new RecursiveTreeItem<>(tableInformation(), RecursiveTreeObject::getChildren);
        table.setRoot(root);
        table.setShowRoot(false);
        disableButtons(true);
    }
    // Se da la informaciòn de la tabla
    public ObservableList<Proveedor> tableInformation() {
        ObservableList<Proveedor> proveedor = FXCollections.observableArrayList();
        modelo.getProveedores().forEach((x) -> {
            proveedor.add(new Proveedor(x.id_proveedor, x.domicilio, x.rfc, x.razon_social, x.telefono, x.estado));
        });
        return proveedor;
    }
    
    // Funciòn agregar
    @FXML
    private void btnAgregarAction(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader modal = new FXMLLoader(getClass().getResource("/View/ProveedoresModal.fxml"));
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
    // Funciòn modificar
    @FXML
    private void btnModificarAction(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader modal = new FXMLLoader(getClass().getResource("/View/ProveedoresModal.fxml"));
        Parent root = modal.load();
        root.getStylesheets().add("/Resources/main.css");
        ProveedoresModalController pmc = modal.getController();
        // Se mandan los valores a modificar del elemento seleccionado
        pmc.setValuesToModify(selectedProveedor.id_proveedor.get(), selectedProveedor.domicilio.get(), selectedProveedor.rfc.get(), selectedProveedor.razon_social.get(), selectedProveedor.telefono.get());
        stage.setScene(new Scene(root));
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.getScene().setFill(Color.TRANSPARENT);
        stage.setTitle("Agregar Producto");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(((Node)event.getSource()).getScene().getWindow() );
        stage.showAndWait();
        createTableView();
    }
    
}
