package Controller;

import Model.ComboBoxClass;
import Model.Dialogs;
import Model.UsuariosModel;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class UsuariosModalController implements Initializable {

    final private UsuariosModel modelo = new UsuariosModel();
    final private Dialogs dialogs = new Dialogs();
    //@FXML
    //private Label labelHeader;
    @FXML
    private JFXButton btnAgregar;
    @FXML
    private TextField txtUsuario;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private PasswordField txtPasswordConfirmacion;
    @FXML
    private JFXComboBox<ComboBoxClass> txtID_perfil;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnAgregar.setOnAction((event) -> {
            agregar();
        });
        txtUsuario.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("a-zA-ZñÑáéíóúÁÉÍÓÚ\\s*")) {
                txtUsuario.setText(newValue.replaceAll("[^a-zA-ZñÑáéíóúÁÉÍÓÚ\\s]*", ""));
            }
        });
        txtPassword.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("a-zA-ZñÑáéíóúÁÉÍÓÚ\\s*")) {
                txtPassword.setText(newValue.replaceAll("[^a-zA-ZñÑáéíóúÁÉÍÓÚ\\s]*", ""));
            }
        });
        txtID_perfil.setConverter(new StringConverter<ComboBoxClass>() {
            @Override
            public String toString(ComboBoxClass object) {
                return object.getNombre();
            }

            @Override
            public ComboBoxClass fromString(String string) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        //txtID_perfil.valueProperty().addListener((obs, oldval, newval) -> {
          //  if(newval != null)
            //    setUnidades(txtMaterial.getSelectionModel().getSelectedItem().getId());
        //});
        setPerfiles();
    }    
    
    private void btnAgregarAction(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader modal = new FXMLLoader(getClass().getResource("/View/UsuariosModal.fxml"));
        Parent root = modal.load();
        root.getStylesheets().add("/Resources/main.css");
        stage.setScene(new Scene(root));
        stage.setTitle("Agregar Producto");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(((Node)event.getSource()).getScene().getWindow() );
        stage.showAndWait();
    }
    
    public void agregar() {
        if (!txtUsuario.getText().equals("") && !txtPassword.getText().equals("") && !txtID_perfil.getSelectionModel().isEmpty()) {
            if(txtPasswordConfirmacion.getText().equals(txtPassword.getText())) {
                String message = modelo.agregarUsuario(txtID_perfil.getValue().getId(),txtUsuario.getText(), txtPassword.getText());
                if(message.equals("Usuario agregado exitosamente")) {
                    Stage stage = (Stage) btnAgregar.getScene().getWindow();
                    stage.close();
                } else {
                    dialogs.displayMessage((Stage) btnAgregar.getScene().getWindow(), "Advertencia", message, "OK");
                }
            } else {
                dialogs.displayMessage((Stage) btnAgregar.getScene().getWindow(), "Advertencia", "Las contraseñas no coinciden en ambos casos", "OK");
            }
        } else {
            dialogs.displayMessage((Stage) btnAgregar.getScene().getWindow(), "Advertencia", "Para agregar un usuario es necesario que todos los campos se encuentren llenos", "OK");
        }
    }
    
    public void setPerfiles() {
        txtID_perfil.setItems(modelo.getPerfiles());
    }
    
}
