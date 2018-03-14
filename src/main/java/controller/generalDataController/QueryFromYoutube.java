package controller.generalDataController;

import com.mashape.unirest.http.exceptions.UnirestException;
import model.Items;
import model.Response;
import model.GeneralDataContainer;

/**
 * Model logic:
 * <p>
 * - all result saves in GeneralDataContainer
 * - for set data in GeneralDataContainer:
 * 1) create variable of GeneralDataContainer class.
 * 2) pass him into method makeQuery(GeneralDataContainer gdc, String channelId)
 * Method makeQuery(GeneralDataContainer gdc, String channelId) initialize and fill all fields of gdc
 */
public class QueryFromYoutube {


    /**
     * Creates a request for basic information about Channel
     * - title
     * - publishing date
     * - views count
     * - videos count
     * - subscriber count
     *
     * @param gdc       object of GeneralDataContainer
     * @param channelId channel ID
     * @throws UnirestException
     */
    public void makeBaseQuery(GeneralDataContainer gdc, String channelId) throws UnirestException {
        Response response = YouTubeAPI.searchByChannelId(channelId);
        Items items = response.items[0];
        gdc.setTitle(items.snippet.title);
        gdc.setPublishedAt(items.snippet.publishedAt);
        gdc.setViewCount(items.statistics.viewCount);
        gdc.setVideoCount(items.statistics.videoCount);
        gdc.setHiddenSubscriberCount(items.statistics.hiddenSubscriberCount);
        gdc.setSubscriberCount(items.statistics.subscriberCount);
        gdc.setUploads(items.contentDetails.relatedPlaylists.uploads);
        gdc.setId(response.items[0].id);
    }

    /**
     * Creates a request for basic information about Channel with comment count
     *
     * @param gdc       General Data Container object
     * @param channelId channel ID
     * @throws UnirestException
     */
    public void makeQuery(GeneralDataContainer gdc, String channelId) throws UnirestException {
        makeBaseQuery(gdc, channelId);
        gdc.setCommentCount(calculateAllCommentCount(gdc.getUploads()));
    }

    /**
     * Takes a String with Video IDs and calculates comment count
     *
     * @param videosId String with videos ID in form "id,id,id,id....id"
     * @return int count of comment until the videos
     * @throws UnirestException
     */
    private int calculateCommentCount(String videosId) throws UnirestException {

        Response response = YouTubeAPI.searchByVideoId(videosId);
        int count = 0;
        Items[] items = response.items;
        for (int i = 0; i < items.length; i++) {
            if (items[i] != null && items[i].statistics.commentCount != null) {
                count += items[i].statistics.commentCount;
            }
        }
        return count;
    }


    /**
     * This method calculate count of comments until all of videos
     *
     * @param playlistId playlist ID
     * @return int count of all comments on the channel
     * @throws UnirestException
     */
    private int calculateAllCommentCount(String playlistId) throws UnirestException {

        String nextPage = "";

        int count = 0;

        do {
            Response response = YouTubeAPI.searchPlayListByIdAndPageToken(playlistId, nextPage);
            Items[] items = response.items;
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < items.length; i++) {
                sb.append(items[i].contentDetails.videoId).append(" ");
            }
            String videosId = sb.toString().replaceAll(" ", ",");
            count += calculateCommentCount(videosId);
            nextPage = response.nextPageToken;

        } while (nextPage != null);

        return count;
    }

}
