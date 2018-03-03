package model.tempController;

import com.mashape.unirest.http.exceptions.UnirestException;
import model.tempRealization.YouTubeAPI;
import model.youTubeDataContainer.Responce;

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
 * 3) метод getTitle() повертає поле title (назва каналу)
 *
 * ============================================================
 * задачі:
 * -переробити логіку класу, щоб в конструктор передавалось channel id і відразу виконувався makeQuery()
 *  (**в цьому невеличеому питанні хочу порадитись - а поки не завершу реалізацію повністю так і залишу
 *   **можливо робити все відразу в конструкторі буде недоцільно)
 *
 * - ну і реалізувати всі поля
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
    private String tittle;

    /**
     * і їхні геттери
     */
    public String getTittle() {
        if (tittle == null) System.out.println("Channel title is null or there was any mistake, try another id");
        return tittle;
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
            tittle = responce.items[0].snippet.title;
        } catch (UnirestException e) {
            e.printStackTrace();
        }
    }

}
