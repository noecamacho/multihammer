package Controller;

import Model.Modulos;
import Model.ModulosModel;
import Model.Perfil;
import com.jfoenix.controls.JFXToggleButton;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.cells.editors.TextFieldEditorBuilder;
import com.jfoenix.controls.cells.editors.base.GenericEditableTreeTableCell;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.layout.FlowPane;

public class ModulosController implements Initializable {
    // Declaración de componentes
    @FXML
    private JFXTreeTableView<Perfil> table;
    @FXML
    private FlowPane panelModulos;
    // Declaración/Instanciación de variables
    private final ModulosModel modelo = new ModulosModel();
    private String idPerfil;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        createTableView();
    }
    // Obtiene el id de la fila seleccionada
    private SimpleStringProperty getLeadSelect() {
        TreeItem<Perfil> selectedItem = table.getSelectionModel().getSelectedItem();
        return selectedItem == null ? null : selectedItem.getValue().id;
    }
    // Función para desplegar los módulos
    public void modulosToggle(String id) {
        // Obtiene todos los módulos y los que ya tiene el perfil asignado
        ArrayList<Modulos> modulos = modelo.modulosxperfil(id);
        // Se limpia el panel de módulos
        panelModulos.getChildren().clear();
        // Ciclo para agregar los módulos al panel
        modulos.forEach((x) -> {
            // Valida que no se imprima el módulo llamado 'Modulos'
            if (!x.getNombre().equals("Modulos")) {
                // Crea un JFXToggle con el nombre del módulo
                JFXToggleButton toggleBtn = new JFXToggleButton();
                toggleBtn.setText(x.getNombre());
                // Se asigna el evento para activar/desactivar el módulo
                toggleBtn.setOnAction((event) -> {
                    modelo.addModulosToPerfil(id, x.getId(), toggleBtn.isSelected());
                });
                // Marca el toggle como seleccionado si el módulo ya se encuentra asignado
                if (x.getActivo() != null) {
                    toggleBtn.setSelected(true);
                } else {
                    toggleBtn.setSelected(false);
                }
                // Se agrega el toggle al panel
                panelModulos.getChildren().add(toggleBtn);
            }
        });
    }
    // Se crea la tabla
    public void createTableView() {

        table.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> modulosToggle(getLeadSelect().get()));

        JFXTreeTableColumn<Perfil, String> id = new JFXTreeTableColumn("ID");
        id.setPrefWidth(100);
        
        id.setCellValueFactory((TreeTableColumn.CellDataFeatures<Perfil, String> param) -> param.getValue().getValue().id);

        JFXTreeTableColumn<Perfil, String> name = new JFXTreeTableColumn("Nombre");
        name.setPrefWidth(150);
        name.setCellValueFactory((TreeTableColumn.CellDataFeatures<Perfil, String> param) -> param.getValue().getValue().name);

        final TreeItem<Perfil> root = new RecursiveTreeItem<>(tableInformation(), RecursiveTreeObject::getChildren);

        table.getColumns().setAll(id, name);
        table.setRoot(root);
        table.setShowRoot(false);
    }
    // Se asigna la información de la tabla
    public ObservableList<Perfil> tableInformation() {
        ObservableList<Perfil> perfiles = FXCollections.observableArrayList();
        modelo.getPerfiles().forEach((x) -> {
            perfiles.add(new Perfil(x.id, x.name));
        });
        return perfiles;
    }
}
