package com.chanven.lib.cptr.loadmore;

import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chanven.lib.cptr.R;

/**
 * Created by roy on 2017/11/2.
 */

public class LoadMoreFooter implements ILoadMoreViewFactory {

    private AnimationDrawable animationDrawable;
    private ILoadMoreView view;

    @Override
    public ILoadMoreView madeLoadMoreView() {
        view = new LoadMoreFooter.LoadMoreHelper();
        return view;
    }

    public ILoadMoreView getView() {
        return view;
    }

    private class LoadMoreHelper implements ILoadMoreView {

        protected View footerView;
        protected TextView footerTv;
        protected ImageView rotateIv;

        protected View.OnClickListener onClickRefreshListener;

        @Override
        public void init(FootViewAdder footViewHolder, View.OnClickListener onClickRefreshListener) {
            footerView = footViewHolder.addFootView(R.layout.loadmore_footer);
            footerTv = (TextView) footerView.findViewById(R.id.loadmore_default_footer_tv);
            rotateIv = (ImageView) footerView.findViewById(R.id.refresh_rotate);
            rotateIv.setImageResource(R.drawable.pull_to_refresh_quee_anim);
            animationDrawable = (AnimationDrawable) rotateIv.getDrawable();
            this.onClickRefreshListener = onClickRefreshListener;
            showNormal();
        }

        @Override
        public void showNormal() {
            footerTv.setText("点击加载更多");
            animationDrawable.stop();
            showAndHideText(true);
            footerView.setOnClickListener(onClickRefreshListener);
        }

        @Override
        public void showLoading() {
            showAndHideText(false);
            startAnim();
            footerView.setOnClickListener(null);
        }

        @Override
        public void showFail(Exception exception) {
            animationDrawable.stop();
            showAndHideText(true);
            footerTv.setText("加载失败，点击重新");
            footerView.setOnClickListener(onClickRefreshListener);
        }

        @Override
        public void showNomore() {
            animationDrawable.stop();
            showAndHideText(true);
            footerTv.setText("已没有更多邮件");
            footerView.setOnClickListener(null);
        }

        @Override
        public void setFooterVisibility(boolean isVisible) {
            footerView.setVisibility(isVisible ? View.VISIBLE : View.GONE);
        }

        private void startAnim(){
            animationDrawable.stop();
            animationDrawable.start();
        }

        private void showAndHideText(boolean show){
            footerTv.setVisibility(show?View.VISIBLE:View.GONE);
            rotateIv.setVisibility(show?View.GONE:View.VISIBLE);
        }
    }


}
