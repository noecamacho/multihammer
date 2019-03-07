package Controller;

import Model.ProductosModel;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ProductosModalController implements Initializable {
    
    private ProductosModel modelo = new ProductosModel();

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
    private Spinner<Integer> txtCantidad;
    @FXML
    private JFXButton btnAgregar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setProveedores();
        setUnidades();
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100);
        txtCantidad.setValueFactory(valueFactory);
    }    
    
    public void agregar() {
        modelo.agregarProducto(txtMaterial.getText(), txtDescripcion.getText(), txtUnidades.getValue(), txtPrecio.getText(), txtProveedor.getValue(), String.valueOf(txtCantidad.getValue()));
        Stage stage = (Stage) btnAgregar.getScene().getWindow();
        stage.close();
    }
    
    public void setProveedores() {
        txtProveedor.setItems(modelo.getProveedores());
    }
    
    public void setUnidades() {
        ObservableList<String> data = FXCollections.observableArrayList("m", "pz", "Saco 20KG");
        txtUnidades.setItems(data);
    }
    
    public void setValuesToModify(String material, String unidad, String precio, String proveedor, String cantidad, String id_producto) {
        txtMaterial.setText(material);
        txtUnidades.setValue(unidad);
        txtPrecio.setText(precio);
        txtCantidad.getValueFactory().setValue(Integer.parseInt(cantidad));
        txtProveedor.setValue(proveedor);
    }
}
