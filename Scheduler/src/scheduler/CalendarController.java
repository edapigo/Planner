/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduler;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Juan Xavier Pita
 */
public class CalendarController implements Initializable {

    //Button to close GUI window
    @FXML
    public void close(MouseEvent click) {
        System.exit(0);
    }

    // Button for user to logout
    @FXML
    public void logout(MouseEvent click) throws IOException{
        Parent calendar = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Scene loginScreen = new Scene(calendar);
        
        Stage window = (Stage)((Node)click.getSource()).getScene().getWindow();
        
        window.setScene(loginScreen);
        window.show();
    }
    
    public void test(MouseEvent click){
        System.exit(0);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
