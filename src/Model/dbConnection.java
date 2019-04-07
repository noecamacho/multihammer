package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class dbConnection {
    private static Connection conn;
    public static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    public static final String USER = "root";
    public static final String PASSWORD = "";
    public static final String URL = "jdbc:mysql://localhost:3306/multihammer?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    
    public dbConnection() {
        conn = null;
        try{
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch(ClassNotFoundException | SQLException e) {
            System.out.println("Error al conectar: " + e);
        }
    }
    
    //conexión a la BD
    public Connection getConnection() {
        return conn;
    }
    //desconectar de la BD
    public void disconnect() {
        conn = null;
//        if(conn == null) {
//            System.out.println("Conexión terminada");
//        }
    }
}
