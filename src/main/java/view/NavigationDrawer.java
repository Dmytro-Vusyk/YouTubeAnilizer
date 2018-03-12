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

import java.util.concurrent.*;

public class NavigationDrawer extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/FXMLDocumentPane.fxml"));
        Scene scene = new Scene(root);

        ExecutorService service = Executors.newFixedThreadPool(1);

        //tesTshowGlobalInformationAboutChannel

        System.out.println("checkPoint1");
        long chackpoint = System.currentTimeMillis();

        startThreadInCash();

//        GeneralDataContainer gdc = dataContainerFutureTask.get();
        chackpoint = System.currentTimeMillis() - chackpoint;
        System.out.println(chackpoint);

        System.out.println("chackPoint2");

        primaryStage.setScene(scene);
        primaryStage.setWidth(800);
        primaryStage.setHeight(600);
        primaryStage.setTitle("Main screen");
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    GeneralDataContainer cash = new GeneralDataContainer();
    static private Future<GeneralDataContainer> dataContainerFutureTask;

    public GeneralDataContainer getCash() {
        try {
            cash = dataContainerFutureTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return cash;
    }

    public void startThreadInCash(){
        ExecutorService service = Executors.newFixedThreadPool(1);
        SettingsController sc = new SettingsController("");
        Callable<GeneralDataContainer> takeDataFromCash = ()-> sc.parseFromJson();
        dataContainerFutureTask = service.submit(takeDataFromCash);
    }

    /**Start program*/
    public static void main(String[] args) {
        launch(args);
    }
    }
