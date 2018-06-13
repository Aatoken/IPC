package com.atoken.cn.android_tcp.socket;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;


public class TcpServerService extends Service {

    private static final String TAG = "TcpServerService";
    //
    private boolean mIsServiceDesroryed = false;
    private String[] mDefinedMessage = new String[]
            {"为要寻一个明星",
                    "我有一个恋爱",
                    "月下雷峰影片",
                    "沪杭车中",
                    "石虎胡同七号",
                    "常州天宁寺闻礼忏声",
                    "月下待杜鹃不来"
            };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        new Thread(new TcpServer()).start();
        super.onCreate();
    }

    private class TcpServer implements Runnable {

        @Override
        public void run() {
            ServerSocket serverSocket = null;
            try {
                serverSocket = new ServerSocket(9567);

            } catch (IOException e) {
                Log.d(TAG, "not found 9567");
                e.printStackTrace();
                return;
            }
            while (!mIsServiceDesroryed) {

                try {
                    //接受客户端
                    final Socket client = serverSocket.accept();
                    Log.d(TAG, "connect client success!");

                    new Thread() {
                        @Override
                        public void run() {
                            try {
                                responseClient(client);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }.start();

                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d(TAG, "connect client failed!");
                }
            }

        }


    }

    private void responseClient(Socket client) throws IOException {

        //用于接受客户端信息
        BufferedReader in = new BufferedReader(new InputStreamReader(
                client.getInputStream()));
        PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
                client.getOutputStream())), true);

        out.println("欢迎来到聊天室");

        while (!mIsServiceDesroryed)
        {
            String str=in.readLine();
            Log.d(TAG,"msg from client:"+str);
            if(str==null)
            {
                //客户端断开连接
                break;
            }

            int i=new Random().nextInt(mDefinedMessage.length);
            String msg=mDefinedMessage[i];

            out.println(msg);
            Log.d(TAG,"send:"+msg);
        }

        System.out.println("client quit!");
        //关闭流
        out.close();
        in.close();
        client.close();

    }


}
