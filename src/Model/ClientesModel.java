package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ClientesModel {
    private dbConnection con;
    
    public ArrayList<Cliente> getClientes() {
        ArrayList<Cliente> clientes = new ArrayList<>();
        con = new dbConnection();
        Connection reg = con.getConnection();
        PreparedStatement ps;
        ResultSet rs;
        try {
            ps = reg.prepareStatement("SELECT * FROM clientes");
            rs = ps.executeQuery();
            while(rs.next()) {
                Cliente x = new Cliente(rs.getString("id_cliente"), rs.getString("domicilio"), rs.getString("rfc"), rs.getString("nombre"),rs.getString("apellido"), rs.getString("telefono"));    
                clientes.add(x);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        con.disconnect();
        return clientes;
    }
    
    public String agregarCliente(String nombre, String apellido, String domicilio, String telefono, String rfc) {
        String message = "Cliente agregado exitosamente";
        con = new dbConnection();
        Connection reg = con.getConnection();
        PreparedStatement ps;
        ResultSet rs;
        try {
            ps = reg.prepareStatement("INSERT INTO clientes (nombre, apellido, domicilio, telefono, rfc) VALUES (?,?,?,?,?)");
            ps.setString(1, nombre);
            ps.setString(2, apellido);
            ps.setString(3, domicilio);
            ps.setString(4, telefono);
            ps.setString(5, rfc);
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
            message = "El RFC de cliente no se puede repetir";
        }
        con.disconnect();
        return message;
    }
    
    public String modificarCliente(String nombre, String apellido, String domicilio, String telefono, String rfc, String id_cliente) {
        String message = "Cliente modificado exitosamente";
        con = new dbConnection();
        Connection reg = con.getConnection();
        PreparedStatement ps;
        ResultSet rs;
        try {
            ps = reg.prepareStatement("UPDATE clientes SET nombre = ?, apellido = ?, domicilio = ?, telefono = ?, rfc = ? WHERE id_cliente = ?");
            ps.setString(1, nombre);
            ps.setString(2, apellido);
            ps.setString(3, domicilio);
            ps.setString(4, telefono);
            ps.setString(5, rfc);
            ps.setString(6, id_cliente);
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
            message = "El RFC de cliente no se puede repetir";
        }
        con.disconnect();
        return message;
    }
}
