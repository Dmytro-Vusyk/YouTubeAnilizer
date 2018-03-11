package controller;

import model.GeneralDataContainer;

import java.io.FileWriter;
import java.io.IOException;

import static controller.SettingsController.parseFromJson;

public class TestMain { public static void main(String[] args) {

    SettingsController controller = new SettingsController(true,true,"");

    controller.saveCash(new GeneralDataContainer());

   // GeneralDataContainer json1  = parseFromJson(json);


   // System.out.println(json1);

}

}
