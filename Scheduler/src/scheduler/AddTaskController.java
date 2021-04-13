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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ComboBoxBase;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextInputControl;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Edward
 */
public class AddTaskController implements Initializable {
    
    @FXML
    private VBox taskData;
    @FXML
    private TextField title;
    @FXML
    private DatePicker startDate;
    @FXML
    private ComboBox startTime;
    @FXML
    private DatePicker endDate;
    @FXML
    private ComboBox endTime;
    @FXML
    private TextArea overview;
    @FXML
    private ListView availableUsers;
    @FXML
    private ListView addedUsers;
    @FXML
    private Text incorrectTaskData;
    private final int TITLE_MAX_CHARS = 15;
    private final int DETAILS_MAX_CHARS = 100;
    private final String[] HOURS = new String[] {
        "0:00", "0:30", "1:00", "1:30", "2:00", "2:30", "3:00", "3:30", "4:00", "4:30", "5:00", "5:30",
        "6:00", "6:30", "7:00", "7:30", "8:00", "8:30", "9:00", "9:30", "10:00", "10:30", "11:00", "11:30",
        "12:00", "12:30", "13:00", "13:30", "14:00", "14:30", "15:00", "15:30", "16:00", "16:30", "17:00", "17:30",
        "18:00", "18:30", "19:00", "19:30", "20:00", "20:30", "21:00", "21:30", "22:00", "22:30", "23:00", "23:30"
    };
    
    @FXML
    public void addUsersButton(ActionEvent event) {
        for (Object user : availableUsers.getSelectionModel().getSelectedItems())
            if(!addedUsers.getItems().contains(user)) addedUsers.getItems().add(user);
        availableUsers.getItems().removeAll(availableUsers.getSelectionModel().getSelectedItems());
        availableUsers.getSelectionModel().clearSelection();
    }

    @FXML
    public void deleteUsersButton(ActionEvent event) {
        for (Object user : addedUsers.getSelectionModel().getSelectedItems())
            if(!availableUsers.getItems().contains(user)) availableUsers.getItems().add(user);
        addedUsers.getItems().removeAll(addedUsers.getSelectionModel().getSelectedItems());
        addedUsers.getSelectionModel().clearSelection();
    }
    
