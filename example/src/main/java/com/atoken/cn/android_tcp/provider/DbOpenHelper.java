package com.atoken.cn.android_tcp.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Aatoken on 2018/6/12.
 */

public class DbOpenHelper extends SQLiteOpenHelper {

    private static final String DB_NAME="book_provider.db";
    public static final String BOOK_TABLE_NAME="book";
    public static final String USER_TABLE_NAME="user";
    private static final int DB_VERSION=1;

    //图书和用户信息表
    private String create_book_table="create table if not exists " + BOOK_TABLE_NAME +
            "(_id integer primary key, name TEXT)";

    private String create_user_table="create table if not exists "+USER_TABLE_NAME
            +"(_id integer primary key,"+"name text,"+"sex int)";

    public DbOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(create_book_table);
        db.execSQL(create_user_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
