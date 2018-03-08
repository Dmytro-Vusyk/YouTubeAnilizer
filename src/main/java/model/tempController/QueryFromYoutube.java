package model.tempController;

import com.mashape.unirest.http.exceptions.UnirestException;
import model.tempRealization.YouTubeAPI;
import model.youTubeDataContainer.GeneralDataContainer;
import model.youTubeDataContainer.Items;
import model.youTubeDataContainer.Responce;

import java.time.Instant;
import java.util.ArrayList;

/**
 * Model logic:
 * <p>
 * - all result saves in GeneralDataContainer
 * - for set data in GeneralDataContainer:
 * 1) create variable of QueryFromYoutube class.
 * QueryFromYoutube youtubeQuery = new QueryFromYoutube();
 * 2) Make request.
 * Method setAllDataContainer(String...channelsId)
 * a) accepts channelId array,
 * b) initialize and fill array GeneralDataContainer container;
 * c) return true, if request is well, and false if not
 * 3) And get data:
 * GeneralDataContainer[] dataContainers = getContainer();
 * <p>
 * So, all data from YouTube channel are moving into dataContainer variable.
 * <p>
 * ***** All fields and methods to delete are marked with a comment // Delete
 */
public class QueryFromYoutube {
    private Responce responce; // DELETE
    // private String channelId; // DELETE
    // private String[] channelIdArray;// DELETE

    // public void setChannelId(String channelId) {
    //     this.channelId = channelId;
    // } // DELETE

    // /**
    //  * поля, що отримуються з YouTube
    //  * !!! im already dont using this - all results i writes to GeneralDataContainer.
    //  * May be, for playlists and for array of videos we will make another (FE) PlaylistContainer/VideoContainer
    //  * But, may be, we generally dont will be use fields "playList" and "videos" - they not will used in model and view
    //  */
    // private String title; // DELETE
    // private Integer viewCount; // DELETE
    // private Boolean hiddenSubscriberCount; // DELETE
    // private Integer subscriberCount; // DELETE
    // private Integer videoCount; // DELETE
    // private Instant publishedAt; // DELETE
    // private String uploads; // DELETE

    // public String getUploads() {
    //     return uploads;
    // }

    // /**
    //  * і їхні геттери
    //  */


    // public String getTitle() {
    //     return title;
    // } // DELETE

    // public Integer getViewCount() {
    //     return viewCount;
    // } // DELETE

    // public Integer getVideoCount() {
    //     return videoCount;
    // } // DELETE

    // public Integer getSubscriberCount() {
    //     return subscriberCount;
    // } // DELETE

    // public Boolean getHiddenSubscriberCount() {
    //     return hiddenSubscriberCount;
    // } // DELETE

    // public Instant getPublishedAt() {
    //     return publishedAt;
    // } // DELETE

    /**
     * головний метод - посилає запит, заповнює поля
     */

    private GeneralDataContainer[] containers;

    public GeneralDataContainer[] getContainers() {
        return containers;
    }

    public Boolean setAllDataContainers(String... channelIdArray) {
        containers = new GeneralDataContainer[channelIdArray.length];
        for (int i = 0; i < channelIdArray.length; i++) {
            try {
                Responce responce = YouTubeAPI.search(channelIdArray[i]);//this old method
                Items items = responce.items[0];
                String title = items.snippet.title;
                Integer viewCount = items.statistics.viewCount;
                Integer videoCount = items.statistics.videoCount;
                Boolean hiddenSubscriberCount = items.statistics.hiddenSubscriberCount;
                //якщо статистика прихована - статистика = 0
                Integer subscriberCount = hiddenSubscriberCount ? 0 : items.statistics.subscriberCount;
                Integer commentCount = items.statistics.commentCount;
                Instant publishedAt = items.snippet.publishedAt;
                String id = items.id; //chanel id

                String uploads = items.contentDetails.relatedPlaylists.uploads; //it is a alone playlist,
                // who contains videos that are not part of any playlist
                // I gave him this name (uploads) because it have him in the YouTube API)


                containers[i] = new GeneralDataContainer();
                containers[i].setTitle(title);
                containers[i].setViewCount(viewCount);
                containers[i].setVideoCount(videoCount);
                containers[i].setHiddenSubscriberCount(hiddenSubscriberCount);
                containers[i].setSubscriberCount(subscriberCount);
                containers[i].setPublishedAt(publishedAt);
                containers[i].setCommentCount(commentCount);
                containers[i].setUploads(uploads);//it is temporary
                containers[i].setId(id);

                //and fill a playlists[] (temporary in GeneralDataContainer)
                // 1) make new response, with new url and parameters

                responce = YouTubeAPI.searchPlaylists(
                        "https://www.googleapis.com/youtube/v3/playlists",
                        "snippet,contentDetails",
                        channelIdArray[i]);

                // 2)we get array(json) of items[], field "id" contains string of our playlist

                final int Len_PlayLists = responce.items.length;
                String plailists[] = new String[Len_PlayLists + 1];
                for (int j = 0; j < Len_PlayLists; j++) {
                    plailists[i] = responce.items[i].id;
                }

                // 3) add the array of playlists to our main container
                containers[i].setPlaylists(plailists); //look at the setter - I'm not sure about the code
                // where I'm copying the array to the array

                // 4) - i think, that the field "String[] playlists" in GeneralDataContainer we can be delete.
                //      We must just getting all video, and calculate all comments. So - you have a choice)

                // Ou eah...just one - we must added some playlist) to playlists[])
                // so....
                plailists[Len_PlayLists] = uploads;


                /*
                 * im sorry, but i not testing a getting of playlists, becouse i sleeps....good luck!)
                 */
            } catch (UnirestException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }


    public void makeQuary(GeneralDataContainer generalDataContainer, String channelId) {
        if (channelId == null) {
            System.out.println("Channel id is not assigned");
            throw new NullPointerException();
        }
        try {
            responce = YouTubeAPI.search(channelId);
            Items items = responce.items[0];
            generalDataContainer.setTitle(items.snippet.title);
            generalDataContainer.setViewCount(items.statistics.viewCount);
            generalDataContainer.setVideoCount(items.statistics.videoCount);
            generalDataContainer.setHiddenSubscriberCount(items.statistics.hiddenSubscriberCount);
            generalDataContainer.setSubscriberCount(items.statistics.subscriberCount);
            generalDataContainer.setPublishedAt(items.snippet.publishedAt);
            generalDataContainer.setUploads(items.contentDetails.relatedPlaylists.uploads);
        } catch (UnirestException e) {
            e.printStackTrace();
        }
    } //DELETE

    public void makeQueryForChennelsID(GeneralDataContainer gdc, String playListID) {
        if (playListID == null) {
            System.out.println("Channel id is not assigned");
            throw new NullPointerException();
        }
        try {
            responce = YouTubeAPI.getVideoList(gdc);

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
