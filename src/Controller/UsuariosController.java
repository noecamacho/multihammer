package Controller;

import Model.Dialogs;
import Model.Usuario;
import Model.UsuariosModel;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
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

public class UsuariosController implements Initializable {
    private final UsuariosModel modelo = new UsuariosModel();
    final private Dialogs dialogs = new Dialogs();
    private Usuario selectedUsuario = new Usuario();
    
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
        view();
        createTableView();
    }    
        
    public void createTableView() {
        TreeItem<Usuario> root = new RecursiveTreeItem<>(tableInformation(), RecursiveTreeObject::getChildren);
        table.setRoot(root);
        table.setShowRoot(false);
        
    }

    public ObservableList<Usuario> tableInformation() {
        ObservableList<Usuario> usuarios = FXCollections.observableArrayList();
        modelo.getUsuarios().forEach((x) -> {
            usuarios.add(new Usuario(x.id_usuario, x.usuario, x.id_perfil, x.password, x.estado));
        });
        return usuarios;
    }
    
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
    
    public void view(){
        col_id_usuario.setCellValueFactory((TreeTableColumn.CellDataFeatures<Usuario, String> param) -> param.getValue().getValue().id_usuario);
        col_usuario.setCellValueFactory((TreeTableColumn.CellDataFeatures<Usuario, String> param) -> param.getValue().getValue().usuario);
        col_perfil.setCellValueFactory((TreeTableColumn.CellDataFeatures<Usuario, String> param) -> param.getValue().getValue().id_perfil);
        col_estado.setCellValueFactory((TreeTableColumn.CellDataFeatures<Usuario, String> param) -> param.getValue().getValue().estado);
    }
}
