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
 *
 * Ініціалізує UnirestSerialization
 * повертає "контейнер" джсона (пакет youTubeDataContainer) з сєрвака YouTubeAPI
 */
public class YouTubeAPI {
static {
    new UnirestSerialization();
}
    private static final String API_KEY = "AIzaSyDBVpCaXdSwREU9b5UeervX2eCUbqYLTcU";
    public static Response search(TypeQuery typeQuery, String part, String queryId) throws UnirestException {
        String url = (typeQuery == TypeQuery.CHANNEL) ? "https://www.googleapis.com/youtube/v3/channels" :
                     (typeQuery == TypeQuery.PLAYLIST) ? "https://www.googleapis.com/youtube/v3/playlistItems" :
                     (typeQuery == TypeQuery.VIDEO) ? "https://www.googleapis.com/youtube/v3/videos" : null;

        String id = (typeQuery == TypeQuery.CHANNEL) ? "id" :
                (typeQuery == TypeQuery.PLAYLIST) ? "playlistId" :
                        (typeQuery == TypeQuery.VIDEO) ? "id" : null;

        HttpResponse<Response> response = Unirest.get(url)
                .queryString("key", API_KEY)
                .queryString("part", part)
                .queryString(id, queryId)
                .asObject(Response.class);
        return response.getBody();
    }


    public static Response searchPlaylists(String unirestURL, String part, String quaryChannelId) throws UnirestException{
        HttpResponse<Response> response = Unirest.get(unirestURL)
                .queryString("key", API_KEY)
                .queryString("part", part)
                .queryString("id", quaryChannelId)
                .asObject(Response.class);
        return response.getBody();
    }

    /**
     * Response for list of uploaded videos
     * @param gdc object of GeneralDataContainer
     * @return object of Recponce
     * @throws UnirestException
     */
    public static Response getVideoList(GeneralDataContainer gdc) throws UnirestException {
        HttpResponse<Response> response = Unirest.get("https://www.googleapis.com/youtube/v3/playlistItems")
                .queryString("key", API_KEY)
                .queryString("part", "contentDetails")
                .queryString("id", gdc.getUploads())
                .asObject(Response.class);
        return response.getBody();
    }

    public static Response getVideoStatistic(String videoId) throws UnirestException {
        HttpResponse<Response> response = Unirest.get("https://www.googleapis.com/youtube/v3/videos")
                .queryString("key", API_KEY)
                .queryString("part", "statistics")
                .queryString("id", videoId)
                .asObject(Response.class);
        return response.getBody();
    }
}
