/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML.java to edit this template
 */
package gui4;

import java.awt.Color;
import java.awt.Font;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
//import javafx.graphics;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.zu.ardulink.Link;
import javafx.application.Application;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import org.apache.commons.math3.stat.descriptive.rank.Median;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.zu.ardulink.RawDataListener;
import org.jfree.chart.fx.ChartViewer;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CombinedDomainXYPlot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;


/**
 *
 * @author Admin
 */
public class Ginterface extends Application implements RawDataListener {

    Parent root;
    Stage stage;
    public String tester;
    public String mat;
    public int changeFreq;
    private static JFreeChart createChart(String name) {
        TimeSeriesCollection datasetY;
        TimeSeriesCollection datasetdY;
        XYPlot plot = new XYPlot();
        XYItemRenderer rendererT = new StandardXYItemRenderer();
        NumberAxis rangeAxisY = new NumberAxis();
        NumberAxis rangeAxisdY = new NumberAxis();
        CombinedDomainXYPlot plotComb = new CombinedDomainXYPlot(new DateAxis("Time (hh:mm:ss)"));
        ValueAxis domainAxis = plotComb.getDomainAxis();
        
        TimeSeries seriesFrequency = new TimeSeries("Frequency (Hz)");
        datasetY = new TimeSeriesCollection(seriesFrequency);
        rangeAxisY = new NumberAxis("Frequency (Hz)");
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
        TimeSeries seriesTemperature = new TimeSeries("Temperature (°C)");
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
        
        plot.mapDatasetToRangeAxis(1, 1);
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
        JFreeChart chart = new JFreeChart(plotComb);
        chart.setBorderPaint(Color.lightGray);
        chart.setBorderVisible(true);
        chart.setBackgroundPaint(Color.white);
        
        // set legend properties
        LegendTitle legend = chart.getLegend();
       // legend.setPosition(RectangleEdge.TOP);
        legend.setItemFont(new Font("Dialog", Font.PLAIN, 9));
        return chart;
    }

    // Ardulink link class for communication with Arduino
    private final Link link = Link.getDefaultInstance();
    // Store file for data recording
   
    // Write file for data recording
    // size of circular buffer
    private final int bufferSize = 10;
    // frequency circular buffer for eliminating signal glitches using median
    ArrayCircularBuffer bufferFrequency = new ArrayCircularBuffer(bufferSize/2);
    ArrayCircularBuffer bufferFrequencyLast = bufferFrequency;
    // frequency circular buffer for averaging frequency data
    ArrayCircularBuffer bufferFrequencyTemp = new ArrayCircularBuffer(bufferSize);
    // temperauture circular buffer for averaging temperature data
    ArrayCircularBuffer bufferTemperature = new ArrayCircularBuffer(bufferSize);
    // temperature circular buffer for smoothing data
    // ArrayCircularBuffer bufferTemperatureTemp = new ArrayCircularBuffer(bufferSize/2);
    // nominal quartz crystal frequency
    int FrequencyNominal = 5000000;
    double meanFrequency = 5000000;
    double meanTemperature =20;
    double ref =0;
    // Arduino half timer clock
    public int ALIAS = 8000000;
    double meanFrequencyL= 2*ALIAS - FrequencyNominal;
    double lastTime=0;
    Button btn2;
    Button btn3;
    Button btn0;
   
     ChartDynamicData chartFreqCnt=new ChartDynamicData();
     ChartDynamicData chartChangeCnt = new ChartDynamicData();
     ChartDynamicData chartThickCnt=new ChartDynamicData3();
     ChartDynamicData chartRocCnt=new ChartDynamicData2();
     FXMLDocumentController controller=new FXMLDocumentController();
     ComboBox cb = controller.getMaterialComboBox();
     
    private FlowPane fp;
    private Button btn1;
    @Override
    public void start(Stage s) throws Exception  {
       stage=s;
        
        Locale locale = new Locale("en");
        Locale.setDefault(locale);
        ResourceBundle resources = ResourceBundle.getBundle("bundles/lang");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/FXMLDocument.fxml"),resources);
        root = loader.load();
        String localeStr = "en"; //TODO: actual language selection
        //loader.setResources(ResourceBundle.getBundle("bundles.lang", new Locale(localeStr)));
        //loader.setResources(ResourceBundle.getBundle("bundles.label", new Locale("de_DE")));
        controller = loader.getController();
        //ConnectboxController controller2 = loader2.getController();
        TabPane tabPane = controller.getTabPane();
        tabPane.getTabs().add(new Tab("Freq Change", new ChartViewer(chartFreqCnt.getChart())));
        tabPane.getTabs().add(new Tab("Frequency", new ChartViewer(chartChangeCnt.getChart())));
        tabPane.getTabs().add(new Tab("Thickness", new ChartViewer(chartThickCnt.getChart())));
        tabPane.getTabs().add(new Tab("Rate of change", new ChartViewer(chartRocCnt.getChart())));
        btn1 = controller.getUsb();
        btn3 = controller.getManual();
        
        Scene scene1 = new Scene(root);
        link.addRawDataListener(this); 
        // the COM port connected to Arduino
        s.setScene(scene1);
        //stage.initModality(Modality.APPLICATION_MODAL);
        s.setTitle("Thickness measurement");
        s.show();
    }

