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
 *
 * @author Juan Xavier Pita
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private void close(ActionEvent event) {
        System.exit(0);
    } 
    
    @FXML
    public void createAccount(ActionEvent event) throws IOException{
        Parent loginScreen = FXMLLoader.load(getClass().getResource("CreateAccount.fxml"));
        Scene createAccount = new Scene(loginScreen);
        
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(createAccount);
        window.show();
    }
    
    @FXML
    public void calendarScreen(ActionEvent event) throws IOException{
        Parent loginScreen = FXMLLoader.load(getClass().getResource("CreateAccount.fxml"));
        Scene calendarScreen = new Scene(loginScreen);
        
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(calendarScreen);
        window.show();
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
