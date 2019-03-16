package Model;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;

public class ReporteClientes extends RecursiveTreeObject<ReporteClientes> {
    
    private SimpleStringProperty nombre;
    private SimpleStringProperty num_ventas;
    private SimpleStringProperty total;

    public ReporteClientes() {
    }

    public ReporteClientes(String nombre, String num_ventas, String total) {
        this.nombre = new SimpleStringProperty(nombre);
        this.num_ventas = new SimpleStringProperty(num_ventas);
        this.total = new SimpleStringProperty(total);
    }

    public ReporteClientes(SimpleStringProperty nombre, SimpleStringProperty num_ventas, SimpleStringProperty total) {
        this.nombre = nombre;
        this.num_ventas = num_ventas;
        this.total = total;
    }

    public SimpleStringProperty getNombre() {
        return nombre;
    }

    public void setNombre(SimpleStringProperty nombre) {
        this.nombre = nombre;
    }

    public SimpleStringProperty getNum_ventas() {
        return num_ventas;
    }

    public void setNum_ventas(SimpleStringProperty num_ventas) {
        this.num_ventas = num_ventas;
    }

    public SimpleStringProperty getTotal() {
        return total;
    }

    public void setTotal(SimpleStringProperty total) {
        this.total = total;
    }
    
    
    
}
