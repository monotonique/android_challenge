package news.agoda.com.sample.ui.misc;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;

/**
 * ClickableRecyclerAdapter wraps {@link android.support.v7.widget.RecyclerView.Adapter} to
 * enable catching item click events from each child item view in adapter.
 * <p/>
 * To perform onItemClick event callback, a {@link RecyclerViewOnItemClickListener} must be set
 * to adapter via {@link #setOnItemClickListener(RecyclerViewOnItemClickListener)} or from
 * constructor.
 *
 * @param <T>
 */
public abstract class ClickableRecyclerAdapter<T extends ClickableRecyclerAdapter.ClickableViewHolder>
    extends RecyclerView.Adapter<T> {

    private final OnItemClickListenerProxy mClickListenerProxy;
    private final OnItemLongClickListenerProxy mLongClickListenerProxy;

    private RecyclerViewOnItemClickListener mClickListener;
    private RecyclerViewOnItemLongClickListener mLongClickListener;

    public ClickableRecyclerAdapter() {
        this.mClickListenerProxy = new OnItemClickListenerProxy() {
            @Override public void onItemClick(View view, int position) {
                if (mClickListener != null) {
                    mClickListener.onItemClick(ClickableRecyclerAdapter.this, view, position);
                }
            }
        };
        this.mLongClickListenerProxy = new OnItemLongClickListenerProxy() {
            @Override public boolean onItemLongClick(View view, int position) {
                if (mLongClickListener != null) {
                    return mLongClickListener.onItemLongClick(ClickableRecyclerAdapter.this,
                        view, position);
                }
                return false;
            }
        };
    }

    /**
     * This method calls {@link #onCreateClickableViewHolder(ViewGroup, int)} to create a new
     * {@link ClickableViewHolder} and bind the onItemClickListener to the RecyclerView.
     *
     * @see #onCreateClickableViewHolder(ViewGroup, int)
     */
    @Override
    public final T onCreateViewHolder(ViewGroup parent, int viewType) {
        T viewHolder = onCreateClickableViewHolder(parent, viewType);
        if (viewHolder != null) {
            viewHolder.setOnItemClickListener(mClickListenerProxy);
            viewHolder.setOnItemLongClickListener(mLongClickListenerProxy);
        }
        return viewHolder;
    }

    /**
     * Called when RecyclerView needs a new {@link ClickableViewHolder} of the given type to
     * represent an item.
     * <p/>
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     */
    public abstract T onCreateClickableViewHolder(ViewGroup parent, int viewType);

    @Override public void onBindViewHolder(T holder, int position) {
        holder.setEnabled(isEnabled(position));
        onBindClickableViewHolder(holder, position);
    }

    public abstract void onBindClickableViewHolder(T holder, int position);

    /**
     * Returns true if the item at the specified position is not a separator.
     * (A separator is a non-selectable, non-clickable item).
     *
     * @param position Index of the item
     * @return True if the item is not a separator
     */
    public boolean isEnabled(int position) {
        return true;
    }

    /**
     * Register a callback to be invoked when an item in this AdapterView has
     * been clicked.
     *
     * @param listener The callback that will be invoked.
     */
    public void setOnItemClickListener(RecyclerViewOnItemClickListener listener) {
        mClickListener = listener;
    }

    /**
     * Register a callback to be invoked when an item in this AdapterView has
     * been long-clicked.
     *
     * @param listener The callback that will be invoked.
     */
    public void setOnItemLongClickListener(RecyclerViewOnItemLongClickListener listener) {
        mLongClickListener = listener;
    }

    /**
     * Interface for proxy item click event from view holder
     */
    private interface OnItemClickListenerProxy {

        void onItemClick(View view, int position);

    }

    /**
     * Interface for proxy item long click event from view holder
     */
    private interface OnItemLongClickListenerProxy {

        boolean onItemLongClick(View view, int position);

    }

    /**
     * The class that extends {@link RecyclerView.ViewHolder} to make the item view has {@link
     * OnClickListener} and {@link OnLongClickListener}
     */
    public abstract static class ClickableViewHolder extends RecyclerView.ViewHolder
        implements OnClickListener, OnLongClickListener {

        private OnItemClickListenerProxy onItemClickListener;
        private OnItemLongClickListenerProxy onItemLongClickListener;
        private boolean enabled;

        public ClickableViewHolder(@NonNull LayoutInflater inflater, @LayoutRes int layoutRes,
            @Nullable ViewGroup parent) {
            super(inflater.inflate(layoutRes, parent, false));
        }

        public ClickableViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            view.setOnLongClickListener(this);
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
            this.itemView.setClickable(enabled);
            this.itemView.setLongClickable(enabled);
        }

        void setOnItemClickListener(OnItemClickListenerProxy onItemClickListener) {
            this.onItemClickListener = onItemClickListener;
        }

        @Override public void onClick(View v) {
            if (enabled && onItemClickListener != null) {
                onItemClickListener.onItemClick(v, getLayoutPosition());
            }
        }

        void setOnItemLongClickListener(OnItemLongClickListenerProxy onItemLongClickListener) {
            this.onItemLongClickListener = onItemLongClickListener;
        }

        @Override public boolean onLongClick(View v) {
            if (enabled && onItemLongClickListener != null) {
                return onItemLongClickListener.onItemLongClick(v, getLayoutPosition());
            }
            return false;
        }

    }

}