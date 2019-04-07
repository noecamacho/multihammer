package Model;

import Reportes.PrintReport;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import net.sf.jasperreports.engine.JRException;

public class VentasModel {
    
    private dbConnection con;
    
    public ObservableList<ComboBoxClass> getClientes() {
        ArrayList<ComboBoxClass> cli = new ArrayList<>();
        ObservableList<ComboBoxClass> clientes = FXCollections.observableList(cli);
        ComboBoxClass cbc;
        con = new dbConnection();
        Connection reg = con.getConnection();
        PreparedStatement ps;
        ResultSet rs;
        try {
            ps = reg.prepareStatement("SELECT id_cliente, CONCAT(nombre, \" \", apellido) AS nombre FROM clientes");
            rs = ps.executeQuery();
            while(rs.next()) {
                cbc = new ComboBoxClass( rs.getString("id_cliente"), rs.getString("nombre"));
                cli.add(cbc);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        con.disconnect();
        return clientes;
    }
    
    public ObservableList<ComboBoxClass> getMateriales() {
        ArrayList<ComboBoxClass> mat = new ArrayList<>();
        ObservableList<ComboBoxClass> materiales = FXCollections.observableList(mat);
        ComboBoxClass cbc;
        con = new dbConnection();
        Connection reg = con.getConnection();
        PreparedStatement ps;
        ResultSet rs;
        try {
            ps = reg.prepareStatement("SELECT id_material, material from materiales");
            rs = ps.executeQuery();
            while(rs.next()) {
                cbc = new ComboBoxClass(rs.getString("id_material"), rs.getString("material"));
                mat.add(cbc);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        con.disconnect();
        return materiales;
    }
    
    public ObservableList<ComboBoxClass> getUnidades(String id_material) {
        ArrayList<ComboBoxClass> uni = new ArrayList<>();
        ObservableList<ComboBoxClass> unidades = FXCollections.observableList(uni);
        ComboBoxClass cbc;
        con = new dbConnection();
        Connection reg = con.getConnection();
        PreparedStatement ps;
        ResultSet rs;
        try {
            ps = reg.prepareStatement("SELECT id_producto, unidad from productos WHERE id_material = ?");
            ps.setString(1, id_material);
            rs = ps.executeQuery();
            while(rs.next()) {
                cbc = new ComboBoxClass(rs.getString("id_producto"), rs.getString("unidad"));
                uni.add(cbc);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        con.disconnect();
        return unidades;
    }
    
    public float getPrecio(String id_producto) {
        float precio = 0;
        con = new dbConnection();
        Connection reg = con.getConnection();
        PreparedStatement ps;
        ResultSet rs;
        try {
            ps = reg.prepareStatement("SELECT precio FROM productos WHERE id_producto = ?");
            ps.setString(1, id_producto);
            rs = ps.executeQuery();
            if(rs.next()) {
                precio = Float.parseFloat(rs.getString("precio"));
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        con.disconnect();
        return precio;
    }
    
    public void registrarVenta(ObservableList<Venta> productos, String id_cliente, String total) {
        con = new dbConnection();
        int id_venta;
        Connection reg = con.getConnection();
        PreparedStatement ps;
        ResultSet rs;
        try {
            ps = reg.prepareStatement("INSERT INTO pedidos (id_cliente, total, estado) VALUES (?,?, 1)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, id_cliente);
            ps.setString(2, total);
            ps.executeUpdate();
            rs = ps.getGeneratedKeys();
            rs.next();
            id_venta = rs.getInt(1);
            ps = reg.prepareStatement("INSERT INTO registros (id_pedido, id_producto, cantidad, precio) VALUES (?,?,?,?)");
            for (Venta producto : productos) {
                ps.setInt(1, id_venta);
                ps.setString(2, producto.getId_producto());
                ps.setString(3, producto.getCantidad().get());
                ps.setString(4, producto.getPrecio().get());
                ps.addBatch();
            }
            ps.executeBatch();
            ps = reg.prepareStatement("UPDATE productos SET cantidad = cantidad - ? WHERE cantidad >= ? && id_producto = ?");
            for (Venta producto : productos) {
                ps.setString(1, producto.cantidad.get());
                ps.setString(2, producto.cantidad.get());
                ps.setString(3, producto.getId_producto());
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        con.disconnect();
    }
    
    public void registrarCotizacion(ObservableList<Venta> productos, String id_cliente, String total) throws JRException, ClassNotFoundException, IOException {
        con = new dbConnection();
        int id_venta;
        Connection reg = con.getConnection();
        PreparedStatement ps;
        ResultSet rs;
        try {
            ps = reg.prepareStatement("INSERT INTO pedidos (id_cliente, total, estado) VALUES (?,?, 0)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, id_cliente);
            ps.setString(2, total);
            ps.executeUpdate();
            rs = ps.getGeneratedKeys();
            rs.next();
            id_venta = rs.getInt(1);
            ps = reg.prepareStatement("INSERT INTO registros (id_pedido, id_producto, cantidad, precio) VALUES (?,?,?,?)");
            for (Venta producto : productos) {
                ps.setInt(1, id_venta);
                ps.setString(2, producto.getId_producto());
                ps.setString(3, producto.getCantidad().get());
                ps.setString(4, producto.getPrecio().get());
                ps.addBatch();
            }
            ps.executeBatch();
            ps = reg.prepareStatement("UPDATE productos SET cantidad = cantidad - ? WHERE cantidad >= ? && id_producto = ?");
            for (Venta producto : productos) {
                ps.setString(1, producto.cantidad.get());
                ps.setString(2, producto.cantidad.get());
                ps.setString(3, producto.getId_producto());
                ps.addBatch();
            }
            ps.executeBatch();
            
            // Instanciación para imprimir cotización utilizando el ID de la venta (pedido)
            PrintReport cotizacion = new PrintReport();
            cotizacion.showCotizacion(id_venta);
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        con.disconnect();
    }
    
    public void getCotizacion() {
        
    }
    
}