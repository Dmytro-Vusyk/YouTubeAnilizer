package controller;


import com.alibaba.fastjson.JSON;
import model.GeneralDataContainer;

import java.io.*;
import java.util.Arrays;


public class SettingsController {

    private String pathToCash;
    private static final char SEPAR = File.separatorChar;
    private static final String DEFAULT_PATH = String.format("src%cmain%cjava%ccash%csaveCash.txt", SEPAR,SEPAR,SEPAR,SEPAR);

    public SettingsController(String pathToCash) {
        if (pathToCash == null || pathToCash.equals("")) {
            this.pathToCash = DEFAULT_PATH;
        } else {
            this.pathToCash = pathToCash;
        }
    }

    private String readCash() {
        String json = "";
        File file = new File(getPathToCash());
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            StringBuilder sb = new StringBuilder();
            String line = reader.readLine();
            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = reader.readLine();
            }
            json = sb.toString();

        } catch (FileNotFoundException e) {
            System.err.print(Arrays.toString(e.getStackTrace()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return json;
    }


    public void saveCash(GeneralDataContainer object) {

        String input = JSON.toJSONString(object);

        try (FileWriter fileWriter = new FileWriter(pathToCash, true);
             BufferedWriter bw = new BufferedWriter(fileWriter);
             PrintWriter out = new PrintWriter(bw)) {
            out.write(input);
            out.flush();
            out.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public GeneralDataContainer parseFromJson() {

        return JSON.parseObject(readCash(), GeneralDataContainer.class);
    }

    public String getPathToCash() {
        return pathToCash;
    }

    public void setPathToCash(String pathToCash) {
        this.pathToCash = pathToCash;
    }
}
