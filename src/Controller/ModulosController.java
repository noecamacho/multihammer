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

    @FXML
    private JFXTreeTableView<Perfil> table;
    @FXML
    private FlowPane panelModulos;

    private final ModulosModel modelo = new ModulosModel();
    private String idPerfil;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        createTableView();
    }

    private SimpleStringProperty getLeadSelect() {
        TreeItem<Perfil> selectedItem = table.getSelectionModel().getSelectedItem();
        return selectedItem == null ? null : selectedItem.getValue().id;
    }

    public void modulosToggle(String id) {
        ArrayList<Modulos> modulos = modelo.modulosxperfil(id);
        panelModulos.getChildren().clear();
        modulos.forEach((x) -> {
            if (!x.getNombre().equals("Modulos")) {
                JFXToggleButton toggleBtn = new JFXToggleButton();
                toggleBtn.setText(x.getNombre());
                toggleBtn.setOnAction((event) -> {
                    modelo.addModulosToPerfil(id, x.getId(), toggleBtn.isSelected());
                });
                if (x.getActivo() != null) {
                    toggleBtn.setSelected(true);
                } else {
                    toggleBtn.setSelected(false);
                }
                panelModulos.getChildren().add(toggleBtn);
            }
        });
    }

    public void createTableView() {

        table.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> modulosToggle(getLeadSelect().get()));

        JFXTreeTableColumn<Perfil, String> id = new JFXTreeTableColumn("ID");
        id.setPrefWidth(100);
        
        id.setCellValueFactory((TreeTableColumn.CellDataFeatures<Perfil, String> param) -> param.getValue().getValue().id);
//        id.setCellFactory((TreeTableColumn<Perfil, String> param) -> new GenericEditableTreeTableCell<>(new TextFieldEditorBuilder()));
//        id.setOnEditCommit((TreeTableColumn.CellEditEvent<Perfil, String> t)-> t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue().id.set(t.getNewValue()));

        JFXTreeTableColumn<Perfil, String> name = new JFXTreeTableColumn("Nombre");
        name.setPrefWidth(100);
        name.setCellValueFactory((TreeTableColumn.CellDataFeatures<Perfil, String> param) -> param.getValue().getValue().name);
//        name.setCellFactory((TreeTableColumn<Perfil, String> param) -> new GenericEditableTreeTableCell<>(new TextFieldEditorBuilder()));
//        name.setOnEditCommit((TreeTableColumn.CellEditEvent<Perfil, String> t)-> t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue().name.set(t.getNewValue()));

        final TreeItem<Perfil> root = new RecursiveTreeItem<>(tableInformation(), RecursiveTreeObject::getChildren);

        table.getColumns().setAll(id, name);
        table.setRoot(root);
//        table.setEditable(true);
        table.setShowRoot(false);
    }

    public ObservableList<Perfil> tableInformation() {
        ObservableList<Perfil> perfiles = FXCollections.observableArrayList();
        modelo.getPerfiles().forEach((x) -> {
            perfiles.add(new Perfil(x.id, x.name));
        });
        return perfiles;
    }
}
