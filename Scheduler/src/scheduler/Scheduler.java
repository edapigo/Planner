/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Juan Xavier Pita
 */
public class Scheduler extends Application {
    
    public static final String USERNAME = "root";                                      // estudiante
    public static final String PASSWORD = "1234";                                      // XTGS41zA
    public static final String CONN_STRING = "jdbc:mysql://localhost:3306/Scheduler";  // 35.185.90.173
    public static Connection connect;
    public static Locale locale = new Locale("en","US"); //To change languague to spanish use ("es","ES")
    
    @Override
    public void start(Stage stage) throws Exception {
        //Internationalizing
        ResourceBundle bundle = ResourceBundle.getBundle("files/labelText",locale);
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"),bundle);
        
        stage.initStyle(StageStyle.UNDECORATED);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try{
            connect = DriverManager.getConnection(Scheduler.CONN_STRING, Scheduler.USERNAME, Scheduler.PASSWORD);
        } catch(SQLException ex) {
            System.out.println(ex.getMessage());
        }
        launch(args);
    }
    
}
