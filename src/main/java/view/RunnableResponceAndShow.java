package view;

import enumerated.MapKeys;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Created by Администратор on 16.03.2018.
 */
public class RunnableResponceAndShow implements Runnable {
    private ObservableList<FXMLTasksController.Json> json_lines;
    private ArrayList<LinkedHashMap<MapKeys, String>> channels;
    private FXMLTasksController thisFXMLTask;
    private Label labelTime;
    RunnableResponceAndShow(FXMLTasksController fxmlTasksController){
        this.json_lines = fxmlTasksController.json_lines;
        this.channels = fxmlTasksController.channels;
        this.labelTime = fxmlTasksController.lableTime;
        thisFXMLTask = fxmlTasksController;

    }
    @Override
    public void run() {
//        final long checkPointStart = System.currentTimeMillis();
        json_lines.clear();
        channels.clear();
        channels = thisFXMLTask.TESTshowGlobalInformationAboutChannel();
        for (LinkedHashMap<MapKeys, String> ch : channels) {
            json_lines.add(new FXMLTasksController.Json(
                    ch.get(MapKeys.CHANNEL_NAME),
                    ch.get(MapKeys.PUBLISHING_DATE),
                    ch.get(MapKeys.SUBSCRIBERS_COUNT),
                    ch.get(MapKeys.VIDEOS_COUNT),
                    ch.get(MapKeys.VIEWS_COUNT),
                    ch.get(MapKeys.COMMENTS_COUNT),
                    ch.get(MapKeys.CHANNEL_ID)
            ));
        }
//        Platform.runLater(()->
//                labelTime.setText(Long.toString(thisFXMLTask.mainController.showTimeMeasurement(checkPointStart, System.currentTimeMillis())) + "ms")
//        );
    }
}
