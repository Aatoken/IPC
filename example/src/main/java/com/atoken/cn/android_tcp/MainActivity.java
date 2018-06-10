package com.atoken.cn.android_tcp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.atoken.cn.android_tcp.activity.SecondActivity;
import com.atoken.cn.android_tcp.activity.UserManager;

public class MainActivity extends AppCompatActivity {

    private static final String TAG="UserManager";
    Button  btn_second;
    TextView tv_main;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_second=(Button)findViewById(R.id.btn_second);
        UserManager.mUerName="main";
        tv_main=(TextView)findViewById(R.id.tv_main);
        tv_main.setText(UserManager.mUerName);



        btn_second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });
    }


}
