package news.agoda.com.sample.data;

import org.junit.Test;

public class MediaEntityTest {

    @Test public void testMediaEntityGetters() {
        MediaEntity mediaEntity = new MediaEntity("url", "format", 100, 300, "type", "sub_type",
            "caption", "copyright");
        assert mediaEntity.getUrl().equals("url");
        assert mediaEntity.getFormat().equals("format");
        assert mediaEntity.getHeight() == 100;
        assert mediaEntity.getWidth() == 300;
        assert mediaEntity.getType().equals("type");
        assert mediaEntity.getSubType().equals("sub_type");
        assert mediaEntity.getCaption().equals("caption");
        assert mediaEntity.getCopyright().equals("copyright");
    }

}
