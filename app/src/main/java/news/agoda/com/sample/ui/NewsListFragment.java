package news.agoda.com.sample.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.DraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.google.common.collect.Lists;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import news.agoda.com.sample.R;
import news.agoda.com.sample.data.DataManager;
import news.agoda.com.sample.data.MediaEntity;
import news.agoda.com.sample.data.NewsEntity;
import news.agoda.com.sample.ui.misc.ClickableRecyclerAdapter;
import news.agoda.com.sample.ui.misc.OnNewsItemSelectedListener;
import news.agoda.com.sample.ui.misc.RecyclerViewOnItemClickListener;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import timber.log.Timber;

public class NewsListFragment extends Fragment implements RecyclerViewOnItemClickListener {

    @BindView(android.R.id.list) RecyclerView mListView;
    @BindView(R.id.progress) ProgressBar progressBar;

    private OnNewsItemSelectedListener mCallback;
    private NewsAdapter mAdapter;

    @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
        @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_news_list, container, false);
        ButterKnife.bind(this, view);
        mAdapter = new NewsAdapter();
        mAdapter.setOnItemClickListener(this);
        mListView.setLayoutManager(new LinearLayoutManager(getContext()));
        mListView.addItemDecoration(new DividerItemDecoration(getContext()));
        mListView.setAdapter(mAdapter);
        progressBar.setVisibility(View.VISIBLE);
        DataManager.getInstance().getNews()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Action1<List<NewsEntity>>() {
                @Override public void call(List<NewsEntity> newsEntities) {
                    mAdapter.addAll(newsEntities);
                }
            }, new Action1<Throwable>() {
                @Override public void call(Throwable throwable) {
                    Timber.e(throwable, "Failed to get news");
                }
            }, new Action0() {
                @Override public void call() {
                    progressBar.setVisibility(View.GONE);
                }
            });
        return view;
    }

    @Override public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (OnNewsItemSelectedListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                + " must implement OnNewsItemSelectedListener");
        }
    }

    @Override public void onDestroy() {
        if (mAdapter != null) {
            mAdapter.setOnItemClickListener(null);
        }
        super.onDestroy();
    }

    @Override public void onItemClick(RecyclerView.Adapter adapter, View view, int position) {
        mCallback.onNewsItemSelected(mAdapter.getItem(position));
    }

    /**
     * Adapter which holds a list of {@link NewsEntity}.
     */
    public static class NewsAdapter extends ClickableRecyclerAdapter<NewsViewHolder> {

        private final List<NewsEntity> newsItemList;

        private LayoutInflater mInflater;

        public NewsAdapter() {
            newsItemList = Lists.newArrayList();
        }

        @Override public int getItemCount() {
            return newsItemList.size();
        }


        @Override
        public NewsViewHolder onCreateClickableViewHolder(ViewGroup parent, int viewType) {
            if (mInflater == null) {
                mInflater = LayoutInflater.from(parent.getContext());
            }
            return new NewsViewHolder(mInflater.inflate(R.layout.list_item_news, parent, false));
        }

        @Override public void onBindClickableViewHolder(NewsViewHolder holder, int position) {
            NewsEntity newsEntity = getItem(position);
            List<MediaEntity> mediaEntityList = newsEntity.getMediaEntity();
            String thumbnailURL = "";
            if (!mediaEntityList.isEmpty()) {
                thumbnailURL = mediaEntityList.get(0).getUrl();
            }
            holder.mTitle.setText(newsEntity.getTitle());
            DraweeController draweeController = Fresco.newDraweeControllerBuilder()
                .setImageRequest(ImageRequest.fromUri(Uri.parse(thumbnailURL)))
                .setOldController(holder.mImage.getController())
                .build();
            holder.mImage.setController(draweeController);
        }


        /**
         * This adds list of {@link NewsEntity}. Note that the list will be cleaned before adding.
         *
         * @param newsEntities
         */
        public void addAll(List<NewsEntity> newsEntities) {
            synchronized (newsItemList) {
                newsItemList.clear();
                newsItemList.addAll(newsEntities);
                notifyDataSetChanged();
            }
        }

        /**
         * This gets {@link NewsEntity} at a position.
         *
         * @param position
         * @return
         */
        public NewsEntity getItem(int position) {
            synchronized (newsItemList) {
                return newsItemList.get(position);
            }
        }

    }

    /**
     * View holder to show item of news.
     */
    public static class NewsViewHolder extends ClickableRecyclerAdapter.ClickableViewHolder {

        @BindView(R.id.news_title) TextView mTitle;
        @BindView(R.id.news_item_image) DraweeView mImage;

        public NewsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

    /**
     * Divider for list UI.
     */
    public static class DividerItemDecoration extends RecyclerView.ItemDecoration {

        private Drawable mDivider;

        public DividerItemDecoration(Context context) {
            mDivider = ResourcesCompat.getDrawable(context.getResources(),
                R.drawable.line_divider, null);
        }

        @Override public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
            int left = parent.getPaddingLeft();
            int right = parent.getWidth() - parent.getPaddingRight();

            int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View child = parent.getChildAt(i);

                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();

                int top = child.getBottom() + params.bottomMargin;
                int bottom = top + mDivider.getIntrinsicHeight();

                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            }
        }

    }

}
