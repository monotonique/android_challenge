package news.agoda.com.sample.data;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

public class DataManagerTest {

    @Test public void testDataManagerTest() throws IOException {
        DataManager dataManager = spy(DataManager.getInstance());
        InputStream is = this.getClass().getClassLoader().getResourceAsStream("news.agoda.com" +
            ".sample/news.json");
        doReturn(is).when(dataManager).getHttpInputStream();
        List<NewsEntity> newsEntities = dataManager.getNews().toBlocking().first();
        verify(dataManager).getHttpInputStream();
        assert newsEntities.size() == 2;
    }

}
