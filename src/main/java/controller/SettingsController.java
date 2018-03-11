package controller;


import com.alibaba.fastjson.JSON;
import model.GeneralDataContainer;

import java.io.FileWriter;
import java.io.IOException;


public class SettingsController {
    private String pathToCash;
    private boolean saveCash;
    private boolean showTime;

private static final String DEFAULT_PATH = "cash\\saveCash.txt";

public SettingsController(boolean saveCash, boolean showTime, String pathToCash) {
    if( pathToCash == ""){
        this.pathToCash = DEFAULT_PATH;

    }
    this.pathToCash = pathToCash;
    this.saveCash = saveCash;
    this.showTime = showTime;
    }


    public static void saveCash(GeneralDataContainer object) {
        //Object json = new Object();
        String input = JSON.toJSONString(object);


        try (FileWriter file = new FileWriter(getDefaultPath())) {
            file.write(input);
            FileWriter fileWriter = new FileWriter(String.valueOf(file));


            fileWriter.flush();
            fileWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    static GeneralDataContainer parseFromJson(String json){
        GeneralDataContainer object = JSON.parseObject(json, GeneralDataContainer.class);

        return object;
    }

    public String getPathToCash() {
        return pathToCash;
    }

    public void setPathToCash(String pathToCash) {
        this.pathToCash = pathToCash;
    }

    public boolean isSaveCash() {
        return saveCash;
    }

    public void setSaveCash(boolean saveCash) {
        this.saveCash = saveCash;
    }

    public boolean isShowTime() {
        return showTime;
    }

    public void setShowTime(boolean showTime) {
        this.showTime = showTime;
    }

    public static String getDefaultPath() {
        return DEFAULT_PATH;
    }
}
