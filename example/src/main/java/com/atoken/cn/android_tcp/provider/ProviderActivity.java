package com.atoken.cn.android_tcp.provider;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.atoken.cn.android_tcp.R;

/**
 * Created by Aatoken on 2018/6/12.
 */

public class ProviderActivity extends AppCompatActivity {

    private static final String TAG="ProviderActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete_provider);

        Uri bookuri = Uri.parse("content://com.atoken.BookProvider" + "/book");
        Uri useruri = Uri.parse("content://com.atoken.BookProvider" + "/user");

        bookQuery(bookuri,new String[]{"_id","name"});
        //userQuery(useruri,new String[]{"_id","name","sex"}

        bookinsert(bookuri);

        bookQuery(bookuri,new String[]{"_id","name"});
    }

    private void  bookinsert(Uri uri)
    {
        ContentValues values=new ContentValues();
        values.put("_id",6);
        values.put("name","极客");
        getContentResolver().insert(uri,values);
    }

    private void bookQuery(Uri uri, String[] projection) {
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);

        if(cursor!=null) {
            while (cursor.moveToNext()) {
                ProBook book = new ProBook();
                book.bookId = cursor.getInt(0);
                book.bookName = cursor.getString(1);

                Log.d(TAG, book.toString());
            }

            cursor.close();
        }


    }

    private void userQuery(Uri uri, String[] projection) {
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);

        if(cursor!=null) {
            while (cursor.moveToNext()) {
                ProUser user=new ProUser();
                user.userId = cursor.getInt(0);
                user.userName = cursor.getString(1);
                user.userSex=cursor.getInt(2)==1;
                Log.d(TAG, cursor.toString());
            }

            cursor.close();
        }


    }



}
