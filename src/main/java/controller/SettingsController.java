package controller;


import com.alibaba.fastjson.JSON;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;
import model.youTubeDataContainer.GeneralDataContainer;
import model.youTubeDataContainer.GeneralDataContainer;

import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.File;
import java.util.Formatter;


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

        String json = SaveCash(new GeneralDataContainer("id", "title", 10, true, "", "", 20,"","","",));

        GeneralDataContainer json1  = parseFromJson(json);


        System.out.println(json1);

        try (FileWriter file = new FileWriter("/Users/<username>/Documents/savedCash.txt")) {
            File file1 = new File("JsonFile.json");
            file.write(json1.toJSONString());
            FileWriter fileWriter = new FileWriter(String.valueOf(file));
            System.out.println("Successfully Copied JSON Object to File...");
            System.out.println("\nJSON Object: " + json1);


            fileWriter.flush();
            fileWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }







        ShowTime();
        SaveCash(json1);
    }

}
