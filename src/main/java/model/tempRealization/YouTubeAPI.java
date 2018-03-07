package model.tempRealization;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import model.youTubeDataContainer.Responce;

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
    public static Responce search(String queryChannelId) throws UnirestException {
//        new UnirestSerialization();
        HttpResponse<Responce> response = Unirest.get("https://www.googleapis.com/youtube/v3/channels")
                .queryString("key", API_KEY)
                .queryString("part", "snippet,contentDetails,statistics")
//                .queryString("maxResults", maxResult)
//                .queryString(published, instant)
                .queryString("id", queryChannelId)
                .asObject(Responce.class);
        return response.getBody();
    }

    public static Responce searchPlaylists(String unirestURL, String part, String quaryChannelId) throws UnirestException{
        HttpResponse<Responce> response = Unirest.get(unirestURL)
                .queryString("key", API_KEY)
                .queryString("part", part)
                .queryString("id", quaryChannelId)
                .asObject(Responce.class);
        return response.getBody();
    }
}
