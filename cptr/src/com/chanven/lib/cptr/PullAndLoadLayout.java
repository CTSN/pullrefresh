package com.chanven.lib.cptr;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;

import com.chanven.lib.cptr.loadmore.ILoadMoreViewFactory;
import com.chanven.lib.cptr.loadmore.LoadMoreFooter;

/**
 * Created by roy on 2017/11/2.
 */

public class PullAndLoadLayout extends PtrFrameLayout {

    private RefreshHeader mPtrClassicHeader;
    private View footerView;
    private LoadMoreFooter loadMoreViewFactory;

    public PullAndLoadLayout(Context context) {
        super(context);
        initViews();
    }

    public PullAndLoadLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    public PullAndLoadLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initViews();
    }

    private void initViews() {
        mPtrClassicHeader = new RefreshHeader(getContext());
        setHeaderView(mPtrClassicHeader);
        addPtrUIHandler(mPtrClassicHeader);

        loadMoreViewFactory = new LoadMoreFooter();
        setFooterView(loadMoreViewFactory);
    }

    public RefreshHeader getHeader() {
        return mPtrClassicHeader;
    }

    public LoadMoreFooter getLoadMoreViewFactory() {
        return loadMoreViewFactory;
    }

    public static boolean canChildScrollUp(View view) {
        if (android.os.Build.VERSION.SDK_INT < 14) {
            if (view instanceof AbsListView) {
                final AbsListView absListView = (AbsListView) view;
                return absListView.getChildCount() > 0
                        && (absListView.getFirstVisiblePosition() > 0 || absListView.getChildAt(0).getTop() <
                        absListView.getPaddingTop());
            } else {
                return ViewCompat.canScrollVertically(view, -1) || view.getScrollY() > 0;
            }
        } else {
            return view.canScrollVertically(-1);
        }
    }

    public static boolean checkContentCanBePulledDown(PtrFrameLayout frame, View content, View header) {
        return !canChildScrollUp(content);
    }

}
