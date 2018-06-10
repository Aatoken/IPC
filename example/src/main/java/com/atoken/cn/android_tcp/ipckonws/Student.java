package com.atoken.cn.android_tcp.ipckonws;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Aatoken on 2018/6/10.
 */

public class Student implements Parcelable {
    public int stuId;
    public int stuAge;
    public String stuName;
    public Book book;


    protected Student(Parcel in) {
        stuId = in.readInt();
        stuAge = in.readInt();
        stuName = in.readString();
        book = in.readParcelable(Book.class.getClassLoader());
    }

    public static final Creator<Student> CREATOR = new Creator<Student>() {
        @Override
        public Student createFromParcel(Parcel in) {
            return new Student(in);
        }

        @Override
        public Student[] newArray(int size) {
            return new Student[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(stuId);
        dest.writeInt(stuAge);
        dest.writeString(stuName);
        dest.writeParcelable(book, flags);
    }
}