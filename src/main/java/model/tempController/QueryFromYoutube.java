package model.tempController;

import com.mashape.unirest.http.exceptions.UnirestException;
import model.tempRealization.YouTubeAPI;
import model.youTubeDataContiner.Responce;

public class QueryFromYoutube {
    private String channelId;

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    private String tittle;

    public String getTittle() {
        tittle = null;
        if (channelId == null) {
            System.out.println("Channel id is not assigned");
            throw new NullPointerException();
        }
        YouTubeAPI youTubeAPI = new YouTubeAPI();
        try {
            Responce responce = YouTubeAPI.search(channelId);
            tittle = responce.items[0].snippet.title;
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        if (tittle == null) System.out.println("Channel title is null or there was any mistake, try another id");
        return tittle;
    }

}
