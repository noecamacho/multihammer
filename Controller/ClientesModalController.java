package Controller;

import Model.ClientesModel;
import Model.Dialogs;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class ClientesModalController implements Initializable {

    final private ClientesModel modelo = new ClientesModel();
    final private Dialogs dialogs = new Dialogs();
    
    @FXML
    private StackPane root;
    @FXML
    private Label labelHeader;
    @FXML
    private JFXTextField txtNombre;
    @FXML
    private JFXTextField txtApellido;
    @FXML
    private JFXTextField txtDomicilio;
    @FXML
    private JFXTextField txtTelefono;
    @FXML
    private JFXTextField txtRFC;
    @FXML
    private JFXButton btnAgregar;

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
//        txtRFC.textProperty().addListener((observable, oldValue, newValue) -> {
//            if (!newValue.matches("[A-Z\\d]{12,13}")) {
//                txtRFC.setText(newValue.replaceAll("[^A-Z\\d]{12,13}", ""));
//            }
//        });
        txtRFC.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[A-ZÑ&]{3,4}\\d{6}[A-V1-9][A-Z1-9][0-9A]")) {
                txtRFC.setText(newValue.replaceAll("[^[A-ZÑ&]{3,4}\\d{6}[A-V1-9][A-Z1-9][0-9A]]", ""));
            }
        });
        txtNombre.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("a-zA-ZñÑáéíóúÁÉÍÓÚ\\s*")) {
                txtNombre.setText(newValue.replaceAll("[^a-zA-ZñÑáéíóúÁÉÍÓÚ\\s]*", ""));
            }
        });
        txtApellido.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("a-zA-ZñÑáéíóúÁÉÍÓÚ\\s*")) {
                txtApellido.setText(newValue.replaceAll("[^a-zA-ZñÑáéíóúÁÉÍÓÚ\\s]*", ""));
            }
        });
    }

    public void agregar() {
        if (!txtNombre.getText().equals("") && !txtApellido.getText().equals("") &&!txtDomicilio.getText().equals("") && !txtTelefono.getText().equals("") && !txtRFC.getText().equals("")) {
            if(txtRFC.getText().matches("[A-ZÑ&]{3,4}\\d{6}[A-V1-9][A-Z1-9][0-9A]")) {
                modelo.agregarCliente(txtNombre.getText(), txtApellido.getText(), txtDomicilio.getText(), txtTelefono.getText(), txtRFC.getText());
                Stage stage = (Stage) btnAgregar.getScene().getWindow();
                stage.close();
            } else {
                dialogs.displayMessage((Stage) btnAgregar.getScene().getWindow(), "Advertencia", "Introduce un RFC válido", "OK");
            }
        } else {
            dialogs.displayMessage((Stage) btnAgregar.getScene().getWindow(), "Advertencia", "Para agregar un cliente es necesario que todos los campos se encuentren llenos", "OK");
        }
    }

    public void modificar(String id_cliente) {
        if (!txtNombre.getText().equals("") && !txtApellido.getText().equals("") &&!txtDomicilio.getText().equals("") && !txtTelefono.getText().equals("") && !txtRFC.getText().equals("")) {
            modelo.modificarCliente(txtNombre.getText(), txtApellido.getText(), txtDomicilio.getText(), txtTelefono.getText(), txtRFC.getText(), id_cliente);
            Stage stage = (Stage) btnAgregar.getScene().getWindow();
            stage.close();
        } else {
            dialogs.displayMessage((Stage) btnAgregar.getScene().getWindow(), "Advertencia", "Para modificar un cliente es necesario que todos los campos se encuentren llenos", "OK");
        }
    }
    
    public void setValuesToModify(String id_cliente, String domicilio, String rfc, String nombre, String apellido, String telefono) {
        txtDomicilio.setText(domicilio);
        txtRFC.setText(rfc);
        txtNombre.setText(nombre);
        txtApellido.setText(apellido);
        txtTelefono.setText(telefono);
        labelHeader.setText("Modificar Cliente");
        btnAgregar.setText("Modificar");
        btnAgregar.setOnAction((event) ->{
            modificar(id_cliente);
        });
    }   
    
}
