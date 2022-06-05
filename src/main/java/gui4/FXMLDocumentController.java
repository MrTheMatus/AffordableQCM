/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package gui4;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.PopupWindow;
import javafx.stage.Stage;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.zu.ardulink.Link;
//import net.rojer.javafx.tool.Tools;


/**
 *
 * @author Admin
 */
public class FXMLDocumentController extends AnchorPane implements Initializable {
    FileWriter fw;
    BufferedWriter bw;
    File sf = null;
   // public FXMLDocumentController(Stage stage){
     //   this.stage = stage;
    //}
    private Stage stage;
    private boolean manualState = false;
    private String filePath = ""; //some value to pass to the next Wizard panel
    private Label label;
    private String portCOM;
    
    // Ardulink link class for communication with Arduino
    private final Link link = Link.getDefaultInstance();
    @FXML
    private ImageView helpImg;
    @FXML
    private ImageView informationImg;
    @FXML
    private ImageView settingsImg;
    @FXML
    private ImageView plotImg;
    @FXML
    private ImageView balanceImg;
    @FXML
    private ImageView saveImg;
    @FXML
    private ImageView recordingImg;
    @FXML
    private ImageView usbImg;
    @FXML
    private ImageView manualImg;
    @FXML
    private ImageView ukImg;
    @FXML
    private ImageView polandImg;
    @FXML
    private ImageView germanyImg;
    @FXML
    private ImageView resetImg;
    @FXML
    private Button helpBtn;
    @FXML
    private Button settingsBtn;
    @FXML
    private Button plotBtn;
    @FXML
    private Button balanceBtn;
    @FXML
    private Button saveBtn;
    @FXML
    private Button recordingBtn;
    @FXML
    private Button usbBtn;
    @FXML
    private Button manualBtn;
    @FXML
    private Button resetBtn;
    @FXML
    private Button informationBtn;
    @FXML
    private TextField thickFld;
    @FXML
    private TextField rocFld;
    @FXML
    private ComboBox<String> materialComboBox;

    public ComboBox<String> getMaterialComboBox() {
        return materialComboBox;
    }
    private Tab freqTab;
    private Tab thickTab;
    private Tab rocTab;
    @FXML
    private TextField relayFld;
    @FXML
    private ComboBox<String> unitComboBox;
    @FXML
    private TextField filterFld;
    @FXML
    private Button germanyBtn;
    @FXML
    private Button polandBtn;
    @FXML
    private Button ukBtn;
    @FXML
    private AnchorPane informationScreen;
    @FXML
    private AnchorPane plotScreen;
    @FXML
    private AnchorPane settingsScreen;
    @FXML
    private AnchorPane homeScreen;
    @FXML
    private AnchorPane helpScreen;
    private Label label2;
    private ResourceBundle bundle;
    private Locale locale;
    @FXML
    private Label helpLbl;
    
    @FXML
    private TabPane tabPane;
    @FXML
    private TextField tempField;
    @FXML
    private TextField freqField;
    @FXML
    private TextField densityField;
    @FXML
    private TextField massField;
    @FXML
    private TextField factorField;
    private AnchorPane connectionScreen;
    @FXML
    private TextField manualIdText;
    @FXML
    private TextField connectionIdText;
    
    public TabPane getTabPane() {
        return tabPane;
    }

