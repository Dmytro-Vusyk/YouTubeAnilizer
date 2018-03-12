package view;

import com.jfoenix.controls.JFXDrawer;
import javafx.animation.RotateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FXMLMainScreenController extends FXMLDocumentController implements Initializable {

    @FXML
    private Circle circle1;

    @FXML
    private Circle circle2;

    @FXML
    private Circle circle3;

    @FXML
    private JFXDrawer drawerLeftMain;

    @FXML
    private JFXDrawer drawerRightMain;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setRotate(circle1, true, 360, 10);
        setRotate(circle2, true, 180, 18);
        setRotate(circle3, true, 145, 24);
    }


    static void setRotate(Circle c, boolean reverse, int angle, int duration ) {
        RotateTransition rotateTransition = new RotateTransition(Duration.seconds(duration),c);

        rotateTransition.setAutoReverse(reverse);

        rotateTransition.setByAngle(angle);
        rotateTransition.setDelay(Duration.seconds(0));
        rotateTransition.setRate(3);
        rotateTransition.setCycleCount(18);
        rotateTransition.play();
}

    @FXML
    void onActionbtnMainsSreenAnalitics(ActionEvent event) {
        try {
          Pane taskController = FXMLLoader.load(getClass().getResource("/FXMLTasks.fxml"));
          documentPane.getChildren().setAll(taskController);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }


    @FXML
    void onActionBtnMainsSreenSettings(ActionEvent event) {
        try {
            Pane taskController = FXMLLoader.load(getClass().getResource("/FXMLSettings.fxml"));
            documentPane.getChildren().setAll(taskController);
        } catch (IOException e1) {
            e1.printStackTrace();
        }

    }

    @FXML
    void onActionBtnMainsSreenExit(ActionEvent event) {
         System.exit(1);
    }







}
