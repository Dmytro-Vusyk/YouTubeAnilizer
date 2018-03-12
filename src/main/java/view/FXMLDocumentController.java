package view;

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
import java.util.*;

/**MainController*/
public class FXMLDocumentController implements Initializable {

    @FXML
    protected AnchorPane documentPane;
    protected HashSet<String> channelNames = new HashSet<String>();

    public HashSet<String> getChannelNames() {
        return channelNames;
    }

    protected ArrayList<LinkedHashMap<MapKeys, String>> channels = new ArrayList<LinkedHashMap<MapKeys, String> >();

/* ТЕСТ НЕ ТРОГАТЬ*/
        private LinkedHashMap<MapKeys, String> TESTshowGlobalInformationAboutChannel() {
        LinkedHashMap<MapKeys, String> output = new LinkedHashMap<>();
        try {
            output.put(MapKeys.CHANNEL_NAME, "nhzv");
            output.put(MapKeys.PUBLISHING_DATE, String.valueOf(new Date()));
            output.put(MapKeys.SUBSCRIBERS_COUNT,"5" );
            output.put(MapKeys.VIDEOS_COUNT, "2");
            output.put(MapKeys.VIEWS_COUNT, "100");
            output.put(MapKeys.COMMENTS_COUNT, "10");
            output.put(MapKeys.CHANNEL_ID, "ky-ky");
        } catch (NullPointerException e) {}
        return output;
    }



    /** Splash Effect*/
    private void loadSplashScreen() {
        try {
            NavigationDrawer.isSplashLoaded=true;

            Pane splashPane = FXMLLoader.load(getClass().getResource("/FXMLSplashWindow.fxml"));
            documentPane.getChildren().setAll(splashPane);

            FadeTransition fadeTransitionIn = new FadeTransition(Duration.seconds(0.5), splashPane);
            fadeTransitionIn.setFromValue(0);
            fadeTransitionIn.setToValue(1);

            FadeTransition fadeTransitionOut = new FadeTransition(Duration.seconds(0.5), splashPane);
            fadeTransitionOut.setFromValue(1);
            fadeTransitionOut.setToValue(0);

            fadeTransitionIn.play();

            fadeTransitionIn.setOnFinished((e) -> {
                fadeTransitionOut.play();

            });

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

        channels.add(TESTshowGlobalInformationAboutChannel());

        if (!NavigationDrawer.isSplashLoaded)
            loadSplashScreen();

    }
}
