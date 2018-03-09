package model.tempRealization;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import enumerated.TypeQuery;
import model.youTubeDataContainer.GeneralDataContainer;
import model.youTubeDataContainer.Response;

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


    public static Response search(TypeQuery typeQuery, String part, String queryId) throws UnirestException {
        String url = null;
        String id = "id";

        switch (typeQuery) {
            case CHANNEL:
                url = CHANNEL_URL;
                break;
            case PLAYLIST:
                url = PLAYLIST_URL;
                id = "playlistId";
                break;
            case VIDEO:
                url = PLAYLIST_URL;
                break;
        }

        HttpResponse<Response> response = Unirest.get(url)
                .queryString("key", API_KEY)
                .queryString("part", part)
                .queryString(id, queryId)
                .asObject(Response.class);
        return response.getBody();


    }

    public static Response searchVideoIdsInPlayList(String part, String queryId, int maxResult) throws UnirestException{
        HttpResponse<Response> response = Unirest.get(PLAYLIST_URL)
                .queryString("key", API_KEY)
                .queryString("part", part)
                .queryString("maxResults", maxResult)
                .queryString("playlistId", queryId)
                .asObject(Response.class);
        return response.getBody();
    }

}
