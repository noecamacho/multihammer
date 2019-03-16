package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ReportesModel {
    
    private dbConnection con;
    
    public ObservableList<ReporteClientes> getInfoClientes(String fechaInicial, String fechaFinal) {
        ArrayList<ReporteClientes> cli = new ArrayList<>();
        ObservableList<ReporteClientes> clientes = FXCollections.observableList(cli);
        con = new dbConnection();
        Connection reg = con.getConnection();
        PreparedStatement ps;
        ResultSet rs;
        try {
            ps = reg.prepareStatement("SELECT CONCAT(clientes.`nombre`, \" \", clientes.`apellido` ) AS nombre, COUNT(*) AS num_ventas, SUM(pedidos.`total`) AS total\n" +
                                        "FROM  pedidos\n" +
                                        "INNER JOIN clientes ON pedidos.`id_cliente` = clientes.`id_cliente`\n" +
                                        "WHERE pedidos.fecha >= ? AND pedidos.fecha <= ?\n" +
                                        "GROUP BY nombre\n" +
                                        "ORDER BY total DESC");
            ps.setString(1, fechaInicial);
            ps.setString(2, fechaFinal);
            rs = ps.executeQuery();
            while(rs.next()) {
                ReporteClientes x = new ReporteClientes(rs.getString("nombre"), rs.getString("num_ventas"), rs.getString("total"));    
                cli.add(x);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        con.disconnect();
        return clientes;
    }
    
    public ObservableList<ReporteProductos> getInfoProductos(String fechaInicial, String fechaFinal) {
        ArrayList<ReporteProductos> pro = new ArrayList<>();
        ObservableList<ReporteProductos> productos = FXCollections.observableList(pro);
        con = new dbConnection();
        Connection reg = con.getConnection();
        PreparedStatement ps;
        ResultSet rs;
        try {
            ps = reg.prepareStatement("SELECT materiales.`material`, productos.`unidad`,SUM(registros.`cantidad`) AS cantidad, productos.`precio`, SUM(registros.`cantidad`) * productos.`precio` AS total\n" +
                                        "FROM registros\n" +
                                        "INNER JOIN productos ON productos.`id_producto` = registros.`id_producto`\n" +
                                        "INNER JOIN materiales ON materiales.`id_material` = productos.`id_material`\n" +
                                        "INNER JOIN pedidos ON pedidos.`id_pedido` = registros.`id_pedido`\n" +
                                        "WHERE pedidos.fecha >= ? AND pedidos.fecha <= ?\n" +
                                        "GROUP BY registros.`id_producto`\n" +
                                        "ORDER BY total DESC");
            ps.setString(1, fechaInicial);
            ps.setString(2, fechaFinal);
            rs = ps.executeQuery();
            while(rs.next()) {
                ReporteProductos x = new ReporteProductos(rs.getString("material"), rs.getString("unidad"), rs.getString("cantidad"), rs.getString("precio"), rs.getString("total"));    
                pro.add(x);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        con.disconnect();
        return productos;
    }
    
    public ObservableList<Pedidos> getPedidos(String fechaInicial, String fechaFinal) {
        ArrayList<Pedidos> ped = new ArrayList<>();
        ObservableList<Pedidos> pedidos = FXCollections.observableList(ped);
        con = new dbConnection();
        Connection reg = con.getConnection();
        PreparedStatement ps;
        ResultSet rs;
        try {
            ps = reg.prepareStatement("SELECT pedidos.id_pedido, CONCAT(clientes.nombre, ' ', clientes.apellido) as nombre, pedidos.fecha, pedidos.total FROM pedidos INNER JOIN clientes ON clientes.id_cliente = pedidos.id_cliente WHERE pedidos.fecha >= ? AND pedidos.fecha <= ?");
            ps.setString(1, fechaInicial);
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
}
