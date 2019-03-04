package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ModulosModel {
    
    private dbConnection con;
    
    public ArrayList<Perfil> getPerfiles() {
        ArrayList<Perfil> perfiles = new ArrayList<>();
        con = new dbConnection();
        Connection reg = con.getConnection();
        PreparedStatement ps;
        ResultSet rs;
        try {
            ps = reg.prepareStatement("SELECT perfiles.id_perfil, perfiles.nombre FROM perfiles");
            rs = ps.executeQuery();
            while(rs.next()) {
                Perfil x = new Perfil(rs.getString("id_perfil"), rs.getString("nombre"));    
                perfiles.add(x);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        con.disconnect();
        return perfiles;
    }
    
    public ArrayList<Modulos> modulosxperfil(String id) {
        ArrayList<Modulos> modulos = new ArrayList<>();
        con = new dbConnection();
        Connection reg = con.getConnection();
        PreparedStatement ps;
        ResultSet rs;
        try {
            ps = reg.prepareStatement("SELECT * , (SELECT 'Si' FROM acceso WHERE acceso.`id_modulo` = modulos.`id_modulo` AND acceso.`id_perfil` = ?) 'activo' FROM modulos");
            ps.setString(1, id);
            rs = ps.executeQuery();
            while(rs.next()) {
                Modulos x = new Modulos(rs.getString("id_modulo"), rs.getString("nombre"), rs.getString("estado"), rs.getString("activo"));    
                modulos.add(x);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        con.disconnect();
        return modulos;
    }
    
    public void addModulosToPerfil(String idPerfil, String idModulo, boolean status) {
        con = new dbConnection();
        Connection reg = con.getConnection();
        PreparedStatement ps;
        try {
            if(status) {
                ps = reg.prepareStatement("INSERT INTO acceso (id_perfil, id_modulo) VALUES (?, ?)");
            } else {
                ps = reg.prepareStatement("DELETE FROM acceso WHERE acceso.id_perfil = ? AND acceso.id_modulo = ?");
            }
            ps.setString(1,idPerfil);
            ps.setString(2,idModulo);
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        con.disconnect();
    }
    
}
