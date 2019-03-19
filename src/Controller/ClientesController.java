package Controller;

import Model.Cliente;
import Model.ClientesModel;
import Model.Dialogs;
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

public class ClientesController implements Initializable {

    private final ClientesModel modelo = new ClientesModel();
    final private Dialogs dialogs = new Dialogs();
    private Cliente selectedCliente = new Cliente();
    
    @FXML
    private JFXButton btnAgregar;
    @FXML
    private JFXButton btnModificar;
    @FXML
    private JFXTreeTableView<Cliente> table;
    @FXML
    private JFXTextField searchField; 

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        createTable();
        createTableView();
        disableButtons(true);
    }    
    
    public void disableButtons(boolean value) {
        btnModificar.setDisable(value);
    }
    
    private Cliente getLeadSelect() {
        TreeItem<Cliente> selectedItem = (TreeItem<Cliente>) table.getSelectionModel().getSelectedItem();
        if(!selectedItem.getValue().id_cliente.get().equals("1")) {
            disableButtons(false);
        } else {
            disableButtons(true);
        }
        return selectedItem == null ? null : selectedItem.getValue();
    }
    
    private ChangeListener<String> setupSearchField(final JFXTreeTableView<Cliente> tableView) {
        return (o, oldVal, newVal) ->
            tableView.setPredicate(personProp -> {
                final Cliente prod = personProp.getValue();
                return prod.nombre.get().contains(newVal)
                       || prod.apellido.get().contains(newVal);
            });
    }
    
    public void createTable() {
        
        table.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> this.selectedCliente = getLeadSelect());
        
        searchField.textProperty().addListener(setupSearchField(table));

        JFXTreeTableColumn<Cliente, String> id = new JFXTreeTableColumn("ID");
        id.setPrefWidth(100);
        id.setCellValueFactory((TreeTableColumn.CellDataFeatures<Cliente, String> param) -> param.getValue().getValue().id_cliente);

        JFXTreeTableColumn<Cliente, String> nombre = new JFXTreeTableColumn("Nombre");
        nombre.setPrefWidth(200);
        nombre.setCellValueFactory((TreeTableColumn.CellDataFeatures<Cliente, String> param) -> param.getValue().getValue().nombre);

        JFXTreeTableColumn<Cliente, String> apellido = new JFXTreeTableColumn("Apellido");
        apellido.setPrefWidth(200);
        apellido.setCellValueFactory((TreeTableColumn.CellDataFeatures<Cliente, String> param) -> param.getValue().getValue().apellido);

        JFXTreeTableColumn<Cliente, String> domicilio = new JFXTreeTableColumn("Domicilio");
        domicilio.setPrefWidth(250);
        domicilio.setCellValueFactory((TreeTableColumn.CellDataFeatures<Cliente, String> param) -> param.getValue().getValue().domicilio);

        JFXTreeTableColumn<Cliente, String> telefono = new JFXTreeTableColumn("Teléfono");
        telefono.setPrefWidth(175);
        telefono.setCellValueFactory((TreeTableColumn.CellDataFeatures<Cliente, String> param) -> param.getValue().getValue().telefono);

        JFXTreeTableColumn<Cliente, String> rfc = new JFXTreeTableColumn("RFC");
        rfc.setPrefWidth(175);
        rfc.setCellValueFactory((TreeTableColumn.CellDataFeatures<Cliente, String> param) -> param.getValue().getValue().rfc);


        table.getColumns().setAll(id, nombre, apellido, domicilio, telefono, rfc);
    }
    
    public void createTableView() {
        TreeItem<Cliente> root = new RecursiveTreeItem<>(tableInformation(), RecursiveTreeObject::getChildren);
        table.setRoot(root);
        table.setShowRoot(false);
        disableButtons(true);
    }

    public ObservableList<Cliente> tableInformation() {
        ObservableList<Cliente> proveedor = FXCollections.observableArrayList();
        modelo.getClientes().forEach((x) -> {
            proveedor.add(new Cliente(x.id_cliente, x.domicilio, x.rfc, x.nombre,x.apellido, x.telefono));
        });
        return proveedor;
    }
    

    @FXML
    private void btnAgregarAction(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader modal = new FXMLLoader(getClass().getResource("/View/ClientesModal.fxml"));
        Parent root = modal.load();
        root.getStylesheets().add("/Resources/main.css");
        stage.setScene(new Scene(root));
        stage.setTitle("Agregar Clientes");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(((Node)event.getSource()).getScene().getWindow() );
        stage.showAndWait();
        createTableView();
    }

    @FXML
    private void btnModificarAction(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader modal = new FXMLLoader(getClass().getResource("/View/ClientesModal.fxml"));
        Parent root = modal.load();
        root.getStylesheets().add("/Resources/main.css");
        ClientesModalController pmc = modal.getController();
        pmc.setValuesToModify(selectedCliente.id_cliente.get(), selectedCliente.domicilio.get(), selectedCliente.rfc.get(), selectedCliente.nombre.get(),selectedCliente.apellido.get(), selectedCliente.telefono.get());
        stage.setScene(new Scene(root));
        stage.setTitle("Agregar Producto");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(((Node)event.getSource()).getScene().getWindow() );
        stage.showAndWait();
        createTableView();
    }

}
