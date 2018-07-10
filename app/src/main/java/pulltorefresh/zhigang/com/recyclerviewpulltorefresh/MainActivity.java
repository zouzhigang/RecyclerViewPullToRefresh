package pulltorefresh.zhigang.com.recyclerviewpulltorefresh;

import android.app.Activity;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;

import pulltorefresh.zhigang.com.recyclerviewpulltorefresh.pulltorefresh.PtrDefaultHandler;
import pulltorefresh.zhigang.com.recyclerviewpulltorefresh.pulltorefresh.PtrFrameLayout;
import pulltorefresh.zhigang.com.recyclerviewpulltorefresh.pulltorefresh.PullToRefreshLayout;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";
    PullToRefreshLayout ptrLayout;
    RecyclerView recyclerView;
    static Activity mBaseActivity;
    RecyclerviewAdapter recyclerviewAdapter;
    private static final String[] TITLE = {"Animation", "MultipleItem", "Header/Footer", "PullToRefresh", "Section", "EmptyView", "DragAndSwipe", "ItemClick", "ExpandableItem", "DataBinding", "UpFetchData"};
    private ArrayList<String> mDataList;
    int mPage = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBaseActivity = this;
        initData();
        initView();

    }

    private void initData() {
        mDataList = new ArrayList<>();
        for (int i = 0; i < TITLE.length; i++) {
            mDataList.add(TITLE[i]);
        }
    }

    public void initView() {
        ptrLayout = (PullToRefreshLayout) findViewById(R.id.ptrLayout);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(mBaseActivity));
        recyclerView.setEnabled(true);
        recyclerviewAdapter = new RecyclerviewAdapter(mBaseActivity, R.layout.layout_order_item, mDataList);
        recyclerView.setAdapter(recyclerviewAdapter);
        recyclerviewAdapter.setEnableLoadMore(true);
        //加载更多
        recyclerviewAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
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
                        mPage = 1;
                        ptrLayout.refreshComplete();

                    }
                }, 1500);

            }
        });
    }


    public void inView() {
//                        myOrderAdapter.loadMoreFail(); //加载失败
//                myOrderAdapter.loadMoreEnd(); //没有更多数据
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPage++;
                if (mPage == 3) {
                    ptrLayout.refreshComplete();
                    recyclerviewAdapter.loadMoreEnd();
                    recyclerviewAdapter.addData(mDataList);
                    return;
                }
                ptrLayout.refreshComplete();
                recyclerviewAdapter.loadMoreComplete();
                recyclerviewAdapter.addData(mDataList);
                Log.d(TAG, "LoadMore-----");


            }
        }, 1500);

    }
}
