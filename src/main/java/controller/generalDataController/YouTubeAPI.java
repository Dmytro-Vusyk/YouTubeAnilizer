package controller.generalDataController;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import model.Response;

/**
 * підкапотний простір запиту в АПІ
 * Використовується методом makeQuery() в класі QueryFromYoutube
 * <p>
 * Ініціалізує UnirestSerialization
 * повертає "контейнер" джсона (пакет youTubeDataContainer) з сєрвака YouTubeAPI
 */
public class YouTubeAPI {

    private static final String API_KEY = "AIzaSyDBVpCaXdSwREU9b5UeervX2eCUbqYLTcU";
    private static final String CHANNEL_URL = "https://www.googleapis.com/youtube/v3/channels";
    private static final String PLAYLIST_URL = "https://www.googleapis.com/youtube/v3/playlistItems";
    private static final String VIDEO_URL = "https://www.googleapis.com/youtube/v3/videos";

    static {
        new UnirestSerialization();
    }


    public static Response searchByChannelName(String channelId) throws UnirestException {

        HttpResponse<Response> response = Unirest.get(CHANNEL_URL)
                .queryString("key", API_KEY)
                .queryString("part", "snippet,statistics,contentDetails")
                .queryString("forUsername", channelId)
                .asObject(Response.class);
        return response.getBody();


    }

    public static Response searchByVideoId(String videosId) throws UnirestException {

        HttpResponse<Response> response = Unirest.get(VIDEO_URL)
                .queryString("key", API_KEY)
                .queryString("part", "statistics")
                .queryString("fields","items/statistics/commentCount")
                .queryString("id", videosId)
                .asObject(Response.class);
        return response.getBody();


    }


    public static Response searchPlayListByIdAndPageToken(String playlistId, String nextPageToken) throws UnirestException {
        HttpResponse<Response> response = Unirest.get(PLAYLIST_URL)
                .queryString("key", API_KEY)
                .queryString("part", "contentDetails")
                .queryString("maxResults", 25)
                .queryString("fields","items/contentDetails/videoId,nextPageToken")
                .queryString("pageToken", nextPageToken)
                .queryString("playlistId", playlistId)
                .asObject(Response.class);
        return response.getBody();
    }

}
