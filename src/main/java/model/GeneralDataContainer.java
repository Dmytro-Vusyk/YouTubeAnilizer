package model;

import java.time.Instant;
import java.util.Arrays;

/**
 * клас - контейнер результатів з API.
 * Результати запиту поміщаються в відповідні поля
 * Доданий для можливості створення масиву.
 *     - використовується класом QueryFromYoutube.
 */
public class GeneralDataContainer {
    /**
     * поля, що отримуються з YouTube
     */
    private String id;
    private String title;
    private Integer viewCount;
    private Boolean hiddenSubscriberCount;
    private Integer subscriberCount;
    private Integer videoCount;
    private Instant publishedAt;
    private Integer commentCount;
    private String uploads;

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
