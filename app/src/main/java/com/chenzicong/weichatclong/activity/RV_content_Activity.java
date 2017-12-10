package com.chenzicong.weichatclong.activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chenzicong.weichatclong.R;
import com.chenzicong.weichatclong.adapter.RV_content_Adapter;
import com.chenzicong.weichatclong.beans.RV_centent_bean;
import com.crazysunj.cardslideview.CardHandler;
import com.crazysunj.cardslideview.CardViewPager;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN;

public class RV_content_Activity extends AppCompatActivity  {
    private List<RV_centent_bean> mList;
    private RecyclerView mRecyclerView;
    private RV_content_Adapter mAdapter;
    private String mContent;
    private MyCardHandler mMyCardHandler;
    private CardViewPager mViewPager;
    private boolean mIsCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(FLAG_FULLSCREEN, FLAG_FULLSCREEN);
        setContentView(R.layout.activity_rv_content);
        Intent intent = getIntent();
        mContent = intent.getStringExtra("content");
        mMyCardHandler  = new MyCardHandler();
        mIsCard = true;
        mViewPager = (CardViewPager) findViewById(R.id.view_pager);



        // mRecyclerView = (RecyclerView) findViewById(R.id.RV_content);
       // mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        new FetchItemsTask().execute();

    }


    private class MyCardHandler implements CardHandler<RV_centent_bean> {


        @Override
        public View onBind(final Context context, final RV_centent_bean data, final int position, int mode) {
            View view = View.inflate(context, R.layout.rv_content_item, null);
            ImageView imageView =  view.findViewById(R.id.ImageView);
            Glide.with(context).load(data.getUrl()).into(imageView);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "data:" + data + "position:" + position, Toast.LENGTH_SHORT).show();
                }
            });
            return view;
        }
    }


    public void onClick(View view) {
        if (mIsCard) {
            mIsCard = false;
            switchNormal();
        } else {
            mIsCard = true;
            switchCard();
        }
    }



    private class FetchItemsTask extends AsyncTask<Void, Void, List<RV_centent_bean>>{
        @Override
        protected void onPostExecute(List<RV_centent_bean> rv_centent_been) {
            super.onPostExecute(rv_centent_been);
            mViewPager.bind(getSupportFragmentManager(),mMyCardHandler,mList);
            switchCard();

        }

        @Override
        protected List<RV_centent_bean> doInBackground(Void... params) {
            mList = new ArrayList<>();
            try {

                String urlq = mContent;

                urlq = urlq.replace(".htm", "");

                for (int i = 1; i < 100; i++) {
                    urlq = urlq + "_" + i + ".htm";
                    Log.i("image_czc", "doInBackground: " + urlq);
                    Document doc = Jsoup.connect(urlq).timeout(10000).get();

                    Element total = doc.select("div.ImageBody").first();

                    Elements items = total.select("p");
                    if (items.size()==0){
                        break;
                    }
                    for (Element element : items) {

                        String imageurl = element.select("img").first().attr("src");
                        //  String title = element.select("img").first().attr("alt");
                        RV_centent_bean rv_centent_bean = new RV_centent_bean();
                        rv_centent_bean.setUrl(imageurl);
                        //  rv_centent_bean.setTitle(title);
                        mList.add(rv_centent_bean);
                        urlq = urlq.replace("_"+i+".htm", "");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return mList;
        }
    }

    private void setAdapter(List<RV_centent_bean> rv_centent_been) {
        mAdapter = new RV_content_Adapter(rv_centent_been, this);


        mRecyclerView.setAdapter(mAdapter);
    }
    private void switchNormal() {
        mViewPager.setCardTransformer(100, 0);
        mViewPager.setCardPadding(100);
        mViewPager.setCardMargin(100);
        mViewPager.notifyUI(CardViewPager.MODE_NORMAL);
    }

    private void switchCard() {
        mViewPager.setCardTransformer(180, 0.38f);
        mViewPager.setCardPadding(60);
        mViewPager.setCardMargin(40);
        mViewPager.notifyUI(CardViewPager.MODE_CARD);
    }
}
