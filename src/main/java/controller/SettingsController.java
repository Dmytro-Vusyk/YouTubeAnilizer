package controller;


import com.alibaba.fastjson.JSON;
import model.GeneralDataContainer;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;


public class SettingsController {

    private String pathToCash;
    private static final char SEPAR = File.separatorChar;
    private static final String DEFAULT_PATH = String.format("src%cmain%cjava%ccash%csaveCash.txt", SEPAR, SEPAR, SEPAR, SEPAR);
    private static final String PATH_TO_PATH_FOLDER = String.format("src%cmain%cjava%cstore%cpath.txt",SEPAR,SEPAR,SEPAR,SEPAR);

    public SettingsController(String pathToCash) {
        if (pathToCash == null || pathToCash.equals("")) {
            this.pathToCash = DEFAULT_PATH;
        } else {
            this.pathToCash = pathToCash;
        }
    }

    private String readCash() {
        String json = "";

        try {
            List<String> list = Files.lines(Paths.get(PATH_TO_PATH_FOLDER), StandardCharsets.UTF_8)
                    .collect(Collectors.toList());
            setPathToCash(list.get(0));
        } catch (IOException e) {
            e.printStackTrace();
        }

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


    public void saveCash(LinkedHashMap<String, GeneralDataContainer> cash) {
        String input = JSON.toJSONString(cash);

        try (FileWriter fileWriter = new FileWriter(pathToCash);
             FileWriter pathWriter = new FileWriter(PATH_TO_PATH_FOLDER)) {
            fileWriter.write(input);
            fileWriter.flush();
            fileWriter.close();
            pathWriter.write(getPathToCash());
            pathWriter.flush();
            pathWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public LinkedHashMap<String, GeneralDataContainer> parseFromJson() {
        return JSON.parseObject(readCash(), LinkedHashMap.class);
    }

    public String getPathToCash() {
        return pathToCash;
    }

    public void setPathToCash(String pathToCash) {
        this.pathToCash = pathToCash;
    }

}
