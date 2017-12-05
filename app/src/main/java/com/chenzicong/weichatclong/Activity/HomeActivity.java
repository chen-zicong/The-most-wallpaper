package com.chenzicong.weichatclong.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.Window;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chenzicong.weichatclong.Adapter.HomeAdapter;
import com.chenzicong.weichatclong.R;
import com.chenzicong.weichatclong.beans.HomeItem;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN;



public class HomeActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private List<HomeItem> mHomeItems;
    private List<HomeItem> mNewHomeItems;
    private static final String ONE_DATA = "one_date";
    private static final String MORE_DATA = "more_date";

    private HomeAdapter mHomeAdapter;
    private int mI = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(FLAG_FULLSCREEN, FLAG_FULLSCREEN);
        setContentView(R.layout.activity_home);
        mRecyclerView = (RecyclerView) findViewById(R.id.RecycleView);
        mRecyclerView.setLayoutManager(
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)); //瀑布流布局
        //启动后台线程
     new FetchItemsTask().execute(ONE_DATA);
     //   initData();


    }
/*
    private void initData() {

        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                mHomeItems = new ArrayList<>();
                Log.i("czc", "可怕哦: 进来了没有");
              String  mUrlq = "http://www.umei.cc/meinvtupian/xingganmeinv";
                Document mDoc = Jsoup.connect(mUrlq).timeout(10000).get();
                Elements mTotal = mDoc.select("div.TypeList");
                Elements mItems = mTotal.select("li");
                for (Element element : mItems) {

                    String imageurl = element.select("img").first().attr("src");
                    //String title = element.select("a").first().attr("alt");
                    String attr = element.select("a").first().attr("href");
                    HomeItem homeItem = new HomeItem();
                    //homeItem.setTitle(title);
                    homeItem.setUrl(imageurl);
                    homeItem.setContent(attr);
                    mHomeItems.add(homeItem);
                }
                e.onNext(ONE_DATA); //发送数据

            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).doOnNext(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                    setAdapter();
            }
        });
    }
*/

    private class FetchItemsTask extends AsyncTask<String, Void, String>

    {

        private String mUrlq;
        private Document mDoc;
        private Elements mTotal;
        private Elements mItems;
        private List<HomeItem> mList;
        private static final String ONE_DATA = "one_date";
        private static final String MORE_DATA = "more_date";


        @Override
        protected String doInBackground(String... params) {
            try {
                switch (params[0]) {
                    case ONE_DATA:
                        loadFirstData();
                        Log.i("czc", "doInBackground: "+params[0]);
                        break;

                    case MORE_DATA:
                        loadMoreData();
                        Log.i("czc", "doInBackground: "+params[0]);
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
         * @throws IOException
         */
    private void loadMoreData() throws IOException {
        mNewHomeItems = new ArrayList<>();
        mUrlq = "http://www.umei.cc/meinvtupian/" + mI + ".htm";

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
        mHomeItems = new ArrayList<>();
        Log.i("czc", "loadFirstData: 进来了没有");
        mUrlq = "http://www.umei.cc/meinvtupian/";
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
            mHomeItems.add(homeItem);
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
                mHomeAdapter.addData(mNewHomeItems);
                mHomeAdapter.loadMoreComplete();
                break;
            default:
                break;


        }
    }



        /**
         * 设置Adapter的一些配置
         * 添加到RecyclerView
         */
    }

    private void setAdapter() {
        mHomeAdapter = new HomeAdapter(mHomeItems, getApplicationContext());
        //设置动画,默认为渐隐
        //  mHomeAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        //设置动画重复播放
        //  mHomeAdapter.isFirstOnly(false);
        //获取header的布局
        //  View view = View.inflate(this, R.layout.header_view, null);
        //设置RECYCLERVIEW的HEADER
        //  mHomeAdapter.addHeaderView(view);

        //设置点击事件 ,需要在Adapter的add
        mHomeAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(HomeActivity.this, RV_content_Activity.class);
                intent.putExtra("Position", position);
                intent.putExtra("content", mHomeItems.get(position).getContent());
                startActivity(intent);
            }
        });

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

        mRecyclerView.setAdapter(mHomeAdapter);
    }




}


