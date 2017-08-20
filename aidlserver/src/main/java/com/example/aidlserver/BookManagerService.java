package com.example.aidlserver;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import com.example.aidldemo.Book;
import com.example.aidldemo.IBookManager;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by bing on 2017/8/20.
 */

public class BookManagerService extends Service {
    private static final String TAG = "BookManagerService";

    private CopyOnWriteArrayList<Book> mBookList = new CopyOnWriteArrayList<Book>();

    private Binder mBinder = new IBookManager.Stub(){

        @Override
        public List<Book> getBookList() throws RemoteException {
            Log.e(TAG,"getBookList()...");
            return mBookList;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            Log.e(TAG,"addBook()...book = " + book);
            mBookList.add(book);
        }
    };

    @Override
    public void onCreate() {
        Log.e(TAG,"onCreate()...");
        super.onCreate();
        mBookList.add(new Book(1,"Android"));
        mBookList.add(new Book(2,"IOS"));
        mBookList.add(new Book(3,"Java"));
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.e(TAG,"onBind()...intent = " + intent);
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.e(TAG,"onUnbind()...intent = " + intent);
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        Log.e(TAG,"onDestroy()..." );
        super.onDestroy();
    }
}
