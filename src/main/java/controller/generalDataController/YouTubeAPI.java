package controller.generalDataController;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import model.Response;

/**
 * Contains all requests which are needed for work with YoutubeAPI
 */
class YouTubeAPI {

    private static final String API_KEY = "AIzaSyDBVpCaXdSwREU9b5UeervX2eCUbqYLTcU";
    private static final String CHANNEL_URL = "https://www.googleapis.com/youtube/v3/channels";
    private static final String PLAYLIST_URL = "https://www.googleapis.com/youtube/v3/playlistItems";
    private static final String VIDEO_URL = "https://www.googleapis.com/youtube/v3/videos";

    static {
        new UnirestSerialization();
    }

    static Response searchByChannelId(String channelId) throws UnirestException {

        HttpResponse<Response> response = Unirest.get(CHANNEL_URL)
                .queryString("key", API_KEY)
                .queryString("part", "snippet,statistics,contentDetails")
                .queryString("id", channelId)
                .asObject(Response.class);
        return response.getBody();


    }

    static Response searchByVideoId(String videosId) throws UnirestException {
        HttpResponse<Response> response = Unirest.get(VIDEO_URL)
                .queryString("key", API_KEY)
                .queryString("part", "statistics")
                .queryString("fields", "items/statistics/commentCount")
                .queryString("id", videosId)
                .asObject(Response.class);
        return response.getBody();
    }

    static Response searchPlayListByIdAndPageToken(String playlistId, String nextPageToken) throws UnirestException {
        HttpResponse<Response> response = Unirest.get(PLAYLIST_URL)
                .queryString("key", API_KEY)
                .queryString("part", "contentDetails")
                .queryString("maxResults", 25)
                .queryString("fields", "items/contentDetails/videoId,nextPageToken")
                .queryString("pageToken", nextPageToken)
                .queryString("playlistId", playlistId)
                .asObject(Response.class);
        return response.getBody();
    }

}
