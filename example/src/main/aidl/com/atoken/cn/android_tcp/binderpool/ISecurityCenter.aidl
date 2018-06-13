// ISecurityCenter.aidl
package com.atoken.cn.android_tcp.binderpool;

// Declare any non-default types here with import statements

interface ISecurityCenter {
   String encrypt(String content);
   String decrypt(String password);
}
