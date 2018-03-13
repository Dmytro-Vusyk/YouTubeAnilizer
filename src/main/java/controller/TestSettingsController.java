package controller;

import java.util.LinkedHashMap;

public class TestSettingsController {
    public static void main(String[] args) {

    SettingsController sc = SettingsController.getInstance("");
    MainController mc = MainController.getInstance();
        LinkedHashMap cash = mc.getCash();


        System.out.println("fillCash");
        mc.showGlobalInformationAboutChannel("UCIKJXqQGc6pCCaVaqhkHLQA");
        System.out.println("show cash");
        System.out.println(cash);
        System.out.println("imitation app close");
        System.out.println("save cash");
        sc.saveCash(cash);
        System.out.println("cash saved");
        System.out.println("imitation of app start");
        MainController newMC =  MainController.getInstance();
        newMC.setCash(sc.parseFromJson());
        System.out.println("read done!");
        System.out.println(newMC.getCash());



}

}
