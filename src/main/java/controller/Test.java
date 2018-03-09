package controller;


import com.mashape.unirest.http.exceptions.UnirestException;
import model.tempController.QueryFromYoutube;
import model.youTubeDataContainer.GeneralDataContainer;
import model.youTubeDataContainer.RelatedPlaylists;


import java.util.Arrays;

public class Test {
    private static String channelId = "UC8mndg3Tr2g9iMoJMI4ob5A";
    private static String channelId2 = "UCXFxgPppcehs2LoiKhkakQg";
    private static String channelId3 = "UCUoEWMvijAEn-hWTLUtfdoA";

    public static void main(String[] args) throws UnirestException {
        QueryFromYoutube qfy = new QueryFromYoutube();
        GeneralDataContainer gdc = new GeneralDataContainer();
        qfy.makeQuary(gdc,channelId);
        System.out.println(gdc.toString());
    }

}
