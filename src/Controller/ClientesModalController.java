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
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class ClientesModalController implements Initializable {
    // Instanciación del modelo ClientesModel
    final private ClientesModel modelo = new ClientesModel();
    // Instanciación de la clase para mostrar dialogos
    final private Dialogs dialogs = new Dialogs();
    // Declaración de componentes
    @FXML
    private StackPane root;
    @FXML
    private Label labelHeader;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtApellido;
    @FXML
    private TextField txtDomicilio;
    @FXML
    private TextField txtTelefono;
    @FXML
    private TextField txtRFC;
    @FXML
    private JFXButton btnAgregar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Se asigna el evento agregar al botón btnAgregar
        btnAgregar.setOnAction((event) -> {
            agregar();
        });
        // Validación del campo Teléfono. Solo se pueden insertar números del 0 al 9
        txtTelefono.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtTelefono.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        // Validación del campo RFC. Solo se pueden insertar números del 0 al 9 y letras de la A a la Z
        txtRFC.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[A-Z\\d]{12,13}")) {
                txtRFC.setText(newValue.replaceAll("[^A-Z\\d]{12,13}", ""));
            }
        });
        // Validación de nombre. Solo se pueden insertar caracteres a-z, A-Z, n, Ñ y acentos en vocales
        txtNombre.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("a-zA-ZñÑáéíóúÁÉÍÓÚ\\s*")) {
                txtNombre.setText(newValue.replaceAll("[^a-zA-ZñÑáéíóúÁÉÍÓÚ\\s]*", ""));
            }
        });
        // Validación de apellido. Solo se pueden insertar caracteres a-z, A-Z, n, Ñ y acentos en vocales
        txtApellido.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("a-zA-ZñÑáéíóúÁÉÍÓÚ\\s*")) {
                txtApellido.setText(newValue.replaceAll("[^a-zA-ZñÑáéíóúÁÉÍÓÚ\\s]*", ""));
            }
        });
    }
    // Función agregar
    public void agregar() {
        // Se validan que todos los campos tengan un valor asignado
        if (!txtNombre.getText().equals("") && !txtApellido.getText().equals("") &&!txtDomicilio.getText().equals("") && !txtTelefono.getText().equals("") && !txtRFC.getText().equals("")) {
            // Se valida que el formato insertado en RFC sea válido 
            if(txtRFC.getText().matches("[A-ZÑ&]{3,4}\\d{6}[A-V1-9][A-Z1-9][0-9A]")) {
                // Se llama a la función agregarCliente del modelo, se mandan los valores en los componentes y se guarda el mensaje regresado en una variable
                String message = modelo.agregarCliente(txtNombre.getText(), txtApellido.getText(), txtDomicilio.getText(), txtTelefono.getText(), txtRFC.getText());
                // Si el cliente se agrego exitosamente se entra en el bloque del if
                if(message.equals("Cliente agregado exitosamente")) {
                    // Se cierra el modal
                    Stage stage = (Stage) btnAgregar.getScene().getWindow();
                    stage.close();
                // Si no se agrego exitosamente, se despliega un mensaje diciendo porque no se agrego
                } else {
                    dialogs.displayMessage((Stage) btnAgregar.getScene().getWindow(), "Advertencia", message, "OK");
                }
            // Despliegue de mensaje de que el RFC no es válido
            } else {
                dialogs.displayMessage((Stage) btnAgregar.getScene().getWindow(), "Advertencia", "Introduce un RFC válido", "OK");
            }
        // Despliegue de mensaje de que no todos los campos estan llenos
        } else {
            dialogs.displayMessage((Stage) btnAgregar.getScene().getWindow(), "Advertencia", "Para agregar un cliente es necesario que todos los campos se encuentren llenos", "OK");
        }
    }
    // Función modificar
    public void modificar(String id_cliente) {
        // Se validan que todos los campos tengan un valor asignado
        if (!txtNombre.getText().equals("") && !txtApellido.getText().equals("") &&!txtDomicilio.getText().equals("") && !txtTelefono.getText().equals("") && !txtRFC.getText().equals("")) {
            // Se valida que el formato insertado en RFC sea válido 
            if(txtRFC.getText().matches("[A-ZÑ&]{3,4}\\d{6}[A-V1-9][A-Z1-9][0-9A]")) {
                // Se llama a la función agregarCliente del modelo, se mandan los valores en los componentes y se guarda el mensaje regresado en una variable
                String message = modelo.modificarCliente(txtNombre.getText(), txtApellido.getText(), txtDomicilio.getText(), txtTelefono.getText(), txtRFC.getText(), id_cliente);
                // Si el cliente se agrego exitosamente se entra en el bloque del if
                if(message.equals("Cliente modificado exitosamente")) {
                    // Se cierra el modal
                    Stage stage = (Stage) btnAgregar.getScene().getWindow();
                    stage.close();
                // Si no se agrego exitosamente, se despliega un mensaje diciendo porque no se agrego
                } else {
                    dialogs.displayMessage((Stage) btnAgregar.getScene().getWindow(), "Advertencia", message, "OK");
                }
            // Despliegue de mensaje de que el RFC no es válido
            } else {
                dialogs.displayMessage((Stage) btnAgregar.getScene().getWindow(), "Advertencia", "Introduce un RFC válido", "OK");
            }
        // Despliegue de mensaje de que no todos los campos estan llenos
        } else {
            dialogs.displayMessage((Stage) btnAgregar.getScene().getWindow(), "Advertencia", "Para modificar un cliente es necesario que todos los campos se encuentren llenos", "OK");
        }
    }
    // Se cargan los componentes con los datos del cliente que se quiere modificar
    public void setValuesToModify(String id_cliente, String domicilio, String rfc, String nombre, String apellido, String telefono) {
        txtDomicilio.setText(domicilio);
        txtRFC.setText(rfc);
        txtNombre.setText(nombre);
        txtApellido.setText(apellido);
        txtTelefono.setText(telefono);
        // Se cambia el texto del título
        labelHeader.setText("Modificar Cliente");
        // Se cambia el texto del botón Agregar
        btnAgregar.setText("Modificar");
        // Se asigna la función modificar al botón Agregar
        btnAgregar.setOnAction((event) ->{
            modificar(id_cliente);
        });
    }   
    // Función para cerrar el modal
    @FXML
    private void closeModal() {
        if(dialogs.displayMessage((Stage) btnAgregar.getScene().getWindow(), "Advertencia", "¿Estás seguro que deseas cerrar esta ventana?", "Si", "No")) {
            Stage stage = (Stage) btnAgregar.getScene().getWindow();
            stage.close();
        }
    }
    
}
