package Model;

import com.jfoenix.controls.JFXToggleButton;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;

public class Producto extends RecursiveTreeObject<Producto>{
    
    public SimpleStringProperty material;
    public SimpleStringProperty unidad;
    public SimpleStringProperty precio;
    public SimpleStringProperty proveedor;
    public SimpleStringProperty cantidad;
    public SimpleStringProperty id_producto;
    public SimpleStringProperty estado;

    public Producto(SimpleStringProperty material, SimpleStringProperty unidad, SimpleStringProperty precio, SimpleStringProperty proveedor, SimpleStringProperty cantidad, SimpleStringProperty id_producto, SimpleStringProperty estado) {
        this.material = material;
        this.unidad = unidad;
        this.precio = precio;
        this.proveedor = proveedor;
        this.cantidad = cantidad;
        this.id_producto = id_producto;
        this.estado = estado;
    }

    public Producto(String material, String unidad, String precio, String proveedor, String cantidad, String id_producto, String estado) {
        this.material = new SimpleStringProperty(material);
        this.unidad = new SimpleStringProperty(unidad);
        this.precio = new SimpleStringProperty(precio);
        this.proveedor = new SimpleStringProperty(proveedor);
        this.cantidad = new SimpleStringProperty(cantidad);
        this.id_producto = new SimpleStringProperty(id_producto);
        this.estado = new SimpleStringProperty(estado);
    }

    public Producto() {
    }

    public SimpleStringProperty getMaterial() {
        return material;
    }

    public void setMaterial(SimpleStringProperty material) {
        this.material = material;
    }

    public SimpleStringProperty getUnidad() {
        return unidad;
    }

    public void setUnidad(SimpleStringProperty unidad) {
        this.unidad = unidad;
    }

    public SimpleStringProperty getPrecio() {
        return precio;
    }

    public void setPrecio(SimpleStringProperty precio) {
        this.precio = precio;
    }

    public SimpleStringProperty getProveedor() {
        return proveedor;
    }

    public void setProveedor(SimpleStringProperty proveedor) {
        this.proveedor = proveedor;
    }

    public SimpleStringProperty getCantidad() {
        return cantidad;
    }

    public void setCantidad(SimpleStringProperty cantidad) {
        this.cantidad = cantidad;
    }

    public SimpleStringProperty getId_producto() {
        return id_producto;
    }

    public void setId_producto(SimpleStringProperty id_producto) {
        this.id_producto = id_producto;
    }

    public void setId_producto(String id_producto) {
        this.id_producto = new SimpleStringProperty(id_producto);
    }

    public SimpleStringProperty getEstado() {
        return estado;
    }

    public void setEstado(SimpleStringProperty estado) {
        this.estado = estado;
    }
    
    
    
}
