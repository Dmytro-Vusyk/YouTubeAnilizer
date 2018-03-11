package view;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FXMLTasksController extends FXMLDocumentController  implements Initializable {
    @FXML
    private JFXButton btnSettings2;

    @FXML
    private AnchorPane leftAnchorPaneTasks;

    @FXML
    private JFXButton btnPrev;

    @FXML
    private JFXButton testBtn;

    @FXML
    private GridPane testGrid;

    @FXML
    private Pane paine11;

    @FXML
    void onActionBtnPrev(ActionEvent event) {

       try {
            Pane splashPane = FXMLLoader.load(getClass().getResource("/FXMLMainScreen.fxml"));
            documentPane.getChildren().setAll(splashPane);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    @FXML
    void onActionBtnSettings2(ActionEvent event) {
        try {
            Pane taskController = FXMLLoader.load(getClass().getResource("/FXMLSettings.fxml"));
            documentPane.getChildren().setAll(taskController);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            Pane menuPane = FXMLLoader.load(getClass().getResource("/FXMLTasksMenu.fxml"));
            leftAnchorPaneTasks.getChildren().addAll(menuPane);
        } catch (IOException e) {
            e.printStackTrace();
        }

        testBtn.addEventHandler(ActionEvent.ACTION, (e) ->{
            //testGrid.getChildren().remove( 1);
         //   paine11.setVisible(false);
        });



    }


}