    /**
     *
     * @param string
     * @param i
     * @param ints
     */
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void parseInput(String id, int numBytes, int[] message) {
        // read the message
        
        //double roc2 = ((meanFrequency - meanFrequencyL)/(chartRocCnt.checkTime()-lastTime));
        StringBuilder build = new StringBuilder(numBytes + 1);
        for (int i = 0; i < numBytes; i++) {
            build.append((char) message[i]);
        }
        String messageString = build.toString();
        tester = messageString;

        // if the message starts with the string "RAWMONITOR" display and store data
        if (messageString.startsWith("RAWMONITOR")) {
            // print the value on the screen
            System.out.println(messageString);
            String message2, message3, message4;
            messageString = messageString.substring("RAWMONITOR".length());
            /*message2=messageString.substring(0,3);
            try{
            message3=messageString.substring(13,20);
            message4=messageString.substring(21,24);
            }catch (StringIndexOutOfBoundsException s){
                 message3=messageString.substring(0,7);
                 message4=messageString.substring(8,11);
            }
            */
            String[] dataSplits = messageString.split("_");
            int dataFrequency=0;
            int dataTemperature=0;
            try{
            dataFrequency = (int) (Integer.parseInt(dataSplits[0]));
            dataTemperature = (int)Integer.parseInt(dataSplits[1]);
            }catch (NumberFormatException e){
                /*try{
                    dataFrequency = (int) (Integer.parseInt(message3));
                    try{
                        dataTemperature =(int) Integer.parseInt(message4);
                    }catch (NumberFormatException e2){
                        dataTemperature = (int)Integer.parseInt(message2);
                    }
                }catch (NumberFormatException e3){
                    messageString ="";
                    return;
                }*/
                messageString ="";
            }
    
            /* 
             * Frequency Median implemented using Apache commons Math
             * frequency data are affected by some glitches due to the 
             * algorithm for counting pulses during a fixed time interval
             * median is a robust algorithm for smoothing frequency data
             * and for eliminating outliers
             * Frequency data processing algorithm: averaging and calculate median 
             */
            
            // insert new frequency data in circuar buffer and calculate the average
            messageString ="";
            bufferFrequencyTemp.insert(dataFrequency);
            double sum = 0;
            for (int i = 0; i < bufferFrequencyTemp.size(); i++) {
                sum = sum + (int) bufferFrequencyTemp.data[i];
            }
            // Average frequency data 
            double averageFrequency = sum / bufferFrequencyTemp.size();
            // insert new average frequency data in circuar buffer and calculate median
            //changeFreq =  bufferFrequency - meanFrequency;
            bufferFrequencyLast = bufferFrequency;
            bufferFrequency.insert(averageFrequency);
            
            // read the circular buffer
            int count = bufferFrequency.size();
            double values [] = new double [count];
            for (int i = 0; i < count; i++) values[i] = (double) (bufferFrequency.data[i]);
            Median median = new Median();
            // calculate the median of frequency data
            meanFrequency = (double) median.evaluate(values);
            
            double valuesL [] = new double [count];
            for (int i = 0; i < count; i++) valuesL[i] = (double) (bufferFrequencyLast.data[i]);
            Median medianL = new Median();
            // calculate the median of frequency data
            meanFrequencyL = (double) medianL.evaluate(valuesL);
            meanFrequencyL = (2 * ALIAS) - meanFrequencyL;
            // alias arduino timer 
            //lastTime = chartFreqCnt.checkTime();
            // insert temperature data in circuar buffer and calculate the average 
            bufferTemperature.insert(dataTemperature);
            double sumT = 0;
            for (int i = 0; i < bufferTemperature.size(); i++) {
                sumT = sumT + (int) bufferTemperature.data[i];
            }
            // Average temperature data
            meanTemperature = sumT / bufferTemperature.size();
            // TODO divide by 10 for decimal
            meanTemperature = meanTemperature/10;
            
            //set value
            chartFreqCnt.addFrequencyData(meanFrequency);
            chartFreqCnt.addTemperatureData(meanTemperature);
            chartThickCnt.addFrequencyData(Double.parseDouble(bufferFrequency.peekLast().toString()));
            chartRocCnt.addFrequencyData(meanFrequency);
            controller.setFreq(meanFrequency);
            controller.setTemp(meanTemperature);
            //controller.setDensity(sumT);
            //controller.set
            
//controller.setFreq(meanFrequency);
            //controller.setFreq(meanTemperature);
            
            /*
            // display data
            frequencyCurrent.setText(String.format("%.1f", meanFrequency));
            temperatureCurrent.setText(String.format("%.1f", meanTemperature));
            //Roc.setText(String.format("%.1f", roc2));
            //add new data in dynamic chart. Frequency data plot by default
            *///chartFreqCnt.addFrequencyData(meanFrequency - ref);
            //chartRocCnt.addRoc(roc2);
            /*
            // show temperature data in dynamic chart
            *///chartFreqCnt.addTemperatureData(meanTemperature);
               // chartFreqCnt.showChartTemperature();
            }
            // hide temperature  
            //else if (showTemperatureBtn.isSelected() == false){
            //    chartTab1.hideChartTemperature();
            //}
            // check domain axis
            //chartFreqCnt.checkDomainAxis();

            // store data 
            /*if (saveFileBtn.isSelected() == true) {
                try {
                    fw = new FileWriter(sf.getAbsoluteFile(), true);
                    BufferedWriter bw = new BufferedWriter(fw);
                    Calendar cal = Calendar.getInstance();
                    cal.getTime();
                    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/YY" + "\t" + "HH:mm:ss");
                    bw.write(
                            sdf.format(cal.getTime()) + "\t" 
                            + String.format("%.1f", meanFrequency)  + "\t" 
                            + String.format("%.1f", meanTemperature) + "\r\n"
                    );
                    bw.close();
                } catch (Exception e) {
                    // do nothing... TODO
                }

            }*/
            
        }
    public double  getTemp(){
        return meanTemperature;
    }
    public double getFreq(){
        return meanFrequency;
    }
    
}

   
    



        
              

    
  
               
