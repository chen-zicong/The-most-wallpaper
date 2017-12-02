package com.chenzicong.weichatclong.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ChenZiCong on 2017/11/26.
 */

public class LoginDatabase extends SQLiteOpenHelper {
    private static final String CREATE_USER ="create table user(" +
            "id integer primary key autoincrement ," +
            "user text ," +
            "password text )" ;
    private static final String DATEBASENAME = "appDatabase.db";

    private Context mContext;
    public LoginDatabase(Context context) {
        super(context, DATEBASENAME, null, 1);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
