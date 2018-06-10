package com.atoken.cn.android_tcp.util;

import android.os.Environment;

import java.io.File;

/**
 * Created by Aatoken on 2018/5/21.
 */

public class FileUtils {

    public static File getFile(String name) {
        //指向的是 fileManager
        File file = new File(Environment.getExternalStorageDirectory(), "Download/" + name);
        return file;
    }
}
