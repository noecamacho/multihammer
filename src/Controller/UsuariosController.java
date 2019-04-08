package Controller;

import Model.Dialogs;
import Model.Producto;
import Model.Usuario;
import Model.UsuariosModel;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

public class UsuariosController implements Initializable {
    //inicializacion del modelo UsuariosModel
    private final UsuariosModel modelo = new UsuariosModel();
        // Instanciación de la clase para mostrar dialogos
    final private Dialogs dialogs = new Dialogs();
    private Usuario selectedUsuario = new Usuario();
    //Declaración de componentes
    @FXML
    private JFXButton btnAgregar;
    @FXML
    private JFXTreeTableView<Usuario> table;
    @FXML
    private JFXTextField searchField;
    @FXML
    private TreeTableColumn<Usuario, String> col_id_usuario;
    @FXML
    private TreeTableColumn<Usuario, String> col_usuario;
    @FXML
    private TreeTableColumn<Usuario, String> col_perfil;
    @FXML
    private TreeTableColumn<Usuario, String> col_estado;
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Se muestran las columnas
        view();
        searchField.textProperty().addListener(setupSearchField(table));
        createTableView();
    }    
        // Función de funcionalidad de campo de búsqueda
    private ChangeListener<String> setupSearchField(final JFXTreeTableView<Usuario> tableView) {
        return (o, oldVal, newVal) ->
            tableView.setPredicate(personProp -> {
                final Usuario prod = personProp.getValue();
                return prod.usuario.get().contains(newVal)
                       //|| prod.estado.get().contains(newVal)
                        || prod.id_perfil.get().contains(newVal)
                        || prod.id_usuario.get().contains(newVal);
            });
    }
        // Se agrega información a la tabla
    public void createTableView() {
        TreeItem<Usuario> root = new RecursiveTreeItem<>(tableInformation(), RecursiveTreeObject::getChildren);
        table.setRoot(root);
        table.setShowRoot(false);
        
    }
    
    // Se obtiene la información
    public ObservableList<Usuario> tableInformation() {
        ObservableList<Usuario> usuarios = FXCollections.observableArrayList();
        modelo.getUsuarios().forEach((x) -> {
            usuarios.add(new Usuario(x.id_usuario, x.usuario, x.id_perfil, x.password, x.estado));
        });
        return usuarios;
    }
    // Función agregar
    @FXML
    void btnAgregarAction(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader modal = new FXMLLoader(getClass().getResource("/View/UsuariosModal.fxml"));
        Parent root = modal.load();
        root.getStylesheets().add("/Resources/main.css");
        stage.setScene(new Scene (root));
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(((Node)event.getSource()).getScene().getWindow() );
        stage.showAndWait();
        
    }
            //Se muestran las columnas
    public void view(){
        col_id_usuario.setCellValueFactory((TreeTableColumn.CellDataFeatures<Usuario, String> param) -> param.getValue().getValue().id_usuario);
        col_usuario.setCellValueFactory((TreeTableColumn.CellDataFeatures<Usuario, String> param) -> param.getValue().getValue().usuario);
        col_perfil.setCellValueFactory((TreeTableColumn.CellDataFeatures<Usuario, String> param) -> param.getValue().getValue().id_perfil);
        //col_estado.setCellValueFactory((TreeTableColumn.CellDataFeatures<Usuario, String> param) -> param.getValue().getValue().estado);
        
        // Crea columna con un JFXToggleButton que indica el estado de existencia del producto
        JFXTreeTableColumn<Usuario, String> estado = new JFXTreeTableColumn<>("Estado");
        estado.setPrefWidth(272);
        Callback<TreeTableColumn<Usuario, String>, TreeTableCell<Usuario, String>> cellFactory
                =                 
        (final TreeTableColumn<Usuario, String> param) -> {
            final TreeTableCell<Usuario, String> cell = new TreeTableCell<Usuario, String>() {
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
                        if(getTreeTableRow().getItem().estado.get().equals("Activo") || getTreeTableRow().getItem().estado.get().equals("1")) {
                            btn.setSelected(true);
                        } else {
                            btn.setSelected(false);
                        }
                        // Acción al activar
                        btn.setOnAction(event -> {
                            // Cambia el estado del producto
                            modelo.cambiarEstado(getTreeTableRow().getItem().id_usuario.get());
                        });
                        setGraphic(btn);
                        setText(null);
                    }
                }
            };
            return cell;
        };
        estado.setCellFactory(cellFactory);
        
        table.getColumns().setAll(col_id_usuario, col_usuario, col_perfil, estado);
    }
    
    
}
