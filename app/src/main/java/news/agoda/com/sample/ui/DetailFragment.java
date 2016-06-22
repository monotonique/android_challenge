package news.agoda.com.sample.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.DraweeView;
import com.facebook.imagepipeline.request.ImageRequest;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import news.agoda.com.sample.R;

public class DetailFragment extends Fragment {

    @BindView(R.id.title) TextView mTitleView;
    @BindView(R.id.news_image) DraweeView mImageView;
    @BindView(R.id.summary_content) TextView mSummaryView;

    private String mStoryURL = "";

    @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
        @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_detail, container, false);
        ButterKnife.bind(this, view);
        Bundle bundle = getArguments();
        if (bundle != null) {
            setContent(bundle.getString("title", ""), bundle.getString("summary", ""),
                bundle.getString("imgURL", ""), bundle.getString("storyURL", ""));
        }
        return view;
    }

    @OnClick(R.id.full_story_link) public void onFullStoryClicked() {
        if (mStoryURL == null || mStoryURL.isEmpty()) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(mStoryURL));
        startActivity(intent);
    }

    public void setContent(String title, String summary, String imageURL, String storyURL) {
        mTitleView.setText(title);
        mSummaryView.setText(summary);
        DraweeController draweeController = Fresco.newDraweeControllerBuilder()
            .setImageRequest(ImageRequest.fromUri(Uri.parse(imageURL)))
            .setOldController(mImageView.getController()).build();
        mImageView.setController(draweeController);
        mStoryURL = storyURL;
    }

}
