package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProveedorModel {
    
    private dbConnection con;
    
    public ArrayList<Proveedor> getProveedores() {
        ArrayList<Proveedor> proveedores = new ArrayList<>();
        con = new dbConnection();
        Connection reg = con.getConnection();
        PreparedStatement ps;
        ResultSet rs;
        try {
            ps = reg.prepareStatement("SELECT * FROM proveedores WHERE estado = 1");
            rs = ps.executeQuery();
            while(rs.next()) {
                Proveedor x = new Proveedor(rs.getString("id_proveedor"), rs.getString("domicilio"), rs.getString("rfc"), rs.getString("razon_social"), rs.getString("telefono"), rs.getString("estado"));    
                proveedores.add(x);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        con.disconnect();
        return proveedores;
    }
    
    public void eliminarProveedor(String id_proveedor) {
        con = new dbConnection();
        Connection reg = con.getConnection();
        PreparedStatement ps;
        ResultSet rs;
        try {
            ps = reg.prepareStatement("UPDATE proveedores SET estado = 0 WHERE id_proveedor = ?");
            ps.setString(1, id_proveedor);
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        con.disconnect();
    }
    
    public String agregarProveedor(String razon_social, String domicilio, String telefono, String rfc) {
        String message = "Proveedor agregado exitosamente";
        con = new dbConnection();
        Connection reg = con.getConnection();
        PreparedStatement ps;
        ResultSet rs;
        try {
            ps = reg.prepareStatement("INSERT INTO proveedores (razon_social, domicilio, telefono, rfc) VALUES (?,?,?,?)");
            ps.setString(1, razon_social);
            ps.setString(2, domicilio);
            ps.setString(3, telefono);
            ps.setString(4, rfc);
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
            message = "La razón social no puede repetirse";
        }
        con.disconnect();
        return message;
    }
    
    public String modificarProveedor(String razon_social, String domicilio, String telefono, String rfc, String id_proveedor) {
        String message = "Proveedor modificado exitosamente";
        con = new dbConnection();
        Connection reg = con.getConnection();
        PreparedStatement ps;
        ResultSet rs;
        try {
            ps = reg.prepareStatement("UPDATE proveedores SET razon_social = ?, domicilio = ?, telefono = ?, rfc = ? WHERE id_proveedor = ?");
            ps.setString(1, razon_social);
            ps.setString(2, domicilio);
            ps.setString(3, telefono);
            ps.setString(4, rfc);
            ps.setString(5, id_proveedor);
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
            message = "La razón social no puede repetirse";
        }
        con.disconnect();
        return message;
    }
    
    public void cambiarEstado(String id_proveedor) {
        con = new dbConnection();
        Connection reg = con.getConnection();
        PreparedStatement ps;
        ResultSet rs;
        try {
            ps = reg.prepareStatement("UPDATE proveedores SET estado = (SELECT IF(estado = '1', '0', '1')) WHERE proveedores.`id_proveedor` = ?");
            ps.setString(1, id_proveedor);
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        con.disconnect();
    }
    
}
