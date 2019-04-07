package Controller;

import Model.ComboBoxClass;
import Model.Dialogs;
import Model.EstadisticasModel;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class EstadisticasController implements Initializable {

    private final Dialogs dialogs = new Dialogs();
    private final EstadisticasModel modelo = new EstadisticasModel();
    
    @FXML
    private JFXComboBox<ComboBoxClass> txtGrafica;
    @FXML
    private JFXComboBox<ComboBoxClass> txtReporte;
    @FXML
    private JFXDatePicker txtFechaInicial;
    @FXML
    private JFXDatePicker txtFechaFinal;
    @FXML
    private PieChart PieChart;
    @FXML
    private LineChart<?, ?> LineChart;
    @FXML
    private NumberAxis y;
    @FXML
    private CategoryAxis x;
    @FXML
    private BarChart<?, ?> BarChart;
    @FXML
    private NumberAxis y2;
    @FXML
    private CategoryAxis x2;
    @FXML
    private JFXButton btnGrafica;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        hideCharts();
        ObservableList<ComboBoxClass> data = FXCollections.observableArrayList();
        data.addAll(new ComboBoxClass("1", "De barras"),
                    new ComboBoxClass("2", "De pastel"),
                    new ComboBoxClass("3", "De líneas")
                );
        txtGrafica.setItems(data);
        ObservableList<ComboBoxClass> data2 = FXCollections.observableArrayList();
        data2.addAll(new ComboBoxClass("1", "De clientes"),
                    new ComboBoxClass("2", "De productos"),
                    new ComboBoxClass("3", "De pedidos")
                );
        txtReporte.setItems(data2);
        txtGrafica.setConverter(new StringConverter<ComboBoxClass>() {
            @Override
            public String toString(ComboBoxClass object) {
                return object.getNombre();
            }

            @Override
            public ComboBoxClass fromString(String string) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        txtReporte.setConverter(new StringConverter<ComboBoxClass>() {
            @Override
            public String toString(ComboBoxClass object) {
                return object.getNombre();
            }

            @Override
            public ComboBoxClass fromString(String string) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
    }    
    
    public void hideCharts() {
        PieChart.setVisible(false);
        LineChart.setVisible(false);
        BarChart.setVisible(false);
    }
    
    //Se llenan los valores de la gráfica seleccionada 
    public void setBarChart(String tipo, String fechaInicial, String fechaFinal) {
        //Se limpia la gráfica
        BarChart.getData().clear();
        //Se asignan los valores a la gráfica
        XYChart.Series data = new XYChart.Series<>();
        modelo.getChartInfo(tipo, fechaInicial, fechaFinal).forEach((x) -> {
            data.getData().add(new XYChart.Data(x.getId(), Double.parseDouble(x.getNombre())));
        });
        BarChart.getData().addAll(data);
        //Se ocultan todas las gráficas
        hideCharts();
        //Se muestra únicamente la tabla que se selecciono
        BarChart.setVisible(true);
    }
    
    public void setPieChart(String tipo, String fechaInicial, String fechaFinal) {
        PieChart.getData().clear();
        ObservableList<PieChart.Data> data = FXCollections.observableArrayList();
        modelo.getChartInfo(tipo, fechaInicial, fechaFinal).forEach((x) -> {
            data.add(new PieChart.Data(x.getId(), Double.parseDouble(x.getNombre())));
        });
        PieChart.setData(data);
        //Se ocultan todas las gráficas
        hideCharts();
        //Se muestra únicamente la tabla que se selecciono
        PieChart.setVisible(true);
    }
    
    public void setLineChart(String tipo, String fechaInicial, String fechaFinal) {
        //Se limpia la gráfica
        LineChart.getData().clear();
        //Se asignan los valores a la gráfica
        XYChart.Series data = new XYChart.Series<>();
        modelo.getChartInfo(tipo, fechaInicial, fechaFinal).forEach((x) -> {
            data.getData().add(new XYChart.Data(x.getId(), Double.parseDouble(x.getNombre())));
        });
        LineChart.getData().addAll(data);
        //Se ocultan todas las gráficas
        hideCharts();
        //Se muestra únicamente la tabla que se selecciono
        LineChart.setVisible(true);
        
    }
    
    @FXML
    private void btnGraficaAction() {
        if(txtGrafica.getValue() != null && txtReporte.getValue() != null && txtFechaInicial.getValue() != null && txtFechaFinal.getValue() != null) {
            if(txtFechaFinal.getValue().isAfter(txtFechaInicial.getValue())) {
                //Acción del botón
                //Se valido que los filtros estuvieran correctos y se selecciona el tipo de gráfica que se va a hacer
                switch(txtGrafica.getValue().getId()) {
                    case "1":
                        setBarChart(txtReporte.getValue().getId(), txtFechaInicial.getValue().toString(), txtFechaFinal.getValue().toString());
                        break;
                    case "2":
                        setPieChart(txtReporte.getValue().getId(), txtFechaInicial.getValue().toString(), txtFechaFinal.getValue().toString());
                        break;
                    case "3":
                        setLineChart(txtReporte.getValue().getId(), txtFechaInicial.getValue().toString(), txtFechaFinal.getValue().toString());
                        break;
                    default:
                        break;
                }
            } else {
                dialogs.displayMessage((Stage) btnGrafica.getScene().getWindow(), "Advertencia", "La fecha final no puede ser antes que la fecha inicial", "Ok");
            }
        } else {
                dialogs.displayMessage((Stage) btnGrafica.getScene().getWindow(), "Advertencia", "Es necesario que todos los datos se encuentren llenos para generar la gráfica", "Ok");
        }
    }
    
}
