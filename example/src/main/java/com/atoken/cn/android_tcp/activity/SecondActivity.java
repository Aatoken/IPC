package com.atoken.cn.android_tcp.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.atoken.cn.android_tcp.R;

/**
 * Created by Aatoken on 2018/6/10.
 */

public class SecondActivity extends AppCompatActivity {

    TextView tv_second;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        tv_second=(TextView)findViewById(R.id.tv_second);
        tv_second.setText(UserManager.mUerName);
    }
}
