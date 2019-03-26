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
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ClientesController implements Initializable {
    //Instanciación del modelo
    private final ClientesModel modelo = new ClientesModel();
    //Instanciación de clase para mostrar dialogos
    final private Dialogs dialogs = new Dialogs();
    //Variable auxiliar para guardar los valores del elemento seleccionado en la tabla
    private Cliente selectedCliente = new Cliente();
    //Declaración de variables
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
        //Se crea la tabla y se carga con valores
        createTable();
        createTableView();
        //Se desactiva el botón Modificar
        disableButtons(true);
    }    
    //Función para desactivar el botón Modificar
    public void disableButtons(boolean value) {
        btnModificar.setDisable(value);
    }
    //Función que se llama al hacer click sobre una fila de la tabla
    private Cliente getLeadSelect() {
        TreeItem<Cliente> selectedItem = (TreeItem<Cliente>) table.getSelectionModel().getSelectedItem();
        //Llama a la función disableButtons si el id_cliente es diferente 1 (si no es Público General)
        if(!selectedItem.getValue().id_cliente.get().equals("1")) {
            disableButtons(false);
        } else {
            disableButtons(true);
        }
        //Asigna el valor de la fila seleccionada a la variable auxiliar
        return selectedItem == null ? null : selectedItem.getValue();
    }
    //Función que permite buscar con el JFXTextField llamado searchField 
    private ChangeListener<String> setupSearchField(final JFXTreeTableView<Cliente> tableView) {
        return (o, oldVal, newVal) ->
            tableView.setPredicate(personProp -> {
                final Cliente prod = personProp.getValue();
                return prod.nombre.get().contains(newVal)
                       || prod.apellido.get().contains(newVal);
            });
    }
    
    public void createTable() {
        // Se asigna el evento de click en las filas para llamar la función getLeadSelect()
        table.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> this.selectedCliente = getLeadSelect());
        //Se agrega el listener a searchField para buscar en la tabla
        searchField.textProperty().addListener(setupSearchField(table));
        //Se declara la estructura de la tabla
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

        //Se agregan las columnas declaradas anteriormente a la tabla
        table.getColumns().setAll(id, nombre, apellido, domicilio, telefono, rfc);
    }
    // Función para mostrar valores en la tabla
    public void createTableView() {
        TreeItem<Cliente> root = new RecursiveTreeItem<>(tableInformation(), RecursiveTreeObject::getChildren);
        table.setRoot(root);
        table.setShowRoot(false);
        disableButtons(true);
    }
    // Función que nos regresa los datos que se mostraran en la tabla
    public ObservableList<Cliente> tableInformation() {
        ObservableList<Cliente> proveedor = FXCollections.observableArrayList();
        modelo.getClientes().forEach((x) -> {
            proveedor.add(new Cliente(x.id_cliente, x.domicilio, x.rfc, x.nombre,x.apellido, x.telefono));
        });
        return proveedor;
    }
    
    // Función Agragar
    @FXML
    private void btnAgregarAction(ActionEvent event) throws IOException {
        // Se carga el modal para agregar clientes
        Stage stage = new Stage();
        FXMLLoader modal = new FXMLLoader(getClass().getResource("/View/ClientesModal.fxml"));
        Parent root = modal.load();
        // Se agrega el archivo main.css al Stage que se declaró
        root.getStylesheets().add("/Resources/main.css");
        stage.setScene(new Scene(root));
        // Se remueve la barra de windows
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.getScene().setFill(Color.TRANSPARENT);
        // Se inicia con modalidad de Modal
        stage.initModality(Modality.WINDOW_MODAL);
        // Se asigna el padre del modal
        stage.initOwner(((Node)event.getSource()).getScene().getWindow() );
        // Se detiene el flujo mientras que el modal se encuentre activo
        stage.showAndWait();
        // Se vuelven a imprimir los valores de la tabla
        createTableView();
    }
    // Función Modificar
    @FXML
    private void btnModificarAction(ActionEvent event) throws IOException {
        // Se carga el modal para agregar clientes
        Stage stage = new Stage();
        FXMLLoader modal = new FXMLLoader(getClass().getResource("/View/ClientesModal.fxml"));
        Parent root = modal.load();
        // Se agrega el archivo main.css al Stage que se declaró
        root.getStylesheets().add("/Resources/main.css");
        // Se instancia el controlador del modal de clientes
        ClientesModalController pmc = modal.getController();
        // Se manda al controlador de la vista ClientesModal los valores de la fila seleccionada
        pmc.setValuesToModify(selectedCliente.id_cliente.get(), selectedCliente.domicilio.get(), selectedCliente.rfc.get(), selectedCliente.nombre.get(),selectedCliente.apellido.get(), selectedCliente.telefono.get());
        stage.setScene(new Scene(root));
        // Se remueve la barra de windows
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.getScene().setFill(Color.TRANSPARENT);
        // Se inicia con modalidad de Modal
        stage.initModality(Modality.WINDOW_MODAL);
        // Se asigna el padre del modal
        stage.initOwner(((Node)event.getSource()).getScene().getWindow() );
        // Se detiene el flujo mientras que el modal se encuentre activo
        stage.showAndWait();
        // Se vuelven a imprimir los valores de la tabla
        createTableView();
    }

}
