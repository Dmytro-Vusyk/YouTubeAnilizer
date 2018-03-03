package model.tempRealization;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.time.Instant;

public class YouTubeAPI {
//    static Responce search(String query, int maxResult, int days) throws UnirestException {
//        new UnirestSerialization();
//        final int DEFAULT = 5;
//        final int MAXIMUM = 50;
//        if (maxResult < 1) maxResult = DEFAULT;
//        if (maxResult > 49) maxResult = MAXIMUM;
//
//        String published = (days < 1) ? "publishedBefore" : "publishedAfter";
//        final int DAY_IN_SEC = 60*60*24;
//        Instant instant = Instant.now();
//        instant = instant.minusSeconds(DAY_IN_SEC * days);
//
//        HttpResponse<Responce> response = Unirest.get("https://www.googleapis.com/youtube/v3/search")
//                .queryString("key", API_KEY)
//                .queryString("part", "snippet")
//                .queryString("maxResults", maxResult)
//                .queryString(published, instant)
//                .queryString("q", query)
//                .asObject(Responce.class);
//
//        return response.getBody();
//    }
}
