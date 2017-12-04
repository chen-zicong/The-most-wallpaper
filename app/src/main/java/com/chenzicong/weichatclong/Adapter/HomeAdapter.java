package com.chenzicong.weichatclong.Adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chenzicong.weichatclong.R;
import com.chenzicong.weichatclong.beans.HomeItem;

import java.util.List;

/**
 * Created by ChenZiCong on 2017/11/27.
 */

public class HomeAdapter extends BaseQuickAdapter<HomeItem, BaseViewHolder> {

    private Context mcontext;

    public HomeAdapter(@Nullable List<HomeItem> data, Context context) {
        super(R.layout.rv_item, data);
        mcontext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeItem item) {

        ImageView image = helper.getView(R.id.ImageView);
    Glide.with(mcontext).load(item.getUrl()).into(image);
      //  Glide.with(mcontext).load(item.getUrl()).into(image);
        helper.addOnClickListener(R.id.ImageView);

    }
}
