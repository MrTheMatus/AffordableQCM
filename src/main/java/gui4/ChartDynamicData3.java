/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui4;

import java.awt.Color;
import java.awt.Font;
import java.text.SimpleDateFormat;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

/**
 *
 * @author Admin
 */
public class ChartDynamicData3 extends ChartDynamicData{
    public ChartDynamicData3(){
         // add primary axis frequency
        TimeSeries seriesFrequency = new TimeSeries("Thickness (nm)");
        datasetY = new TimeSeriesCollection(seriesFrequency);
        rangeAxisY = new NumberAxis("Thickness (nm)");
        XYItemRenderer renderer = new StandardXYItemRenderer();
        renderer.setSeriesPaint(0, new Color(0, 142, 192));
        rangeAxisY.setAutoRangeIncludesZero(false);
        rangeAxisY.setAutoRange(true);
        rangeAxisY.setAutoRangeMinimumSize(50);
        
        plot.setDataset(0, datasetY);
        plot.setRangeAxis(0, rangeAxisY);
        plot.setRangeAxisLocation(0, AxisLocation.BOTTOM_OR_LEFT);
        plot.setRenderer(0, renderer);  
        plot.mapDatasetToRangeAxis(0, 0);
        
        // add secondary axis temperature
        /*TimeSeries seriesTemperature = new TimeSeries("Temperature (°C)");
        datasetdY = new TimeSeriesCollection(seriesTemperature);
        plot.setDataset(1, datasetdY);
        NumberAxis rangeAxisT = new NumberAxis("Temperature (°C)");
        rangeAxisT.setAutoRangeIncludesZero(false);
        rangeAxisT.setAutoRange(true);
        rangeAxisT.setAutoRangeMinimumSize(5);
        plot.setRangeAxis(1, rangeAxisT);
        plot.setRangeAxisLocation(1, AxisLocation.BOTTOM_OR_RIGHT);
        // custom renderer for dinamically changing temperaure
        rendererT.setSeriesPaint(0, new Color(255, 128, 0)); 
        plot.setRenderer(1, rendererT);
        
        plot.mapDatasetToRangeAxis(1, 1);*/
        plotComb.add(plot);
        plotComb.setBackgroundPaint(Color.white);
        plotComb.setDomainGridlinePaint(Color.white);
        plotComb.setRangeGridlinePaint(Color.white);
        // enable panning for both axis
        plotComb.setRangePannable(true);
        plotComb.setDomainPannable(true);
                
        // set time axis properties
        // format time axis as hh:mm:ss
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        DateAxis axis = (DateAxis) plotComb.getDomainAxis();
        axis.setDateFormatOverride(format);
        // default auto range
        domainAxis.setAutoRange(true);
        
        // init the JFreeChart
        chart = new JFreeChart(plotComb);
        chart.setBorderPaint(Color.lightGray);
        chart.setBorderVisible(true);
        chart.setBackgroundPaint(Color.white);
        
        // set legend properties
        LegendTitle legend = chart.getLegend();
       // legend.setPosition(RectangleEdge.TOP);
        legend.setItemFont(new Font("Dialog", Font.PLAIN, 9));
        
        // constructor for org.jfree.chart.ChartPanel
        // ChartPanel(JFreeChart chart, boolean properties, boolean save, boolean print, boolean zoom, boolean tooltips)
        ChartPanel chartPanel = new ChartPanel(chart, false, true, true, true, true);
        // enable mouse wheel support for the chart panel
        chartPanel.setMouseWheelEnabled(true);
        //StackPane pn = new StackPane();
        //pn.getChildren().add(chartPanel);
        
        //this.setBackground(new Background(new BackgroundFill((javafx.scene.paint.Paint)(Paint)blue, null, null)));
        // add real time chart to the frame
        //this.getChildren().add(chartPanel);      
    }
}
