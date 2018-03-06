package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class NavigationDrawer extends Application {

    public static boolean isSplashLoaded = false;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/FXMLDocumentPane.fxml"));
        Scene scene = new Scene(root);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Main screen");
      //  primaryStage.setOpacity(0.2);
        primaryStage.show();
    }


    /**Start program*/
    public static void main(String[] args) {launch(args);}


}
