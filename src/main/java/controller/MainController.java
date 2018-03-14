package controller;

import com.mashape.unirest.http.exceptions.UnirestException;
import enumerated.Comparators;
import enumerated.MapKeys;
import controller.generalDataController.QueryFromYoutube;
import model.GeneralDataContainer;

import java.util.*;

/**
 * This Controller connects Model with View
 */
public class MainController {
    private static MainController instance;

    private boolean saveCash = true;
    private boolean showTime;
    private LinkedHashMap<String, GeneralDataContainer> cache = new LinkedHashMap<>(); //1st level cache

    private QueryFromYoutube qfy = new QueryFromYoutube();

    private MainController() {
    }

    public static synchronized MainController getInstance() {
        if (instance == null) {
            synchronized (MainController.class) {
                if (instance == null) {
                    instance = new MainController();
                }
            }
        }
        return instance;
    }
//====================================== get list with data of channels ===================================

    public long showTimeMeasurement(long start, long stop) {
        return (stop - start);
    }

    /**
     * This method collects information about Channel and return it as HashMap<MapKeys,String>
     * where Keys are from Enum MapKeys and string - are values of the fields of Channel
     * **all without comment count
     *
     * @param channelId ChannelName
     * @return HashMap where MapKeys are from Enum and String - are channel data in
     * String form
     */
    public LinkedHashMap<MapKeys, String> showBaseInformationAboutChannel(String channelId) {
        GeneralDataContainer gdc = makeBaseRequest(channelId);
        return showGlobalInformationAboutChannel(gdc);
    }

    /**
     * This method collects information about Array of Channels and return it as ArrayList<HashMap<MapKeys,String>>
     * where Keys are from Enum MapKeys and string - are values of the fields of Channel
     * **all without comment count
     *
     * @param channelId ChannelName
     * @return HashMap where MapKeys are from Enum and String - are channel data in
     * String form
     */
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
     * with comment count
     *
     * @param channelId ChannelName[]
     * @return HashMap where MapKeys are from Enum and String - are channel data in
     * String form
     */
    public LinkedHashMap<MapKeys, String> showGlobalInformationAboutChannel(String channelId) {
        GeneralDataContainer gdc = makeGlobalRequest(channelId);
        return showGlobalInformationAboutChannel(gdc);
    }

    /**
     * This method collects information about Channel and return it as ArrayList<HashMap<MapKeys,String>>
     * where Keys are from Enum MapKeys and string - are values of the fields of Channel
     * with comment count
     *
     * @param channelId ChannelName[]
     * @return HashMap where MapKeys are from Enum and String - are channel data in
     * String form
     */
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
    //in our program we using standard sorting of object in table >> View
    // i just left this method to show you that we can make comparators

    /**
     * This method sort Channels by comparator "Comparators"
     *
     * @param idArray    array with id's of channels
     * @param comparator type of comparator
     * @return sorted ArrayList
     */
    public ArrayList<LinkedHashMap<MapKeys, String>> sortChannels(String[] idArray, Comparators comparator) {
        //output ArrayList
        ArrayList<LinkedHashMap<MapKeys, String>> output = new ArrayList<>();
        //Array list for founded channels
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
     * Parse GeneralDataContainer object to String values in LinkedHashMap
     *
     * @param gdc GeneralDataContainer object
     * @return LinkedMap with Enum MapKeys and string values of GDC fields
     */
    private LinkedHashMap<MapKeys, String> showGlobalInformationAboutChannel(GeneralDataContainer gdc) {
        LinkedHashMap<MapKeys, String> output = new LinkedHashMap<>();
        if (gdc.getTitle() == null) return output;
        output.put(MapKeys.CHANNEL_NAME, gdc.getTitle());
        output.put(MapKeys.PUBLISHING_DATE, gdc.getPublishedAt().toString());
        output.put(MapKeys.SUBSCRIBERS_COUNT, gdc.getSubscriberCount().toString());
        output.put(MapKeys.VIDEOS_COUNT, gdc.getVideoCount().toString());
        output.put(MapKeys.VIEWS_COUNT, gdc.getViewCount().toString());
        output.put(MapKeys.CHANNEL_ID, gdc.getId());
        if (gdc.getCommentCount() != null)
            output.put(MapKeys.COMMENTS_COUNT, gdc.getCommentCount().toString());
        return output;
    }

    /**
     * This method take channelId and make request for data in cache and youtube
     * without all comments count
     *
     * @param channelId channel id
     * @return GeneralDataContainer object
     */
    private GeneralDataContainer makeBaseRequest(String channelId) {
        GeneralDataContainer gdc;

            gdc = checkIdInCash(channelId);

        try {
            qfy.makeBaseQuery(gdc, channelId);
        } catch (UnirestException e) {
            e.printStackTrace();
        } catch (IndexOutOfBoundsException i) {
            printError();
        }


        if (gdc != null) {
            cache.put(channelId, gdc);
        }

        return gdc;
    }

    /**
     * This method take channelId and make request for data in cache and youtube
     * with comments count
     *
     * @param channelId channel id
     * @return GeneralDataContainer objec
     */
    private GeneralDataContainer makeGlobalRequest(String channelId) {
        GeneralDataContainer gdc;

            gdc = checkIdInCash(channelId);

        if (gdc != null && gdc.getCommentCount() == null) {
            try {
                qfy.makeQuery(gdc, channelId);
            } catch (UnirestException e) {
                e.printStackTrace();
            } catch (IndexOutOfBoundsException i) {
                printError();
            }
        }

        if (gdc != null) {
            cache.put(channelId, gdc);
        }

        return gdc;
    }

    /**
     * Check if Object with channel id is in cache
     *
     * @param channelId channel ID
     * @return GeneralDataContainer object;
     */
    private GeneralDataContainer checkIdInCash(String channelId) {
        if (cache.keySet().contains(channelId)) {
            return cache.get(channelId);
        }
        return new GeneralDataContainer();
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

    public void setCache(LinkedHashMap<String, GeneralDataContainer> cache) {
        this.cache = cache;
    }

    public LinkedHashMap<String, GeneralDataContainer> getCache() {
        return cache;
    }
}
