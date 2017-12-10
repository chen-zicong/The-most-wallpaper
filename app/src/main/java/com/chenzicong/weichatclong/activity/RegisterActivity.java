package com.chenzicong.weichatclong.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.chenzicong.weichatclong.R;
import com.chenzicong.weichatclong.database.LoginDatabase;

public class RegisterActivity extends AppCompatActivity {

    private EditText mRepassword;
    private EditText mPassword;
    private EditText mUser;
    private Button mSignUp;
    private SQLiteDatabase mDatabase;

    //为了那该死的矢量图
    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register);
        initUI();
        initData();
    }

    private void initData() {
        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = mUser.getText().toString();
                String password = mPassword.getText().toString();
                String Repassword = mRepassword.getText().toString();
                if (userName.equals("") || password.equals("") || Repassword.equals("")) {
                    Toast.makeText(RegisterActivity.this, "不能为空", Toast.LENGTH_SHORT).show();
                } else if (!password.equals(Repassword)) {
                    Toast.makeText(RegisterActivity.this, "密码不一致", Toast.LENGTH_SHORT).show();
                } else {
                    mDatabase = new LoginDatabase(getApplicationContext()).getWritableDatabase();
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("user", userName);
                    contentValues.put("password", password);
                    mDatabase.insert("user", null, contentValues);
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);

                }
            }
        });
    }

    private void initUI() {
        mUser = (EditText) findViewById(R.id.EditText_user);
        mPassword = (EditText) findViewById(R.id.EditText_password);
        mRepassword = (EditText) findViewById(R.id.EditText_Repassword);
        mSignUp = (Button) findViewById(R.id.signupButton);

    }
}
