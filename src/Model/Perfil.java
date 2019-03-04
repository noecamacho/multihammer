package Model;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;

public class Perfil extends RecursiveTreeObject<Perfil> {

    public SimpleStringProperty name;

    public SimpleStringProperty id;

    public Perfil(String name, String id) {
        this.name = new SimpleStringProperty(name);
        this.id = new SimpleStringProperty(id);
    }

    public Perfil(SimpleStringProperty name, SimpleStringProperty id) {
        this.name = name;
        this.id = id;
    }
    
}
