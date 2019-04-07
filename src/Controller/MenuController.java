package Controller;

import Model.ComboBoxClass;
import Model.LoginModel;
import Model.MenuModel;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXHamburger;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
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
    
    public void createModulesButtons(ArrayList<ComboBoxClass> modules) {
        MaterialDesignIconView iconInicio = new MaterialDesignIconView();
        iconInicio.setGlyphName("HOME");
        JFXButton inicio = new JFXButton("  Inicio");
        inicio.getStyleClass().add("menuBtn");
        inicio.setOnAction((event) -> {
            panel.getChildren().clear();
        });
        inicio.setGraphic(iconInicio);
        navBar.getChildren().add(inicio);
        for(ComboBoxClass x : modules) {
            MaterialDesignIconView icon = new MaterialDesignIconView();
            icon.setGlyphName(x.getNombre());
            JFXButton button = new JFXButton("  " + x.getId());
            button.setGraphic(icon);
            button.getStyleClass().add("menuBtn");
            button.setOnAction((event) -> {
                try {
                    loadView(x.getId());
                } catch (IOException ex) {
                    System.out.println(ex);
                }
            });
            navBar.getChildren().add(button);
        }
        JFXButton salir = new JFXButton("  Salir");
        MaterialDesignIconView iconSalir = new MaterialDesignIconView();
        iconSalir.setGlyphName("EXIT_TO_APP");
        salir.setGraphic(iconSalir);
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
        panel.getChildren().setAll(root);
    }
    
    public void setIdPerfil(int idPerfil) {
        this.idPerfil = idPerfil;
        createModulesButtons(MenuModel.menuxperfil(idPerfil));
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
