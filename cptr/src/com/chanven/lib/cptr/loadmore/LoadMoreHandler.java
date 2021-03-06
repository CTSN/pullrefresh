package com.chanven.lib.cptr.loadmore;

import android.view.View;
import android.view.View.OnClickListener;

import com.chanven.lib.cptr.loadmore.ILoadMoreViewFactory.ILoadMoreView;

public interface LoadMoreHandler {

    public boolean handleSetAdapter(View contentView, ILoadMoreView loadMoreView, OnClickListener
            onClickLoadMoreListener);

    public void setOnScrollBottomListener(View contentView, OnScrollBottomListener onScrollBottomListener);

    void removeFooter();
    void addFooter();
}
