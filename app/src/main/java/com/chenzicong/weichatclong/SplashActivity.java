package com.chenzicong.weichatclong;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import static android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN;


public class SplashActivity extends AppCompatActivity {
    private static final int ENTERLOGIN = 1;

      Handler handler  = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if(msg.what==ENTERLOGIN) {
                    enterLogin();
                }
            }
        };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        //去除状态栏，设置为全屏
      getWindow().setFlags(FLAG_FULLSCREEN, FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        new Thread(new Runnable() {

            private Message mMsg;
            @Override
            public void run() {
        try{
            mMsg =Message.obtain();
            //静静的观赏两秒
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mMsg.what =ENTERLOGIN;
                handler.sendMessage(mMsg);
            }
        }).start();

    }
    public void enterLogin(){
        Intent intent =  new Intent(this,LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
