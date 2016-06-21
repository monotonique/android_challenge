package news.agoda.com.sample.data;

import java.util.List;

/**
 * This represents a news item
 */
public class NewsEntity {

    private String title;
    private String summary;
    private String articleUrl;
    private String byline;
    private String publishedDate;
    private List<MediaEntity> mediaEntityList;

    public NewsEntity(String title, String summary, String articleUrl, String byline, String
        publishedDate, List<MediaEntity> mediaEntityList) {
        this.title = title;
        this.summary = summary;
        this.articleUrl = articleUrl;
        this.byline = byline;
        this.publishedDate = publishedDate;
        this.mediaEntityList = mediaEntityList;
    }

    public String getTitle() {
        return title;
    }

    public String getSummary() {
        return summary;
    }

    public String getArticleUrl() {
        return articleUrl;
    }

    public String getByline() {
        return byline;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public List<MediaEntity> getMediaEntity() {
        return mediaEntityList;
    }

}
