package view;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import controller.SettingsController;
import enumerated.MapKeys;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.ResourceBundle;
import java.util.concurrent.*;

public class FXMLTasksController extends FXMLDocumentController implements Initializable {

    private static int task = 1;
    private static int columns = 6;
    private Date dateNow = new Date();
    private SimpleDateFormat formatForTime = new SimpleDateFormat("hh:mm");
    private SimpleDateFormat formatForDateTime = new SimpleDateFormat("dd:yy hh:mm");

    private ObservableList<Json> json_lines = FXCollections.observableArrayList();


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
    private JFXTreeTableView<Json> tableView;

    @FXML
    private JFXButton btnClear;

    @FXML
    private JFXButton btnAdd;

    @FXML
    private Label lblNameOfTasks;

    @FXML
    private AnchorPane leftAnchorPaneTasks;

    @FXML
    private JFXTextField textChannelName;

    @FXML
    private JFXButton btnExit2;

    @FXML
    private GridPane gridPaneSorting;

    @FXML
    private TextArea textAreaChannels;

    @FXML
    private HBox timePane;


    @FXML
    void onActionBtnExit2(ActionEvent event) {
        SettingsController.getInstance().saveCash(mainController.getCash());
        System.exit(1);
    }

    @FXML
    void onActionBtnClear(ActionEvent event) {
        textAreaChannels.clear();
        textChannelName.clear();
        btnStart.setDisable(true);
    }


    @FXML
    void onActionBtnAdd(ActionEvent event) {

        if (textChannelName.getText().equals("") || textChannelName.getText() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Field is empty");
            alert.setHeaderText(null);
            alert.setContentText("Enter the ID of the channel");

            alert.showAndWait();
            return;
        }

        btnStart.setDisable(false);
        if ((task == 1 || task == 4)) {
            channelNames.clear();
            channelNames.add(textChannelName.getText());
        }
        if ((task == 2 || task == 5) && channelNames.size() == 2) {
            channelNames.remove(0);
        }

        channelNames.add(textChannelName.getText());

        textAreaChannels.clear();

        for (String s : channelNames) {
            textAreaChannels.setText(textAreaChannels.getText() + s + "   |   ");
        }
    }

    class Json extends RecursiveTreeObject<Json> {
        StringProperty channelName;
        StringProperty publishing_date;
        StringProperty subscribers_count;
        StringProperty videos_count;
        StringProperty views_count;
        StringProperty comments_count;
        StringProperty channel_ID;

        public Json(String channelName, String publishing_date, String subscribers_count, String videos_count, String views_count, String comments_count, String channel_ID) {
            this.channelName = new SimpleStringProperty(channelName);
            this.publishing_date = new SimpleStringProperty(publishing_date);
            this.subscribers_count = new SimpleStringProperty(subscribers_count);
            this.videos_count = new SimpleStringProperty(videos_count);
            this.views_count = new SimpleStringProperty(views_count);
            this.comments_count = new SimpleStringProperty(comments_count);
            this.channel_ID = new SimpleStringProperty(channel_ID);
        }
    }


    /* ТЕСТ НЕ ТРОГАТЬ*/  //позно! мухахахахах
    protected ArrayList<LinkedHashMap<MapKeys, String>> TESTshowGlobalInformationAboutChannel() {
        ArrayList<LinkedHashMap<MapKeys, String>> out = new ArrayList<>();
        if (task == 1) {
            for (String s :
                    channelNames) {
                out.add(mainController.showBaseInformationAboutChannel(s));
            }
        }
        if (task == 2) {
            for (String s :
                    channelNames) {
                out.add(mainController.showBaseInformationAboutChannel(s));
            }
        }
        if (task == 3) {
            out = mainController.showBaseInformationAboutChannel(channelNames.toArray(new String[channelNames.size()]));
        }
        if (task == 4) {
            for (String s :
                    channelNames) {
                out.add(mainController.showGlobalInformationAboutChannel(s));
            }
        }
        if (task == 5) {
            for (String s :
                    channelNames) {
                out.add(mainController.showGlobalInformationAboutChannel(s));
            }
        }
        if (task == 6) {
            out = mainController.showGlobalInformationAboutChannels(channelNames.toArray(new String[channelNames.size()]));
        }

        return out;
    }

