package com.chenzicong.weichatclong.activity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.text.SpannableString;
import android.text.Spanned;
import android.util.Property;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.chenzicong.weichatclong.beautifulTextUtils.MutableForegroundColorSpan;
import com.chenzicong.weichatclong.R;
import com.chenzicong.weichatclong.beautifulTextUtils.TypeWriterSpanGroup;
import com.chenzicong.weichatclong.database.LoginDatabase;

import static android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN;

/**
 * Created by ChenZiCong on 2017/11/26.
 */

public class LoginActivity extends AppCompatActivity {

    private EditText mEditText_user;
    private EditText mEditText_password;
    private Button mLoginButton;
    private TextView mForgetPassword;
    private TextView mRegister;
    private CheckBox mRemember;
    private SharedPreferences mSp;
    private SharedPreferences.Editor mEditor;

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    private TextView mTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(FLAG_FULLSCREEN, FLAG_FULLSCREEN);
        setContentView(R.layout.login_activity);
        //去除状态栏，设置为全屏
        initUI();
        initData();

    }

    private void initData() {
        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = mEditText_user.getText().toString();
                String password = mEditText_password.getText().toString();
                SQLiteDatabase mDatabase = new LoginDatabase(getApplicationContext()).getWritableDatabase();
                Cursor cursor = mDatabase.query("user", new String[]{"user", "password"}, "user=? and password=?", new String[]{user, password}, null, null, null);


                if (user.equals("") || password.equals("")) {
                    Toast.makeText(LoginActivity.this, "不能为空", Toast.LENGTH_SHORT).show();
                } else if (cursor.moveToNext()) {
                    mEditor = mSp.edit();
                    if (mRemember.isChecked()) {
                        mEditor.putBoolean("remember", true);
                        mEditor.putString("user", user);
                        mEditor.putString("password",password);
                    }else {
                        mEditor.clear();
                    }
                    mEditor.apply();
                    Intent intent = new Intent(LoginActivity.this, PagerActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this, "erro", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void initUI() {
        mSp = PreferenceManager.getDefaultSharedPreferences(this);
        mEditText_user = (EditText) findViewById(R.id.EditText_user);
        mEditText_password = (EditText) findViewById(R.id.EditText_password);
        mLoginButton = (Button) findViewById(R.id.loginButton);
        mForgetPassword = (TextView) findViewById(R.id.forgetPassword);
        mRegister = (TextView) findViewById(R.id.register);
        mRemember = (CheckBox) findViewById(R.id.Remember);
        mTitle = (TextView) findViewById(R.id.Title);

        boolean remember = mSp.getBoolean("remember", false);
        if(remember){
            mEditText_user.setText(mSp.getString("user",""));
            mEditText_password.setText(mSp.getString("password",""));
            mRemember.setChecked(true);
        }
        setText();
        


    }

    /**
     * 设置一个键盘键入的文字动画效果
     */
    private void setText() {
        String val = "Hello Again";
        final SpannableString spannableString = new SpannableString(val);
        // 添加Span
        final TypeWriterSpanGroup group = new TypeWriterSpanGroup(0);
        for (int index = 0; index <= val.length() - 1; index++) {
            MutableForegroundColorSpan span = new MutableForegroundColorSpan();
            group.addSpan(span);
            spannableString.setSpan(span, index, index + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        // 添加动画
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(group, TYPE_WRITER_GROUP_ALPHA_PROPERTY, 0.0f, 1.0f);
        objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //refresh
                mTitle.setText(spannableString);
            }
        });
        objectAnimator.setDuration(3000);
        objectAnimator.start();
    }

    /**
     * 作用于上面那个文字动画的  一个动画属性变化器
     */
    private static final Property<TypeWriterSpanGroup, Float> TYPE_WRITER_GROUP_ALPHA_PROPERTY =
            new Property<TypeWriterSpanGroup, Float>(Float.class, "TYPE_WRITER_GROUP_ALPHA_PROPERTY") {
                @Override
                public void set(TypeWriterSpanGroup spanGroup, Float value) {
                    spanGroup.setAlpha(value);
                }
                @Override
                public Float get(TypeWriterSpanGroup spanGroup) {
                    return spanGroup.getAlpha();
                }
            };
}
