package news.agoda.com.sample.data;

import com.google.common.collect.Lists;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * A deserializer which handles the case when value of {@code multimedia} is not a json array.
 */
public class NewsEntityDeserializer implements JsonDeserializer<NewsEntity> {

    @Override public NewsEntity deserialize(JsonElement json, Type typeOfT,
        JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        JsonElement jsonElement = jsonObject.get("multimedia");
        List<MediaEntity> mediaEntities = Lists.newArrayList();
        if(jsonElement.isJsonArray()) {
            mediaEntities = context.deserialize(jsonElement.getAsJsonArray(),
                new TypeToken<List<MediaEntity>>(){}.getType());
        }
        return new NewsEntity(jsonObject.get("title").getAsString(),
            jsonObject.get("abstract").getAsString(),
            jsonObject.get("url").getAsString(),
            jsonObject.get("byline").getAsString(),
            jsonObject.get("published_date").getAsString(),
            mediaEntities);
    }

}
