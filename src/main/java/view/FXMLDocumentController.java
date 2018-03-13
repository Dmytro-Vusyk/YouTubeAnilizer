package view;

import controller.MainController;
import enumerated.MapKeys;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.ResourceBundle;

/**MainController*/
public class FXMLDocumentController implements Initializable {

    private static boolean isSplashLoaded = false;

    protected static boolean isTimeCheked = true;
    protected MainController mc = MainController.getInstance();


    @FXML
    protected AnchorPane documentPane;
    protected HashSet<String> channelNames = new HashSet<String>();

    public HashSet<String> getChannelNames() {
        return channelNames;
    }

    protected ArrayList<LinkedHashMap<MapKeys, String>> channels = new ArrayList<LinkedHashMap<MapKeys, String> >();

    /** Splash Effect*/
    private void loadSplashScreen() {
        try {
            isSplashLoaded=true;

            Pane splashPane = FXMLLoader.load(getClass().getResource("/FXMLSplashWindow.fxml"));
            documentPane.getChildren().setAll(splashPane);

            FadeTransition fadeTransitionIn = new FadeTransition(Duration.seconds(1), splashPane);
            fadeTransitionIn.setFromValue(0);
            fadeTransitionIn.setToValue(1);

            FadeTransition fadeTransitionOut = new FadeTransition(Duration.seconds(1), splashPane);
            fadeTransitionOut.setFromValue(1);
            fadeTransitionOut.setToValue(0);

            fadeTransitionIn.play();

            fadeTransitionIn.setOnFinished((e) -> {fadeTransitionOut.play();});

            fadeTransitionOut.setOnFinished((e) -> {
                try {
                    Pane pane = FXMLLoader.load(getClass().getResource("/FXMLMainScreen.fxml"));
                    documentPane.getChildren().setAll(pane);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        if (!isSplashLoaded)
            loadSplashScreen();

    }
}
