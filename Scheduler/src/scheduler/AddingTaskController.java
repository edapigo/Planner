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
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Edward
 */
public class AddingTaskController implements Initializable {
    
    @FXML
    public void cancel(ActionEvent event) throws IOException {
        Parent taskScreen = FXMLLoader.load(getClass().getResource("Calendar.fxml"));
        Scene calendarScreen = new Scene(taskScreen);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(calendarScreen);
        window.centerOnScreen();
        window.show();
    }
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
