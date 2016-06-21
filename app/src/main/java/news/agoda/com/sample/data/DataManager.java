package news.agoda.com.sample.data;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.concurrent.Callable;

import news.agoda.com.sample.util.IOUtils;
import rx.Observable;
import rx.schedulers.Schedulers;

public class DataManager {

    private static DataManager mManager;

    public Observable<List<NewsEntity>> getNews() {
        return Observable.fromCallable(new Callable<List<NewsEntity>>() {
            @Override public List<NewsEntity> call() throws Exception {
                URL url = new URL("http://www.mocky.io/v2/573c89f31100004a1daa8adb");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                String data = IOUtils.readStream(con.getInputStream());
                Gson gson = new GsonBuilder().registerTypeAdapter(NewsEntity.class,
                    new NewsEntityDeserializer()).create();
                return gson.fromJson(data, NewsDataWrapper.class).getNewsEntities();
            }
        }).subscribeOn(Schedulers.io());
    }

    public static DataManager getInstance() {
        if (mManager == null) {
            mManager = new DataManager();
        }
        return mManager;
    }

}
