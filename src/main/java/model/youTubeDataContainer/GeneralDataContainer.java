package model.youTubeDataContainer;

import java.time.Instant;
import java.util.Arrays;

/**
 * class - container of results from YoutubeAPI
 * Results of query set to fields.
 */
public class GeneralDataContainer {
    private String id;
    private String title;
    private Integer viewCount;
    private Boolean hiddenSubscriberCount;
    private Integer subscriberCount;
    private Integer videoCount;
    private Instant publishedAt;
    private Integer commentCount;
    private String uploads;                 //TEMPORARY
    private String[] playlists;             //TEMPORARY
    private String[] videoIds;

    public GeneralDataContainer() {

    }


    public void setVideoIds(String[] videoIds) {
        this.videoIds = videoIds;
    }
    public void setPlaylists(String[] playlists){this.playlists = Arrays.copyOf(playlists, playlists.length);} //TEMPORARY
    public void setId(String id){this.id = id;}
    public void setUploads(String uploads) {
        this.uploads = uploads;
    }       //TEMPORARY
    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }
    public void setHiddenSubscriberCount(Boolean hiddenSubscriberCount) {
        this.hiddenSubscriberCount = hiddenSubscriberCount;
    }
    public void setSubscriberCount(Integer subscriberCount) {
        this.subscriberCount = subscriberCount;
    }
    public void setVideoCount(Integer videoCount) {
        this.videoCount = videoCount;
    }
    public void setPublishedAt(Instant publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String[] getPlaylists(){return playlists;}       //TEMPORARY
    public String getId() {
        return id;
    }
    public String getUploads() {
        return uploads;
    }       //temporary
    public Integer getCommentCount() {
        return commentCount;
    }
    public String getTitle() {
        return title;
    }
    public Integer getViewCount() {
        return viewCount;
    }
    public Boolean getHiddenSubscriberCount() {
        return hiddenSubscriberCount;
    }
    public Integer getSubscriberCount() {
        return subscriberCount;
    }
    public Integer getVideoCount() {
        return videoCount;
    }
    public Instant getPublishedAt() {
        return publishedAt;
    }
    public String[] getVideoIds() {
        return videoIds;
    }

    public GeneralDataContainer(String id, String title, Integer viewCount, Boolean hiddenSubscriberCount, Integer subscriberCount, Integer videoCount, Instant publishedAt, Integer commentCount, String uploads, String[] playlists, String[] videoIds) {
        this.id = id;
        this.title = title;
        this.viewCount = viewCount;
        this.hiddenSubscriberCount = hiddenSubscriberCount;
        this.subscriberCount = subscriberCount;
        this.videoCount = videoCount;
        this.publishedAt = publishedAt;
        this.commentCount = commentCount;
        this.uploads = uploads;
        this.playlists = playlists;
        this.videoIds = videoIds;
    }

    @Override
    public String toString() {
        return "GeneralDataContainer{" +
                "title='" + title + '\'' +
                ", viewCount=" + viewCount +
                ", hiddenSubscriberCount=" + hiddenSubscriberCount +
                ", subscriberCount=" + subscriberCount +
                ", videoCount=" + videoCount +
                ", publishedAt=" + publishedAt +
                ", commentCount=" + commentCount +
                '}';
    }
}
