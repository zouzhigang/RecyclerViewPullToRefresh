package pulltorefresh.zhigang.com.recyclerviewpulltorefresh;

import android.app.Activity;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;

import pulltorefresh.zhigang.com.recyclerviewpulltorefresh.pulltorefresh.PullToRefreshLayout;

public class MainActivity extends AppCompatActivity {
    PullToRefreshLayout ptrLayout;
    RecyclerView recyclerView;
    static Activity mBaseActivity;
    private static final String[] TITLE = {"Animation", "MultipleItem", "Header/Footer", "PullToRefresh", "Section", "EmptyView", "DragAndSwipe", "ItemClick", "ExpandableItem", "DataBinding", "UpFetchData"};
    private ArrayList<String> mDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBaseActivity = this ;
        initView();
        initData();
    }

    public void initView() {
        ptrLayout = (PullToRefreshLayout) findViewById(R.id.ptrLayout);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(mBaseActivity));
        recyclerView.setEnabled(true);
        myOrderAdapter = new MyOrderAdapter(mBaseActivity, R.layout.layout_order_item, mDataList);
        recyclerView.setAdapter(myOrderAdapter);
        myOrderAdapter.setEnableLoadMore(true);
        //加载更多
        myOrderAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                inView();
            }
        }, recyclerView);
        //刷新
        ptrLayout.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ptrLayout.refreshComplete();

                    }
                }, 3000);

            }
        });
    }

    private void initData() {
        mDataList = new ArrayList<>();
        for (int i = 0; i < TITLE.length; i++) {
            mDataList.add(TITLE[i]);
        }
    }

    public void inView() {
//                        myOrderAdapter.loadMoreFail(); //加载失败
//                myOrderAdapter.loadMoreEnd(); //没有更多数据
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ptrLayout.refreshComplete();
                myOrderAdapter.loadMoreComplete();
                myOrderAdapter.addData(mDataList);
                Log.d(TAG, "LoadMore-----");

            }
        }, 3000);

    }
}
