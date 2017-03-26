/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Controller.CitaController;
import Entity.Cita;
import Util.Context;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.model.DashboardColumn;
import org.primefaces.model.DashboardModel;
import org.primefaces.model.DefaultDashboardColumn;
import org.primefaces.model.DefaultDashboardModel;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;

/**
 *
 * @author Leonel
 *
 *
 */
@ManagedBean
@SessionScoped
public class DashboardController implements Serializable {

    private DashboardModel model;
    private CartesianChartModel citaChart;

    /**
     * Creates a new instance of DashboardController
     */
    public DashboardController() {
        model = new DefaultDashboardModel();

        DashboardColumn column1 = new DefaultDashboardColumn();
        DashboardColumn column2 = new DefaultDashboardColumn();

        column1.addWidget("Consultas");
        column1.addWidget("Odontogramas");
        column2.addWidget("GraficaCitas");

        model.addColumn(column1);
        model.addColumn(column2);

        citaChart = new CartesianChartModel();
        ChartSeries citaSeries = new ChartSeries();
        List<Cita> citasToday = ((CitaController) Context.getBean("citaController")).findCitaToday();
        Map<Integer, Integer> chartData = new HashMap<Integer, Integer>();
        for (int i = 0; i < 24; i++) {
            chartData.put(i, 0);
        }
        for (int i = 0; i < citasToday.size(); i++) {
            Integer hora = new Integer(citasToday.get(i).getFechacita().getHours());
            chartData.put(hora, chartData.get(hora) + 1);
        }
        for (Map.Entry<Integer, Integer> entry : chartData.entrySet()) {
            citaSeries.set(entry.getKey(), entry.getValue());
        }        
        citaChart.addSeries(citaSeries);
    }

    public DashboardModel getModel() {
        return model;
    }

    public CartesianChartModel getCitaChart() {
        return citaChart;
    }
}
