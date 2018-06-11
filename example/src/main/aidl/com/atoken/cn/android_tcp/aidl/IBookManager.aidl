// IBookManager.aidl
package com.atoken.cn.android_tcp.aidl;

// Declare any non-default types here with import statements

//导包
import com.atoken.cn.android_tcp.aidl.Book;
interface IBookManager {

   List<Book> getBookList();
   void addBook(in Book book);

}
