package com.atoken.cn.android_tcp.ipckonws;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Aatoken on 2018/6/10.
 */

public class Son implements Parcelable {
    public int bookId;
    public String bookName;

    protected Son(Parcel in) {
        bookId = in.readInt();
        bookName = in.readString();
    }

    public static final Creator<Son> CREATOR = new Creator<Son>() {
        @Override
        public Son createFromParcel(Parcel in) {
            return new Son(in);
        }

        @Override
        public Son[] newArray(int size) {
            return new Son[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(bookId);
        dest.writeString(bookName);
    }
}
