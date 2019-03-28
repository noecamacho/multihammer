package Controller;

import Model.ComboBoxClass;
import Model.Dialogs;
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
    // Declaración/Instanciación de variables
    private int idPerfil;
    private MenuModel menuModel = new MenuModel();
    private final Dialogs dialogs = new Dialogs();
    // Declaración de componentes
    @FXML
    private AnchorPane panel;
    @FXML
    private FlowPane navBar;
    @FXML 
    private JFXHamburger btnMenu;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Se desplega el menú
        displayMenu();
    }    
    // Función para crear los módulos del menú
    public void createModulesButtons(ArrayList<ComboBoxClass> modules) {
        // Se declara el botón inicio
        MaterialDesignIconView iconInicio = new MaterialDesignIconView();
        iconInicio.setGlyphName("HOME");
        JFXButton inicio = new JFXButton("  Inicio");
        inicio.getStyleClass().add("menuBtn");
        // Se agrega el evento de click al botón
        inicio.setOnAction((event) -> {
            // Se limpia el panel
            panel.getChildren().clear();
        });
        inicio.setGraphic(iconInicio);
        // Se agrega el botón al FlowPane
        navBar.getChildren().add(inicio);
        // Ciclo for de los módulos por perfil
        for(ComboBoxClass x : modules) {
            // Declaración de los botones
            MaterialDesignIconView icon = new MaterialDesignIconView();
            icon.setGlyphName(x.getNombre());
            JFXButton button = new JFXButton("  " + x.getId());
            button.setGraphic(icon);
            button.getStyleClass().add("menuBtn");
            // Se asigna el evento al botón
            button.setOnAction((event) -> {
                try {
                    loadView(x.getId());
                } catch (IOException ex) {
                    System.out.println(ex);
                }
            });
            // Se agrega el botón al FlowPane
            navBar.getChildren().add(button);
        }
        // Declaración del botón salir
        JFXButton salir = new JFXButton("  Salir");
        MaterialDesignIconView iconSalir = new MaterialDesignIconView();
        iconSalir.setGlyphName("EXIT_TO_APP");
        salir.setGraphic(iconSalir);
        salir.getStyleClass().add("menuBtn");
        salir.setOnAction((event) -> {
            // Despliega una confirmación de si deseas salir
            if(dialogs.displayMessage((Stage) btnMenu.getScene().getWindow(), "Cierre de sistema", "¿Estás seguro que deseas cerrar el sistema?", "Si", "No")) {
                Stage stage = (Stage) salir.getScene().getWindow();
                stage.close();
            }
        });
        // Se agrega el botón al FlowPane
        navBar.getChildren().add(salir);
    }
    // Se carga la vista que tenga el mismo nombre que el botón
    public void loadView(String name) throws IOException {
        FXMLLoader loadFXML = new FXMLLoader(getClass().getResource("/View/"+name+".fxml"));
        Parent root = loadFXML.load();
        // Se agrega el FXML cargado al AnchorPane llamado panel
        panel.getChildren().setAll(root);
    }
    // Se inserta el idPerfil obtenido en el login
    public void setIdPerfil(int idPerfil) {
        this.idPerfil = idPerfil;
        // Se llama la función de crear los botones y se manda de parametro el id del perfil
        createModulesButtons(MenuModel.menuxperfil(idPerfil));
    }
    // Función para agregar la transición al menú
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
