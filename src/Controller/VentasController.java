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
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.StringConverter;
import Reportes.PrintReport;
import java.sql.SQLException;
import net.sf.jasperreports.engine.JRException;

public class VentasController implements Initializable {
    // Instanciaciòn/Declaraciòn de variables
    private final VentasModel modelo = new VentasModel();
    final private Dialogs dialogs = new Dialogs();
    ObservableList<Venta> productos = FXCollections.observableArrayList();
    // Declaraciòn de componentes
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
        // Crea la tabla
        createTable();
        // Valida que la cantidad solo pueda recibir digitos.
        txtCantidad.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtCantidad.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        // Que solo se despliegue el nombre
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
        // Que solo se despliegue el nombre
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
        // Que solo se despliegue el nombre
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
        // Asigna los valores al ComboBox de clientes
        setClientes();
        // Asigna los valores al ComboBox de materiales
        setMateriales();
    }
    // Crea la tabla
    public void createTable() {
        // Evento de doble click que despliega un mensaje para dar de baja lògica a la fila seleccionada
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
        // Estructura de la tabla
        JFXTreeTableColumn<Venta, String> material = new JFXTreeTableColumn("Material");
        material.setPrefWidth(250);
        material.setCellValueFactory((TreeTableColumn.CellDataFeatures<Venta, String> param) -> param.getValue().getValue().material);

        JFXTreeTableColumn<Venta, String> unidad = new JFXTreeTableColumn("Unidad");
        unidad.setPrefWidth(200);
        unidad.setCellValueFactory((TreeTableColumn.CellDataFeatures<Venta, String> param) -> param.getValue().getValue().unidad);

        JFXTreeTableColumn<Venta, String> cantidad = new JFXTreeTableColumn("Cantidad");
        cantidad.setPrefWidth(200);
        cantidad.setCellValueFactory((TreeTableColumn.CellDataFeatures<Venta, String> param) -> param.getValue().getValue().cantidad);

        JFXTreeTableColumn<Venta, String> precio = new JFXTreeTableColumn("Precio");
        precio.setPrefWidth(200);
        precio.setCellValueFactory((TreeTableColumn.CellDataFeatures<Venta, String> param) -> param.getValue().getValue().precio);

        JFXTreeTableColumn<Venta, String> total = new JFXTreeTableColumn("Total");
        total.setPrefWidth(300);
        total.setCellValueFactory((TreeTableColumn.CellDataFeatures<Venta, String> param) -> param.getValue().getValue().total);

        table.getColumns().setAll(material, unidad, cantidad, precio, total);
    }
    // Asigna los datos
    public void createTableView() {
        TreeItem<Venta> root = new RecursiveTreeItem<>(productos, RecursiveTreeObject::getChildren);
        table.setRoot(root);
        table.setShowRoot(false);
    }
    // Asigna los datos a Clientes
    public void setClientes() {
        txtCliente.setItems(modelo.getClientes());
    }
    
    // Asigna los datos a Materiales
    public void setMateriales() {
        txtMaterial.setItems(modelo.getMateriales());
    }
    
    // Asigna los datos a Unidades, dandole las unidades asignadas al material seleccionado
    public void setUnidades(String id_material) {
        txtUnidad.setItems(modelo.getUnidades(id_material));
        txtUnidad.setDisable(false);
    }
    // Rellena el label de total con el total de los productos  seleccionados
    public void setTotal() {
        float total = 0;
        for (Venta producto : productos) {
            total += Float.parseFloat(producto.getTotal().get());
        }
        // Da formato al total
        DecimalFormat decFor = new DecimalFormat("########.##");
        labelTotal.setText(String.valueOf(decFor.format(total)));
    }
    // Funciòn agregar
    @FXML
    private void btnAgregarAction() {
        // Valida que todos los campos tengan un valor
        if(!txtCantidad.getText().equals("") && !txtMaterial.getSelectionModel().isEmpty() && !txtUnidad.getSelectionModel().isEmpty()) {
            // Declaraciòn de variables
            Venta product = new Venta();
            boolean existencia = false;
            int x;
            // Formato para solo dos decimales
            DecimalFormat decFor = new DecimalFormat("########.##");
            // Agrega los valores del material a la variable auxiliar
            float precio = modelo.getPrecio(txtUnidad.getSelectionModel().getSelectedItem().getId());
            product.setId_producto(txtUnidad.getSelectionModel().getSelectedItem().getId());
            product.setMaterial(txtMaterial.getSelectionModel().getSelectedItem().getNombre());
            product.setUnidad(txtUnidad.getSelectionModel().getSelectedItem().getNombre());
            product.setPrecio(String.valueOf(precio));
            product.setCantidad(txtCantidad.getText());
            // Calcula el total: precio * cantidad
            product.setTotal(String.valueOf(decFor.format(precio * Integer.parseInt(txtCantidad.getText()))));
            // Ciclo for para revisar si ya se encuentra registrado el producto, si ya se encuentra, se suma la cantidad y se recalcula el total
            for(x = 0; x < productos.size(); x++) {
                if(productos.get(x).getId_producto().equals(txtUnidad.getSelectionModel().getSelectedItem().getId())) {
                    product.setCantidad(String.valueOf(Integer.parseInt(txtCantidad.getText()) + Integer.parseInt(productos.get(x).getCantidad().get())));
                    product.setTotal(String.valueOf(decFor.format(precio * Integer.parseInt(product.getCantidad().get()))));
                    existencia = true;
                    break;
                }
            }
            // Si ya existìa el producto en la tabla de ventas, se modifica su ìndice
            if(existencia) {
                productos.set(x, product);
            } else {
            // Sino, se agrega el producto
                productos.add(product);
            }
            // Se vuelven a mostar los valores en la tabla
            createTableView();
            // Se reinician los componentes para agregar
            setMateriales();
            txtUnidad.getSelectionModel().clearSelection();
            txtUnidad.setDisable(true);
            txtCantidad.setText("");
            setTotal();
        } else {
            dialogs.displayMessage((Stage) btnAgregar.getScene().getWindow(), "Advertencia", "Es necesario que los campos Material, Unidad y Cantidad se encuentren llenos", "Ok");
        }
    }
    // Funciòn para hacer la venta
    @FXML
    private void btnVentaAction() {
        // Valida que se tenga agregado mìnimo 1 producto y 1 cliente
        if(productos.size() > 0 && !txtCliente.getSelectionModel().isEmpty()) {
            // Confirmaciòn de que se realize la venta
            if(dialogs.displayMessage((Stage) btnAgregar.getScene().getWindow(), "Registrar venta", "¿Estás seguro que quieres concluir esta venta?", "Si", "No")) {
                // Se registra la venta
                modelo.registrarVenta(productos, txtCliente.getSelectionModel().getSelectedItem().getId(), labelTotal.getText());
                // Se limpia la vista
                productos.clear();
                createTableView();
                setClientes();
                labelTotal.setText("");
            }
        } else {
            dialogs.displayMessage((Stage) btnAgregar.getScene().getWindow(), "Advertencia", "Es necesario ingresar por lo menos un producto para realizar una venta y seleccionar un cliente", "Ok");
        }
    }
    // Funciòn para cotizar
    @FXML
    private void btnCotizarAction() throws JRException, ClassNotFoundException, IOException {
        if(productos.size() > 0 && !txtCliente.getSelectionModel().isEmpty()) {
            if(dialogs.displayMessage((Stage) btnAgregar.getScene().getWindow(), "Cotización", "¿Estás seguro de que quieres generar una cotización?", "Si", "No")) {
                modelo.registrarCotizacion(productos, txtCliente.getSelectionModel().getSelectedItem().getId(), labelTotal.getText());
                productos.clear();
                createTableView();
                setClientes();
                labelTotal.setText("");
            }
        }    
    }
    // Funciòn para agregar cliente
    @FXML
    private void btnAgregarClienteAction(ActionEvent event) throws IOException {
        // Prepara el modal para agregar un cliente
        Stage stage = new Stage();
        FXMLLoader modal = new FXMLLoader(getClass().getResource("/View/ClientesModal.fxml"));
        Parent root = modal.load();
        root.getStylesheets().add("/Resources/main.css");
        stage.setScene(new Scene(root));
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.getScene().setFill(Color.TRANSPARENT);
        stage.setTitle("Agregar Clientes");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(((Node)event.getSource()).getScene().getWindow() );
        stage.showAndWait();
        setClientes();
    }
    
}
