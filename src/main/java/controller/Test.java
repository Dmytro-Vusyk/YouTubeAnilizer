package controller;


import model.youTubeDataContainer.GeneralDataContainer;

public class Test {
    private static String channelId = "UC8mndg3Tr2g9iMoJMI4ob5A";
    private static String channelId2 = "UCXFxgPppcehs2LoiKhkakQg";
    private static String channelId3 = "UCUoEWMvijAEn-hWTLUtfdoA";

    public static void main(String[] args) {

        MainController mc = new MainController();
        String[] ids = {channelId,channelId2,channelId3};

        System.out.println(mc.sortChannels(ids).toString());
    }

}
