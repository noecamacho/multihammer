package Model;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;

public class Proveedor extends RecursiveTreeObject<Proveedor>{
    
    public SimpleStringProperty id_proveedor;
    public SimpleStringProperty domicilio;
    public SimpleStringProperty rfc;
    public SimpleStringProperty razon_social;
    public SimpleStringProperty telefono;

    public Proveedor(String id_proveedor, String domicilio, String rfc, String razon_social, String telefono) {
        this.id_proveedor = new SimpleStringProperty(id_proveedor);
        this.domicilio = new SimpleStringProperty(domicilio);
        this.rfc = new SimpleStringProperty(rfc);
        this.razon_social = new SimpleStringProperty(razon_social);
        this.telefono = new SimpleStringProperty(telefono);
    }

    public Proveedor(SimpleStringProperty id_proveedor, SimpleStringProperty domicilio, SimpleStringProperty rfc, SimpleStringProperty razon_social, SimpleStringProperty telefono) {
        this.id_proveedor = id_proveedor;
        this.domicilio = domicilio;
        this.rfc = rfc;
        this.razon_social = razon_social;
        this.telefono = telefono;
    }
    
    public Proveedor() {
        
    }

    public SimpleStringProperty getId_proveedor() {
        return id_proveedor;
    }

    public void setId_proveedor(SimpleStringProperty id_proveedor) {
        this.id_proveedor = id_proveedor;
    }

    public void setId_proveedor(String id_proveedor) {
        this.id_proveedor = new SimpleStringProperty(id_proveedor);
    }

    public SimpleStringProperty getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(SimpleStringProperty domicilio) {
        this.domicilio = domicilio;
    }

    public SimpleStringProperty getRfc() {
        return rfc;
    }

    public void setRfc(SimpleStringProperty rfc) {
        this.rfc = rfc;
    }

    public SimpleStringProperty getRazon_social() {
        return razon_social;
    }

    public void setRazon_social(SimpleStringProperty razon_social) {
        this.razon_social = razon_social;
    }

    public SimpleStringProperty getTelefono() {
        return telefono;
    }

    public void setTelefono(SimpleStringProperty telefono) {
        this.telefono = telefono;
    }
    
    
}