    @FXML
    public void saveTaskButton(ActionEvent event) {        
        try {
            boolean valid = !emptyFieldsValidator(taskData) && (dtValidator(startDate, endDate, "yyyy-MM-dd") >= 0);
            if (valid) {
                if ((dtValidator(startDate, endDate, "yyyy-MM-dd") == 0 && dtValidator(startTime, endTime, "H:mm") > 0) ||
                        dtValidator(startDate, endDate, "yyyy-MM-dd") > 0) {
                    if(userLoggedIsAvailable()) {
                        createTask();
                        taskAllocation(getTaskId());
                        taskCreationDialog(event);
                    } else incorrectTaskData.setText("Usted ya tiene asignada una o más tareas en ese horario.\nSeleccione "
                            + "otra fecha y hora de inicio y fin");
                }
                else incorrectTaskData.setText("El evento no puede finalizar antes o a la misma hora de inicio.\nIntente de nuevo.");
            } else if(emptyFieldsValidator(taskData))
                incorrectTaskData.setText("Debe llenar todos los campos obligatorios.\nIntente de nuevo.");
            else if(dtValidator(startDate, endDate, "yyyy-MM-dd") < 0)
                incorrectTaskData.setText("La fecha de inicio debe ser menor o igual que la fecha de fin.\nIntente de nuevo.");
        } catch (IOException | ParseException | SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    @FXML
    public void cancelTaskButton(ActionEvent event) {
        try {
            Parent taskScreen = FXMLLoader.load(getClass().getResource("Calendar.fxml"));
            Scene calendarScreen = new Scene(taskScreen);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(calendarScreen);
            window.centerOnScreen();
            window.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public boolean emptyFieldsValidator(Pane container) {
        for (Node content : container.getChildren()) {     // Nodes' content has the title and 3 Panes (VBox)
            if (content instanceof TextField) {    // Node is TextField
                if (((TextInputControl) content).getText().isEmpty()) return true;
            } else {    // Node is VBox
                for (Node outerContent : ((Pane) content).getChildren()) {  // VBox has 2 nodes: Label and HBox
                    if (outerContent instanceof Pane) {
                        for (Node innerContent : ((Pane) outerContent).getChildren()) {
                            if (innerContent instanceof DatePicker) {
                                if (((DatePicker) innerContent).getValue() == null) return true;
                            } else if (((ComboBox) innerContent).getValue() == null) return true;
                        }
                    }
                }
            }
        } return false;
    }
    
    // VALIDATOR FOR DATE AND/OR TIME
    // dtValidator -> Validator for date&time
    public int dtValidator(ComboBoxBase start, ComboBoxBase end, String datetimeFormat) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(datetimeFormat);
        if (start instanceof ComboBox)
            return sdf.parse(end.getValue().toString()).compareTo(sdf.parse(start.getValue().toString()));
        return sdf.parse(end.getValue().toString()).compareTo(sdf.parse(start.getValue().toString()));
    }
    
    public String timeSeparator(String time, int sep) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("H:mm");    
        sdf.getCalendar().setTime(sdf.parse(time));
        return sdf.format(new Date(sdf.getCalendar().getTimeInMillis() + (sep * 60 * 1000)));
    }
    
    // ADD DATETIME IN WHERE CONDITION OF QUERY TO EXCLUDE USERS WHO ALREADY HAVE A TASK ON THAT DATE AND TIME
    // CHECK IF USER LOGGED IS BUSY ON DATE AND TIME SELECTED
    public void getAvailableUsers() throws SQLException {
        availableUsers.getItems().clear();
        addedUsers.getItems().clear();
        Statement query = Scheduler.connect.createStatement();
        query.executeQuery("USE Scheduler;");
        ResultSet users = query.executeQuery("SELECT * FROM Accounts "
            + "WHERE username NOT LIKE \"" + LoginController.loggedAccUsername + "\" AND username != ALL(SELECT DISTINCT username "
            + "FROM Accounts acc JOIN Allocation a ON a.userId = acc.userId "
            + "WHERE ADDTIME(\"" + startDate.getValue() + " " + startTime.getValue() + "\", 1) BETWEEN beginning AND ending OR "
            + "SUBTIME(\"" + endDate.getValue() + " " + endTime.getValue() + "\", 1) BETWEEN beginning AND ending);");
        while(users.next()){
            if(!availableUsers.getItems().contains(users.getString("username")))
                availableUsers.getItems().add(users.getString("username"));
        } query.closeOnCompletion();
    }
    
    public boolean userLoggedIsAvailable() throws SQLException {
        Statement query = Scheduler.connect.createStatement();
        query.executeQuery("USE Scheduler;");
        ResultSet userTasks = query.executeQuery("SELECT * FROM Accounts acc JOIN Allocation a on a.userId=acc.userId "
            + "WHERE username LIKE \"" + LoginController.loggedAccUsername + "\" AND "
            + "(ADDTIME(\"" + startDate.getValue() + " " + startTime.getValue() + "\", 1) BETWEEN beginning AND ending OR "
            + "SUBTIME(\"" + endDate.getValue() + " " + endTime.getValue() + "\", 1) BETWEEN beginning AND ending);");
        return !userTasks.next();
    }
    
    public void createTask() throws SQLException {
        Statement insert = Scheduler.connect.createStatement();
        if(!overview.getText().trim().isEmpty())
            insert.executeUpdate("INSERT INTO Tasks(title, overview) "
                              + "VALUE (\"" + title.getText().trim() + "\", \"" + overview.getText().trim() + "\");");
        else insert.executeUpdate("INSERT INTO Tasks(title) "
                               + "VALUE (\"" + title.getText().trim() + "\");");
        insert.closeOnCompletion();
    }
    
    public int getTaskId() throws SQLException {
        Statement query = Scheduler.connect.createStatement();
        ResultSet lastInsertId = query.executeQuery("SELECT LAST_INSERT_ID();");
        lastInsertId.next();
        query.closeOnCompletion();
        return lastInsertId.getInt(1);
    }
    
    public void taskAllocation(int taskId) throws SQLException {    // CALL userLoggedTaskAllocation
        userLoggedTaskAllocation(taskId);
        Statement query = Scheduler.connect.createStatement();
        if(!addedUsers.getItems().isEmpty()) {
            for(Object username : addedUsers.getItems()) {
                ResultSet addedUserId = query.executeQuery("SELECT userId FROM Accounts WHERE username LIKE \"" 
                        + username + "\";");
                addedUserId.next();
                query.executeUpdate("INSERT INTO Allocation VALUE(" + addedUserId.getInt(1) + ", " + taskId + ", \"" 
                + startDate.getValue() + " " + startTime.getValue() + "\", \"" + endDate.getValue() + " " + endTime.getValue() + "\");");
            }
        } query.closeOnCompletion();
    }

    public void userLoggedTaskAllocation(int taskId) throws SQLException {
        Statement query = Scheduler.connect.createStatement();
        ResultSet loggedUserId = query.executeQuery("SELECT userId FROM Accounts WHERE username LIKE \"" 
            + LoginController.loggedAccUsername + "\";");
        loggedUserId.next();
        query.executeUpdate("INSERT INTO Allocation VALUE(" + loggedUserId.getInt(1) + ", " + taskId + ", \"" 
            + startDate.getValue() + " " + startTime.getValue() + "\", \"" + endDate.getValue() + " " + endTime.getValue() + "\");");
        query.closeOnCompletion();
    }
    
    public void taskCreationDialog(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "La tarea ha sido creada exitosamente");
        alert.setHeaderText("Creación de tarea");
        alert.initStyle(StageStyle.UNDECORATED);
        Optional<ButtonType> optionChose = alert.showAndWait();
        Parent addTaskScreen = FXMLLoader.load(getClass().getResource("Calendar.fxml"));
        Scene calendarScreen = new Scene(addTaskScreen);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(calendarScreen);
        window.centerOnScreen();
        window.show();
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        // 15-character limit for title
        this.title.setTextFormatter(new TextFormatter<>(
            change -> change.getControlNewText().length() <= TITLE_MAX_CHARS ? change : null
        ));
        // LISTENER FOR DATEPICKER: startDate
        this.startDate.valueProperty().addListener((observable, oldValue, newValue) -> {
            try {
                if(startDate.getValue() != null && endDate.getValue() != null && dtValidator(startDate, endDate, "yyyy-MM-dd") < 0)
                    endDate.setValue(newValue);
                if(startDate.getValue() != null && startTime.getValue() != null && 
                        endDate.getValue() != null && endTime.getValue() != null)
                    getAvailableUsers();
            } catch (ParseException | SQLException ex) {
                System.out.println(ex.getMessage());
            }
        });
        this.startTime.getItems().addAll((Object[]) HOURS);  // Fill startTime ComboBox
        // LISTENER FOR COMBOBOX:   startTime
        this.startTime.valueProperty().addListener((observable, oldValue, newValue) -> {
            try {
                if(startDate.getValue() != null && endDate.getValue() != null && dtValidator(startDate, endDate, "yyyy-MM-dd") == 0) {
                    if(startTime.getValue() != null && endTime.getValue() != null && dtValidator(startTime, endTime, "H:mm") <= 0)
                        endTime.setValue(timeSeparator(newValue.toString(), 30));
                    if(endTime.getValue().toString().contentEquals("0:00")) 
                        endDate.setValue(endDate.getValue().plusDays(1));
                }
                if(startDate.getValue() != null && startTime.getValue() != null && 
                        endDate.getValue() != null && endTime.getValue() != null)
                    getAvailableUsers();
            } catch (ParseException | SQLException ex) {
                System.out.println(ex.getMessage());
            }
        });
        // LISTENER FOR DATEPICKER: endDate
        this.endDate.valueProperty().addListener((observable, oldValue, newValue) -> {
            try {
                if(startDate.getValue() != null && endDate.getValue() != null && dtValidator(startDate, endDate, "yyyy-MM-dd") < 0)
                    startDate.setValue(newValue);
                if(startDate.getValue() != null && startTime.getValue() != null && 
                        endDate.getValue() != null && endTime.getValue() != null)
                    getAvailableUsers();
            } catch (ParseException | SQLException ex) {
                System.out.println(ex.getMessage());
            }
        });
        this.endTime.getItems().addAll((Object[]) HOURS);    // Fill endTime ComboBox
        // LISTENER FOR COMBOBOX:   endTime
        this.endTime.valueProperty().addListener((observable, oldValue, newValue) -> {
            try {
                if(startDate.getValue() != null && endDate.getValue() != null)
                    if(dtValidator(startDate, endDate, "yyyy-MM-dd") == 0)
                        if(startTime.getValue() != null && endTime.getValue() != null && dtValidator(startTime, endTime, "H:mm") <= 0)
                            startTime.setValue(timeSeparator(newValue.toString(), -30));
//                            if(newValue.toString().contentEquals("0:00")) 
//                                startDate.setValue(startDate.getValue().minusDays(1));
                if(startDate.getValue() != null && startTime.getValue() != null && 
                        endDate.getValue() != null && endTime.getValue() != null)
                    getAvailableUsers();
            } catch (ParseException | SQLException ex) {
                System.out.println(ex.getMessage());
            }
        });
        // 100-character limit for overview
        this.overview.setTextFormatter(new TextFormatter<>(
            change -> change.getControlNewText().length() <= DETAILS_MAX_CHARS ? change : null)
        );
        this.availableUsers.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        this.addedUsers.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }
}
