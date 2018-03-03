package model.tempController;

import com.mashape.unirest.http.exceptions.UnirestException;
import model.tempRealization.YouTubeAPI;
import model.youTubeDataContainer.*;

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
 * ============================================================
 * задачі:
 * -переробити логіку класу, щоб в конструктор передавалось channel id і відразу виконувався makeQuery()
 *  (**в цьому невеличеому питанні хочу порадитись - а поки не завершу реалізацію повністю так і залишу -
 *   можливо робити все відразу в конструкторі буде недоцільно)
 *
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
     */
    private String title;
    private Integer viewCount;
    private Boolean hiddenSubscriberCount;
    private Integer subscriberCount;
    private Integer videoCount;
    private Instant publishedAt;

    /**
     * і їхні геттери
     */
    public String getTitle() {
        if (title == null) System.out.println("Channel title is null or there was any mistake, try another id");
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
     */


    private GeneralDataContainer[] containers;
    public GeneralDataContainer[] getAllDataContainers(String ...channelIdArray){
        containers = new GeneralDataContainer[channelIdArray.length];
        for (int i = 0; i < channelIdArray.length; i++) {
            try {
                Responce responce = YouTubeAPI.search(channelIdArray[i]);
                Items<Snippet, Statistics> items = responce.items[0];
                String title = items.snippet.title;
                Integer viewCount = items.statistics.viewCount;
                Integer videoCount = items.statistics.videoCount;
                Boolean hiddenSubscriberCount = items.statistics.hiddenSubscriberCount;
                //якщо статистика прихована - статистика = 0
                Integer subscriberCount = hiddenSubscriberCount ? 0 : items.statistics.subscriberCount;
                Instant publishedAt = items.snippet.publishedAt;
                containers[i] = new GeneralDataContainer();
                containers[i].setTitle(title);
                containers[i].setViewCount(viewCount);
                containers[i].setVideoCount(videoCount);
                containers[i].setHiddenSubscriberCount(hiddenSubscriberCount);
                containers[i].setSubscriberCount(subscriberCount);
                containers[i].setPublishedAt(publishedAt);
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
            Items<Snippet, Statistics> items = responce.items[0];
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
