package controller;


import com.mashape.unirest.http.exceptions.UnirestException;
import model.tempController.QueryFromYoutube;
import model.tempRealization.YouTubeAPI;
import model.youTubeDataContainer.GeneralDataContainer;
import model.youTubeDataContainer.RelatedPlaylists;
import model.youTubeDataContainer.Responce;

import java.util.Arrays;

public class Test {
    private static String channelId = "UC8mndg3Tr2g9iMoJMI4ob5A";
    private static String channelId2 = "UCXFxgPppcehs2LoiKhkakQg";
    private static String channelId3 = "UCUoEWMvijAEn-hWTLUtfdoA";

    public static void main(String[] args) throws UnirestException {
        QueryFromYoutube qfy = new QueryFromYoutube();
        GeneralDataContainer gdc = new GeneralDataContainer();
        MainController mc = new MainController();
        String[] ids = {channelId, channelId2, channelId3};
        RelatedPlaylists rp = new RelatedPlaylists();
        // System.out.println(mc.sortChannels(ids).toString());


        System.out.println(gdc.getUploads());
        qfy.makeQuary(gdc, "UC8mndg3Tr2g9iMoJMI4ob5A");
        System.out.println(gdc.getUploads());
        qfy.makeQueryForChennelsID(gdc,gdc.getUploads());
        System.out.println(Arrays.toString(gdc.getVideoIds()));
        //YouTubeAPI.getVideoList();
    }

}
