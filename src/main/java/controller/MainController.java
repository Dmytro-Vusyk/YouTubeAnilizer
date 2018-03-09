package controller;

import enumerated.MapKeys;
import model.tempController.QueryFromYoutube;
import model.youTubeDataContainer.GeneralDataContainer;

import java.util.*;

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
        qfy.makeQuary(gdc,channelId);
    }

    /**
     * This method collects information about Channel and return it as HashMap<MapKeys,String>
     * where Keys are from Enum MapKeys and string - are values of the fields of Channel
     *
     * @param channelId
     * @return HashMap<MapKeys, String> where MapKeys are from Enum and String - are channel data in
     * String form
     */
    public LinkedHashMap<MapKeys, String> showGlobalInformationAboutChannel(String channelId) {
        makeRequest(channelId);
        LinkedHashMap<MapKeys, String> output = new LinkedHashMap<>();
        output.put(MapKeys.CHANNEL_NAME, gdc.getTitle());
        output.put(MapKeys.COMMENTS_COUNT, gdc.getCommentCount().toString());
        output.put(MapKeys.PUBLISHING_DATE, gdc.getPublishedAt().toString());
        output.put(MapKeys.SUBSCRIBERS_COUNT, gdc.getSubscriberCount().toString());
        output.put(MapKeys.VIDEOS_COUNT, gdc.getVideoCount().toString());
        output.put(MapKeys.VIEWS_COUNT, gdc.getViewCount().toString());
        return output;
    }

    /**
     * This method collects information about Channel and return it as HashMap<MapKeys,String>
     * where Keys are from Enum MapKeys and string - are values of the fields of Channel
     *
     * @param gdc General Data Container object
     * @return HashMap<MapKeys, String> where MapKeys are from Enum and String - are channel data in
     * String form
     */
    private LinkedHashMap<MapKeys, String> showGlobalInformationAboutChannel(GeneralDataContainer gdc) {
        LinkedHashMap<MapKeys, String> output = new LinkedHashMap<>();
        output.put(MapKeys.CHANNEL_NAME, gdc.getTitle());
        output.put(MapKeys.COMMENTS_COUNT, gdc.getCommentCount().toString());
        output.put(MapKeys.PUBLISHING_DATE, gdc.getPublishedAt().toString());
        output.put(MapKeys.SUBSCRIBERS_COUNT, gdc.getSubscriberCount().toString());
        output.put(MapKeys.VIDEOS_COUNT, gdc.getVideoCount().toString());
        output.put(MapKeys.VIEWS_COUNT, gdc.getViewCount().toString());
        return output;
    }

    /**
     * This method sort array of channels in order:
     * - Channel name
     * - Channel publishing date
     * - Subscribers count
     * - Videos count
     * - Views count
     *
     * @param idArray array of String with channel's id's
     * @return sorted ArrayList of channels
     */
    public ArrayList<LinkedHashMap<MapKeys,String>> sortChannels(String[] idArray) {

        ArrayList<LinkedHashMap<MapKeys,String>> output = new ArrayList<>();

     //  ArrayList<GeneralDataContainer> gdcArrayList =
     //           new ArrayList<>(Arrays.asList(qfy.setAllDataContainers(idArray)));

        Comparator<GeneralDataContainer> containerComparator = (o1, o2) -> {
            if (o1.getTitle().compareToIgnoreCase(o2.getTitle()) == 0) {
                if (o1.getPublishedAt().compareTo(o2.getPublishedAt()) == 0) {
                    if (o1.getSubscriberCount().compareTo(o2.getSubscriberCount()) == 0) {
                        if (o1.getVideoCount().compareTo(o2.getVideoCount()) == 0) {
                            if (o1.getViewCount().compareTo(o2.getViewCount()) == 0) {
                                return 1;
                            }
                        }
                    }
                }
            }
            return o1.getTitle().compareToIgnoreCase(o2.getTitle());
        };

        //gdcArrayList.sort(containerComparator);

        //for (GeneralDataContainer gdc: gdcArrayList) {
        //    output.add(showGlobalInformationAboutChannel(gdc));
        //}

        return output;
    }


    public HashMap<MapKeys, String> showMediaResonance(String channelId){

        return null;
    }

    public LinkedHashMap<MapKeys, String> tesTshowGlobalInformationAboutChannel(String channelId) {
        makeRequest(channelId);
        LinkedHashMap<MapKeys, String> output = new LinkedHashMap<>();
        output.put(MapKeys.CHANNEL_NAME, "ChannelName");
        output.put(MapKeys.COMMENTS_COUNT, "100500");
        output.put(MapKeys.PUBLISHING_DATE, "Tyt Data");
        output.put(MapKeys.SUBSCRIBERS_COUNT, "101");
        output.put(MapKeys.VIDEOS_COUNT, "1000000");
        output.put(MapKeys.VIEWS_COUNT, "101010101");
        return output;
    }
}
