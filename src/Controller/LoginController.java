package Controller;

import Model.Dialogs;
import Model.LoginModel;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.Base64;
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
import javafx.stage.StageStyle;

// FXML Clase del Controlador "Login"
public class LoginController implements Initializable {
    // Instanciación de la clase para mostrar dialogos
    private final Dialogs dialogs = new Dialogs();
    // Declaración de componentes
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
        // Se pone el focus al campo de usuario
        Platform.runLater(()->txtUser.requestFocus());
        // Se valida que solo puedas insertar letras de la a-z, A-Z, ñ, Ñ y digitos del 0 al 9
        txtUser.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("a-zA-ZñÑ\\d*")) {
                txtUser.setText(newValue.replaceAll("[^a-zA-ZñÑ\\d]*", ""));
            }
        });
        // Se valida que solo puedas insertar letras de la a-z, A-Z, ñ, Ñ y digitos del 0 al 9
        txtPassword.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("a-zA-ZñÑ\\d*")) {
                txtPassword.setText(newValue.replaceAll("[^a-zA-ZñÑ\\d]*", ""));
            }
        });
    }
    // Se inicializa el modelo
    public void initModel(LoginModel model) {
        if(this.model != null) {
            throw new IllegalStateException("Model can only be initialized once");
        } else {
            this.model = model;
        }
    }
    //Función para encriptar la contraseña
    private static String encriptar(String s) throws UnsupportedEncodingException{
        return Base64.getEncoder().encodeToString(s.getBytes("utf-8"));
    }
    
   // Función para ingresar
    public void Login(ActionEvent event) throws IOException{
        // Se valida que los campos tengan texto
        if(txtUser.getText().equals("") || txtPassword.getText().equals("")) {
            dialogs.displayMessage((Stage) btnLogin.getScene().getWindow(), "Advertencia", "Usuario o contraseña faltante", "Ok");
        } else {
            //Encritar contraseña para compararla ya encriptada
            String encrip = encriptar(txtPassword.getText());
                
            //Valida que los datos de usuario/password sean correctos                      
            int id = model.validarLogin(txtUser.getText(), encrip); 
            // Si el modelo encontro al usuario y contraseña, se ingresa al bloque del if
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
                // Centra la vista
                Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
                window.setX((primScreenBounds.getWidth() - window.getWidth()) / 2);
                window.setY((primScreenBounds.getHeight() - window.getHeight()) / 2);
            } else {
                dialogs.displayMessage((Stage) btnLogin.getScene().getWindow(), "Advertencia", "Usuario o contraseña incorrecta", "Ok");
            }
        }
    }
    
    
}
