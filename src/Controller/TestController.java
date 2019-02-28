/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.LoginModel;
import Model.MenuModel;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Kevin Lizárraga
 */
public class TestController implements Initializable {

    private int idPerfil;
    private MenuModel menuModel = new MenuModel();
    
    @FXML
    private AnchorPane panel;
    @FXML
    private Button btnMenu;
    @FXML
    private FlowPane navBar;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        displayMenu();
    }    
    
    public void generateModules() {
        
    }
    
    public void createModulesButtons(ArrayList<String> modules) {
        Button inicio = new Button("Inicio");
        inicio.setOnAction((event) -> {
            panel.getChildren().clear();
        });
        navBar.getChildren().add(inicio);
        for(String x : modules) {
            Button button = new Button(x);
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
        salir.setOnAction((event) -> {
            System.out.println("Saliendo");
        });
        navBar.getChildren().add(salir);
    }
    
    public void loadView(String name) throws IOException {
        FXMLLoader loadFXML = new FXMLLoader(getClass().getResource("/View/"+name+".fxml"));
        Parent root = loadFXML.load();
        LoginController loginController = loadFXML.getController();
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
        btnMenu.setOnAction((ActionEvent evt)->{
            if(navBar.getTranslateX()!=0){
                openNav.play();
            }else{
                closeNav.setToX(-(navBar.getWidth()));
                closeNav.play();
            }
        });
    }
    
}
