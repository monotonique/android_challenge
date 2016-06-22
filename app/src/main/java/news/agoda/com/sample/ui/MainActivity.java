package news.agoda.com.sample.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import news.agoda.com.sample.R;
import news.agoda.com.sample.data.NewsEntity;
import news.agoda.com.sample.ui.misc.OnNewsItemSelectedListener;

public class MainActivity extends AppCompatActivity implements OnNewsItemSelectedListener {

    @Nullable @BindView(R.id.fragment_container) FrameLayout mFragmentContainer;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        if (mFragmentContainer != null && savedInstanceState == null) {
            NewsListFragment mNewsNewsListFragment = new NewsListFragment();
            getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, mNewsNewsListFragment).commit();
        }
    }

    @Override public void onNewsItemSelected(NewsEntity newsEntity) {
        String title = newsEntity.getTitle();
        String summary = newsEntity.getSummary();
        String imgURL = newsEntity.getMediaEntity().isEmpty() ? "" : newsEntity
            .getMediaEntity().get(0).getUrl();
        String storyURL = newsEntity.getArticleUrl();

        DetailFragment detailFragment = (DetailFragment)
            getSupportFragmentManager().findFragmentById(R.id.detail_fragment);

        if (detailFragment != null) {
            // two-panel layout
            detailFragment.setContent(title, summary, imgURL, storyURL);
        } else {
            // one-panel layout, just stack the fragment on top
            DetailFragment newFragment = new DetailFragment();
            Bundle args = new Bundle();
            args.putString("title", title);
            args.putString("summary", summary);
            args.putString("imgURL", imgURL);
            args.putString("storyURL", storyURL);
            newFragment.setArguments(args);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.fragment_container, newFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }

}
