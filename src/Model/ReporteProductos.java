package Model;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;

public class ReporteProductos extends RecursiveTreeObject<ReporteProductos> {
    
    private SimpleStringProperty material;
    private SimpleStringProperty unidad;
    private SimpleStringProperty cantidad;
    private SimpleStringProperty precio;
    private SimpleStringProperty total;

    public ReporteProductos() {
    }

    public ReporteProductos(String material, String unidad, String cantidad, String precio, String total) {
        this.material = new SimpleStringProperty(material);
        this.unidad = new SimpleStringProperty(unidad);
        this.cantidad = new SimpleStringProperty(cantidad);
        this.precio = new SimpleStringProperty(precio);
        this.total = new SimpleStringProperty(total);
    }

    public ReporteProductos(SimpleStringProperty material, SimpleStringProperty unidad, SimpleStringProperty cantidad, SimpleStringProperty precio, SimpleStringProperty total) {
        this.material = material;
        this.unidad = unidad;
        this.cantidad = cantidad;
        this.precio = precio;
        this.total = total;
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

    public SimpleStringProperty getCantidad() {
        return cantidad;
    }

    public void setCantidad(SimpleStringProperty cantidad) {
        this.cantidad = cantidad;
    }

    public SimpleStringProperty getPrecio() {
        return precio;
    }

    public void setPrecio(SimpleStringProperty precio) {
        this.precio = precio;
    }

    public SimpleStringProperty getTotal() {
        return total;
    }

    public void setTotal(SimpleStringProperty total) {
        this.total = total;
    }
    
    
    
}
