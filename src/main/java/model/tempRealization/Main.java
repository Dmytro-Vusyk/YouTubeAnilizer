package model.tempRealization;

import model.tempController.QueryFromYoutube;

import java.util.Scanner;

public class Main {
    private static String channelId = "UC8mndg3Tr2g9iMoJMI4ob5A";
    public static void main(String[] args) throws InterruptedException {
        System.out.printf("Test 1: \"Query one channel\". \n" +
                "Channel id: %s\n" +
                "Task: get title name from YouTubeAPI, by channel id\n\n", channelId);
        Thread.sleep(1000);

        QueryFromYoutube query = new QueryFromYoutube();
        query.setChannelId(channelId);
        String tittle = query.getTittle();
        System.out.println("Tittle = \"" + tittle + "\"");
    }
}
