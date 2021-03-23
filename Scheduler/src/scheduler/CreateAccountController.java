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
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Juan Xavier Pita
 */
public class CreateAccountController implements Initializable {
    
    private final String EMAIL_REGEX = "(?:[a-z0-9!#$%&\'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&\'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-"
            + "\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-"
            + "9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-"
            + "9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-"
            + "\\x5a\\x53-\\x7f]|\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
    private final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
    
    @FXML
    private VBox userData;
    @FXML
    private TextField fName;    // check that text is alphabetic (2-15 characters long)
    @FXML
    private TextField lName;    // check that text is alphabetic (2-15 characters long)
    @FXML
    private TextField username;     // check that text is alphanumeric (4-20 characters long)
    @FXML
    private PasswordField password;     // at least 8 characters long (8-20 characters long)
    @FXML
    private PasswordField verifyPw;     // check that text matches original password
    @FXML
    private TextField email;    // check that text contains valid email address
    @FXML
    private Text incorrectRegistry;
    
    
    // Button to close GUI window
    @FXML
    public void closeButton(MouseEvent click) {
        System.exit(0);
    }
    
    // Button that returns to user returnButton
    @FXML
    public void returnButton(MouseEvent click) {
        try {
            Parent createAccount = FXMLLoader.load(getClass().getResource("Login.fxml"));
            Scene loginScreen = new Scene(createAccount);
            Stage window = (Stage)((Node)click.getSource()).getScene().getWindow();
            window.setScene(loginScreen);
            window.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    // After account creation return to returnButton screen
    @FXML
    public void createAccountButton(ActionEvent event) {
        try {
            Statement query = Scheduler.connect.createStatement();
            query.executeQuery("USE Scheduler;");
            ResultSet accountsData = query.executeQuery("SELECT username FROM Accounts;");
            boolean valid = false;
            while (!valid && accountsData.next()) {
                valid = !emptyFieldsValidator(userData) && alphaValidator(fName, 2, 15) && alphaValidator(lName, 2, 15) && 
                        alNumValidator(username, 4, 20) && !userTakenValidator(username, query) && pwValidator(password, 8, 20) &&
                        pwConfirmValidator(password, verifyPw) && emailValidator(email) && !emailRegisteredValidator(email, query);
                if (valid) {
                    String firstName, lastName, accountName, pass, e_mail;
                    cleanText(userData);
                    firstName = fName.getText().substring(0, 1).toUpperCase() + fName.getText().substring(1).toLowerCase();
                    lastName = lName.getText().substring(0, 1).toUpperCase() + lName.getText().substring(1).toLowerCase();
                    accountName = username.getText().replace(" ", "").toLowerCase();
                    pass = password.getText();
                    e_mail = email.getText();
                    query.executeQuery("CALL sp_create_account(\'" + firstName + "\', \'" + lastName + "\', \'" + accountName 
                                                                   + "\', \'" + pass + "\', \'" + e_mail + "\');");
                    showInformationDialog("Su cuenta ha sido creada exitosamente");
                    Parent loginScreen = FXMLLoader.load(getClass().getResource("Login.fxml"));
                    Scene createAccount = new Scene(loginScreen);
                    Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
                    window.setScene(createAccount);
                    window.show();
                } else if (emptyFieldsValidator(userData)) {
                    incorrectRegistry.setText("Debe llenar todos los campos para poder registrarse.\nIntente de nuevo.");
                } else if (!alphaValidator(fName, 2, 15)) {
                    incorrectRegistry.setText("Su nombre debe contener entre 2 y 15 caracteres alfabéticos.\nIntente de nuevo.");
                } else if (!alphaValidator(lName, 2, 15)) {
                    incorrectRegistry.setText("Su apellido debe contener entre 2 y 15 caracteres alfabéticos.\nIntente "
                            + "de nuevo.");
                } else if (!alNumValidator(username, 4, 20)) {
                    incorrectRegistry.setText("Su nombre de usuario debe contener entre 4 y 20 caracteres alfabéticos y/o "
                            + "numéricos.\nIntente de nuevo.");
                } else if (userTakenValidator(username, query)) {
                     incorrectRegistry.setText("Nombre de usuario ya existe.\nIntente de nuevo.");
                } else if (!pwValidator(password, 8, 20)) {
                    incorrectRegistry.setText("Su contraseña debe contener entre 8 y 20 caracteres.\nIntente de nuevo.");
                } else if (!pwConfirmValidator(password, verifyPw)) {
                    incorrectRegistry.setText("Las contraseñas que ha ingresado no coinciden.\nIntente de nuevo.");
                } else if (!emailValidator(email)) {
                    incorrectRegistry.setText("No ha ingresado un correo electrónico válido.\nIntente de nuevo.");
                } else if (emailRegisteredValidator(email, query)) {
                    incorrectRegistry.setText("Ese correo electrónico ya se encuentra registrado.\nIntente de nuevo.");
                }
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
    
    public boolean alphaValidator(TextField tf, int minLength, int maxLength) {
        if (!tf.getText().isEmpty()) {
            for (int i=0; i<tf.getText().replace(" ", "").length(); i++) {
                if (!Character.isAlphabetic(tf.getText().replace(" ", "").charAt(i))) return false;
            }
        } else return false;
        return tf.getText().length() >= minLength && tf.getText().length() <= maxLength;
    }
    
    public boolean alNumValidator(TextField tf, int minLength, int maxLength) {
        if (!tf.getText().isEmpty()) {
            for (int i=0; i<tf.getText().replace(" ", "").length(); i++) {
                if (!Character.isLetterOrDigit(tf.getText().replace(" ", "").charAt(i))) return false;
            }
        } else return false;
        return tf.getText().length() >= minLength && tf.getText().length() <= maxLength;
    }
    
    public boolean userTakenValidator(TextField user, Statement stmt) throws SQLException {
        ResultSet rs = stmt.executeQuery("SELECT username FROM Accounts WHERE username LIKE\'" 
                                        + user.getText() + "\';");
        return rs.next();   // if ResultSet is empty rs.next() will be false, otherwise if it's true username is not available
    }
    
    public boolean pwValidator(PasswordField pwf, int minLength, int maxLength) {
        if (!pwf.getText().isEmpty()) {
            for (int i = 0; i < pwf.getText().length(); i++) {
                if (!Character.isBmpCodePoint(pwf.getText().charAt(i))) return false;
            }
        } else return false;
        return pwf.getText().length() >= minLength && pwf.getText().length() <= maxLength;
    }
    
    public boolean pwConfirmValidator(PasswordField pass, PasswordField verifyPass) {
        return pass.getText().contentEquals(verifyPass.getText());
    }

    public boolean emailValidator(TextField email) {
        if (email.getText().isEmpty()) return false;
        return EMAIL_PATTERN.matcher(email.getText().toLowerCase()).matches();
    }
    
    public boolean emailRegisteredValidator(TextField email, Statement stmt) throws SQLException {
        ResultSet rs = stmt.executeQuery("SELECT email FROM Accounts WHERE email LIKE\'" 
                                        + email.getText() + "\';");
        return rs.next();   // if ResultSet is empty rs.next() will be false, otherwise if it's true email is already registered
    }
    
    public void cleanText(Pane container){
        for (Node child : container.getChildren()) {
            if (!(child instanceof PasswordField)) {
                ((TextInputControl) child).setText(((TextInputControl) child).getText().trim().split(" ")[0]);
            }
        }
    }
    
    public void showInformationDialog(String message) {
        Alert alert = new Alert(AlertType.NONE, message, ButtonType.OK);
        alert.showAndWait();
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
}
