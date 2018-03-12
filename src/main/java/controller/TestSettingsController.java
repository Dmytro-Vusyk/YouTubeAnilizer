package controller;


public class TestSettingsController {
    public static void main(String[] args) {

    SettingsController sc = new SettingsController("");
    MainController mc = new MainController();


    System.out.println("пишем в файл");
  //  sc.saveCash(mc.);
  //  sc.saveCash();
    System.out.println("читаем из файла");
    System.out.println(sc.parseFromJson().toString());

}

}
