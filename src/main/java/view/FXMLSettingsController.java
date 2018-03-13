package view;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import controller.SettingsController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
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
    private JFXCheckBox isUsingCash;

    public void Cheked() {
        if(isTimeChecked)
        {
            isTimeVisible.setSelected(true);

        }else
            {
                isTimeVisible.setSelected(false);

            }
        if (isUsingCashChecked)
        {
            isUsingCash.setSelected(true);
        }

        if (directoryChooser.getInitialDirectory()==null)
            directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));

        textFieldAdress.setText(directoryChooser.getInitialDirectory().getAbsolutePath());
    }


    //create object of directory chooser
    final DirectoryChooser directoryChooser = new DirectoryChooser();

    //configu
    private void configuringDirectoryChooser(DirectoryChooser directoryChooser) {
        // Set title for DirectoryChooser
        directoryChooser.setTitle("Select Directories");

        // Set Initial Directory
        directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));
    }

    @FXML
    void onActionBtnBrowse(ActionEvent event) {
        configuringDirectoryChooser(directoryChooser);
        File dir = directoryChooser.showDialog(new Stage().getOwner());// - тут не работает!!! Аня поправь Т_т ему нужен стейдж в нутрь showDialog() X_x
        if (dir != null) {
            textFieldAdress.setText(dir.getAbsolutePath());
            SettingsController.getInstance().setPathToCash(textFieldAdress.toString());
        } else {
            textFieldAdress.setText(null);
        }
        System.out.println(textFieldAdress);
    }

    @FXML
    void ionActionUsingCash(ActionEvent event) {

        if(isTimeVisible.isSelected()){
         isUsingCashChecked = true;
        } else {
         isUsingCashChecked = false;
        }
        mainController.setSaveCash(isUsingCashChecked);
    }


    @FXML
    void onActionTimeVisible(ActionEvent event) {
        if (isTimeVisible.isSelected())
        {
            isTimeChecked =true;
        }
        else
        {
           isTimeChecked =false;
        }
        mainController.setShowTime(isTimeChecked);
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
    public void initialize(URL location, ResourceBundle resources) {
        Cheked();
    }

}
