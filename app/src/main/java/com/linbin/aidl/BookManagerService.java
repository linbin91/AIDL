package com.linbin.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/2.
 */
public class BookManagerService extends Service {

    private List<Book> mBookList = new ArrayList<>();
    private RemoteCallbackList<IOnNewBookArrivedListener> mListener = new RemoteCallbackList<>();
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mBookList.add(new Book(1, "疯狂java"));
        mBookList.add(new Book(2,"疯狂android"));

        new Thread(new ServiceWorker()).start();
    }

    private Binder mBinder = new IBookManager.Stub(){
        @Override
        public List<Book> getBookList() throws RemoteException {
            return mBookList;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            mBookList.add(book);
        }

        @Override
        public void registerListener(IOnNewBookArrivedListener listener) throws RemoteException {
            mListener.register(listener);
        }

        @Override
        public void unRegitsterListener(IOnNewBookArrivedListener listener) throws RemoteException {
           mListener.unregister(listener);
        }
    };

    private class ServiceWorker implements Runnable{

        @Override
        public void run() {
            while (true){
                try {
                    Thread.sleep(3000);
                }catch (InterruptedException e){

                }
                int bookID = mBookList.size() + 1;
                Book book = new Book(bookID,"new book" + bookID);
                onNewBookArrived(book);

            }
        }


    }

    private boolean mIsServiceDestored = false;
    @Override
    public void onDestroy() {
        super.onDestroy();
        mIsServiceDestored = true;
    }

    private void onNewBookArrived(Book book) {
        mBookList.add(book);
        final int n = mListener.beginBroadcast();
        for (int i = 0; i < n; i++){
            try {
                IOnNewBookArrivedListener listener = mListener.getBroadcastItem(i);
                if (listener != null){
                    listener.onNewBookArrived(book);
                }

            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        mListener.finishBroadcast();
    }
}
