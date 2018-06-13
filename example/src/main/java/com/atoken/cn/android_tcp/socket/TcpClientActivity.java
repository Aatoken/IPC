package com.atoken.cn.android_tcp.socket;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.atoken.cn.android_tcp.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;


public class TcpClientActivity extends AppCompatActivity {

    private static final String TAG = "TcpClientActivity";
    private Button btn_submit;
    private TextView tv_info;
    private EditText editText;

    private Socket mClientSocket;
    private PrintWriter mPrintWriter;

    private static final int MESSAGE_RECIVE_NEW_MSG = 1;
    private static final int MESSAGE_SOCKET_CONNECTED = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete_socket);

        initView();

        Intent intent = new Intent(this, TcpServerService.class);
        startService(intent);

        new Thread() {
            @Override
            public void run() {

                connectTcpServer();
            }
        }.start();
    }

    private void connectTcpServer() {
        Socket socket = null;
        while (socket == null) {
            try {
                socket = new Socket("localhost", 9567);
                mClientSocket = socket;
                mPrintWriter = new PrintWriter(new BufferedWriter(new
                        OutputStreamWriter(socket.getOutputStream())), true);

                mHandler.sendEmptyMessage(MESSAGE_SOCKET_CONNECTED);

                Log.d(TAG, "connect tcp server success!");

            } catch (IOException e) {
                e.printStackTrace();

                SystemClock.sleep(1000);
                Log.d(TAG, "connect tcp server failed！");
            }
        }

        try {
            //接收服务端的消息
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while (!TcpClientActivity.this.isFinishing()) {

                String msg = br.readLine();
                Log.d(TAG, "recive:" + msg);
                if (msg != null) {
                    String time = formatDatTime(System.currentTimeMillis());
                    String showedMsg = "server:" + time + ":" + msg + "\n";
                    //发送消息
                    mHandler.obtainMessage(MESSAGE_RECIVE_NEW_MSG, showedMsg)
                            .sendToTarget();
                }
            }


            Log.d(TAG, "quit....");
            mPrintWriter.close();
            br.close();
            socket.close();


        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_RECIVE_NEW_MSG:
                    tv_info.setText(tv_info.getText() + (String) msg.obj);
                    break;
                case MESSAGE_SOCKET_CONNECTED:
                    btn_submit.setEnabled(true);
                    break;
                default:
                    break;
            }
        }
    };


    private String formatDatTime(long time) {
        return new SimpleDateFormat("(HH:mm:ss)").format(new Date(time));
    }

    private void initView() {
        btn_submit = (Button) findViewById(R.id.tcp_btn_send);
        tv_info = (TextView) findViewById(R.id.tcp_tv);
        editText = (EditText) findViewById(R.id.tcp_edit_info);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String msg = editText.getText().toString();
                if (!TextUtils.isEmpty(msg) && mPrintWriter != null) {
                    mPrintWriter.println(msg);
                    editText.setText("");

                    String time = formatDatTime(System.currentTimeMillis());
                    String showedMsg = "client:" + time + ":" + msg + "\n";
                    tv_info.setText(tv_info.getText() + showedMsg);

                }

            }
        });
    }


    @Override
    protected void onDestroy() {

        if (mClientSocket != null) {
            try {
                mClientSocket.shutdownInput();
                mClientSocket.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        super.onDestroy();
    }
}
