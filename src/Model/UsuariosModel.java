package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class UsuariosModel {
    private dbConnection con;
    
    public ArrayList<Usuario> getUsuarios() {
        ArrayList<Usuario> usuarios = new ArrayList<>();
        con = new dbConnection();
        Connection reg = con.getConnection();
        PreparedStatement ps;
        ResultSet rs;
        try {
            ps = reg.prepareStatement("SELECT id_usuario,usuario,nombre, (SELECT IF (estado = 1, \"Activo\", \"Inactivo\") )as estado FROM usuarios INNER JOIN perfiles ON usuarios.id_perfil=perfiles.id_perfil");
            rs = ps.executeQuery();
            while(rs.next()) {
                Usuario x = new Usuario(rs.getString("id_usuario"), rs.getString("usuario"), rs.getString("nombre"), rs.getString("usuario"), rs.getString("estado"));    
                usuarios.add(x);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        con.disconnect();
        return usuarios;
    }
    
    public String agregarUsuario(String id_perfil, String usuario, String password) {
        String message = "Usuario agregado exitosamente";
        con = new dbConnection();
        Connection reg = con.getConnection();
        PreparedStatement ps;
        ResultSet rs;
        try {
            ps = reg.prepareStatement("INSERT INTO usuarios (id_perfil, usuario, password) VALUES (?,?,?)");
            ps.setString(1, id_perfil);
            ps.setString(2, usuario);
            ps.setString(3, password);
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
            message = "El Usuario no se puede repetir";
        }
        con.disconnect();
        return message;
    }
    
    public ObservableList<ComboBoxClass> getPerfiles() {
        ArrayList<ComboBoxClass> per = new ArrayList<>();
        ObservableList<ComboBoxClass> perfiles = FXCollections.observableList(per);
        ComboBoxClass cbc;
        con = new dbConnection();
        Connection reg = con.getConnection();
        PreparedStatement ps;
        ResultSet rs;
        try {
            ps = reg.prepareStatement("SELECT id_perfil, nombre from perfiles");
            rs = ps.executeQuery();
            while(rs.next()) {
                cbc = new ComboBoxClass(rs.getString("id_perfil"), rs.getString("nombre"));
                per.add(cbc);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        con.disconnect();
        return perfiles;
    }
    
    public void cambiarEstado(String id_usuario) {
        con = new dbConnection();
        Connection reg = con.getConnection();
        PreparedStatement ps;
        ResultSet rs;
        try {
            ps = reg.prepareStatement("UPDATE usuarios SET estado = (SELECT IF(estado = '1', '0', '1')) WHERE usuarios.`id_usuario` = ?");
            ps.setString(1, id_usuario);
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        con.disconnect();
    }
}
