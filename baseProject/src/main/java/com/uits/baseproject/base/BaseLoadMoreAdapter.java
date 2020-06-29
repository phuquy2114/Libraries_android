package com.uits.baseproject.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.uits.baseproject.R;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Copyright © 2018 SOFT ONE  CO., LTD
 * Created by PhuQuy on 31/08/2018.
 */
public abstract class BaseLoadMoreAdapter<DATA> extends BaseAdapter {
    /**
     * load more listener
     */
    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    private final int VIEW_LOAD_MORE = 0;
    private WeakReference<Context> mWeakContext;
    private List<DATA> mDataList;
    private int mVisibleThreshold = 2;
    private int mLastVisibleItem;
    private int mTotalItemCount;
    private int mChildItemCount;
    private boolean isLoading;
    private OnLoadMoreListener mOnLoadMoreListener;
    private RecyclerView mRecyclerView;

    protected BaseLoadMoreAdapter(@NonNull Context context, List<DATA> data) {
        super(context);
        mWeakContext = new WeakReference<Context>(context);
        mDataList = data;
    }

    /**
     * constructor
     *`
     * @param context
     * @param data
     * @param recyclerView
     */
    protected BaseLoadMoreAdapter(@NonNull Context context, List<DATA> data, RecyclerView recyclerView) {
        super(context);
        mRecyclerView = recyclerView;
        mWeakContext = new WeakReference<Context>(context);
        mDataList = data;
        if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    mChildItemCount = recyclerView.getChildCount();
                    mTotalItemCount = gridLayoutManager.getItemCount();
                    mLastVisibleItem = gridLayoutManager.findLastVisibleItemPosition();
                    if (mChildItemCount < mTotalItemCount) {
                        if (!isLoading && mTotalItemCount <= (mLastVisibleItem + mVisibleThreshold)) {
                            if (mOnLoadMoreListener != null) {
                                startLoadMore();
                                mOnLoadMoreListener.onLoadMore();
                            }
                            isLoading = true;
                        }
                    }
                }
            });

            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (getItemViewType(position) == VIEW_LOAD_MORE) {
                        return gridLayoutManager.getSpanCount();
                    } else {
                        return 1;
                    }
                }
            });
            return;
        }

        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    if (dy > 0) {
                        mChildItemCount = recyclerView.getChildCount();
                        mTotalItemCount = linearLayoutManager.getItemCount();
                        mLastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                        if (mChildItemCount < mTotalItemCount) {
                            if (!isLoading && mTotalItemCount <= (mLastVisibleItem + mVisibleThreshold)) {
                                isLoading = true;
                                if (mOnLoadMoreListener != null) {
                                    startLoadMore();
                                    mOnLoadMoreListener.onLoadMore();
                                }
                            }
                        }
                    }
                }
            });
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_LOAD_MORE) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.progress_item, parent, false);
            return new ProgressViewHolder(v);
        } else {
            return onCreateHolder(parent, viewType);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == VIEW_LOAD_MORE) {
            ((ProgressViewHolder) holder).progressBar.setIndeterminate(true);
        } else {
            onBindHolder(holder, position);
        }
    }

    @Override
    public final void onBindViewHolder(RecyclerView.ViewHolder holder, int position, List payloads) {
        super.onBindViewHolder(holder, position, payloads);
        if (getItemViewType(position) != VIEW_LOAD_MORE) {
            onBindHolder(holder, position, payloads);
        }
    }

    /**
     * bind your view holder
     *
     * @param holder
     * @param position
     */
    public abstract void onBindHolder(RecyclerView.ViewHolder holder, int position);

    /**
     * bind view holder with payloads
     *
     * @param holder
     * @param position
     * @param payloads
     */
    public void onBindHolder(RecyclerView.ViewHolder holder, int position, List payloads) {
    }

    @Override
    public final int getItemViewType(int position) {
        if (position < mDataList.size()) {
            if (mDataList.get(position) != null) {
                return getItemType(position);
            } else {
                return VIEW_LOAD_MORE;
            }
        } else {
            return VIEW_LOAD_MORE;
        }
    }

    @Override
    public final int getItemCount() {
        return mDataList.size();
    }

    /**
     * enable load more
     */
    public final void setLoaded() {
        isLoading = false;
    }

    /**
     * on create view holder
     *
     * @param parent
     * @param viewType
     * @return
     */
    protected abstract RecyclerView.ViewHolder onCreateHolder(ViewGroup parent, int viewType);

    /**
     * @param mOnLoadMoreListener
     */
    public final void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.mOnLoadMoreListener = mOnLoadMoreListener;
    }

    /**
     * get item type of list
     *
     * @param position
     * @return
     */
    protected abstract int getItemType(int position);

    public List<DATA> getData() {
        return mDataList;
    }

    /**
     * get adapter position
     *
     * @return
     */
    public int getAdapterPosition() {
        return getAdapterPosition();
    }

    /**
     * get layout position
     *
     * @return
     */
    public int getLayoutPosition() {
        return getLayoutPosition();
    }

    /**
     * show progress
     * call in onLoadMore
     */
    private final void startLoadMore() {
        mDataList.add(null);
        mRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                notifyItemInserted(mDataList.size() - 1);
            }
        });
    }

    /**
     * dismiss and notify after load more
     * call when thread success
     *
     * @param list
     */
    public final void notifyLoadMore(List<DATA> list) {
        mDataList.remove(mDataList.size() - 1);
        notifyItemRemoved(mDataList.size());
        mDataList.addAll(list);
        notifyItemInserted(mDataList.size());
        setLoaded();
    }

    /**
     * notify data
     *
     * @param list
     */
    public final void addChangeData(List<DATA> list) {
        mDataList.clear();
        mDataList.addAll(list);
        notifyDataSetChanged();
        setLoaded();
    }

    /**
     * dismiss progress load more
     */
    public final void dismissLoadMore() {
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mDataList.remove(mDataList.size() - 1);
                notifyItemRemoved(mDataList.size());
                setLoaded();
            }
        }, 5000);
    }

    /**
     * ViewHolder for progress
     */
    public static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public ProgressViewHolder(View v) {
            super(v);
            progressBar = (ProgressBar) v.findViewById(R.id.mProgressBar);
        }
    }
}
