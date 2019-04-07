package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class EstadisticasModel {
    
    private dbConnection con;
    
    public ObservableList<ComboBoxClass> getChartInfo(String tipo, String fechaInicial, String fechaFinal) {
        ArrayList<ComboBoxClass> info = new ArrayList<>();
        ObservableList<ComboBoxClass> data = FXCollections.observableList(info);
        con = new dbConnection();
        Connection reg = con.getConnection();
        PreparedStatement ps;
        ResultSet rs;
        try {
            switch(tipo) {
                case "1":
                    ps = reg.prepareStatement("SELECT CONCAT(clientes.`nombre`, \" \", clientes.`apellido` ) AS x, SUM(pedidos.`total`) AS y\n" +
                                                "FROM  pedidos\n" +
                                                "INNER JOIN clientes ON pedidos.`id_cliente` = clientes.`id_cliente`\n" +
                                                "WHERE pedidos.fecha >= ? AND pedidos.fecha <= ?\n" +
                                                "GROUP BY x\n" +
                                                "ORDER BY y DESC");
                    break;
                case "2":
                    ps = reg.prepareStatement("SELECT CONCAT(materiales.`material`, '-', productos.`unidad`) as x, productos.`precio`, SUM(registros.`cantidad`) * productos.`precio` AS y\n" +
                                        "FROM registros\n" +
                                        "INNER JOIN productos ON productos.`id_producto` = registros.`id_producto`\n" +
                                        "INNER JOIN materiales ON materiales.`id_material` = productos.`id_material`\n" +
                                        "INNER JOIN pedidos ON pedidos.`id_pedido` = registros.`id_pedido`\n" +
                                        "WHERE pedidos.fecha >= ? AND pedidos.fecha <= ?\n" +
                                        "GROUP BY x\n" +
                                        "ORDER BY y DESC");
                    break;
                case "3":
                    ps = reg.prepareStatement("SELECT pedidos.id_pedido as x, pedidos.total as y FROM pedidos INNER JOIN clientes ON clientes.id_cliente = pedidos.id_cliente WHERE pedidos.fecha >= ? AND pedidos.fecha <= ?");
                    break;
                default:
                    ps = reg.prepareStatement("SELECT CONCAT(materiales.`material`, '-', productos.`unidad`) as x, productos.`precio`, SUM(registros.`cantidad`) * productos.`precio` AS y\n" +
                                        "FROM registros\n" +
                                        "INNER JOIN productos ON productos.`id_producto` = registros.`id_producto`\n" +
                                        "INNER JOIN materiales ON materiales.`id_material` = productos.`id_material`\n" +
                                        "INNER JOIN pedidos ON pedidos.`id_pedido` = registros.`id_pedido`\n" +
                                        "WHERE pedidos.fecha >= ? AND pedidos.fecha <= ?\n" +
                                        "GROUP BY x.`id_producto`\n" +
                                        "ORDER BY y DESC");
                    break;
            }
            ps.setString(1, fechaInicial);
            ps.setString(2, fechaFinal);
            rs = ps.executeQuery();
            while(rs.next()) {
                ComboBoxClass x = new ComboBoxClass(rs.getString("x"), rs.getString("y"));    
                info.add(x);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        con.disconnect();
        return data;
    }
    
}
