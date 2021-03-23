package scheduler;

import java.io.IOException;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

import java.time.LocalDate;
import java.time.Month;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

    /**
     * Create a anchor pane node. Date is not assigned in the constructor.
     *
     * @param children children of the anchor pane
     */
    public AnchorPaneNode(Node... children) {
        super(children);
        // Add action handler for mouse clicked
        this.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                System.out.println("This pane's date is: " + date);
                
                Stage stage = new Stage();
                stage.initStyle(StageStyle.UNDECORATED);
                Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();               
                window.close();

                try {
                    
                    Parent root = FXMLLoader.load(AnchorPaneNode.this.getClass().getResource("AddingTask.fxml"));
                    
                    fillDatePicker((Pane) root);
                    
                    stage.setScene(new Scene(root));
                    stage.show();
                } catch (IOException ex) {
                    Logger.getLogger(AnchorPaneNode.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    
    /**
     *  A methon to fill the DatePicker with the Date pressed
     * @param container
     */
    public void fillDatePicker(Pane container) {
        for (Node child : container.getChildren()) {
            if (child instanceof DatePicker) {
                ((DatePicker) child).setValue(LocalDate.of(date.getYear(), date.getMonth(), date.getDayOfMonth()));
            } 
        }
    }
    
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
