package scheduler;

import java.io.IOException;
import java.text.SimpleDateFormat;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ComboBoxBase;
import javafx.scene.control.DatePicker;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Create an anchor pane that can store additional data.
 */
public class AnchorPaneNode extends AnchorPane {

    // Date associated with this pane
    private LocalDate date;
//    private Calendar calendar;

    /**
     * Create a anchor pane node. Date is not assigned in the constructor.
     *
     * @param children children of the anchor pane
     */
    public AnchorPaneNode(Node... children) {
        super(children);
        // Add action handler for mouse clicked
        this.setOnMouseClicked((MouseEvent e) -> {
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();
            window.close();
            try {
                Parent root = FXMLLoader.load(AnchorPaneNode.this.getClass().getResource("AddTask.fxml"));
                dtFiller((Pane) root);
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        });
    }
    
    /**
     *  A method to fill the DatePicker with the Date pressed and time with the ComboBox with the approximate real time
     * @param container
     */
    public void dtFiller(Pane container) {
        int counter = 0;
        for (Node content : container.getChildren()) {     // Nodes' content has the title and 3 Panes (VBox)
            if(content instanceof Pane) {
                for (Node outerContent : ((Pane) content).getChildren()) {  // VBox has 2 nodes: Label and HBox
                    if (outerContent instanceof Pane) {
                        for (Node innerContent : ((Pane) outerContent).getChildren()) {
                            if (innerContent instanceof Pane) {
                                for (Node node : ((Pane) innerContent).getChildren()) {
                                    if (node instanceof DatePicker)     // fill date
                                        ((ComboBoxBase<LocalDate>) node).setValue(LocalDate.of(date.getYear(), 
                                                                                               date.getMonth(), 
                                                                                               date.getDayOfMonth()));  
                                    else if (node instanceof ComboBox)  // fill time
                                        if(counter == 0) {
                                            ((ComboBoxBase) node).setValue(getNearTime(0));
                                            counter++;
                                        } else ((ComboBoxBase) node).setValue(getNearTime(30));
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
    
    // CHECK THAT NEAR TIME IS CORRECT (check line 101)
    public String getNearTime(int minuteGap) {
        SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        int unroundedMinutes = calendar.get(Calendar.MINUTE);
        int mod = unroundedMinutes % 30;
        calendar.add(Calendar.MINUTE, mod < 8 ? -mod : (30-mod));
        return sdf.format(new Date(calendar.getTimeInMillis() + (minuteGap * 60 * 1000)));
    }
}
