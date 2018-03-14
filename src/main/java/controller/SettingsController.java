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

    private static SettingsController instance;
    private String pathToCache;
    private static final char SEPAR = File.separatorChar;
    private static final String DEFAULT_PATH = String.format("src%cmain%cjava%ccash%csaveCash.txt", SEPAR, SEPAR, SEPAR, SEPAR);
    private static final String PATH_TO_PATH_FOLDER = String.format("src%cmain%cjava%cstore%cpath.txt", SEPAR, SEPAR, SEPAR, SEPAR);

    /**
     * Constructor initialize default path to cache file if null
     */
    private SettingsController() {
        if (pathToCache == null || pathToCache.equals("")) {
            this.pathToCache = DEFAULT_PATH;
        }
    }


    public static synchronized SettingsController getInstance() {
        if (instance == null) {
            synchronized (MainController.class) {
                if (instance == null) {
                    instance = new SettingsController();
                }
            }
        }
        return instance;
    }


    /**
     * Reads cache on hard drive and returns it as json
     *
     * @return json with cache
     */
    private String readCache() {
        String json = "";

        try {
            File file = new File(Paths.get(PATH_TO_PATH_FOLDER).toString());
            if (!file.exists()) {
                file.createNewFile();
            }
            List<String> list = Files.lines(Paths.get(PATH_TO_PATH_FOLDER), StandardCharsets.UTF_8)
                    .collect(Collectors.toList());
            setPathToCache(list.get(0));
        } catch (IOException e) {
            e.printStackTrace();
        }

        File file = new File(getPathToCache());
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


    /**
     * This method saves the cache to the specified folder and link on it
     *
     * @param cache
     */
    public void saveCache(LinkedHashMap<String, GeneralDataContainer> cache) {
        String input = JSON.toJSONString(cache);

        try (FileWriter fileWriter = new FileWriter(pathToCache);
             FileWriter pathWriter = new FileWriter(PATH_TO_PATH_FOLDER)) {
            fileWriter.write(input);
            fileWriter.flush();
            fileWriter.close();
            pathWriter.write(getPathToCache());
            pathWriter.flush();
            pathWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /**
     * This method is parsed from a file using readCache and returns LinkedHashMap
     *
     * @return LinkedHashMap
     */
    public LinkedHashMap<String, GeneralDataContainer> parseFromJson() {
        return JSON.parseObject(readCache(), LinkedHashMap.class);
    }

    public String getPathToCache() {
        return pathToCache;
    }

    public void setPathToCache(String pathToCache) {
        this.pathToCache = pathToCache;
    }

}
