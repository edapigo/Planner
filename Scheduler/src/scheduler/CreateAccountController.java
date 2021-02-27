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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Juan Xavier Pita
 */
public class CreateAccountController implements Initializable {

    @FXML
    private TextField email;            // valid email
    @FXML
    private TextField username;         // check if username is taken
    @FXML
    private PasswordField password;     // at least 6 characters long
    @FXML
    private PasswordField verifyPw;     // check that both PasswordFields match
    
    // Button to close GUI window
    @FXML
    public void close(MouseEvent click) {
        System.exit(0);
    }
    
    // Button that returns to user login
    @FXML
    public void login(MouseEvent click) throws IOException{
        Parent createAccount = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Scene loginScreen = new Scene(createAccount);
        
        Stage window = (Stage)((Node)click.getSource()).getScene().getWindow();
        
        window.setScene(loginScreen);
        window.show();
    }
    
    // After account creation return to login screen
    @FXML
    public void createAccount(ActionEvent event) throws IOException{
        Parent loginScreen = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Scene createAccount = new Scene(loginScreen);
        
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(createAccount);
        window.show();
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
