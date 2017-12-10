package com.chenzicong.weichatclong.activity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chenzicong.weichatclong.R;
import com.chenzicong.weichatclong.adapter.HomeAdapter;
import com.chenzicong.weichatclong.beans.HomeItem;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ChenZiCong on 2017/12/2.
 */

public class PagerItemFragment extends Fragment  {
    private static String  mImageType;
    private RecyclerView mRecyclerView;
    private List<HomeItem> mHomeItems;
    private List<HomeItem> mNewHomeItems;
    private static final String ONE_DATA = "one_date";
    private static final String MORE_DATA = "more_date";


    private HomeAdapter mHomeAdapter;
    private int mI = 2;

    public static PagerItemFragment newInstance(String imageType) {
       mImageType = imageType ;
        return new PagerItemFragment();
    }

    public PagerItemFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_home, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.RecycleView);
        mRecyclerView.setLayoutManager(
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)); //瀑布流布局
        new FetchItemsTask().execute(ONE_DATA);
        return view;

    }




    private void setAdapter() {
        Log.i("czc", "setAdapter: haha");
        mHomeAdapter = new HomeAdapter(mHomeItems, getActivity());
        //设置动画,默认为渐隐
      mHomeAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT );
        //设置动画重复播放
   //     mHomeAdapter.isFirstOnly(false);
        //获取header的布局
        //  View view = View.inflate(this, R.layout.header_view, null);
        //设置RECYCLERVIEW的HEADER
        //  mHomeAdapter.addHeaderView(view);

        //设置点击事件 ,需要在Adapter的add
        mHomeAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(getActivity().getApplicationContext(), RV_content_Activity.class);
                intent.putExtra("Position", position);
                intent.putExtra("content", mHomeItems.get(position).getContent());
                startActivity(intent);
            }
        });
        View view = View.inflate(getActivity(),R.layout.empty_data_layout,null);
        mHomeAdapter.setEmptyView(view);

        mHomeAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mRecyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.i("czc", "run:进来了");
                        new FetchItemsTask().execute(MORE_DATA);
                        Log.i("czc", "run: "+Thread.currentThread().getName());
                        //这里是主线程


                    }
                }, 1000);
            }
        }, mRecyclerView);

         mHomeAdapter.setUpFetchEnable(true);
        mHomeAdapter.setUpFetchListener(new BaseQuickAdapter.UpFetchListener() {
            @Override
            public void onUpFetch() {
                mRecyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.i("czc", "run: 下拉");
                        new FetchItemsTask().execute(MORE_DATA);
                        Log.i("czc", "run: "+Thread.currentThread().getName());
                        //这里是主线程


                    }
                }, 1000);
            }
        });

        mRecyclerView.setAdapter(mHomeAdapter);
    }

























    private class FetchItemsTask extends AsyncTask<String, Void, String>

    {

        private String mUrlq;
        private Document mDoc;
        private Elements mTotal;
        private Elements mItems;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... params) {
            try {
                switch (params[0]) {
                    case ONE_DATA:
                        loadFirstData();
                        Log.i("czc", "doInBackground: " + params[0]);
                        break;

                    case MORE_DATA:
                       // loadMoreData();
                        Log.i("czc", "doInBackground: " + params[0]);
                        break;

                    default:
                        break;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            return params[0];
        }

        /**
         * 加载更多的数据
         *
         * @throws IOException
         */
        private void loadMoreData() throws IOException {


            mNewHomeItems = new ArrayList<>();
            mUrlq = "http://www.umei.cc/meinvtupian/" + mImageType + "/" + mI + ".htm";

            mDoc = Jsoup.connect(mUrlq).timeout(10000).get();
            mTotal = mDoc.select("div.TypeList");
            mItems = mTotal.select("li");
            for (Element element : mItems) {

                String imageurl = element.select("img").first().attr("src");
                //String title = element.select("img").first().attr("alt");
                String attr = element.select("a").first().attr("href");
                HomeItem homeItem = new HomeItem();
                //homeItem.setTitle(title);
                homeItem.setUrl(imageurl);
                homeItem.setContent(attr);
                homeItem.save();
                mHomeItems.add(homeItem);

            }
            mI++;
        }

        /**
         * 加载第一次数据
         *
         * @throws IOException
         */
        private void loadFirstData() throws IOException {

         //   LitePal.getDatabase(); //创建数据库

            SQLiteDatabase db = LitePal.getDatabase();
           // db.execSQL(CREATE_TABLE);

            mHomeItems = new ArrayList<>();

           // DataSupport.deleteAll(HomeItem.class);
           if(!DataSupport.isExist(HomeItem.class)) {
                mUrlq = "http://www.umei.cc/meinvtupian/" + mImageType;
                mDoc = Jsoup.connect(mUrlq).timeout(10000).get();
                mTotal = mDoc.select("div.TypeList");
                mItems = mTotal.select("li");
                for (Element element : mItems) {

                    String imageurl = element.select("img").first().attr("src");
                    //String title = element.select("a").first().attr("alt");
                    String attr = element.select("a").first().attr("href");
                    HomeItem homeItem = new HomeItem();
                    //homeItem.setTitle(title);
                    homeItem.setUrl(imageurl);
                    homeItem.setContent(attr);
                    homeItem.save();
                    mHomeItems.add(homeItem);
                }
                    }else {
               Log.i("czc", "loadFirstData: "+"快来我这里");
                mHomeItems = DataSupport.findAll(HomeItem.class);
            }
        }

        /**
         * 在doInBackground执行结束后调用
         */
        @Override
        protected void onPostExecute(String type) {
            super.onPostExecute(type);
            switch (type) {
                case ONE_DATA:
                    setAdapter();
                    break;
                case MORE_DATA:
                   // mHomeAdapter.addData(mNewHomeItems);
                    mHomeAdapter.loadMoreComplete();
                    break;
                default:
                    break;


            }
        }

    }
}
