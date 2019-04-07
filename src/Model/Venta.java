package Model;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;

public class Venta extends RecursiveTreeObject<Venta> {
    public SimpleStringProperty id_producto;
    public SimpleStringProperty material;
    public SimpleStringProperty unidad;
    public SimpleStringProperty cantidad;
    public SimpleStringProperty precio;
    public SimpleStringProperty total;

    public Venta() {
    }

    public Venta(String id_producto, String material, String unidad, String cantidad, String precio, String total) {
        this.id_producto = new SimpleStringProperty(id_producto);
        this.material = new SimpleStringProperty(material);
        this.unidad = new SimpleStringProperty(unidad);
        this.cantidad = new SimpleStringProperty(cantidad);
        this.precio = new SimpleStringProperty(precio);
        this.total = new SimpleStringProperty(total);
    }

    public Venta(SimpleStringProperty id_producto, SimpleStringProperty material, SimpleStringProperty unidad, SimpleStringProperty cantidad, SimpleStringProperty precio, SimpleStringProperty total) {
        this.id_producto = id_producto;
        this.material = material;
        this.unidad = unidad;
        this.cantidad = cantidad;
        this.precio = precio;
        this.total = total;
    }

    public String getId_producto() {
        return id_producto.get();
    }

    public void setId_producto(String id_producto) {
        this.id_producto = new SimpleStringProperty(id_producto);
    }

    public SimpleStringProperty getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = new SimpleStringProperty(material);
    }

    public SimpleStringProperty getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = new SimpleStringProperty(unidad);
    }

    public SimpleStringProperty getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = new SimpleStringProperty(cantidad);
    }

    public SimpleStringProperty getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = new SimpleStringProperty(precio);
    }

    public SimpleStringProperty getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = new SimpleStringProperty(total);
    }
    
    
}
