package view;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FXMLSettingsController extends FXMLDocumentController implements Initializable  {

    @FXML
    private JFXButton btnBrowse;

    @FXML
    private JFXTextField textFieldAdress;


    @FXML
    private JFXCheckBox isTimeVisible;




    @FXML
    void onActionTimeVisible(ActionEvent event) {
        if (isTimeVisible.allowIndeterminateProperty().getValue())
        {
            isTimeCheked=true;
        }
        else
            {
                isTimeCheked=false;
            }
    }


    @FXML
    void onActionBtnGoToMain(ActionEvent event) {

        try {
            Pane mainPane = FXMLLoader.load(getClass().getResource("/FXMLMainScreen.fxml"));
            documentPane.getChildren().setAll(mainPane);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {}

}
