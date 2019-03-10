package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ProductosModel {
    
    private dbConnection con;
    
    public ArrayList<Producto> getProductos() {
        ArrayList<Producto> productos = new ArrayList<>();
        con = new dbConnection();
        Connection reg = con.getConnection();
        PreparedStatement ps;
        ResultSet rs;
        try {
            ps = reg.prepareStatement("SELECT id_producto, material, unidad, precio, cantidad, razon_social FROM `materiales` INNER JOIN `productos` ON `productos`.`id_material` = `materiales`.`id_material` INNER JOIN proveedores ON productos.`id_proveedor` = proveedores.`id_proveedor` WHERE productos.existencia = 1");
            rs = ps.executeQuery();
            while(rs.next()) {
                Producto x = new Producto(rs.getString("material"), rs.getString("unidad"), rs.getString("precio"), rs.getString("razon_social"), rs.getString("cantidad"),rs.getString("id_producto"));    
                productos.add(x);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        con.disconnect();
        return productos;
    }
    
    public void eliminarProducto(String id) {
        con = new dbConnection();
        Connection reg = con.getConnection();
        PreparedStatement ps;
        ResultSet rs;
        try {
            ps = reg.prepareStatement("UPDATE productos SET existencia = 0 WHERE productos.id_producto = ?");
            ps.setString(1, id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        con.disconnect();
    }
    
    public void agregarProducto(String material, String descripcion, String unidad, String precio, String proveedor, String cantidad) {
        int id_material;
        con = new dbConnection();
        Connection reg = con.getConnection();
        PreparedStatement ps;
        ResultSet rs;
        try {
            ps = reg.prepareStatement("INSERT INTO materiales (material, descripcion) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, material);
            ps.setString(2, descripcion);
            ps.executeUpdate();
            rs = ps.getGeneratedKeys();
            rs.next();
            id_material = rs.getInt(1);
            System.out.println(id_material);
            ps = reg.prepareStatement("INSERT INTO productos (id_material, unidad, precio, id_proveedor, cantidad) VALUES (?,?,?,(SELECT proveedores.`id_proveedor` FROM proveedores WHERE proveedores.`razon_social` = ?),?)");
            ps.setInt(1, id_material);
            ps.setString(2, unidad);
            ps.setString(3, precio);
            ps.setString(4, proveedor);
            ps.setString(5, cantidad);
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        con.disconnect();
    }
    
    public void modificarProducto(String material, String descripcion, String unidad, String precio, String proveedor, String cantidad, String id_producto) {
        int id_material;
        con = new dbConnection();
        Connection reg = con.getConnection();
        PreparedStatement ps;
        ResultSet rs;
        try {
            ps = reg.prepareStatement("UPDATE materiales SET material = ?, descripcion = ? WHERE id_material = (SELECT id_material FROM productos WHERE id_producto = ?)");
            ps.setString(1, material);
            ps.setString(2, descripcion);
            ps.setString(3, id_producto);
            ps.executeUpdate();
            ps = reg.prepareStatement("UPDATE productos SET unidad = ?, precio = ?, id_proveedor = (SELECT id_proveedor FROM proveedores WHERE razon_social = ?), cantidad = ? WHERE id_producto = ?");
            ps.setString(1, unidad);
            ps.setString(2, precio);
            ps.setString(3, proveedor);
            ps.setString(4, cantidad);
            ps.setString(5, id_producto);
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        con.disconnect();
    }
    
    public ObservableList<String> getProveedores() {
        ArrayList<String> proveedores = new ArrayList<>();
        ObservableList<String> prov = FXCollections.observableList(proveedores);
        con = new dbConnection();
        Connection reg = con.getConnection();
        PreparedStatement ps;
        ResultSet rs;
        try {
            ps = reg.prepareStatement("SELECT razon_social from proveedores");
            rs = ps.executeQuery();
            while(rs.next()) {
                proveedores.add(rs.getString("razon_social"));
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        con.disconnect();
        return prov;
    }
    
    public String getDescripcion(String id_producto) {
        con = new dbConnection();
        Connection reg = con.getConnection();
        PreparedStatement ps;
        ResultSet rs;
        String descripcion = "";
        try {
            ps = reg.prepareStatement("SELECT descripcion from materiales WHERE id_material = (SELECT id_material from productos WHERE id_producto = ?)");
            ps.setString(1, id_producto);
            rs = ps.executeQuery();
            if(rs.next()) {
                descripcion = rs.getString("descripcion");
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        con.disconnect();
        return descripcion;
    }
}
