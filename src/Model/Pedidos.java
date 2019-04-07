package Model;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;

public class Pedidos extends RecursiveTreeObject<Pedidos>{
    
    public SimpleStringProperty id_pedido;
    public SimpleStringProperty nombre;
    public SimpleStringProperty fecha;
    public SimpleStringProperty total;

    public Pedidos() {
    }

    public Pedidos(String id_pedido, String nombre, String fecha, String total) {
        this.id_pedido = new SimpleStringProperty(id_pedido);
        this.nombre = new SimpleStringProperty(nombre);
        this.fecha = new SimpleStringProperty(fecha);
        this.total = new SimpleStringProperty(total);
    }

    public Pedidos(SimpleStringProperty id_pedido, SimpleStringProperty nombre, SimpleStringProperty fecha, SimpleStringProperty total) {
        this.id_pedido = id_pedido;
        this.nombre = nombre;
        this.fecha = fecha;
        this.total = total;
    }

    public SimpleStringProperty getId_pedido() {
        return id_pedido;
    }

    public void setId_pedido(SimpleStringProperty id_pedido) {
        this.id_pedido = id_pedido;
    }

    public SimpleStringProperty getNombre() {
        return nombre;
    }

    public void setNombre(SimpleStringProperty nombre) {
        this.nombre = nombre;
    }

    public SimpleStringProperty getFecha() {
        return fecha;
    }

    public void setFecha(SimpleStringProperty fecha) {
        this.fecha = fecha;
    }

    public SimpleStringProperty getTotal() {
        return total;
    }

    public void setTotal(SimpleStringProperty total) {
        this.total = total;
    }
    
    

    
}
