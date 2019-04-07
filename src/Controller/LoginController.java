package Controller;

import Model.Dialogs;
import Model.LoginModel;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

// FXML Clase del Controlador "Login"
public class LoginController implements Initializable {
    
    private final Dialogs dialogs = new Dialogs();
    
    @FXML 
    private TextField txtUser;
    @FXML 
    private PasswordField txtPassword;
    @FXML 
    private JFXButton btnLogin;
    @FXML
    private AnchorPane panel;
    private LoginModel model;
    
    // Inicializa la clase del controlador
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(()->txtUser.requestFocus());
        txtUser.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("a-zA-ZñÑ\\d*")) {
                txtUser.setText(newValue.replaceAll("[^a-zA-ZñÑ\\d]*", ""));
            }
        });
        txtPassword.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("a-zA-ZñÑ\\d*")) {
                txtPassword.setText(newValue.replaceAll("[^a-zA-ZñÑ\\d]*", ""));
            }
        });
    }
    
    public void initModel(LoginModel model) {
        if(this.model != null) {
            throw new IllegalStateException("Model can only be initialized once");
        } else {
            this.model = model;
        }
    }
   
    public void Login(ActionEvent event) throws IOException{
        if(txtUser.getText().equals("") || txtPassword.getText().equals("")) {
            dialogs.displayMessage((Stage) btnLogin.getScene().getWindow(), "Advertencia", "Usuario o contraseña faltante", "Ok");
        } else {
            //Valida que los datos de usuario/password sean correctos
            int id = model.validarLogin(txtUser.getText(), txtPassword.getText()); 
            if(id != 0) {
                //Crea controlador de la vista Menú y manda el id del perfil que ingresó, prepara la nueva vista a cargar
                FXMLLoader testLoader = new FXMLLoader(getClass().getResource("/View/Menu.fxml"));
                Parent testParent = testLoader.load();
                MenuController testController = testLoader.getController();
                testController.setIdPerfil(id);
                Scene testScene = new Scene(testParent);
                testScene.getStylesheets().add("/Resources/main.css");
                // Obtiene la información de Stage
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                //Carga la vista de menu
                window.setScene(testScene);
                window.show();
                Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
                window.setX((primScreenBounds.getWidth() - window.getWidth()) / 2);
                window.setY((primScreenBounds.getHeight() - window.getHeight()) / 2);
            } else {
                dialogs.displayMessage((Stage) btnLogin.getScene().getWindow(), "Advertencia", "Usuario o contraseña incorrecta", "Ok");
            }
        }
    }
    
    
}
