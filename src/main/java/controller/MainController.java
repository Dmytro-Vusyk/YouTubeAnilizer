package controller;

import com.mashape.unirest.http.exceptions.UnirestException;
//import com.sun.tools.doclets.formats.html.SourceToHTMLConverter;
import enumerated.Comparators;
import enumerated.MapKeys;
import controller.generalDataController.QueryFromYoutube;
import model.GeneralDataContainer;

import java.util.*;

/**
 * This Controller connects Model with View
 */
public class MainController {

    private boolean saveCash;
    private boolean showTime;
    private LinkedHashMap<String, GeneralDataContainer> cash = new LinkedHashMap<>(); //1st level cash

    public LinkedHashMap<String, GeneralDataContainer> getCash() {
        return cash;
    }

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
     *
     * @param channelId ChannelName
     * @return HashMap where MapKeys are from Enum and String - are channel data in
     * String form
     */
    public LinkedHashMap<MapKeys, String> showBaseInformationAboutChannel(String channelId) {
        GeneralDataContainer gdc = makeBaseRequest(channelId);
        return showGlobalInformationAboutChannel(gdc);
    }

    // + Array
    public ArrayList<LinkedHashMap<MapKeys, String>> showBaseInformationAboutChannel(String... channelId) {
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
    public ArrayList<LinkedHashMap<MapKeys, String>> showGlobalInformationAboutChannels(String... channelId) {
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
     * This method compare GDC objects
     *
     * @param idArray    array with id
     * @param comparator
     * @return
     */
    public ArrayList<LinkedHashMap<MapKeys, String>> sortChannels(String[] idArray, Comparators comparator) {
        //output ArrayList
        ArrayList<LinkedHashMap<MapKeys, String>> output = new ArrayList<>();
        //Array list for founded
        ArrayList<GeneralDataContainer> channelsList = new ArrayList<>();
        for (int i = 0; i < idArray.length; i++) {
            GeneralDataContainer gdc = makeGlobalRequest(idArray[i]);
            if (gdc.getTitle() != null) {
                channelsList.add(gdc);
            }
        }
        Comparator<GeneralDataContainer> containerComparator;

        switch (comparator) {
            case CHANNEL_NAME:
                containerComparator = Comparator.comparing(GeneralDataContainer::getTitle);
                break;
            case PUBLISHING_DATE:
                containerComparator = Comparator.comparing(GeneralDataContainer::getPublishedAt);
                break;
            case SUBSCRIBERS_COUNT:
                containerComparator = Comparator.comparing(GeneralDataContainer::getSubscriberCount);
                break;
            case VIDEOS_COUNT:
                containerComparator = Comparator.comparing(GeneralDataContainer::getVideoCount);
                break;
            case VIEWS_COUNT:
                containerComparator = Comparator.comparing(GeneralDataContainer::getViewCount);
                break;
            case COMMENTS_COUNT:
                containerComparator = Comparator.comparing(GeneralDataContainer::getCommentCount);
                break;
            default:
                containerComparator = Comparator.comparing(GeneralDataContainer::getTitle);
                break;
        }

        channelsList.sort(containerComparator);

        for (GeneralDataContainer gdc :
                channelsList) {
            output.add(showGlobalInformationAboutChannel(gdc));
        }
        return output;
    }


    public String printError() {
        return "Wrong input";
    }
//==============================================================================================
//--------------------------------------------private ------------------------------------------

    /**
     * put all data from gdc into linkedMap
     *
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
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return output;
    }

    /**
     * accepts
     *
     * @param idArray - array of channel id.
     *                <p>
     *                make request, and
     * @return ArrayList with GeneralDataContainer data
     */
    private ArrayList<GeneralDataContainer> arrayGDCmaker(String[] idArray) {
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
    private GeneralDataContainer makeBaseRequest(String channelId) {
        GeneralDataContainer gdc;
        if (saveCash) {
            gdc = checkIdInCash(channelId);
        } else {
            gdc = new GeneralDataContainer();
        }
        try {
            qfy.makeBaseQuery(gdc, channelId);
        } catch (UnirestException e) {
            e.printStackTrace();
        } catch (IndexOutOfBoundsException i) {
            printError();
        }


        if (saveCash && gdc != null) {
            cash.put(channelId, gdc);
        }

        return gdc;
    }


    private GeneralDataContainer makeGlobalRequest(String channelId) {
        GeneralDataContainer gdc;
        if (saveCash) {
            gdc = checkIdInCash(channelId);
        } else {
            gdc = new GeneralDataContainer();
        }
        if (gdc != null && gdc.getCommentCount() == null) {
            try {
                qfy.makeQuery(gdc, channelId);
            } catch (UnirestException e) {
                e.printStackTrace();
            } catch (IndexOutOfBoundsException i) {
                printError();
            }
        }

        if (saveCash) {
            cash.put(channelId, gdc);
        }

        return gdc;
    }

    /**
     * Check if Object with channel id is in cash
     *
     * @param channelId channel ID
     * @return GeneralDataContainer;
     */
    private GeneralDataContainer checkIdInCash(String channelId) {
        if (cash.keySet().contains(channelId)) {
            return cash.get(channelId);
        }
        return null;
    }
    //=========================================== Getters Setters ============================================


    public boolean isSaveCash() {
        return saveCash;
    }

    public void setSaveCash(boolean saveCash) {
        this.saveCash = saveCash;
    }

    public boolean isShowTime() {
        return showTime;
    }

    public void setShowTime(boolean showTime) {
        this.showTime = showTime;
    }
}