    //// TODO: 3/13/2018 цей метод в потік
    @FXML
    void onActionBtnStart(ActionEvent event) {

        JFXTreeTableColumn<Json, String> deptChannelName = new JFXTreeTableColumn<>("Name");
        deptChannelName.setPrefWidth(100);
        deptChannelName.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Json, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Json, String> param) {
                return param.getValue().getValue().channelName;
            }
        });
        JFXTreeTableColumn<Json, String> deptPublishing_date = new JFXTreeTableColumn<>("Publishing");
        deptPublishing_date.setPrefWidth(128);
        deptPublishing_date.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Json, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Json, String> param) {
                return param.getValue().getValue().publishing_date;
            }
        });
        JFXTreeTableColumn<Json, String> deptSubscribers_count = new JFXTreeTableColumn<>("Subscribers");
        deptSubscribers_count.setPrefWidth(130);
        deptSubscribers_count.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Json, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Json, String> param) {
                return param.getValue().getValue().subscribers_count;
            }
        });
        JFXTreeTableColumn<Json, String> deptVideos_count = new JFXTreeTableColumn<>("Videos");
        deptVideos_count.setPrefWidth(105);
        deptVideos_count.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Json, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Json, String> param) {
                return param.getValue().getValue().videos_count;
            }
        });
        JFXTreeTableColumn<Json, String> deptViews_count = new JFXTreeTableColumn<>("Views");
        deptViews_count.setPrefWidth(100);
        deptViews_count.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Json, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Json, String> param) {
                return param.getValue().getValue().views_count;
            }
        });


        if (task >= 4) {
            JFXTreeTableColumn<Json, String> deptComments_count = new JFXTreeTableColumn<>("Comments");
            deptComments_count.setPrefWidth(110);
            deptComments_count.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Json, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Json, String> param) {
                    return param.getValue().getValue().comments_count;
                }
            });

            tableView.getColumns().setAll(deptChannelName, deptPublishing_date, deptSubscribers_count, deptVideos_count, deptViews_count, deptComments_count);
        } else {
            tableView.getColumns().setAll(deptChannelName, deptPublishing_date, deptSubscribers_count, deptVideos_count, deptViews_count);
        }

        setTableCollectionValue();
        final TreeItem<Json> root = new RecursiveTreeItem<Json>(json_lines, RecursiveTreeObject::getChildren);

        tableView.setRoot(root);
        tableView.setShowRoot(false);

    }

    private static ExecutorService service = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    private void setTableCollectionValue() {
//            ExecutorService service = Executors.newFixedThreadPool(1);

        json_lines.clear();
        channels.clear();

        //// TODO: 3/12/2018 Сюда коллекцию, или елементы для вывода в таблицу

        QueryThread queryThread = new QueryThread(System.currentTimeMillis(), this);

        Future<ArrayList<LinkedHashMap<MapKeys, String>>> future = service.submit(queryThread);


        Platform.runLater(() -> {
            try {
                channels = future.get();//TESTshowGlobalInformationAboutChannel();
                System.out.println("11111111111111111111111111111111111");
                final long checkPointFinish = System.currentTimeMillis();
                lableTime.setText(Long.toString(mainController.showTimeMeasurement(queryThread.getCheckPoint(), checkPointFinish)) + "ms");

                System.out.println(System.currentTimeMillis() - queryThread.getCheckPoint());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            for (LinkedHashMap<MapKeys, String> ch : channels) {
                json_lines.add(new Json(
                        ch.get(MapKeys.CHANNEL_NAME),
                        ch.get(MapKeys.PUBLISHING_DATE),
                        ch.get(MapKeys.SUBSCRIBERS_COUNT),
                        ch.get(MapKeys.VIDEOS_COUNT),
                        ch.get(MapKeys.VIEWS_COUNT),
                        ch.get(MapKeys.COMMENTS_COUNT),
                        ch.get(MapKeys.CHANNEL_ID)
                ));
            }
            System.out.println("222222222222222222222222222222222222222");
        });
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


        if (channelNames.isEmpty()){
            btnStart.setDisable(true);
        }


        try {
            Pane menuPane = FXMLLoader.load(getClass().getResource("/FXMLTasksMenu.fxml"));
            leftAnchorPaneTasks.getChildren().addAll(menuPane);

            if (isTimeChecked) {
                timePane.setVisible(false);
            } else {
                timePane.setVisible(true);
            }

            //lableTime.setText(formatForTime.format(dateNow)); - шойта?
            lableTime.setText("0s");
            lblNameOfTasks.setText("Display global information about the channel");
            btnClear.setVisible(false);
            task = 1;


            for (Node node : menuPane.getChildren()) {
                if (node.getAccessibleText() != null) {
                    node.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
                        System.out.println(node.getAccessibleText());
                        switch (node.getAccessibleText()) {
                            case "task1":
                                lblNameOfTasks.setText("Display global information about the channel");
                                btnClear.setVisible(false);
                                task = 1;
                                break;
                            case "task2":
                                lblNameOfTasks.setText("Compare global information about channels");
                                btnClear.setVisible(true);
                                task = 2;
                                break;
                            case "task3":
                                lblNameOfTasks.setText("Sort channels by their data");
                                btnClear.setVisible(true);
                                task = 3;
                                break;
                            case "task4":
                                lblNameOfTasks.setText("Media Resonance");
                                btnClear.setVisible(false);
                                task = 4;
                                break;
                            case "task5":
                                lblNameOfTasks.setText("Compare Media Resonance");
                                btnClear.setVisible(true);
                                task = 5;
                                break;
                            case "task6":
                                lblNameOfTasks.setText("Sort by Media Resonance");
                                btnClear.setVisible(true);
                                task = 6;
                                break;
                        }
                    });
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
