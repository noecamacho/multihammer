package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author Kevin Lizárraga
 */
public class MenuModel {
    
    private static dbConnection con;
    
    public static ArrayList<ComboBoxClass> menuxperfil(int idPerfil) {
        
        ArrayList<ComboBoxClass> modulos = new ArrayList<>();
        con = new dbConnection();
        Connection reg = con.getConnection();
        PreparedStatement ps;
        ResultSet rs;
        try {
            ps = reg.prepareStatement("SELECT modulos.`nombre`, modulos.icon FROM acceso INNER JOIN modulos ON acceso.id_modulo = modulos.id_modulo WHERE acceso.id_perfil = ? ");
            ps.setInt(1, idPerfil);
            rs = ps.executeQuery();
            con.disconnect();
            while(rs.next()) {
                    modulos.add(new ComboBoxClass(rs.getString("nombre"), rs.getString("icon")));
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return modulos;
    }
}
