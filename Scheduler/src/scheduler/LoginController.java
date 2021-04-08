/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduler;

import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.TextInputControl;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author Juan Xavier Pita
 */
public class LoginController implements Initializable {
    
    @FXML
    private VBox loginData;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Text incorrectLogin;
    public static String loggedAccUsername;
    
    // Button to closeButton GUI window
    @FXML
    public void closeButton(MouseEvent click) {
        System.exit(0);
    }
    
    // Button that shows Account Creation screen
    @FXML
    public void createAccountButton(ActionEvent event) {
        try {
            Parent loginScreen = FXMLLoader.load(getClass().getResource("CreateAccount.fxml"));
            Scene createAccountScreen = new Scene(loginScreen);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(createAccountScreen);
            window.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    // Screen to show after user login
    @FXML
    public void loginButton(ActionEvent event){
        try {
            Statement query = Scheduler.connect.createStatement();
            query.executeQuery("USE Scheduler;");
            ResultSet accountsData = query.executeQuery("SELECT username, passwd FROM Accounts;");
            boolean valid = false;
            while (!valid && accountsData.next()) {
                valid = (username.getText().toLowerCase().contentEquals(accountsData.getString(1).toLowerCase()) && 
                         password.getText().contentEquals(accountsData.getString(2)) && !emptyFieldsValidator(loginData));
                if (valid) {
                    loggedAccUsername = username.getText();
                    Parent loginScreen = FXMLLoader.load(getClass().getResource("Calendar.fxml"));
                    Scene calendarScreen = new Scene(loginScreen);
                    Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
                    window.setScene(calendarScreen);
                    window.show();
                } else if (emptyFieldsValidator(loginData)) {
                    incorrectLogin.setText("No ha ingresado usuario o contraseña.\nPor favor, intente de nuevo.");
                } else incorrectLogin.setText("Nombre de usuario o contraseña incorrecto.\nPor favor, intente de nuevo.");
                username.clear();
                password.clear();
            } query.close(); 
        } catch (IOException | SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public boolean emptyFieldsValidator(Pane container) {
        for (Node child : container.getChildren()) {
            if (child instanceof TextField) {
                if (((TextInputControl) child).getText().isEmpty()) return true;
            } else return true;
        }
        return false;
    }
    
        
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
}
