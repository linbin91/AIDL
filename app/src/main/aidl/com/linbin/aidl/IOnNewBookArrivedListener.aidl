// IOnNewBookArrivedListener.aidl
package com.linbin.aidl;

// Declare any non-default types here with import statements

import com.linbin.aidl.Book;
interface IOnNewBookArrivedListener {
    void onNewBookArrived(in Book book);
}
