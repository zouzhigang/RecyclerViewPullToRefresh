package pulltorefresh.zhigang.com.recyclerviewpulltorefresh.pulltorefresh;

import android.content.Context;
import android.util.AttributeSet;


/**
 * 继承自{@link android.widget.FrameLayout},只能拥有一个子view
 */

public class PullToRefreshLayout extends PtrFrameLayout {

    private PullToRefreshHeaderView mHeaderView;

    public PullToRefreshLayout(Context context) {
        super(context);
        initViews();
    }

    public PullToRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    public PullToRefreshLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initViews();
    }

    private void initViews() {
        mHeaderView = new PullToRefreshHeaderView(getContext());
        setHeaderView(mHeaderView);
        addPtrUIHandler(mHeaderView);
    }
}
