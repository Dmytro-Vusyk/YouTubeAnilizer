package controller;

import java.util.LinkedHashMap;

public class TestSettingsController {
    public static void main(String[] args) {

    SettingsController sc = new SettingsController("");
    MainController mc = new MainController();
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
        MainController newMC = new MainController();
        newMC.setCash(sc.parseFromJson());
        System.out.println("read done!");
        System.out.println(newMC.getCash());



}

}
