package view;

import enumerated.MapKeys;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.concurrent.Callable;

/**
 * Created by Администратор on 13.03.2018.
 */
public class QueryThread implements Callable<ArrayList<LinkedHashMap<MapKeys, String>>> {
    private long checkPoint;
    private FXMLTasksController fxmlTasksController;

    QueryThread(long checkPointStart, FXMLTasksController fxmlTasksController) {
        checkPoint = checkPointStart;
        this.fxmlTasksController = fxmlTasksController;
    }

    @Override
    public ArrayList<LinkedHashMap<MapKeys, String>> call() throws Exception {
        return fxmlTasksController.TESTshowGlobalInformationAboutChannel();
    }

    long getCheckPoint() {
        return checkPoint;
    }
}
