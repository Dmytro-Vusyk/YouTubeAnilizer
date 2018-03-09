package model.tempController;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.sun.org.apache.regexp.internal.RE;
import enumerated.TypeQuery;
import model.tempRealization.YouTubeAPI;
import model.youTubeDataContainer.GeneralDataContainer;
import model.youTubeDataContainer.Items;
import model.youTubeDataContainer.Response;

import java.util.ArrayList;

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

    public void makeQuary(GeneralDataContainer generalDataContainer, String channelId) {
        try {
            Response response = YouTubeAPI.search(
                    TypeQuery.CHANNEL,
                    "snippet,contentDetails,statistics",
                    channelId);
            Items items = response.items[0];
            generalDataContainer.setTitle(items.snippet.title);
            generalDataContainer.setViewCount(items.statistics.viewCount);
            generalDataContainer.setVideoCount(items.statistics.videoCount);
            generalDataContainer.setHiddenSubscriberCount(items.statistics.hiddenSubscriberCount);
            generalDataContainer.setSubscriberCount(items.statistics.subscriberCount);
            generalDataContainer.setPublishedAt(items.snippet.publishedAt);
            generalDataContainer.setUploads(items.contentDetails.relatedPlaylists.uploads);
//set List with videos id (used uploads)
            setVideosIds(generalDataContainer);
//init and set comment count
            int countId = getAllCommentCount(generalDataContainer.getVideoIds());
            generalDataContainer.setCommentCount(countId);
        } catch (UnirestException e) {
            e.printStackTrace();
        }
    }

    private int getAllCommentCount(String...videosId) throws UnirestException {
        int count = 0;
        for (String vid:
                videosId) {
            Response response = YouTubeAPI.search(
                    TypeQuery.VIDEO,
                    "contentDetails",
                    vid
            );
            count += response.items[0].statistics.commentCount;
        }
        return count;
    }
    private void setVideosIds(GeneralDataContainer gdc) {
        try {
            Response responce = YouTubeAPI.searchVideoIdsInPlayList(
                    "contentDetails",
                    gdc.getUploads(),50);
            ArrayList<String> videoIds = new ArrayList<>();
            Items[] items = responce.items;
            for (int i = 0; i < items.length; i++) {
                videoIds.add(items[i].contentDetails.videoId);
            }
            gdc.setVideoIds(videoIds.toArray(new String[videoIds.size()]));
        } catch (UnirestException e) {
            e.printStackTrace();
        }
    }

}