    public void setTabPane(TabPane tabPane) {
        this.tabPane = tabPane;
    }
   
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Image help = new Image(getClass().getResourceAsStream("/images/help.png"));
        ImageView helpView = new ImageView();
        helpView.setImage(help);     
        materialComboBox.getItems().add("Gold");
        materialComboBox.getItems().add("Carbon");
        materialComboBox.getItems().add("Platinum");
        materialComboBox.setEditable(true);
    }   
        public String getName() {
        return "FXML JFXPanel";
    }
    public String getFilePath() {
        return filePath;
    }
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    @FXML
    private void handlehelpBtn(ActionEvent event) {
            plotScreen.setVisible(false);
            homeScreen.setVisible(false);
            settingsScreen.setVisible(false);
            helpScreen.setVisible(true);
            informationScreen.setVisible(false);
            connectionScreen.setVisible(false);
    }

    @FXML
    private void handleinformationBtn(ActionEvent event) {
            plotScreen.setVisible(false);
            homeScreen.setVisible(false);
            settingsScreen.setVisible(false);
            helpScreen.setVisible(false);
            informationScreen.setVisible(true);
            connectionScreen.setVisible(false);
    }

    @FXML
    private void handlesettingsBtn(ActionEvent event) {
            plotScreen.setVisible(false);
            homeScreen.setVisible(false);
            settingsScreen.setVisible(true);
            helpScreen.setVisible(false);
            informationScreen.setVisible(false);
            connectionScreen.setVisible(false);
    }

    @FXML
    private void handleplotBtn(ActionEvent event) {
        if (event.getTarget() == plotBtn){
            plotScreen.setVisible(true);
            homeScreen.setVisible(false);
            settingsScreen.setVisible(false);
            helpScreen.setVisible(false);
            informationScreen.setVisible(false);
            connectionScreen.setVisible(false);
        }
    }

    

    @FXML
    private void handlebalanceBtn(ActionEvent event) {
    }

    @FXML
    private void handlerecordingBtn(ActionEvent event) {
        try {
            fw = new FileWriter(sf.getAbsoluteFile(), true);
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
       BufferedWriter bw = new BufferedWriter(fw);
    }

    @FXML
    private void handleusbBtn(ActionEvent event) { 
    if (event.getTarget() == usbBtn){
            plotScreen.setVisible(false);
            homeScreen.setVisible(true);
            settingsScreen.setVisible(false);
            helpScreen.setVisible(false);
            informationScreen.setVisible(false);
            popUp();
        }
    }
    
    private void handleconenctionBtn(ActionEvent event) { 
         if (event.getTarget() == plotBtn){
            plotScreen.setVisible(false);
            homeScreen.setVisible(false);
            settingsScreen.setVisible(false);
            helpScreen.setVisible(false);
            informationScreen.setVisible(false);
            connectionScreen.setVisible(true);
            popUp();
        }
    }
               

    @FXML
    private void handlemanualBtn(ActionEvent event) {
        if (isManual()){
            manualState = false;
            manualIdText.setText("MANUAL : OFF");
        }
        else{
            manualState = true;
            manualIdText.setText("MANUAL : ON");
        }
    }

    @FXML
    private void handlematerialComboBox(ActionEvent event) {
        
    }
    private void addBox(ActionEvent event, String name, String shortcut, String density, String d) {
    }
    
    @FXML
    private void handleresetBtn(ActionEvent event) {
            plotScreen.setVisible(false);
            homeScreen.setVisible(true);
            settingsScreen.setVisible(false);
            helpScreen.setVisible(false);
            informationScreen.setVisible(false);
    }

  
    @FXML
    private void handlegermanyBtn(ActionEvent event) {
        loadLang("de");
    }

    @FXML
    private void handlepolandBtn(ActionEvent event) {
         loadLang("pl");
    }

    @FXML
    private void handleukBtn(ActionEvent event) {
         loadLang("en");
    }
    
    

   

    @FXML
    private void handlerelayFld(ActionEvent event) {
    }

    @FXML
    private void handleunitComboBox(ActionEvent event) {
    }

    @FXML
    private void handlefilterFld(ActionEvent event) {
    }
    private void loadLang(String lang){
        locale = new Locale(lang);
        bundle = ResourceBundle.getBundle("bundles.lang", locale);
        balanceBtn.setText(bundle.getString("TARE"));
        freqTab.setText(bundle.getString("Frequency"));
        helpBtn.setText(bundle.getString("HELP"));
        informationBtn.setText(bundle.getString("ABOUT"));
        manualBtn.setText(bundle.getString("MANUAL"));
        plotBtn.setText(bundle.getString("PLOTS"));
        recordingBtn.setText(bundle.getString("RECORD"));
        relayFld.setText(bundle.getString("Relay_switching_threshold"));
        rocFld.setText(bundle.getString("Rate_of_change"));
        rocTab.setText(bundle.getString("Rate_of_change"));
        saveBtn.setText(bundle.getString("SAVE"));
        settingsBtn.setText(bundle.getString("SETTINGS"));
        thickFld.setText(bundle.getString("Thickness"));
        thickTab.setText(bundle.getString("Thickness"));
        usbBtn.setText(bundle.getString("CONNECT"));
        helpLbl.setText(bundle.getString("HELP"));
    }
    Button getUsb(){
        return usbBtn;
    }
    
     
    Button getManual(){
        return manualBtn;
    }
    
    void setTemp(double t){
        tempField.setText(Double.toString(t));
    }
    void setFreq(double t){
        freqField.setText(Double.toString(t));
    }
    void setMass(double t){
        massField.setText(Double.toString(t));
    }
    void setFactor(double t){
        factorField.setText(Double.toString(t));
    }
    void setDensity(double t){
        densityField.setText(Double.toString(t));
    }

    
/*
    @FXML
    private void handlebalanceBtn(ActionEvent event) {
    }
*/
    @FXML
    private void handlesaveBtn(ActionEvent event) {
        double tf = Double.parseDouble(tempField.getText());
        double ff = Double.parseDouble(freqField.getText());
        try {
            writeToFile(tf, ff );
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
/*
    @FXML
    private void handlerecordingBtn(ActionEvent event) {
    }

    @FXML
    private void handleusbBtn(ActionEvent event) {
    }

    @FXML
    private void handlemanualBtn(ActionEvent event) {
    }

    @FXML
    private void handlematerialComboBox(ActionEvent event) {
    }

    @FXML
    private void handleresetBtn(ActionEvent event) {
    }

    @FXML
    private void handlefreqTab(Event event) {
    }

    @FXML
    private void handlethickTab(Event event) {
    }
*/
    
/*
    @FXML
    private void handlerelayFld(ActionEvent event) {
    }

    @FXML
    private void handleunitComboBox(ActionEvent event) {
    }

    @FXML
    private void handlefilterFld(ActionEvent event) {
    }

    
 */ 
    public void writeToFile(double F, double T) throws IOException{
         
            // open a file chooser
            JFileChooser chooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Text Files", "txt", "dat"
            );
            chooser.setFileFilter(filter);
            int option = chooser.showSaveDialog(chooser);
            if (option == JFileChooser.APPROVE_OPTION) {
                sf = chooser.getSelectedFile();
            //int option = chooser.showSaveDialog(this);
                
         // if the button is released
                    
                    Calendar cal = Calendar.getInstance();
                    cal.getTime();
                    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/YY" + "\t" + "HH:mm:ss");
                    bw.write(
                            sdf.format(cal.getTime()) + "\t" 
                            + String.format("%.1f", F)  + "\t" 
                            + String.format("%.1f", T) + "\r\n"
                    );
                    bw.close();
                }          
                    // do nothing... TODO //To change body of generated methods, choose Tools | Templates.
    
    }
public boolean isManual(){
    return manualState;
}    
    
public void popUp(){
            SerialConnectionJDialog dlg = new SerialConnectionJDialog(
                new JFrame(), "Arduino Connection", "Select the COM port");
            portCOM = dlg.port;
            if (portCOM == null || "".equals(portCOM)) {
                JOptionPane.showMessageDialog(
                    null, "Invalid COM PORT setted.", "Error", JOptionPane.ERROR_MESSAGE);
               connectionIdText.setText("DISCONNECTED") ;
            } else {
                try {
                    // Connect to the Arduino board
                    link.connect(portCOM);
                    connectionIdText.setText(portCOM); 
                    //startBtn.setText("Disconnect");
                } catch (Exception ex) {
                   ex.printStackTrace();
                    String message = ex.getMessage();
                    if (message == null || message.trim().equals("")) {
                        message = "Generic Error on connection";
                        connectionIdText.setText("DISCONNECTED") ;
                    }
                    JOptionPane.showMessageDialog(
                        null, message, "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
}
}

/*
        } else if (startBtn.isSelected() == false) {
            boolean disconnected = link.disconnect();
            chartTab1.clearChart();
            startBtn.setText("Connect");
            // stop save file
            saveFileBtn.setText("Save File");
            saveFileBtn.setSelected(false);
        }*/