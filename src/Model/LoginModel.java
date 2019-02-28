package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginModel {
    
    private dbConnection con;
    
    public int validarLogin(String username, String password) {
        con = new dbConnection();
        Connection reg = con.getConnection();
        PreparedStatement ps;
        ResultSet rs;
        try {
            ps = reg.prepareStatement("SELECT usuarios.id_usuario, usuarios.usuario, usuarios.password FROM usuarios where usuario = ? AND password = ?");
            ps.setString(1, username);
            ps.setString(2, password);
            rs = ps.executeQuery();
            con.disconnect();
            if(rs.next()) {
                    System.out.println("Ingreso exitoso");
                    return rs.getInt("id_usuario");
                } else {
                    System.out.println("Nel");
                }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return 0;
    }
}
