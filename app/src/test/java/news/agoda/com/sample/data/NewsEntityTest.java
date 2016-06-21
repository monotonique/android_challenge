package news.agoda.com.sample.data;

import com.google.common.collect.Lists;

import org.junit.Test;

import java.util.List;

public class NewsEntityTest {

    @Test public void testNewsEntityGetters() {
        List<MediaEntity> mediaEntityList = Lists.newArrayList();
        NewsEntity newsEntity = new NewsEntity("news_title", "news_summary", "articleUrl",
            "byline", "publishedDate", mediaEntityList);
        assert newsEntity.getTitle().equals("news_title");
        assert newsEntity.getSummary().equals("news_summary");
        assert newsEntity.getArticleUrl().equals("articleUrl");
        assert newsEntity.getByline().equals("byline");
        assert newsEntity.getPublishedDate().equals("publishedDate");
        assert newsEntity.getMediaEntity().isEmpty();
    }

}
