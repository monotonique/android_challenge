package news.agoda.com.sample.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * This wraps a list of {@link NewsEntity}.
 */
public class NewsDataWrapper {

    @SerializedName("results") private List<NewsEntity> newsEntities;

    public List<NewsEntity> getNewsEntities() {
        return newsEntities;
    }

}