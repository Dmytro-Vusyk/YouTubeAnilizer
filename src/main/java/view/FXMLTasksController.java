package view;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FXMLTasksController extends FXMLDocumentController  implements Initializable {

    private static int task=1;

        @FXML
        private JFXButton btnStart;

        @FXML
        private JFXButton btnSettings2;

        @FXML
        private Label lableTime;

        @FXML
        private JFXButton btnPrev;

        @FXML
        private GridPane testGrid;

        @FXML
        private JFXTreeTableView<?> tableView;

        @FXML
        private JFXRadioButton cbSubscribesrs;

        @FXML
        private JFXButton btnClear;

        @FXML
        private JFXRadioButton cbName;

        @FXML
        private JFXButton btnAdd;

        @FXML
        private JFXRadioButton cbDate;

        @FXML
        private Label lblNameOfTasks;

        @FXML
        private JFXRadioButton cbVideos;

        @FXML
        private AnchorPane leftAnchorPaneTasks;

        @FXML
        private JFXRadioButton cbViews;

        @FXML
        private JFXTextField textChannelName;

        @FXML
        private JFXButton btnExit2;

    @FXML
    private GridPane gridPaneSorting;

    @FXML
    private TextArea textAreaChannels;


    @FXML
    void onActionBtnExit2(ActionEvent event)  {
        System.exit(1);
    }

    @FXML
        void onActionBtnClear(ActionEvent event) {
            textAreaChannels.clear();
        }


        @FXML
        void onActionBtnAdd(ActionEvent event) {

            if (textChannelName.getText().equals("") || textChannelName.getText()==null)
                return;

            if((task == 1||task==4)){
                channelNames.clear();
                channelNames.add(textChannelName.getText());
            }
            if((task == 2||task==5)&&channelNames.size()==2){
                channelNames.remove(0);}

            channelNames.add(textChannelName.getText());

            textAreaChannels.clear();

            for (String s: channelNames) {
                textAreaChannels.setText(textAreaChannels.getText() + s+"| ");
            }
        }


        @FXML
        void onActionBtnStart(ActionEvent event) {

          /*
            if (!(textChannelName.getText().equals("") || textChannelName.getText()==null))
                channelNames.add(textChannelName.getText());
            else
                channelNames.add("RedFoc");

            textAreaChannels.clear();
*/

        //JFXTreeTableView<?> tableView;
//            protected ArrayList<LinkedHashMap<MapKeys, String>> channels = new ArrayList<LinkedHashMap<MapKeys, String> >();


            // Create three columns
          // tableView.setRoot(channels.get(0));

         /*   TreeItem<String> root = new TreeItem<>("Functions");
            for (QNameMap.Entry<String, String> entryRow : dc.getSortedfuncAll().entrySet()) {
                root.getChildren().add(new TreeItem<String>(entryRow.getValue()));

            }
            tableView = new JFXTreeTableView<> (root);
*/
            // Create three columns
        }


    @FXML
    void onActionBtnPrev(ActionEvent event) {

       try {
            Pane splashPane = FXMLLoader.load(getClass().getResource("/FXMLMainScreen.fxml"));
            documentPane.getChildren().setAll(splashPane);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    @FXML
    void onActionBtnSettings2(ActionEvent event) {
        try {
            Pane taskController = FXMLLoader.load(getClass().getResource("/FXMLSettings.fxml"));
            documentPane.getChildren().setAll(taskController);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            Pane menuPane = FXMLLoader.load(getClass().getResource("/FXMLTasksMenu.fxml"));
            leftAnchorPaneTasks.getChildren().addAll(menuPane);

            for (Node node: menuPane.getChildren()){
                if (node.getAccessibleText() !=null)
                { node.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
                    System.out.println(node.getAccessibleText());
                    switch (node.getAccessibleText())
                    {
                        case "task1":
                            lblNameOfTasks.setText("Display global information about the channel");
                            btnClear.setVisible(false);
                            gridPaneSorting.setVisible(false);
                            task=1;
                            break;
                        case "task2":
                            lblNameOfTasks.setText("Compare global information about channels");
                            btnClear.setVisible(true);
                            gridPaneSorting.setVisible(false);
                            task=2;
                            break;
                        case "task3":
                            lblNameOfTasks.setText("Sort channels by their data");
                            btnClear.setVisible(true);
                            gridPaneSorting.setVisible(true);
                            task=3;
                            break;
                        case "task4":
                            lblNameOfTasks.setText("Media Resonance");
                            btnClear.setVisible(false);
                            gridPaneSorting.setVisible(false);
                            task=4;
                            break;
                        case "task5":
                            lblNameOfTasks.setText("Compare Media Resonance");
                            btnClear.setVisible(true);
                            gridPaneSorting.setVisible(false);
                            task=5;
                            break;
                        case "task6":
                            lblNameOfTasks.setText("Sort by Media Resonance");
                            btnClear.setVisible(true);
                            gridPaneSorting.setVisible(true);
                            task=6;
                            break;

                    }
                });
                }
            }



        } catch (IOException e) {
            e.printStackTrace();
        }

      //  testBtn.addEventHandler(ActionEvent.ACTION, (e) ->{
            //testGrid.getChildren().remove( 1);
         //   paine11.setVisible(false);




    }


}
