package news.agoda.com.sample.ui;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MenuItem;
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
            // one-panel layout
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
            getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.translate_in, R.anim.translate_out,
                    R.anim.translate_in, R.anim.translate_out)
                .add(R.id.fragment_container, newFragment)
                .addToBackStack(null)
                .commit();
            showBackArrow();
        }
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            hideBackArrow();
            getSupportFragmentManager().popBackStack();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (Build.VERSION.SDK_INT > 5
            && keyCode == KeyEvent.KEYCODE_BACK
            && event.getRepeatCount() == 0) {
            hideBackArrow();
        }
        return super.onKeyDown(keyCode, event);
    }

    private void showBackArrow() {
        ActionBar bar = getSupportActionBar();
        if (bar != null) {
            bar.setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    private void hideBackArrow() {
        ActionBar bar = getSupportActionBar();
        if (bar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setDisplayShowHomeEnabled(false);
        }
    }

}
