package Controller;

import Model.Dialogs;
import Model.ProductosModel;
import Model.Proveedor;
import Model.ProveedorModel;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
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
import javafx.scene.control.TreeTableColumn;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ProveedoresController implements Initializable {

    private final ProveedorModel modelo = new ProveedorModel();
    final private Dialogs dialogs = new Dialogs();
    private Proveedor selectedProveedor = new Proveedor();
    
    @FXML
    private JFXButton btnAgregar;
    @FXML
    private JFXButton btnModificar;
    @FXML
    private JFXButton btnEliminar;
    @FXML
    private JFXTreeTableView<Proveedor> table;
    @FXML
    private JFXTextField searchField; 

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        createTable();
        createTableView();
        searchField.focusedProperty().addListener((ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) -> {
            if(newPropertyValue) {
                disableButtons(true);
            }
        });
    }    
    
    public void disableButtons(boolean value) {
        btnModificar.setDisable(value);
        btnEliminar.setDisable(value);
    }
    
    private Proveedor getLeadSelect() {
        TreeItem<Proveedor> selectedItem = (TreeItem<Proveedor>) table.getSelectionModel().getSelectedItem();
        disableButtons(false);
        return selectedItem == null ? null : selectedItem.getValue();
    }
    
    private ChangeListener<String> setupSearchField(final JFXTreeTableView<Proveedor> tableView) {
        return (o, oldVal, newVal) ->
            tableView.setPredicate(personProp -> {
                final Proveedor prod = personProp.getValue();
                return prod.razon_social.get().contains(newVal);
            });
    }
    
    public void createTable() {
        
        table.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> this.selectedProveedor = getLeadSelect());
        
        searchField.textProperty().addListener(setupSearchField(table));

        JFXTreeTableColumn<Proveedor, String> id = new JFXTreeTableColumn("ID");
        id.setPrefWidth(100);
        id.setCellValueFactory((TreeTableColumn.CellDataFeatures<Proveedor, String> param) -> param.getValue().getValue().id_proveedor);

        JFXTreeTableColumn<Proveedor, String> razonSocial = new JFXTreeTableColumn("Razón Social");
        razonSocial.setPrefWidth(250);
        razonSocial.setCellValueFactory((TreeTableColumn.CellDataFeatures<Proveedor, String> param) -> param.getValue().getValue().razon_social);

        JFXTreeTableColumn<Proveedor, String> domicilio = new JFXTreeTableColumn("Domicilio");
        domicilio.setPrefWidth(300);
        domicilio.setCellValueFactory((TreeTableColumn.CellDataFeatures<Proveedor, String> param) -> param.getValue().getValue().domicilio);

        JFXTreeTableColumn<Proveedor, String> telefono = new JFXTreeTableColumn("Teléfono");
        telefono.setPrefWidth(200);
        telefono.setCellValueFactory((TreeTableColumn.CellDataFeatures<Proveedor, String> param) -> param.getValue().getValue().telefono);

        JFXTreeTableColumn<Proveedor, String> rfc = new JFXTreeTableColumn("RFC");
        rfc.setPrefWidth(250);
        rfc.setCellValueFactory((TreeTableColumn.CellDataFeatures<Proveedor, String> param) -> param.getValue().getValue().rfc);


        table.getColumns().setAll(id, razonSocial, domicilio, telefono, rfc);
    }
    
    public void createTableView() {
        TreeItem<Proveedor> root = new RecursiveTreeItem<>(tableInformation(), RecursiveTreeObject::getChildren);
        table.setRoot(root);
        table.setShowRoot(false);
        disableButtons(true);
    }

    public ObservableList<Proveedor> tableInformation() {
        ObservableList<Proveedor> proveedor = FXCollections.observableArrayList();
        modelo.getProveedores().forEach((x) -> {
            proveedor.add(new Proveedor(x.id_proveedor, x.domicilio, x.rfc, x.razon_social, x.telefono));
        });
        return proveedor;
    }
    

    @FXML
    private void btnAgregarAction(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader modal = new FXMLLoader(getClass().getResource("/View/ProveedoresModal.fxml"));
        Parent root = modal.load();
        root.getStylesheets().add("/Resources/main.css");
        stage.setScene(new Scene(root));
        stage.setTitle("Agregar Producto");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(((Node)event.getSource()).getScene().getWindow() );
        stage.showAndWait();
        createTableView();
    }

    @FXML
    private void btnModificarAction(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader modal = new FXMLLoader(getClass().getResource("/View/ProveedoresModal.fxml"));
        Parent root = modal.load();
        root.getStylesheets().add("/Resources/main.css");
        ProveedoresModalController pmc = modal.getController();
        pmc.setValuesToModify(selectedProveedor.id_proveedor.get(), selectedProveedor.domicilio.get(), selectedProveedor.rfc.get(), selectedProveedor.razon_social.get(), selectedProveedor.telefono.get());
        stage.setScene(new Scene(root));
        stage.setTitle("Agregar Producto");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(((Node)event.getSource()).getScene().getWindow() );
        stage.showAndWait();
        createTableView();
    }

    @FXML
    private void btnEliminarAction(ActionEvent event) {
        if(dialogs.displayMessage((Stage) btnEliminar.getScene().getWindow(), "Eliminar proveedor", "¿Estás seguro que deseas eliminar este proveedor?", "Si", "No")) {
            if(this.selectedProveedor.id_proveedor != null) {
                modelo.eliminarProveedor(this.selectedProveedor.id_proveedor.get());
                this.selectedProveedor.setId_proveedor("");
            } else {
                System.out.println("selecciona algo");
            }
            createTableView();
        }
    } 
    
}
