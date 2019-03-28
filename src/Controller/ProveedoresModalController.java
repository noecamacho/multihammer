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
    // Instanciaciòn de clases
    final private ProveedorModel modelo = new ProveedorModel();
    final private Dialogs dialogs = new Dialogs();
    // Declaraciòn de componentes
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
        // Se agrega la funciòn agregar al botòn Agregar
        btnAgregar.setOnAction((event) -> {
            agregar();
        });
        // Validaciòn del telèfono. Solo recibe digitos
        txtTelefono.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtTelefono.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        // Validaciòn de RFC. Solo 12-13 mayùsculas y digitos
        txtRFC.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[A-Z\\d]{12,13}")) {
                txtRFC.setText(newValue.replaceAll("[^A-Z\\d]{12,13}", ""));
            }
        });
    }
    // Funciòn agregar
    public void agregar() {
        if (!txtRazonSocial.getText().equals("") && !txtDomicilio.getText().equals("") && !txtTelefono.getText().equals("") && !txtRFC.getText().equals("")) {
            if(txtRFC.getText().matches("[A-ZÑ&]{3,4}\\d{6}[A-V1-9][A-Z1-9][0-9A]")) {
                String message = modelo.agregarProveedor(txtRazonSocial.getText(), txtDomicilio.getText(), txtTelefono.getText(), txtRFC.getText());
                if(message.equals("Proveedor agregado exitosamente")) {
                    Stage stage = (Stage) btnAgregar.getScene().getWindow();
                    stage.close();
                } else 
                    dialogs.displayMessage((Stage) btnAgregar.getScene().getWindow(), "Advertencia", message, "OK");
            } else {
                dialogs.displayMessage((Stage) btnAgregar.getScene().getWindow(), "Advertencia", "Introduce un RFC válido", "OK");
            }
        } else {
            dialogs.displayMessage((Stage) btnAgregar.getScene().getWindow(), "Advertencia", "Para agregar un proveedor es necesario que todos los campos se encuentren llenos", "OK");
        }
    }
    // Funciòn modificar
    public void modificar(String id_proveedor) {
        if (!txtRazonSocial.getText().equals("") && !txtDomicilio.getText().equals("") && !txtTelefono.getText().equals("") && !txtRFC.getText().equals("")) {
            if(txtRFC.getText().matches("[A-ZÑ&]{3,4}\\d{6}[A-V1-9][A-Z1-9][0-9A]")) {
                String message = modelo.modificarProveedor(txtRazonSocial.getText(), txtDomicilio.getText(), txtTelefono.getText(), txtRFC.getText(), id_proveedor);
                if(message.equals("Proveedor modificado exitosamente")) {
                    Stage stage = (Stage) btnAgregar.getScene().getWindow();
                    stage.close();
                } else 
                    dialogs.displayMessage((Stage) btnAgregar.getScene().getWindow(), "Advertencia", message, "OK");
            } else {
                dialogs.displayMessage((Stage) btnAgregar.getScene().getWindow(), "Advertencia", "Introduce un RFC válido", "OK");
            }
        } else {
            dialogs.displayMessage((Stage) btnAgregar.getScene().getWindow(), "Advertencia", "Para modificar un proveedor es necesario que todos los campos se encuentren llenos", "OK");
        }
    }
    // Asigna los valores a los campos del elemento a modificar
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
    // Funciòn para cerrar el modal
    @FXML
    private void closeModal() {
        if(dialogs.displayMessage((Stage) btnAgregar.getScene().getWindow(), "Advertencia", "¿Estás seguro que deseas cerrar esta ventana?", "Si", "No")) {
            Stage stage = (Stage) btnAgregar.getScene().getWindow();
            stage.close();
        }
    }
    
}
