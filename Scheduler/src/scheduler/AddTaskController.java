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
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Edward
 */
public class AddTaskController implements Initializable {
    
    @FXML
    private VBox taskData;
    @FXML
    private TextField title;
    @FXML
    private DatePicker startDate;
    @FXML
    private ComboBox startTime;
    @FXML
    private DatePicker endDate;
    @FXML
    private ComboBox endTime;
    @FXML
    private TextArea details;
    @FXML
    private ListView availableUsers;
    @FXML
    private ListView addedUsers;
    @FXML
    private Text incorrectTaskData;
    private final String[] HOURS = new String[]{
        "0:00", "0:30", "1:00", "1:30", "2:00", "2:30", "3:00", "3:30", "4:00", "4:30", "5:00", "5:30", "6:00", 
        "6:30", "7:00", "7:30", "8:00", "8:30", "9:00", "9:30", "10:00", "10:30", "11:00", "11:30", "12:00", 
        "12:30", "13:00", "13:30", "14:00", "14:30", "15:00", "15:30", "16:00", "16:30", "17:00", "17:30", "18:00", 
        "18:30", "19:00", "19:30", "20:00", "20:30", "21:00", "21:30", "22:00", "22:30", "23:00", "23:30"
    };   
    
    // VALDIATION METHODS WILL BE USED WHEN USER CLICKS SAVE BUTTON
    @FXML
    public void saveTask(ActionEvent event) {
        if (!emptyFieldsValidator(taskData)){
            System.out.println("Entra sin problemas");
            System.out.println(title.getText()+"\n" + startDate.getValue().toString());
            System.out.println(startTime.getValue().toString());
            // IF TASK IS VALIDATED GO BACK TO CALENDAR SCREEN
        } else {
            incorrectTaskData.setText("Debe llenar todos los campos obligatorios.\nIntente de nuevo.");
        }
    }
    
    @FXML
    public void cancelTask(ActionEvent event) {
        try {
            Parent taskScreen = FXMLLoader.load(getClass().getResource("Calendar.fxml"));
            Scene calendarScreen = new Scene(taskScreen);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(calendarScreen);
            window.centerOnScreen();
            window.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public boolean emptyFieldsValidator(Pane container) {
        for (Node content : container.getChildren()) {     // Nodes' content has the title and 3 Panes (VBox)
            if (content instanceof TextField) {    // Node is TextField
                if (((TextInputControl) content).getText().isEmpty()) return true;
            } else {    // Node is VBox
                for (Node outerContent : ((Pane) content).getChildren()) {  // VBox has 2 nodes
                    if (outerContent instanceof Pane) {
                        for (Node innerContent : ((Pane) outerContent).getChildren()) {
                            if (innerContent instanceof DatePicker) {
                                if (((DatePicker) innerContent).getValue() == null) return true;
                            } else if (((ComboBox) innerContent).getValue() == null) return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        startTime.getItems().addAll((Object[]) HOURS);
        endTime.getItems().addAll((Object[]) HOURS);
    }    
}
