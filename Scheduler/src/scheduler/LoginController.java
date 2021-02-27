/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduler;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
    public void close(MouseEvent click) {
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
        
        while (!auth && error){        
            try {
                Connection connect = DriverManager.getConnection(Scheduler.CONN_STRING, Scheduler.USERNAME, Scheduler.PASSWORD);
                SchedulerDatabase data = new SchedulerDatabase(connect);
                Statement query = connect.createStatement();
                String showAccounts = "SELECT username, passwd FROM Accounts;";
                ResultSet accountsData = query.executeQuery(showAccounts);
                
                while(accountsData.next()){
                    if (username.getText().toLowerCase().contentEquals(accountsData.getString(1).toLowerCase()) &&
                            password.getText().contentEquals(accountsData.getString(2))){
                        auth = true;
                        error = false;
                        
                        Parent loginScreen = FXMLLoader.load(getClass().getResource("Calendar.fxml"));
                        Scene calendarScreen = new Scene(loginScreen);

                        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
                        
                        window.setScene(calendarScreen);
                        window.show();
                    }
                }
                
                if (error){
                    incorrectLogin.setText("Nombre de usuario o contraseña incorrecto.\nPor favor, intente de nuevo.");
                    error = false;
                }
            } catch(SQLException sqlEx){
                sqlEx.printStackTrace();
            }
        }
    }
    
        
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
}
