package Model;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;

public class LoginModel {
    
    private dbConnection con;
    private final Dialogs dialogs = new Dialogs();
    
    public int validarLogin(String username, String password) {
        con = new dbConnection();
        Connection reg = con.getConnection();
        PreparedStatement ps;
        ResultSet rs;
        try {
            ps = reg.prepareStatement("SELECT usuarios.id_perfil FROM usuarios where usuario = ? AND password = ? AND estado = 1");
            ps.setString(1, username);
            ps.setString(2, password);
            rs = ps.executeQuery();
            con.disconnect();
            if(rs.next()) {
                    System.out.println("Ingreso exitoso");
                    return rs.getInt("id_perfil");
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return 0;
    }
}
