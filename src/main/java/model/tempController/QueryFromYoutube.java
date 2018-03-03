package model.tempController;

import com.mashape.unirest.http.exceptions.UnirestException;
import model.tempRealization.YouTubeAPI;
import model.youTubeDataContainer.Items;
import model.youTubeDataContainer.Responce;
import model.youTubeDataContainer.Snippet;
import model.youTubeDataContainer.Statistics;

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
