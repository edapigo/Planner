/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduler;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Juan Xavier Pita
 */
public class CalendarController implements Initializable {

    @FXML
    private GridPane calendar;
    
    private YearMonth currentYearMonth;
    private ArrayList<AnchorPaneNode> daysOfCalendar = new ArrayList<>(35);

    // Monthnames array
    String[] months = new String[]{"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto",
         "Septiembre", "Octubre", "Noviembre", "Diciembre"};

    @FXML
    private Label month;
    @FXML
    private Label year;
    
    //Button to closeButton GUI window
    @FXML
    public void closeButton(MouseEvent click) {
        System.exit(0);
    }

    // Button for user to logoutButton
    @FXML
    public void logoutButton(MouseEvent click) {
        try {
            
            Parent calendarScreen = FXMLLoader.load(getClass().getResource("Login.fxml"));
            Scene loginScreen = new Scene(calendarScreen);

            Stage window = (Stage) ((Node) click.getSource()).getScene().getWindow();

            window.setScene(loginScreen);
            window.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void test(MouseEvent click) {
        System.exit(0);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        currentYearMonth = YearMonth.now();
        currentYearMonth.getMonthValue();
        System.out.println(currentYearMonth);
        //Fill the GridPane with AnchorPaneNode
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 7; j++) {
                AnchorPaneNode ap = new AnchorPaneNode();
                ap.setPrefSize(200, 200);
                calendar.add(ap, j, i);
                daysOfCalendar.add(ap);
                month.setText(months[currentYearMonth.getMonthValue() - 1]);
                year.setText(String.valueOf(currentYearMonth.getYear()));
            }
        }
        //Update the days of the calendar
        updateCalendar(YearMonth.now());
    }

    //Update the days of the calendar
    public void updateCalendar(YearMonth yearMonth) {
        // Obtain the month to startempezar.
        LocalDate calendarDate = LocalDate.of(yearMonth.getYear(), yearMonth.getMonthValue(), 1);
        // Regresar los dias hasta que sea lunes, excepto si empieza en lunes
        while (!calendarDate.getDayOfWeek().toString().equals("MONDAY")) {
            calendarDate = calendarDate.minusDays(1);
        }
        // Update the calendar with days.
        for (AnchorPaneNode ap : daysOfCalendar) {
            if (!ap.getChildren().isEmpty()) {
                ap.getChildren().remove(0);
            }
            Text txt = new Text(String.valueOf(calendarDate.getDayOfMonth()));
            ap.setDate(calendarDate);
            ap.setTopAnchor(txt, 5.0);
            ap.setLeftAnchor(txt, 5.0);
            ap.getChildren().add(txt);
            calendarDate = calendarDate.plusDays(1);
        }
    }

    //Label to change to the previous month
    @FXML
    public void previousMonthButton(MouseEvent event) {
        currentYearMonth = currentYearMonth.minusMonths(1);
        month.setText(months[currentYearMonth.getMonthValue() - 1]);
        updateCalendar(currentYearMonth);
    }
    
    //Label to change to the next month
    @FXML
    public void nextMonthButton(MouseEvent event) {
        currentYearMonth = currentYearMonth.plusMonths(1);
        month.setText(months[currentYearMonth.getMonthValue() - 1]);
        updateCalendar(currentYearMonth);
    }
    
    //Label to change to the previous year.
    @FXML
    public void previousYearButton(MouseEvent event) {
        currentYearMonth = currentYearMonth.minusYears(1);
        year.setText(String.valueOf(currentYearMonth.getYear()));
        updateCalendar(currentYearMonth);
    }
    
    //Label to change to the next year
    @FXML
    public void nextYearButton(MouseEvent event) {
        currentYearMonth = currentYearMonth.plusYears(1);
        year.setText(String.valueOf(currentYearMonth.getYear()));
        updateCalendar(currentYearMonth);
    }
    
    public void showInformationDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, message, ButtonType.OK);
        alert.showAndWait();
    }
}
