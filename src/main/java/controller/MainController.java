package controller;

import model.tempController.QueryFromYoutube;

/**
 * <code>MainController</code> використовуєтся для збіра данних з мережі чи кешу та відправки їх у <code>View</code>
 */
public class MainController {

    private String channelId;

    /**
     * обьект який надходить з мережі чи кешу
     */
    private QueryFromYoutube qfy = new QueryFromYoutube();


    public MainController() {
    }

    /**
     * This method take channelId and make request for data in cash and youtube
     */
    public void makeRequest(String channelId) {
        //TO DO: add realisation of cash reading
        qfy.setChannelId(channelId);
        qfy.makeQuary();
    }

    /**
     * @return channel title as String
     */
    public String getChannelName() {
        return qfy.getTitle();
    }

    /**
     * @return count of views from all of videos on the channel as String
     */
    public String getViewCount() {
        return qfy.getViewCount().toString();
    }

    /**
     * @return count of videos on the channel as String
     */
    public String getVideoCount() {
        return qfy.getVideoCount().toString();
    }

    /**
     * This method returns the count of subscribers on the channel
     * if subscribers are hidden - method returns the message "Subscriber are hidden"
     *
     * @return count of subscriber on the channel
     */
    public String getSubscriberCount() {
        if (qfy.getHiddenSubscriberCount()) {
            return "Subscriber are hidden";
        }
        return qfy.getSubscriberCount().toString();
    }

    /**
     * This method returns the publishing date of channel
     * @return publishing date as String
     */
    public String getPublishingDate() {
        return qfy.getPublishedAt().toString();
    }


}
