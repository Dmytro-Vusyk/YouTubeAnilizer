package controller;

import com.mashape.unirest.http.exceptions.UnirestException;
//import com.sun.tools.doclets.formats.html.SourceToHTMLConverter;
import enumerated.MapKeys;
import controller.generalDataController.QueryFromYoutube;
import model.GeneralDataContainer;

import java.util.*;

/**
 * This Controller connects Model with View
 */
public class MainController {

    private QueryFromYoutube qfy = new QueryFromYoutube();

    public MainController() {
    }

//====================================== get list with data of channels ===================================
    /**
     * This method collects information about Channel and return it as HashMap<MapKeys,String>
     * where Keys are from Enum MapKeys and string - are values of the fields of Channel
     * **all without comment count
     * **comment count calculate and initialize in
     * showGlobalInformationAboutChannel() -> makeGlobalRequest() -> queryFromYoutube.makeQuery() -> calculateAllComment()
     * @param channelId ChannelName
     * @return HashMap where MapKeys are from Enum and String - are channel data in
     * String form
     */
    public LinkedHashMap<MapKeys, String> showBaseInformationAboutChannel(String channelId){
        GeneralDataContainer gdc = makeBaseRequest(channelId);
        return showGlobalInformationAboutChannel(gdc);
    }
    // + Array
    public ArrayList<LinkedHashMap<MapKeys, String>> showBaseInformationAboutChannel(String...channelId){
        final int LENGTH_CHANNEL = channelId.length;
        ArrayList<LinkedHashMap<MapKeys, String>> channelsDataArray = new ArrayList<>(LENGTH_CHANNEL);
        for (int i = 0; i < LENGTH_CHANNEL; i++) {
            GeneralDataContainer gdc = makeBaseRequest(channelId[i]);
            LinkedHashMap<MapKeys, String> linkedHashMap = showGlobalInformationAboutChannel(gdc);
            channelsDataArray.add(linkedHashMap);
        }
        return channelsDataArray;
    }
    /**
     * This method collects information about Channel and return it as HashMap<MapKeys,String>
     * where Keys are from Enum MapKeys and string - are values of the fields of Channel
     *
     * @param channelId ChannelName
     * @return HashMap where MapKeys are from Enum and String - are channel data in
     * String form
     */


    public LinkedHashMap<MapKeys, String> showGlobalInformationAboutChannel(String channelId) {
        GeneralDataContainer gdc = makeGlobalRequest(channelId);
        return showGlobalInformationAboutChannel(gdc);
    }

    // + Array
    public ArrayList<LinkedHashMap<MapKeys, String>> showGlobalInformationAboutChannel(String...channelId) {
        final int LENGTH_CHANNEL = channelId.length;
        ArrayList<LinkedHashMap<MapKeys, String>> channelsDataArray = new ArrayList<>(LENGTH_CHANNEL);
        for (int i = 0; i < LENGTH_CHANNEL; i++) {
            GeneralDataContainer gdc = makeGlobalRequest(channelId[i]);
            LinkedHashMap<MapKeys, String> linkedHashMap = showGlobalInformationAboutChannel(gdc);
            channelsDataArray.add(linkedHashMap);
        }
        return channelsDataArray;
    }


//=========================================== Sort ============================================

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
    public ArrayList<LinkedHashMap<MapKeys, String>> sortChannels(String[] idArray) {
        ArrayList<LinkedHashMap<MapKeys, String>> output = new ArrayList<>();
        ArrayList<GeneralDataContainer> channelsList = new ArrayList<>();
        for (int i = 0; i < idArray.length; i++) {
            GeneralDataContainer gdc = makeGlobalRequest(idArray[i]);
            if (gdc.getTitle() != null) {
                channelsList.add(gdc);
            }
        }

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
        channelsList.sort(containerComparator);

        for (GeneralDataContainer gdc :
                channelsList) { output.add(showGlobalInformationAboutChannel(gdc));}
        return output;
    }

    /**
     * This method sort array of channels by comment count
     *
     * @param idArray array of String with channel's id's
     * @return sorted ArrayList of channels
     */
    public ArrayList<LinkedHashMap<MapKeys, String>> sortChannelsByMediaResonance(String[] idArray) {
        ArrayList<LinkedHashMap<MapKeys, String>> output = new ArrayList<>();
        ArrayList<GeneralDataContainer> channelsList = arrayGDCmaker(idArray);

        Comparator<GeneralDataContainer> containerComparator = Comparator.comparing(GeneralDataContainer::getCommentCount);
        channelsList.sort(containerComparator);
        for (GeneralDataContainer gdc : channelsList) {
            output.add(showGlobalInformationAboutChannel(gdc));
        }

        return output;
    }

    public String printError(){
        return "Wrong input";
    }
//==============================================================================================
//--------------------------------------------private ------------------------------------------

    /**
     * put all data from gdc into linkedMap
     * @param gdc
     * @return LinkedMap
     */
    private LinkedHashMap<MapKeys, String> showGlobalInformationAboutChannel(GeneralDataContainer gdc) {
        LinkedHashMap<MapKeys, String> output = new LinkedHashMap<>();
        try {
            output.put(MapKeys.CHANNEL_NAME, gdc.getTitle());
            output.put(MapKeys.PUBLISHING_DATE, gdc.getPublishedAt().toString());
            output.put(MapKeys.SUBSCRIBERS_COUNT, gdc.getSubscriberCount().toString());
            output.put(MapKeys.VIDEOS_COUNT, gdc.getVideoCount().toString());
            output.put(MapKeys.VIEWS_COUNT, gdc.getViewCount().toString());
            output.put(MapKeys.COMMENTS_COUNT, gdc.getCommentCount().toString());
            output.put(MapKeys.CHANNEL_ID, gdc.getId());
        } catch (NullPointerException e) {}
        return output;
    }

    /**
     * accepts
     * @param idArray - array of channel id.
     *
     * make request, and
     * @return ArrayList with GeneralDataContainer data
     */
    private ArrayList<GeneralDataContainer> arrayGDCmaker(String[] idArray){
        ArrayList<GeneralDataContainer> channelsList = new ArrayList<>();
        for (int i = 0; i < idArray.length; i++) {
            GeneralDataContainer gdc = makeGlobalRequest(idArray[i]);
            if (gdc.getTitle() != null) {
                channelsList.add(gdc);
            }
        }
        return channelsList;
    }

    /**
     * This method take channelId and make request for data in cash and youtube
     */
    private GeneralDataContainer makeBaseRequest(String channelId)  {
        GeneralDataContainer gdc = new GeneralDataContainer();
        try {
            qfy.makeBaseQuery(gdc, channelId);
        } catch (UnirestException e) {
            e.printStackTrace();
        } catch (IndexOutOfBoundsException i){
            printError();
        }
        return gdc;
    }


    private GeneralDataContainer makeGlobalRequest(String channelId) {
        GeneralDataContainer gdc = new GeneralDataContainer();
        try {
            qfy.makeQuery(gdc, channelId);
        } catch (UnirestException e) {
            e.printStackTrace();
        } catch (IndexOutOfBoundsException i){
            printError();
        }
        return gdc;
    }
}
