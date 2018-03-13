package view;

import controller.SettingsController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.GeneralDataContainer;

import java.util.LinkedHashMap;
import java.util.concurrent.*;

public class NavigationDrawer extends Application {
    static ExecutorService service = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/FXMLDocumentPane.fxml"));
        Scene scene = new Scene(root);
        startThreadInCash();


//        GeneralDataContainer gdc = dataContainerFutureTask.get();

        primaryStage.setScene(scene);
        primaryStage.setWidth(800);
        primaryStage.setHeight(600);
        primaryStage.setTitle("Main screen");
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    String cashString = new String();

    static private Future<LinkedHashMap<String, GeneralDataContainer>> dataContainerFutureTask;

    public LinkedHashMap<String, GeneralDataContainer> getCash() {
        LinkedHashMap<String, GeneralDataContainer> cash = new LinkedHashMap<>();
        try {
            cash = dataContainerFutureTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return cash;
    }

    private void startThreadInCash() {
        SettingsController sc = SettingsController.getInstance();
        Callable<LinkedHashMap<String, GeneralDataContainer>> takeDataFromCash = () -> {
            System.out.println("From startThreadInCash -> Callable. Thread: " + Thread.currentThread());
            System.out.println(sc.getPathToCash());
            return sc.parseFromJson();
        };
        dataContainerFutureTask = service.submit(takeDataFromCash);
    }

    /**
     * Start program
     */
    public static void main(String[] args) {
        launch(args);

    }
}
