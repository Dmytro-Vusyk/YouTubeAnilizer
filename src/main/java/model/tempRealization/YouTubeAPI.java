package model.tempRealization;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import model.youTubeDataContiner.Responce;

import java.time.Instant;

public class YouTubeAPI {
    private static final String API_KEY = "AIzaSyDBVpCaXdSwREU9b5UeervX2eCUbqYLTcU";
    public static Responce search(String queryChannelId) throws UnirestException {
        new UnirestSerialization();
        final int DEFAULT = 5;
        final int MAXIMUM = 50;
        final int DAY_IN_SEC = 60*60*24;
//        maxResult = (maxResult < 1) ? DEFAULT :
//                    (maxResult > 49) ? MAXIMUM : maxResult;
//        String published = (days < 1) ? "publishedBefore" : "publishedAfter";
//        Instant instant = Instant.now();
//        instant = instant.minusSeconds(DAY_IN_SEC * days);


        HttpResponse<Responce> response = Unirest.get("https://www.googleapis.com/youtube/v3/channels")
                .queryString("key", API_KEY)
                .queryString("part", "snippet,contentDetails,statistics")
//                .queryString("maxResults", maxResult)
//                .queryString(published, instant)
                .queryString("id", queryChannelId)
                .asObject(Responce.class);

        return response.getBody();
    }

    static Responce[] search(String ...queryChannelId) throws UnirestException{
        int count = queryChannelId.length;
        Responce[] responces = new Responce[count];
        for (int i = 0; i < count; i++) {
            responces[i] = search(queryChannelId[i]);
        }
        return responces;
    }
}
