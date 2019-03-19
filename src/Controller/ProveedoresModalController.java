/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Dialogs;
import Model.ProveedorModel;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Kevin Lizárraga
 */
public class ProveedoresModalController implements Initializable {

    final private ProveedorModel modelo = new ProveedorModel();
    final private Dialogs dialogs = new Dialogs();
    
    @FXML
    private StackPane root;
    @FXML
    private TextField txtRazonSocial;
    @FXML
    private TextField txtDomicilio;
    @FXML
    private TextField txtTelefono;
    @FXML
    private TextField txtRFC;
    @FXML
    private JFXButton btnAgregar;
    @FXML
    private Label labelHeader;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnAgregar.setOnAction((event) -> {
            agregar();
        });
        txtTelefono.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtTelefono.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        txtRFC.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[A-Z\\d]{12,13}")) {
                txtRFC.setText(newValue.replaceAll("[^A-Z\\d]{12,13}", ""));
            }
        });
    }

    public void agregar() {
        if (!txtRazonSocial.getText().equals("") && !txtDomicilio.getText().equals("") && !txtTelefono.getText().equals("") && !txtRFC.getText().equals("")) {
            if(txtRFC.getText().matches("[A-ZÑ&]{3,4}\\d{6}[A-V1-9][A-Z1-9][0-9A]")) {
                modelo.agregarProveedor(txtRazonSocial.getText(), txtDomicilio.getText(), txtTelefono.getText(), txtRFC.getText());
                Stage stage = (Stage) btnAgregar.getScene().getWindow();
                stage.close();
            } else {
                dialogs.displayMessage((Stage) btnAgregar.getScene().getWindow(), "Advertencia", "Introduce un RFC válido", "OK");
            }
        } else {
            dialogs.displayMessage((Stage) btnAgregar.getScene().getWindow(), "Advertencia", "Para agregar un proveedor es necesario que todos los campos se encuentren llenos", "OK");
        }
    }

    public void modificar(String id_proveedor) {
        if (!txtRazonSocial.getText().equals("") && !txtDomicilio.getText().equals("") && !txtTelefono.getText().equals("") && !txtRFC.getText().equals("")) {
            modelo.modificarProveedor(txtRazonSocial.getText(), txtDomicilio.getText(), txtTelefono.getText(), txtRFC.getText(), id_proveedor);
            Stage stage = (Stage) btnAgregar.getScene().getWindow();
            stage.close();
        } else {
            dialogs.displayMessage((Stage) btnAgregar.getScene().getWindow(), "Advertencia", "Para modificar un proveedor es necesario que todos los campos se encuentren llenos", "OK");
        }
    }
    
    public void setValuesToModify(String id_proveedor, String domicilio, String rfc, String razon_social, String telefono) {
        txtDomicilio.setText(domicilio);
        txtRFC.setText(rfc);
        txtRazonSocial.setText(razon_social);
        txtTelefono.setText(telefono);
        labelHeader.setText("Modificar Proveedor");
        btnAgregar.setText("Modificar");
        btnAgregar.setOnAction((event) ->{
            modificar(id_proveedor);
        });
    }  
    
}
