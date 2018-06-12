package com.atoken.cn.android_tcp.provider;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Aatoken on 2018/6/12.
 */

public class BookProvider extends ContentProvider {

    private static final String TAG = "BookProvider";
    private static final String AUTHORITY = "com.atoken.BookProvider";
    private static final Uri BOOK_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/book");
    private static final Uri USER_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/user");
    private static final int BOOK_CONTENT_CODE = 0;
    private static final int USER_CONTENT_CODE = 1;

    private static final UriMatcher mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        mUriMatcher.addURI(AUTHORITY, "book", BOOK_CONTENT_CODE);
        mUriMatcher.addURI(AUTHORITY, "user", BOOK_CONTENT_CODE);
    }

    private Context mContext;
    private SQLiteDatabase mDB;

    private String getTableName(Uri uri) {
        String tableName = null;
        switch (mUriMatcher.match(uri)) {
            case BOOK_CONTENT_CODE:
                tableName=DbOpenHelper.BOOK_TABLE_NAME;
                break;
            case USER_CONTENT_CODE:
                tableName=DbOpenHelper.USER_TABLE_NAME;
                break;
            default:
                break;
        }
        return tableName;
    }


    @Override
    public boolean onCreate() {
        Log.d(TAG, "onCreate,current thread:" + Thread.currentThread().getName());



        mContext=getContext();
        //初始化创建数据库 ，在UI，项目中不推荐使用，这里是演示
        initProviderData();
        return true;
    }

    private void initProviderData() {
        mDB=new DbOpenHelper(mContext).getWritableDatabase();
        mDB.execSQL("delete from "+DbOpenHelper.BOOK_TABLE_NAME);
        mDB.execSQL("delete from "+DbOpenHelper.USER_TABLE_NAME);

        mDB.execSQL("insert into book values(1,'Android');");
        mDB.execSQL("insert into book values(2,'IOS');");
        mDB.execSQL("insert into book values(3,'Html5');");

        mDB.execSQL("insert into user values(1,'Tom',1);");
        mDB.execSQL("insert into user values(2,'java',2);");


    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri,
                        @Nullable String[] projection,
                        @Nullable String selection,
                        @Nullable String[] selectionArgs,
                        @Nullable String sortOrder) {
        Log.d(TAG, "query,current thread:" + Thread.currentThread().getName());

        //tableName
        String tableName = getTableName(uri);
        if(tableName==null)
        {
            throw new IllegalArgumentException("not exists!");
        }

        return mDB.query(tableName,projection,selection,
                selectionArgs,null,null,sortOrder,null);

    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        Log.d(TAG, "getType");
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        Log.d(TAG, "insert");

        //tableName
        String tableName = getTableName(uri);
        if(tableName==null)
        {
            throw new IllegalArgumentException("not exists!");
        }

        mDB.insert(tableName,null,values);
        mContext.getContentResolver().notifyChange(uri,null);
        return uri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        Log.d(TAG, "delete");
        String tableName = getTableName(uri);
        if(tableName==null)
        {
            throw new IllegalArgumentException("not exists!");
        }

        int count = mDB.delete(tableName, selection, selectionArgs);
        if(count>0)
        {
            mContext.getContentResolver().notifyChange(uri,null);
        }
        return count;

    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values,
                      @Nullable String selection,
                      @Nullable String[] selectionArgs) {
        Log.d(TAG, "update");

        String tableName = getTableName(uri);
        if(tableName==null)
        {
            throw new IllegalArgumentException("not exists!");
        }

        int count = mDB.update(tableName, values, selection, selectionArgs);

        if(count>0){
            mContext.getContentResolver().notifyChange(uri,null);
        }

        return count;
    }
}
