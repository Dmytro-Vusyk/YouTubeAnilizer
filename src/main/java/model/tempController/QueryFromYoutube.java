package model.tempController;

import com.mashape.unirest.http.exceptions.UnirestException;
import model.tempRealization.YouTubeAPI;
import model.youTubeDataContainer.GeneralDataContainer;
import model.youTubeDataContainer.Items;
import model.youTubeDataContainer.Responce;

import java.time.Instant;

/**
 * Тимчасова реалізація класу, який реалізує запити.
 *
 * для роботи з класом необхідно
 * 1) встановити поле
 * @channelId методом setChannelId
 *
 * 2) метод makeQuery виконує запит, створює "контейнер класів" (парсить json) в пакеті youTubeDataContainer
 *   і запонює поля цього (QueryFromYoutube) класу для подальшого використання.
 *
 * 3) наприклад метод getTitle() повертає поле title (назва каналу)
 *
 * + додав метод getGeneralDataContainer(String...channelsId) який повертає масив "контейнерів"
 * ============================================================
 * задачі:
 * -переробити логіку класу, щоб в конструктор передавалось channel id і відразу виконувався makeQuery()
 *  (**в цьому невеличеому питанні хочу порадитись - а поки не завершу реалізацію повністю так і залишу -
 *   можливо робити все відразу в конструкторі буде недоцільно)
 * - залишити одну реалізацію запиту і заповнення. І для запиту 1 айді і для масиву
 */
public class QueryFromYoutube {
    /**
     * "головне" поле. Встановлюється в методі makeQuery(). (краще зробити локальним)
     */
    private Responce responce;

    /**
     * поле
     * @channelId для роботи з класом повинно обов’язково
     * ініціалізуватись - тому впевнений, що вбудую в конструктор
     */
    private String channelId;
    private String[] channelIdArray;
    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    /**
     * поля, що отримуються з YouTube
     * !!! im already dont using this - all results i writes to GeneralDataContainer.
     *     May be, for playlists and for array of videos we will make another (FE) PlaylistContainer/VideoContainer
     *     But, may be, we generally dont will be use fields "playList" and "videos" - they not will used in model and view
     */
    private String title;
    private Integer viewCount;
    private Boolean hiddenSubscriberCount;
    private Integer subscriberCount;
    private Integer videoCount;
    private Instant publishedAt;
    private String uploads;

    /**
     * і їхні геттери
     */
    public String getTitle() {
        return title;
    }
    public Integer getViewCount() {
        return viewCount;
    }
    public Integer getVideoCount() {
        return videoCount;
    }
    public Integer getSubscriberCount() {
        return subscriberCount;
    }
    public Boolean getHiddenSubscriberCount() {
        return hiddenSubscriberCount;
    }
    public Instant getPublishedAt() {
        return publishedAt;
    }

    /**
     * головний метод - посилає запит, заповнює поля
     * @throws NullPointerException у випадку, коли поле channelId == null
     *
     */


    private GeneralDataContainer[] containers;
    public GeneralDataContainer[] getAllDataContainers(String ...channelIdArray){
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
                return null;
            }
        }
        return containers;
    }

     public void makeQuary(){
        if (channelId == null) {
            System.out.println("Channel id is not assigned");
            throw new NullPointerException();
        }
        try {
            responce = YouTubeAPI.search(channelId);
            Items items = responce.items[0];
            title = items.snippet.title;
            viewCount = items.statistics.viewCount;
            videoCount = items.statistics.videoCount;
            hiddenSubscriberCount = items.statistics.hiddenSubscriberCount;
            //якщо статистика прихована - статистика = 0
            subscriberCount = hiddenSubscriberCount ? 0 : items.statistics.subscriberCount;
            publishedAt = items.snippet.publishedAt;
        } catch (UnirestException e) {
            e.printStackTrace();
        }
    }

}
