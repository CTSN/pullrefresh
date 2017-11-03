package com.chanven.lib.cptr;

import com.chanven.lib.cptr.indicator.PtrIndicator;

public interface PtrUIHandler {

    public void onUIReset(PtrFrameLayout frame);

    public void onUIRefreshPrepare(PtrFrameLayout frame);

    public void onUIRefreshBegin(PtrFrameLayout frame);

    public void onUIRefreshComplete(PtrFrameLayout frame);

    public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator);
}
