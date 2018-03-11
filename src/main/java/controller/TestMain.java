package controller;


import model.GeneralDataContainer;

public class TestMain {
    public static void main(String[] args) {

    SettingsController sc = new SettingsController(true,true,"");
    MainController mc = new MainController();

    GeneralDataContainer gdc = mc.makeBaseRequest("BlackSilverUFA");

    System.out.println("пишем в файл");
    sc.saveCash(gdc);
    System.out.println("читаем из файла");
    System.out.println(sc.parseFromJson().toString());

}

}
