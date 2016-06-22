package news.agoda.com.sample.data;


import com.google.common.annotations.VisibleForTesting;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.concurrent.Callable;

import news.agoda.com.sample.util.IOUtils;
import rx.Observable;
import rx.schedulers.Schedulers;

public class DataManager {

    private static DataManager mManager;

    /**
     * Return an {@link Observable} which emits list of {@link NewsEntity}.
     *
     * @return
     */
    public Observable<List<NewsEntity>> getNews() {
        return Observable.fromCallable(new Callable<List<NewsEntity>>() {
            @Override public List<NewsEntity> call() throws Exception {
                String data = IOUtils.readStream(getHttpInputStream());
                Gson gson = new GsonBuilder().registerTypeAdapter(NewsEntity.class,
                    new NewsEntityDeserializer()).create();
                return gson.fromJson(data, NewsDataWrapper.class).getNewsEntities();
            }
        }).subscribeOn(Schedulers.io());
    }

    /**
     * Use this to get instance of {@link DataManager}.
     *
     * @return
     */
    public static DataManager getInstance() {
        if (mManager == null) {
            mManager = new DataManager();
        }
        return mManager;
    }

    @VisibleForTesting InputStream getHttpInputStream() throws IOException {
        URL url = new URL("http://www.mocky.io/v2/573c89f31100004a1daa8adb");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        return con.getInputStream();
    }

    private DataManager() {
    }

}
