package com.chenzicong.weichatclong.Adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chenzicong.weichatclong.R;
import com.chenzicong.weichatclong.beans.RV_centent_bean;

import java.util.List;

/**
 * Created by ChenZiCong on 2017/11/29.
 */

public class RV_content_Adapter extends BaseQuickAdapter<RV_centent_bean,BaseViewHolder> {
    private Context mcontext;
    public RV_content_Adapter(@Nullable List<RV_centent_bean> data, Context context) {
        super(R.layout.rv_content_item, data);
        mcontext = context;

    }



    @Override
    protected void convert(BaseViewHolder helper, RV_centent_bean item) {

        ImageView image = helper.getView(R.id.ImageView);
        Glide.with(mcontext).load(item.getUrl()).into(image);

    }

}
