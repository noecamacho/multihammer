package Controller;

import Model.LoginModel;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

// FXML Clase del Controlador "Login"
public class LoginController implements Initializable {
    @FXML TextField txtUser;
    @FXML PasswordField txtPassword;
    private LoginModel model;
    
    // Inicializa la clase del controlador
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public void initModel(LoginModel model) {
        if(this.model != null) {
            throw new IllegalStateException("Model can only be initialized once");
        } else {
            this.model = model;
        }
    }
    
    public void Login(){
        model.validarLogin(txtUser.getText(), txtPassword.getText());
    }
    
}
