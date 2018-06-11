package com.atoken.cn.android_tcp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.atoken.cn.android_tcp.activity.SecondActivity;
import com.atoken.cn.android_tcp.activity.UserManager;
import com.atoken.cn.android_tcp.bundle.TargetBundle;
import com.atoken.cn.android_tcp.ipckonws.User;
import com.atoken.cn.android_tcp.sharefile.TargetFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "UserManager";
    private static final String SHAREFILE = "ShareFile";
    Button btn_second;
    TextView tv_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_second = (Button) findViewById(R.id.btn_second);
        tv_main = (TextView) findViewById(R.id.tv_main);

        //1.不同进程读取值
        //demo1();

        //2.序列化 Serializable
        //demo2();


        btn_second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //1.页面跳转
                //toSecond();

                //2.反序列化
                //writeobject();

                //3.bundle传值
                //postbundle();

                //4.使用文件共享
                postFile();
            }
        });
    }


    private void parcelableToFile() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                User user = new User(1, 24, "android");

                ObjectOutputStream out = null;

                try {
                    File file = new File(Environment.getExternalStorageDirectory(), "handler.txt");

                    out = new ObjectOutputStream(new
                            FileOutputStream(file));
                    out.writeObject(user);
                    Log.d(SHAREFILE, "persist user:" + user);

                    out.close();
                } catch (
                        IOException e)

                {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    private void postFile() {
        //1.在resume()方法执行
        Intent intent = new Intent(MainActivity.this, TargetFile.class);
        startActivity(intent);
        parcelableToFile();

    }


    private void postbundle() {
        Bundle bundle = new Bundle();
        bundle.putString("data", "Data form MainActivity!");
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, TargetBundle.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void writeobject() {
        try {
            //反序列化
            ObjectInputStream inputStream = new ObjectInputStream(
                    new FileInputStream("cache.txt"));
            User newuser = (User) inputStream.readObject();
            inputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void toSecond() {
        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
        startActivity(intent);
    }

    private void demo2() {

        try {
            //序列化
            User user = new User(1, 23, "Tom");
            ObjectOutputStream out = new ObjectOutputStream(
                    new FileOutputStream("cache.txt"));
            out.writeObject(user);
            out.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void demo1() {
        UserManager.mUerName = "main";
        tv_main.setText(UserManager.mUerName);
    }


}
