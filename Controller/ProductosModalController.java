package Controller;

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
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ProductosModalController implements Initializable {

    final private ProductosModel modelo = new ProductosModel();
    final private Dialogs dialogs = new Dialogs();
    
    @FXML
    private StackPane root;
    @FXML
    private JFXTextField txtMaterial;
    @FXML
    private JFXTextArea txtDescripcion;
    @FXML
    private JFXComboBox<String> txtUnidades;
    @FXML
    private JFXTextField txtPrecio;
    @FXML
    private JFXComboBox<String> txtProveedor;
    @FXML
    private JFXTextField txtCantidad;
    @FXML
    private JFXButton btnAgregar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setProveedores();
        setUnidades();
        txtMaterial.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\sa-zA-ZñÑáéíóúÁÉÍÓÚ*")) {
                txtMaterial.setText(newValue.replaceAll("[^\\sa-zA-ZñÑáéíóúÁÉÍÓÚ]", ""));
            }
        });
        txtPrecio.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*(\\.\\d*)?")) {
                txtPrecio.setText(newValue.replaceAll("[^\\d]*(\\.\\d*)?", ""));
            }
        });
        txtCantidad.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtCantidad.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        txtDescripcion.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\sa-zA-ZñÑáéíóúÁÉÍÓÚ*")) {
                txtDescripcion.setText(newValue.replaceAll("[^\\sa-zA-ZñÑáéíóúÁÉÍÓÚ]", ""));
            }
        });
        btnAgregar.setOnAction((event) -> {
            agregar();
        });
    }

    public void agregar() {
        if (!txtMaterial.getText().equals("") && !txtDescripcion.getText().equals("") && !txtUnidades.getValue().equals("") && !txtPrecio.getText().equals("") && !txtCantidad.getText().equals("")) {
            modelo.agregarProducto(txtMaterial.getText(), txtDescripcion.getText(), txtUnidades.getValue(), txtPrecio.getText(), txtProveedor.getValue(), txtCantidad.getText());
            Stage stage = (Stage) btnAgregar.getScene().getWindow();
            stage.close();
        } else {
            dialogs.displayMessage((Stage) btnAgregar.getScene().getWindow(), "Advertencia", "Para agregar un producto es necesario que todos los campos se encuentren llenos", "OK");
        }
    }

    public void modificar(String id_producto) {
        if (!txtMaterial.getText().equals("") && !txtDescripcion.getText().equals("") && !txtUnidades.getValue().equals("") && !txtPrecio.getText().equals("") && !txtCantidad.getText().equals("")) {
            modelo.modificarProducto(txtMaterial.getText(), txtDescripcion.getText(), txtUnidades.getValue(), txtPrecio.getText(), txtProveedor.getValue(), txtCantidad.getText(), id_producto);
            Stage stage = (Stage) btnAgregar.getScene().getWindow();
            stage.close();
        } else {
            dialogs.displayMessage((Stage) btnAgregar.getScene().getWindow(), "Advertencia", "Para modificar un producto es necesario que todos los campos se encuentren llenos", "OK");
        }
    }
    
    public void setProveedores() {
        txtProveedor.setItems(modelo.getProveedores());
    }

    public void setUnidades() {
        ObservableList<String> data = FXCollections.observableArrayList("m", "pz", "Saco 20KG", "m²");
        txtUnidades.setItems(data);
    }

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
    }
}
