package controller.generalDataController;

import com.mashape.unirest.http.exceptions.UnirestException;
import model.GeneralDataContainer;
import model.Items;
import model.Response;

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

    public void makeQuary(GeneralDataContainer gdc, String channelName) throws UnirestException {

        Response response = YouTubeAPI.searchByChannelName(
                channelName);
        Items items = response.items[0];
        gdc.setTitle(items.snippet.title);
        gdc.setPublishedAt(items.snippet.publishedAt);
        gdc.setViewCount(items.statistics.viewCount);
        gdc.setVideoCount(items.statistics.videoCount);
        gdc.setHiddenSubscriberCount(items.statistics.hiddenSubscriberCount);
        gdc.setSubscriberCount(items.statistics.subscriberCount);
        gdc.setUploads(items.contentDetails.relatedPlaylists.uploads);
        gdc.setCommentCount(calculateAllCommentCount(gdc.getUploads()));
    }

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
