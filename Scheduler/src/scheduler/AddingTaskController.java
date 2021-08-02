/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduler;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
    
/**
 * FXML Controller class
 *
 * @author Edward
 */
public class AddingTaskController implements Initializable {

    @FXML
    private TextField title;
    @FXML
    private ComboBox startTime;
    @FXML
    private ComboBox endTime;
    @FXML
    private DatePicker startDate;
    @FXML
    private DatePicker endDate;
    @FXML
    private TextArea details;
    
    private ArrayList<Account> participants;

    private String[] hours = new String[]{
        "0:00", "0:30", "1:00", "1:30", "2:00", "2:30", "3:00", "3:30", "4:00", "4:30", "5:00", "5:30", "6:00", "6:30", "7:00", "7:30",
        "8:00", "8:30", "9:00", "9:30", "10:00", "10:30", "11:00", "11:30", "12:00", "12:30", "13:00", "13:30", "14:00", "14:30", "15:00", "15:30",
        "16:00", "16:30", "17:00", "17:30", "18:00", "18:30", "19:00", "19:30", "20:00", "20:30", "21:00", "21:30", "22:00", "22:30", "23:00", "23:30"
    };

    @FXML
    public void cancel(ActionEvent event) throws IOException {
        //Internationalizing
        ResourceBundle bundle = ResourceBundle.getBundle("files/labelText",Scheduler.locale);
        Parent taskScreen = FXMLLoader.load(getClass().getResource("Calendar.fxml"),bundle);
        
        Scene calendarScreen = new Scene(taskScreen);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(calendarScreen);
        window.centerOnScreen();
        window.show();

        String hola = "hola";

    }

    public boolean emptyfields() {
        if (title.getText().trim().isEmpty()) {
            System.out.println("Valio vrg");
            return true;
        }
        System.out.println("No esta vacio");
        return false;
    }
    
    public void reminder(accounts ArrayList<Account>) {
    
    }
    
    public void addParticipant(Account user) {
        this.participants.add(user);
    }

//    public boolean 
    @FXML
    public void saveTask(ActionEvent event) throws IOException {
        if (!emptyfields()) {
            System.out.println("Entra sin problemas");
            System.out.println(title.getText() + "\n" + startDate.getValue().toString());
            System.out.println(startTime.getValue().toString());
            //System.out.println(details.getText());

        }

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        startTime.getItems().addAll(hours);
        endTime.getItems().addAll(hours);
    }

}
