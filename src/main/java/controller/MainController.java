package controller;

import enumerated.MapKeys;
import model.tempController.QueryFromYoutube;
import model.youTubeDataContainer.GeneralDataContainer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

//TO DO: add realisation of cash reading in method makeRequest();


/**
 * This Controller connects Model with View
 */
public class MainController {

    private QueryFromYoutube qfy = new QueryFromYoutube();
    private GeneralDataContainer gdc = new GeneralDataContainer();

    public MainController() {
    }

    /**
     * This method take channelId and make request for data in cash and youtube
     */
    private void makeRequest(String channelId) {

        qfy.setChannelId(channelId);
        qfy.makeQuary();
    }

    /**
     * This method collects information about Channel and return it as HashMap<MapKeys,String>
     * where Keys are from Enum MapKeys and string - are values of the fields of Channel
     *
     * @param channelId
     * @return
     */
    public HashMap<MapKeys, String> showGlobalInformationAboutChannel(String channelId) {
        makeRequest(channelId);
        HashMap<MapKeys, String> output = new HashMap<>();
        output.put(MapKeys.CHANNEL_NAME, gdc.getTitle());
        output.put(MapKeys.COMMENTS_COUNT, gdc.getCommentCount().toString());
        output.put(MapKeys.PUBLISHING_DATE, gdc.getPublishedAt().toString());
        output.put(MapKeys.SUBSCRIBERS_COUNT, gdc.getSubscriberCount().toString());
        output.put(MapKeys.VIDEOS_COUNT, gdc.getVideoCount().toString());
        output.put(MapKeys.VIEWS_COUNT, gdc.getViewCount().toString());
        return output;
    }

    public ArrayList<GeneralDataContainer> sortChannels(String[] idArray) {
        ArrayList<GeneralDataContainer> gdcArrayList = new ArrayList<>(Arrays.asList(qfy.getAllDataContainers(idArray)));

        Comparator<GeneralDataContainer> containerComparator = (o1, o2) -> {
            if (o1.getTitle().compareToIgnoreCase(o2.getTitle()) == 0) {
                if (o1.getPublishedAt().compareTo(o2.getPublishedAt()) == 0) {
                    if (o1.getSubscriberCount().compareTo(o2.getSubscriberCount()) == 0) {
                        if (o1.getVideoCount().compareTo(o2.getVideoCount()) == 0) {
                            if(o1.getViewCount().compareTo(o2.getViewCount()) == 0){
                                return 1;
                            }
                        }
                    }
                }
            }
            return o1.getTitle().compareToIgnoreCase(o2.getTitle());
        };

        gdcArrayList.sort(containerComparator);
        return gdcArrayList;
    }
}
