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
 * @author Juan Xavier Pita
 */
public class CreateAccountController implements Initializable {

    @FXML
    private void close(ActionEvent event) {
        System.exit(0);
    }
    
    @FXML
    public void login(ActionEvent event) throws IOException{
        Parent createAccount = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        Scene loginScreen = new Scene(createAccount);
        
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(loginScreen);
        window.show();
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
