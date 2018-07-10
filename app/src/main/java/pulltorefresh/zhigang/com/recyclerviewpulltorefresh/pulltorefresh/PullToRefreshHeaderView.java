package pulltorefresh.zhigang.com.recyclerviewpulltorefresh.pulltorefresh;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import pulltorefresh.zhigang.com.recyclerviewpulltorefresh.R;
import pulltorefresh.zhigang.com.recyclerviewpulltorefresh.pulltorefresh.indicator.PtrIndicator;


public class PullToRefreshHeaderView extends LinearLayout implements PtrUIHandler {

    private TextView mHeaderTv;
    private ImageView mHeaderIv;


    public static final String PULL_TO_REFRESH_TXT = "下拉刷新";
    public static final String RELEASE_TO_REFRESH_TXT = "松开立即刷新";
    public static final String REFRESHING_TXT = "加载中";
    public static final String REFRESH_COMPLETE_TXT = "加载完成";

    public PullToRefreshHeaderView(Context context) {
        super(context);
        init(null);
    }

    public PullToRefreshHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public PullToRefreshHeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @TargetApi(21)
    public PullToRefreshHeaderView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(AttributeSet attributeSet) {
        View header = LayoutInflater.from(getContext()).inflate(R.layout.ptr_header_view, this);
        mHeaderIv = (ImageView) header.findViewById(R.id.header_iv);
        mHeaderTv = (TextView) header.findViewById(R.id.header_tv);
        mHeaderTv.setText(PULL_TO_REFRESH_TXT);
    }

    public void startAnimation() {
        Drawable drawable = mHeaderIv.getDrawable();
        if (null != drawable && drawable instanceof AnimationDrawable) {
            AnimationDrawable animationDrawable = (AnimationDrawable) drawable;
            animationDrawable.start();
        }
    }

    public void stopAnimation() {
        Drawable drawable = mHeaderIv.getDrawable();
        if (null != drawable && drawable instanceof AnimationDrawable) {
            AnimationDrawable animationDrawable = (AnimationDrawable) drawable;
            animationDrawable.stop();
        }
    }

    public void setHeaderText(String text) {
        mHeaderTv.setText(text);
    }

    @Override
    public void onUIReset(PtrFrameLayout frame) {
        mHeaderTv.setText(PULL_TO_REFRESH_TXT);
        stopAnimation();
    }

    @Override
    public void onUIRefreshPrepare(PtrFrameLayout frame) {
        mHeaderTv.setText(PULL_TO_REFRESH_TXT);
    }

    @Override
    public void onUIRefreshBegin(PtrFrameLayout frame) {
        mHeaderTv.setText(REFRESHING_TXT);
        startAnimation();
    }

    @Override
    public void onUIRefreshComplete(PtrFrameLayout frame) {
        mHeaderTv.setText(REFRESH_COMPLETE_TXT);
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
            mHeaderTv.setVisibility(VISIBLE);
            mHeaderTv.setText(R.string.cube_ptr_release_to_refresh);
        }
    }

    private void crossRotateLineFromBottomUnderTouch(PtrFrameLayout frame) {
        mHeaderTv.setVisibility(VISIBLE);
        if (frame.isPullToRefresh()) {
            mHeaderTv.setText(getResources().getString(R.string.cube_ptr_pull_down_to_refresh));
        } else {
            mHeaderTv.setText(getResources().getString(R.string.cube_ptr_pull_down));
        }
    }
}
