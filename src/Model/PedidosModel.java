package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PedidosModel {
    
    private dbConnection con;
    
    public ObservableList<Pedidos> getPedidos() {
        ArrayList<Pedidos> ped = new ArrayList<>();
        ObservableList<Pedidos> pedidos = FXCollections.observableList(ped);
        con = new dbConnection();
        Connection reg = con.getConnection();
        PreparedStatement ps;
        ResultSet rs;
        try {
            ps = reg.prepareStatement("SELECT pedidos.id_pedido, CONCAT(clientes.nombre, ' ', clientes.apellido) as nombre, pedidos.fecha, pedidos.total FROM pedidos INNER JOIN clientes ON clientes.id_cliente = pedidos.id_cliente");
            rs = ps.executeQuery();
            while(rs.next()) {
                Pedidos x = new Pedidos(rs.getString("id_pedido"), rs.getString("nombre"), rs.getString("fecha"), rs.getString("total"));    
                ped.add(x);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        con.disconnect();
        return pedidos;
    }
    
    public ObservableList<Pedidos> getPedidos(String fechaInicio, String fechaFinal) {
        ArrayList<Pedidos> ped = new ArrayList<>();
        ObservableList<Pedidos> pedidos = FXCollections.observableList(ped);
        con = new dbConnection();
        Connection reg = con.getConnection();
        PreparedStatement ps;
        ResultSet rs;
        try {
            ps = reg.prepareStatement("SELECT pedidos.id_pedido, CONCAT(clientes.nombre, ' ', clientes.apellido) as nombre, pedidos.fecha, pedidos.total FROM pedidos INNER JOIN clientes ON clientes.id_cliente = pedidos.id_cliente WHERE pedidos.fecha >= ? AND pedidos.fecha <= ?");
            ps.setString(1, fechaInicio);
            ps.setString(2, fechaFinal);
            rs = ps.executeQuery();
            while(rs.next()) {
                Pedidos x = new Pedidos(rs.getString("id_pedido"), rs.getString("nombre"), rs.getString("fecha"), rs.getString("total"));    
                ped.add(x);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        con.disconnect();
        return pedidos;
    }
    
    public ObservableList<Registros> getRegistros(String id_venta) {
        ArrayList<Registros> ped = new ArrayList<>();
        ObservableList<Registros> pedidos = FXCollections.observableList(ped);
        con = new dbConnection();
        Connection reg = con.getConnection();
        PreparedStatement ps;
        ResultSet rs;
        try {
            ps = reg.prepareStatement("SELECT materiales.`material`, productos.`unidad`, registros.`cantidad`, registros.`precio` * registros.`cantidad` AS total FROM registros INNER JOIN productos ON productos.`id_producto` = registros.`id_producto` INNER JOIN materiales ON productos.`id_material` = materiales.`id_material` WHERE registros.`id_pedido` = ?;");
            ps.setString(1, id_venta);
            rs = ps.executeQuery();
            while(rs.next()) {
                Registros x = new Registros(rs.getString("material"), rs.getString("unidad"), rs.getString("cantidad"), rs.getString("total"));    
                ped.add(x);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        con.disconnect();
        return pedidos;
    }
}
