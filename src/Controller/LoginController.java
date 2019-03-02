package Controller;

import Model.LoginModel;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
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
import javafx.stage.Stage;

// FXML Clase del Controlador "Login"
public class LoginController implements Initializable {
    @FXML JFXTextField txtUser;
    @FXML JFXPasswordField txtPassword;
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
   
    public void Login(ActionEvent event) throws IOException{
        if(txtUser.getText().equals("") || txtPassword.getText().equals("")) {
            System.out.println("Usuario o contraseña faltante");
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
                // Obtiene la información de Stage
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                //Carga la vista de menu
                window.setScene(testScene);
                window.show();
            }
        }
    }
    
}
