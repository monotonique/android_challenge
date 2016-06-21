package news.agoda.com.sample.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.DraweeView;
import com.facebook.imagepipeline.request.ImageRequest;

import butterknife.BindView;
import butterknife.ButterKnife;
import news.agoda.com.sample.R;

/**
 * News detail view
 */
public class DetailViewActivity extends AppCompatActivity {

    @BindView(R.id.title) TextView mTitleView;
    @BindView(R.id.news_image) DraweeView mImageView;
    @BindView(R.id.summary_content) TextView mSummaryView;

    private String storyURL = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        Bundle extras = getIntent().getExtras();
        storyURL = extras.getString("storyURL");
        String title = extras.getString("title");
        String summary = extras.getString("summary");
        String imageURL = extras.getString("imageURL");

        mTitleView.setText(title);
        mSummaryView.setText(summary);

        DraweeController draweeController = Fresco.newDraweeControllerBuilder()
                .setImageRequest(ImageRequest.fromUri(Uri.parse(imageURL)))
                .setOldController(mImageView.getController()).build();
        mImageView.setController(draweeController);
    }

    public void onFullStoryClicked(View view) {
        if (storyURL == null || storyURL.isEmpty()) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(storyURL));
        startActivity(intent);
    }

}
