package controller;


import com.mashape.unirest.http.exceptions.UnirestException;
import controller.generalDataController.QueryFromYoutube;
import model.GeneralDataContainer;

public class Test {
    private static String channelId = "BlackSilverUfa";
    private static String channelId2 = "UCXFxgPppcehs2LoiKhkakQg";
    private static String channelId3 = "UCUoEWMvijAEn-hWTLUtfdoA";

    public static void main(String[] args) throws UnirestException {
        QueryFromYoutube qfy = new QueryFromYoutube();
        GeneralDataContainer gdc = new GeneralDataContainer();

        qfy.makeQuary(gdc, channelId);
        System.out.println(gdc.toString());
    }

}
