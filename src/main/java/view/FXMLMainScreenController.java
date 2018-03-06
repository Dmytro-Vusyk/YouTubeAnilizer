package view;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class FXMLMainScreenController implements Initializable {

    @FXML
    private HBox hboxMainScreen;

    @FXML
    private JFXButton btnMainsSreenSettings;

    @FXML
    private JFXButton btnMainsSreenAnalitics;

    @FXML
    private JFXButton btnMainsSreenExit;

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

    private int rotate=0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setRotate(circle1, true, 360,10);
        setRotate(circle2, true, 180,18);
        setRotate(circle3, true, 145,24);

        btnMainsSreenAnalitics.addEventHandler(MouseEvent.MOUSE_PRESSED,(e)->{
            // подія зміни вікна
        });
    }

    /**пока временно побудет статическим*/
     static void setRotate(Circle c, boolean reverse, int angle, int duration ) {
        RotateTransition rotateTransition = new RotateTransition(Duration.seconds(duration),c);

        rotateTransition.setAutoReverse(reverse);

        rotateTransition.setByAngle(angle);
        rotateTransition.setDelay(Duration.seconds(0));
        rotateTransition.setRate(3);
        rotateTransition.setCycleCount(18);
        rotateTransition.play();
    }




}
