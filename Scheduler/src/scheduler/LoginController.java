/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduler;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author Juan Xavier Pita
 */
public class LoginController implements Initializable {
    
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Text incorrectLogin;
    
    // Button to close GUI window
    @FXML
    private void close(ActionEvent event) {
        System.exit(0);
    }
    
    // Button that shows Account Creation screen
    @FXML
    public void createAccount(ActionEvent event) throws IOException{
        Parent loginScreen = FXMLLoader.load(getClass().getResource("CreateAccount.fxml"));
        Scene createAccount = new Scene(loginScreen);
        
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(createAccount);
        window.show();
    }
    
    // create method to read a file (MAYBE NOT)
    
    // Screen to show after user login
    @FXML
    public void calendarScreen(ActionEvent event) throws IOException{
        
        boolean auth = false;
        boolean error = true;
        
        Parent loginScreen = FXMLLoader.load(getClass().getResource("Calendar.fxml"));
        Scene calendarScreen = new Scene(loginScreen);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        while (!auth && error){        
            BufferedReader br = null;
            try{	
                br = new BufferedReader(new FileReader("accounts.txt"));		
                String line = br.readLine();
                while (line != null) {
                    if (username.getText().contentEquals(line.split(",")[0]) &&
                            password.getText().contentEquals(line.split(",")[1])){
                        auth = true;
                        error = false;
                        window.setScene(calendarScreen);
                    }
                    line = br.readLine();
                }
                if (error){
                    incorrectLogin.setText("Nombre de usuario o contraseña incorrecto.\nPor favor, intente de nuevo.");
                    error = false;
                }
            } 
            catch (FileNotFoundException fnfEx){
                fnfEx.printStackTrace();
            }
            
        }
        window.show();
    }

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
}
