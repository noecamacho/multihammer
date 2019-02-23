package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginModel {
    
    private dbConnection con;
    
    public void validarLogin(String username, String password) {
        con = new dbConnection();
        Connection reg = con.getConnection();
        PreparedStatement ps;
        ResultSet rs;
        try {
            ps = reg.prepareStatement("SELECT usuario.usuario, usuario.password FROM usuario where usuario = ? AND password = ?");
            ps.setString(1, username);
            ps.setString(2, password);
            rs = ps.executeQuery();
            if(rs.next()) {
                    System.out.println("Ingreso exitoso");
                } else {
                    System.out.println("Nel");
                }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        con.disconnect();
    }
}
