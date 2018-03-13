package view;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
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

    final DirectoryChooser directoryChooser = new DirectoryChooser();

    private void configuringDirectoryChooser(DirectoryChooser directoryChooser) {
        // Set title for DirectoryChooser
        directoryChooser.setTitle("Select Directories");

        // Set Initial Directory
        directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));
    }

    @FXML
    void onActionBtnBrowse(ActionEvent event) {
        configuringDirectoryChooser(directoryChooser);
        File dir = directoryChooser.showDialog(new Stage().getOwner());
        if (dir != null) {
            textFieldAdress.setText(dir.getAbsolutePath());
        } else {
            textFieldAdress.setText(null);
        }
        System.out.println(textFieldAdress);
    }

    @FXML
    void ionActionUsingCash(ActionEvent event) {



    }


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
