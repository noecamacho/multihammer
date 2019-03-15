package Controller;

import Model.ComboBoxClass;
import Model.Dialogs;
import Model.Venta;
import Model.VentasModel;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableRow;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableRow;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class VentasController implements Initializable {

    private final VentasModel modelo = new VentasModel();
    final private Dialogs dialogs = new Dialogs();
    ObservableList<Venta> productos = FXCollections.observableArrayList();
    
    @FXML
    private JFXButton btnAgregar;
    @FXML
    private JFXButton btnCotizar;
    @FXML
    private JFXButton btnVenta;
    @FXML
    private JFXButton btnAgregarCliente;
    @FXML
    private JFXComboBox<ComboBoxClass> txtMaterial;
    @FXML
    private JFXComboBox<ComboBoxClass> txtUnidad;
    @FXML
    private JFXComboBox<ComboBoxClass> txtCliente;
    @FXML
    private JFXTextField txtCantidad;
    @FXML
    private JFXTreeTableView table;
    @FXML
    private Label labelTotal;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        createTable();
        txtCantidad.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtCantidad.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        txtMaterial.setConverter(new StringConverter<ComboBoxClass>() {
            @Override
            public String toString(ComboBoxClass object) {
                return object.getNombre();
            }

            @Override
            public ComboBoxClass fromString(String string) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        txtMaterial.valueProperty().addListener((obs, oldval, newval) -> {
            if(newval != null)
                setUnidades(txtMaterial.getSelectionModel().getSelectedItem().getId());
        });
        txtUnidad.setConverter(new StringConverter<ComboBoxClass>() {
            @Override
            public String toString(ComboBoxClass object) {
                return object.getNombre();
            }

            @Override
            public ComboBoxClass fromString(String string) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        txtCliente.setConverter(new StringConverter<ComboBoxClass>() {
            @Override
            public String toString(ComboBoxClass object) {
                return object.getNombre();
            }

            @Override
            public ComboBoxClass fromString(String string) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        setClientes();
        setMateriales();
    }
    
    public void createTable() {
        
        table.setRowFactory( tv -> {
            TreeTableRow<Venta> row = new TreeTableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    Venta rowData = row.getItem();
                    if(dialogs.displayMessage((Stage) btnAgregar.getScene().getWindow(), "Eliminar producto", "¿Deseas eliminar este producto de la lista de venta?", "Si", "No")) {
                        for(int x = 0; x < productos.size(); x++) {
                            if(productos.get(x).getId_producto().equals(rowData.getId_producto())) {
                                productos.remove(x);
                                setTotal();
                                break;
                            }
                        }
                    }
                }
            });
            return row ;
        });
        
        JFXTreeTableColumn<Venta, String> material = new JFXTreeTableColumn("Material");
        material.setPrefWidth(120);
        material.setCellValueFactory((TreeTableColumn.CellDataFeatures<Venta, String> param) -> param.getValue().getValue().material);

        JFXTreeTableColumn<Venta, String> unidad = new JFXTreeTableColumn("Unidad");
        unidad.setPrefWidth(120);
        unidad.setCellValueFactory((TreeTableColumn.CellDataFeatures<Venta, String> param) -> param.getValue().getValue().unidad);

        JFXTreeTableColumn<Venta, String> cantidad = new JFXTreeTableColumn("Cantidad");
        cantidad.setPrefWidth(120);
        cantidad.setCellValueFactory((TreeTableColumn.CellDataFeatures<Venta, String> param) -> param.getValue().getValue().cantidad);

        JFXTreeTableColumn<Venta, String> precio = new JFXTreeTableColumn("Precio");
        precio.setPrefWidth(120);
        precio.setCellValueFactory((TreeTableColumn.CellDataFeatures<Venta, String> param) -> param.getValue().getValue().precio);

        JFXTreeTableColumn<Venta, String> total = new JFXTreeTableColumn("Total");
        total.setPrefWidth(120);
        total.setCellValueFactory((TreeTableColumn.CellDataFeatures<Venta, String> param) -> param.getValue().getValue().total);

        table.getColumns().setAll(material, unidad, cantidad, precio, total);
    }
    
    public void createTableView() {
        TreeItem<Venta> root = new RecursiveTreeItem<>(productos, RecursiveTreeObject::getChildren);
        table.setRoot(root);
        table.setShowRoot(false);
    }

    public void setClientes() {
        txtCliente.setItems(modelo.getClientes());
    }
    
    public void setMateriales() {
        txtMaterial.setItems(modelo.getMateriales());
    }
    
    public void setUnidades(String id_material) {
        txtUnidad.setItems(modelo.getUnidades(id_material));
        txtUnidad.setDisable(false);
    }
    
    public void setTotal() {
        float total = 0;
        for (Venta producto : productos) {
            total += Float.parseFloat(producto.getTotal().get());
        }
        DecimalFormat decFor = new DecimalFormat("########.##");
        labelTotal.setText(String.valueOf(decFor.format(total)));
    }
    
    @FXML
    private void btnAgregarAction() {
        if(!txtCantidad.getText().equals("") && !txtMaterial.getSelectionModel().isEmpty() && !txtUnidad.getSelectionModel().isEmpty()) {
            Venta product = new Venta();
            boolean existencia = false;
            int x;
            DecimalFormat decFor = new DecimalFormat("########.##");
            float precio = modelo.getPrecio(txtUnidad.getSelectionModel().getSelectedItem().getId());
            product.setId_producto(txtUnidad.getSelectionModel().getSelectedItem().getId());
            product.setMaterial(txtMaterial.getSelectionModel().getSelectedItem().getNombre());
            product.setUnidad(txtUnidad.getSelectionModel().getSelectedItem().getNombre());
            product.setPrecio(String.valueOf(precio));
            product.setCantidad(txtCantidad.getText());
            product.setTotal(String.valueOf(decFor.format(precio * Integer.parseInt(txtCantidad.getText()))));
            for(x = 0; x < productos.size(); x++) {
                if(productos.get(x).getId_producto().equals(txtUnidad.getSelectionModel().getSelectedItem().getId())) {
                    product.setCantidad(String.valueOf(Integer.parseInt(txtCantidad.getText()) + Integer.parseInt(productos.get(x).getCantidad().get())));
                    product.setTotal(String.valueOf(decFor.format(precio * Integer.parseInt(product.getCantidad().get()))));
                    existencia = true;
                    break;
                }
            }
            if(existencia) {
                productos.set(x, product);
            } else {
                productos.add(product);
            }
            createTableView();
            setMateriales();
            txtUnidad.getSelectionModel().clearSelection();
            txtUnidad.setDisable(true);
            txtCantidad.setText("");
            setTotal();
        } else {
            dialogs.displayMessage((Stage) btnAgregar.getScene().getWindow(), "Advertencia", "Es necesario que los campos Material, Unidad y Cantidad se encuentren llenos", "Ok");
        }
    }

    @FXML
    private void btnVentaAction() {
        if(productos.size() > 0) {
            if(dialogs.displayMessage((Stage) btnAgregar.getScene().getWindow(), "Registrar venta", "¿Estás seguro que quieres concluir esta venta?", "Si", "No")) {
                modelo.registrarVenta(productos, txtCliente.getSelectionModel().getSelectedItem().getId(), labelTotal.getText());
                productos.clear();
                createTableView();
                setClientes();
                labelTotal.setText("");
            }
        } else {
            dialogs.displayMessage((Stage) btnAgregar.getScene().getWindow(), "Advertencia", "Es necesario ingresar por lo menos un producto para realizar una venta", "Ok");
        }
    }

    @FXML
    private void btnCotizarAction() {
        
    }

    @FXML
    private void btnAgregarClienteAction(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader modal = new FXMLLoader(getClass().getResource("/View/ClientesModal.fxml"));
        Parent root = modal.load();
        stage.setScene(new Scene(root));
        stage.setTitle("Agregar Clientes");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(((Node)event.getSource()).getScene().getWindow() );
        stage.showAndWait();
        setClientes();
    }
    
}
