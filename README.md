# RecyclerViewPullToRefresh
android RecyclerView 下拉刷新，上拉加载更多框架

# RecyclerViewPullToRefresh
android RecyclerView 下拉刷新，上拉加载更多框架

1.初始化操作

```
  MyOrderAdapter myOrderAdapter;
    static Activity mBaseActivity;

    private static final String[] TITLE = {"Animation", "MultipleItem", "Header/Footer", "PullToRefresh", "Section", "EmptyView", "DragAndSwipe", "ItemClick", "ExpandableItem", "DataBinding", "UpFetchData"};

    private ArrayList<String> mDataList;


 
```
2.如何使用

```
ptrLayout = (PullToRefreshLayout) mContentView.findViewById(R.id.ptrLayout);
        recyclerView = (RecyclerView) mContentView.findViewById(R.id.recyclerView);
recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
 recyclerView.setEnabled(true);

```

3.添加测试数据

```
 mDataList = new ArrayList<>();
        for (int i = 0; i < TITLE.length; i++) {
            mDataList.add(TITLE[i]);
        }
```

4.实战效果

```
  myOrderAdapter = new MyOrderAdapter(mBaseActivity, R.layout.layout_order_item, mDataList);
        recyclerView.setAdapter(myOrderAdapter);
        myOrderAdapter.setEnableLoadMore(true);
        //加载更多
        myOrderAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
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

```


