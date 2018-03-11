package controller;


import com.alibaba.fastjson.JSON;
import model.GeneralDataContainer;

import java.io.*;


public class SettingsController {
    private String pathToCash;
    private boolean saveCash;
    private boolean showTime;

    private static final String DEFAULT_PATH = "src\\main\\java\\cash\\saveCash.txt";

    public SettingsController(boolean saveCash, boolean showTime, String pathToCash) {
        if (pathToCash == "") {
            this.pathToCash = DEFAULT_PATH;

        }
        this.pathToCash = pathToCash;
        this.saveCash = saveCash;
        this.showTime = showTime;
    }

    private String readCash() {
        String json = "";
        File file = new File(getPathToCash());
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            StringBuilder sb = new StringBuilder();
            String line = reader.readLine();
            while (line != null){
                sb.append(line);
                sb.append(System.lineSeparator());
                line = reader.readLine();
            }
            json = sb.toString();

        } catch (FileNotFoundException e) {
            System.err.print(e.getStackTrace());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return json;
    }


    public void saveCash(GeneralDataContainer object) {

        String input = JSON.toJSONString(object);

        try (FileWriter file = new FileWriter(getDefaultPath())) {
            file.write(input);

            file.flush();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public GeneralDataContainer parseFromJson() {

        GeneralDataContainer object = JSON.parseObject(readCash(), GeneralDataContainer.class);

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
