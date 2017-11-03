package com.chanven.lib.cptr;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.chanven.lib.cptr.indicator.PtrIndicator;

public class RefreshHeader extends FrameLayout implements PtrUIHandler {

    private static final int FLIP_ANIMATION_DURATION = 150;
    private AnimationDrawable animationDrawable;
    private Animation mRotateAnimation, mResetRotateAnimation;

    private ImageView beginIv;
    private ImageView rotateIv;

    public RefreshHeader(Context context) {
        super(context);
        initViews();
    }

    public RefreshHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    public RefreshHeader(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initViews();
    }

    protected void initViews() {

        View header = LayoutInflater.from(getContext()).inflate(R.layout.refreah_header, this);
        beginIv = (ImageView)header.findViewById(R.id.refresh_begin);
        rotateIv = (ImageView) header.findViewById(R.id.refresh_rotate);

        beginIv.setImageResource(R.drawable.default_ptr_quee);
        rotateIv.setImageResource(R.drawable.pull_to_refresh_quee_anim);
        buildAnimation();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }


    private void buildAnimation() {
        animationDrawable = (AnimationDrawable) rotateIv.getDrawable();

        mRotateAnimation = new RotateAnimation(0, -180,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        mRotateAnimation.setInterpolator(new LinearInterpolator());
        mRotateAnimation.setDuration(FLIP_ANIMATION_DURATION);
        mRotateAnimation.setFillAfter(true);

        mResetRotateAnimation = new RotateAnimation(-180, 0,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        mResetRotateAnimation.setInterpolator(new LinearInterpolator());
        mResetRotateAnimation.setDuration(FLIP_ANIMATION_DURATION);
        mResetRotateAnimation.setFillAfter(true);

    }


    @Override
    public void onUIReset(PtrFrameLayout frame) {
        animationDrawable.stop();
    }

    @Override
    public void onUIRefreshPrepare(PtrFrameLayout frame) {
        rotateIv.setVisibility(View.GONE);
        beginIv.setVisibility(View.VISIBLE);
        beginIv.setImageResource(R.drawable.default_ptr_quee);
    }

    @Override
    public void onUIRefreshBegin(PtrFrameLayout frame) {
        hide();
        animationDrawable.stop();
        animationDrawable.start();
    }

    @Override
    public void onUIRefreshComplete(PtrFrameLayout frame) {
    }

    @Override
    public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {

        final int mOffsetToRefresh = frame.getOffsetToRefresh();
        final int currentPos = ptrIndicator.getCurrentPosY();
        final int lastPos = ptrIndicator.getLastPosY();

        if (currentPos < mOffsetToRefresh && lastPos >= mOffsetToRefresh) {
            if (isUnderTouch && status == PtrFrameLayout.PTR_STATUS_PREPARE) {
                crossRotateLineFromBottomUnderTouch(frame);

            }
        } else if (currentPos > mOffsetToRefresh && lastPos <= mOffsetToRefresh) {
            if (isUnderTouch && status == PtrFrameLayout.PTR_STATUS_PREPARE) {
                crossRotateLineFromTopUnderTouch(frame);
            }
        }
    }

    private void crossRotateLineFromTopUnderTouch(PtrFrameLayout frame) {
        if (!frame.isPullToRefresh()) {
            beginIv.startAnimation(mRotateAnimation);
        }
    }

    private void crossRotateLineFromBottomUnderTouch(PtrFrameLayout frame) {
        if (frame.isPullToRefresh()) {
            beginIv.startAnimation(mRotateAnimation);
        } else {
            beginIv.startAnimation(mResetRotateAnimation);
        }
    }

    public void hide() {
        beginIv.clearAnimation();
        beginIv.setVisibility(View.GONE);
        rotateIv.setVisibility(View.VISIBLE);
    }
}
