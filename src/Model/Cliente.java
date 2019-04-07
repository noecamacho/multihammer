package Model;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;

public class Cliente extends RecursiveTreeObject<Cliente> {
    
    public SimpleStringProperty id_cliente;
    public SimpleStringProperty domicilio;
    public SimpleStringProperty rfc;
    public SimpleStringProperty nombre;
    public SimpleStringProperty apellido;
    public SimpleStringProperty telefono;

    public Cliente(String id_cliente, String domicilio, String rfc, String nombre, String apellido, String telefono) {
        this.id_cliente = new SimpleStringProperty(id_cliente);
        this.domicilio = new SimpleStringProperty(domicilio);
        this.rfc = new SimpleStringProperty(rfc);
        this.nombre = new SimpleStringProperty(nombre);
        this.apellido = new SimpleStringProperty(apellido);
        this.telefono = new SimpleStringProperty(telefono);
    }

    public Cliente(SimpleStringProperty id_cliente, SimpleStringProperty domicilio, SimpleStringProperty rfc, SimpleStringProperty nombre, SimpleStringProperty apellido, SimpleStringProperty telefono) {
        this.id_cliente = id_cliente;
        this.domicilio = domicilio;
        this.rfc = rfc;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
    }

    public Cliente() {
        
    }
    
    public SimpleStringProperty getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(SimpleStringProperty id_cliente) {
        this.id_cliente = id_cliente;
    }

    public void setId_cliente(String id_cliente) {
        this.id_cliente = new SimpleStringProperty(id_cliente);
    }
    
    
}
