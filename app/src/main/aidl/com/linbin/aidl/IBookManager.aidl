// IBookManager.aidl
package com.linbin.aidl;
import com.linbin.aidl.Book;
// Declare any non-default types here with import statements
import com.linbin.aidl.IOnNewBookArrivedListener;
interface IBookManager {
    List<Book> getBookList();
    void addBook(in Book book);
    void registerListener(IOnNewBookArrivedListener listener);
    void unRegitsterListener(IOnNewBookArrivedListener listener);

}
