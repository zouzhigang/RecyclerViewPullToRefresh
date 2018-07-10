package pulltorefresh.zhigang.com.recyclerviewpulltorefresh;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;

public class RecyclerviewAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public RecyclerviewAdapter(Context context, int layoutResId, ArrayList<String> regions) {
        super(layoutResId, regions);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, String regions) {
        baseViewHolder.setText(R.id.tv_title, regions);
    }
}
