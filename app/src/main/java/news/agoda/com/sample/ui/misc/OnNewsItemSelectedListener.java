package news.agoda.com.sample.ui.misc;

import news.agoda.com.sample.data.NewsEntity;

/**
 * Interface definition for a callback to be invoked when an item in
 * {@link news.agoda.com.sample.ui.NewsListFragment} has been clicked.
 */
public interface OnNewsItemSelectedListener {

    void onNewsItemSelected(NewsEntity newsEntity);

}