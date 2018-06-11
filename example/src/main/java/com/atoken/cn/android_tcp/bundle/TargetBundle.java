package com.atoken.cn.android_tcp.bundle;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.atoken.cn.android_tcp.R;

/**
 * Created by Aatoken on 2018/6/11.
 */

public class TargetBundle extends AppCompatActivity {

    TextView tv_getInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete_target);
        tv_getInfo=(TextView)findViewById(R.id.tv_getInfo);
        Bundle bundle = getIntent().getExtras();
        String data = bundle.getString("data");
        tv_getInfo.setText(data);
    }

}
