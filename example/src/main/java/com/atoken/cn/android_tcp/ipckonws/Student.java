package com.atoken.cn.android_tcp.ipckonws;

import android.os.Parcel;
import android.os.Parcelable;



public class Student implements Parcelable {
    public int stuId;
    public int stuAge;
    public String stuName;
    public Son son;


    protected Student(Parcel in) {
        stuId = in.readInt();
        stuAge = in.readInt();
        stuName = in.readString();
        son = in.readParcelable(Son.class.getClassLoader());
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

    public Student() {
    }

    public Student(int stuId, int stuAge, String stuName, Son son) {
        this.stuId = stuId;
        this.stuAge = stuAge;
        this.stuName = stuName;
        this.son = son;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(stuId);
        dest.writeInt(stuAge);
        dest.writeString(stuName);
        dest.writeParcelable(son, flags);
    }

    @Override
    public String toString() {
        return "Student{" +
                "stuId=" + stuId +
                ", stuAge=" + stuAge +
                ", stuName='" + stuName + '\'' +
                ", son=" + son +
                '}';
    }
}