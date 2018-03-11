package controller;


import com.alibaba.fastjson.JSON;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;
import model.youTubeDataContainer.GeneralDataContainer;


public class SettingsController {
    private BooleanProperty saveCash;
    private BooleanProperty showTime;
    private StringProperty urlOfCash;

    public SettingsController(String urlOfCash, boolean saveCash, boolean showTime) {
    }



    private static void ShowTime() {
        long startTime = System.currentTimeMillis();

        long timeSpent = System.currentTimeMillis()%55;

        System.out.println("программа выполнялась " + timeSpent + " миллисекунд");
    }


    static Object SaveCash(GeneralDataContainer object) {
        Object json = new Object();
        String input = JSON.toJSONString(object);
        System.out.println(input + "json");

        return json;
    }

    static GeneralDataContainer parseFromJson(String json){
        GeneralDataContainer object = JSON.parseObject(json, GeneralDataContainer.class);
        return object;
    }


    public static void main(String[] args) {
//
//        String json = (String) SaveCash(new GeneralDataContainer());
//
//        GeneralDataContainer json1  = parseFromJson(json);
//
//        System.out.println(json);


        ShowTime();
        //SaveCash();
    }

}
