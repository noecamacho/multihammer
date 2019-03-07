package Controller;

import Model.LoginModel;
import Model.MenuModel;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXHamburger;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MenuController implements Initializable {

    private int idPerfil;
    private MenuModel menuModel = new MenuModel();
    
    @FXML
    private AnchorPane panel;
    @FXML
    private FlowPane navBar;
    @FXML 
    private JFXHamburger btnMenu;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        displayMenu();
    }    
    
    public void createModulesButtons(ArrayList<String> modules) {
        Button inicio = new Button("Inicio");
        inicio.getStyleClass().add("menuBtn");
        inicio.setOnAction((event) -> {
            panel.getChildren().clear();
        });
        navBar.getChildren().add(inicio);
        for(String x : modules) {
            Button button = new Button(x);
            button.getStyleClass().add("menuBtn");
            button.setOnAction((event) -> {
                try {
                    loadView(x);
                } catch (IOException ex) {
                    System.out.println(ex);
                }
            });
            navBar.getChildren().add(button);
        }
        Button salir = new Button("Salir");
        salir.getStyleClass().add("menuBtn");
        salir.setOnAction((event) -> {
            Stage stage = (Stage) salir.getScene().getWindow();
            stage.close();
        });
        navBar.getChildren().add(salir);
    }
    
    public void loadView(String name) throws IOException {
        FXMLLoader loadFXML = new FXMLLoader(getClass().getResource("/View/"+name+".fxml"));
        Parent root = loadFXML.load();
//        LoginController loginController = loadFXML.getController();
        panel.getChildren().setAll(root);
    }
    
    public void setIdPerfil(int idPerfil) {
        ArrayList<String> modulos = new ArrayList<>();
        this.idPerfil = idPerfil;
        modulos = MenuModel.menuxperfil(idPerfil);
        createModulesButtons(modulos);
    }
    
    public void displayMenu() {
        TranslateTransition openNav=new TranslateTransition(new Duration(350), navBar);
        openNav.setToX(0);
        TranslateTransition closeNav=new TranslateTransition(new Duration(350), navBar);
        btnMenu.addEventHandler(MouseEvent.MOUSE_PRESSED, (e)->{
            if(navBar.getTranslateX()!=0){
                openNav.play();
            }else{
                closeNav.setToX(-(navBar.getWidth()));
                closeNav.play();
            }
        });
    }
}
