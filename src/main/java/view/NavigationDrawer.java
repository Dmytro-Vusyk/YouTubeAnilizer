package view;

import controller.MainController;
import controller.SettingsController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.GeneralDataContainer;

import java.util.LinkedHashMap;
import java.util.concurrent.*;

public class NavigationDrawer extends Application {
    private static ExecutorService service = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/FXMLDocumentPane.fxml"));
        Scene scene = new Scene(root);
        startThreadInCash();

        primaryStage.setScene(scene);
        primaryStage.setWidth(800);
        primaryStage.setHeight(600);
        primaryStage.setTitle("Main screen");
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setResizable(false);
        primaryStage.show();
        MainController.getInstance().setCache(SettingsController.getInstance().parseFromJson());
    }

    static private Future<LinkedHashMap<String, GeneralDataContainer>> dataContainerFutureTask;

    private void startThreadInCash() {
        SettingsController sc = SettingsController.getInstance();
        Callable<LinkedHashMap<String, GeneralDataContainer>> takeDataFromCash = sc::parseFromJson;
        dataContainerFutureTask = service.submit(takeDataFromCash);
    }

    /**
     * Start program
     */
    public static void main(String[] args) {
        launch(args);

    }
}
