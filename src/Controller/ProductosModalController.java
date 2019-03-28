package Controller;

import Model.ComboBoxClass;
import Model.Dialogs;
import Model.ProductosModel;
import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class ProductosModalController implements Initializable {
    //Instanciación de clases
    final private ProductosModel modelo = new ProductosModel();
    final private Dialogs dialogs = new Dialogs();
    // Declaración de los componentes
    @FXML
    private Label labelHeader;
    @FXML
    private Label labelDescr;
    @FXML
    private StackPane root;
    @FXML
    private TextField txtMaterial;
    @FXML
    private TextArea txtDescripcion;
    @FXML
    private ComboBox<String> txtUnidades;
    @FXML
    private TextField txtPrecio;
    @FXML
    private ComboBox<String> txtProveedor;
    @FXML
    private TextField txtCantidad;
    @FXML
    private JFXButton btnAgregar;
    @FXML
    private ComboBox<ComboBoxClass> txtMateriales2;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Asigna los valores a proveedores
        setProveedores();
        // Asigna los valores a unidades
        setUnidades();
        // Validación para el campo de material. Acepta espacios en blanco, a-z, A-Z, ñ, Ñ y acentos
        txtMaterial.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\sa-zA-ZñÑáéíóúÁÉÍÓÚ\\d\\/*")) {
                txtMaterial.setText(newValue.replaceAll("[^\\sa-zA-ZñÑáéíóúÁÉÍÓÚ\\d\\/]", ""));
            }
        });
        // Validación de precio. Acepta digitos, 1 punto, y más digitos
        txtPrecio.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*(\\.\\d*)?")) {
                txtPrecio.setText(newValue.replaceAll("[^\\d]*(\\.\\d*)?", ""));
            }
        });
        // Validación de cantidad. Acepta solo digitos
        txtCantidad.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtCantidad.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        // Validación de descripción. Acepta solo caracteres a-z, A-Z, ñ, Ñ y acentos en vocales
        txtDescripcion.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\sa-zA-ZñÑáéíóúÁÉÍÓÚ*")) {
                txtDescripcion.setText(newValue.replaceAll("[^\\sa-zA-ZñÑáéíóúÁÉÍÓÚ]", ""));
            }
        });
        // Se asigna la acción agregar() al botón agregar.
        btnAgregar.setOnAction((event) -> {
            agregar();
        });
        // Se esconde el ComboBox de materiales
        txtMateriales2.setVisible(false);
        // Que solo se despliegue el nombre del material
        txtMateriales2.setConverter(new StringConverter<ComboBoxClass>() {
            @Override
            public String toString(ComboBoxClass object) {
                return object.getNombre();
            }

            @Override
            public ComboBoxClass fromString(String string) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
    }
    // Función agregar
    public void agregar() {
        // Validación de que los campos tengan datos
        if (!txtMaterial.getText().equals("") && !txtDescripcion.getText().equals("") && !txtUnidades.getValue().equals("") && !txtPrecio.getText().equals("") && !txtCantidad.getText().equals("")) {
            // Se agrega el producto/material y se guarda un mensaje
            String message = modelo.agregarProducto(txtMaterial.getText(), txtDescripcion.getText(), txtUnidades.getValue(), txtPrecio.getText(), txtProveedor.getValue(), txtCantidad.getText());
            // Si funciona se cierra el modal
            if(message.equals("Producto y material agregado exitosamente")) {
                Stage stage = (Stage) btnAgregar.getScene().getWindow();
                stage.close();
            // Sino se despliega el mensaje ccon el error
            } else {
                dialogs.displayMessage((Stage) btnAgregar.getScene().getWindow(), "Advertencia", message, "OK");
            }
        } else {
            dialogs.displayMessage((Stage) btnAgregar.getScene().getWindow(), "Advertencia", "Para agregar un producto es necesario que todos los campos se encuentren llenos", "OK");
        }
    }
    // Función para agregar unidades a un material
    public void agregarUnidades() {
        // Valida el contenido de los campos
        if (!txtMateriales2.getValue().equals("") && !txtUnidades.getValue().equals("") && !txtPrecio.getText().equals("") && !txtCantidad.getText().equals("")) {
            // Se agrega la unidad y se guarda el mensaje que regrese el método
            String message = modelo.agregarUnidad(txtMateriales2.getValue(), txtUnidades.getValue(), txtPrecio.getText(), txtProveedor.getValue(), txtCantidad.getText());
            // Si funciono, se cierra el modal
            if(message.equals("Unidad agregada exitosamente")) {
                Stage stage = (Stage) btnAgregar.getScene().getWindow();
                stage.close();
            // Sino, se despliega el mensaje
            } else {
                dialogs.displayMessage((Stage) btnAgregar.getScene().getWindow(), "Advertencia", message, "OK");
            }
        } else {
            dialogs.displayMessage((Stage) btnAgregar.getScene().getWindow(), "Advertencia", "Para agregar una unidad es necesario que todos los campos se encuentren llenos", "OK");
        }
    }
    // Función modificar
    public void modificar(String id_producto) {
        // Validación de que los campos tengan datos
        if (!txtMaterial.getText().equals("") && !txtDescripcion.getText().equals("") && !txtUnidades.getValue().equals("") && !txtPrecio.getText().equals("") && !txtCantidad.getText().equals("")) {
            // Se modiica el producto/material y se guarda un mensaje
            String message = modelo.modificarProducto(txtMaterial.getText(), txtDescripcion.getText(), txtUnidades.getValue(), txtPrecio.getText(), txtProveedor.getValue(), txtCantidad.getText(), id_producto);
            // Si funciona se cierra el modal
            if(message.equals("Producto y material modificado exitosamente")) {
                Stage stage = (Stage) btnAgregar.getScene().getWindow();
                stage.close();
            // Sino se despliega el mensaje ccon el error
            } else {
                dialogs.displayMessage((Stage) btnAgregar.getScene().getWindow(), "Advertencia", message, "OK");
            }
        } else {
            dialogs.displayMessage((Stage) btnAgregar.getScene().getWindow(), "Advertencia", "Para modificar un producto es necesario que todos los campos se encuentren llenos", "OK");
        }
    }
    // Funciòn para asignar los proveedores al ComboBox
    public void setProveedores() {
        txtProveedor.setItems(modelo.getProveedores());
    }
    // Funciòn para asignar las unidades al ComboBox
    public void setUnidades() {
        ObservableList<String> data = FXCollections.observableArrayList("m", "PZA", "Saco 50KG", "m²", "KG");
        txtUnidades.setItems(data);
    }
    // Asigna los campos con los datos a modificar
    public void setValuesToModify(String material, String unidad, String precio, String proveedor, String cantidad, String id_producto) {
        txtMaterial.setText(material);
        txtUnidades.setValue(unidad);
        txtPrecio.setText(precio);
        txtCantidad.setText(cantidad);
        txtProveedor.setValue(proveedor);
        txtDescripcion.setText(modelo.getDescripcion(id_producto));
        btnAgregar.setText("Modificar");
        btnAgregar.setOnAction((event) ->{
            modificar(id_producto);
        });
        labelHeader.setText("Modificar producto");
    }
    // Funciòn para cerrar el modal
    @FXML
    private void closeModal() {
        if(dialogs.displayMessage((Stage) btnAgregar.getScene().getWindow(), "Advertencia", "¿Estás seguro que deseas cerrar esta ventana?", "Si", "No")) {
            Stage stage = (Stage) btnAgregar.getScene().getWindow();
            stage.close();
        }
    }
    // Prepara el modal para agregar unidad
    public void agregarUnidad() {
        btnAgregar.setOnAction((event) -> {
            agregarUnidades();
        });
        txtMateriales2.setVisible(true);
        txtMaterial.setVisible(false);
        txtDescripcion.setVisible(false);
        labelDescr.setVisible(false);
        labelHeader.setText("Agregar Unidad");
        setMateriales();
    }
    // Funciòn para asignar los materiales al ComboBox
    public void setMateriales() {
        txtMateriales2.setItems(modelo.getMateriales());
    }
}
