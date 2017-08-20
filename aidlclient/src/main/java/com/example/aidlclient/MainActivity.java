package com.example.aidlclient;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.example.aidldemo.Book;
import com.example.aidldemo.IBookManager;
import java.util.List;

public class MainActivity extends Activity {
    private static final String TAG = "MainActivity";
    private IBookManager mBookManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e(TAG,"onCreate()...");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e(TAG,"onServiceConnected()...name = " + name + " service = " + service);
            mBookManager = IBookManager.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e(TAG,"onServiceDisconnected()...name = " + name);
            mBookManager = null;
        }
    };

    private void initView(){
        Button bindServiceBtn = (Button) findViewById(R.id.bind_service);
        final Button unbindServiceBtn = (Button) findViewById(R.id.unbind_service);
        Button queryBtn = (Button) findViewById(R.id.query_book_list);
        Button addBookBtn = (Button) findViewById(R.id.add_book);
        bindServiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("com.example.BOOK_MANAGER_SERVICE");
                intent.setPackage("com.example.aidlserver");
                Log.e(TAG,"bindService()...intent = " + intent);
                bindService(intent,mConnection, Context.BIND_AUTO_CREATE);
            }
        });

        unbindServiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG,"unbindService()..");
                unbindService(mConnection);
            }
        });

        queryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                queryBookList();
            }
        });

        addBookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBook();
            }
        });
    }

    private void queryBookList(){
        if (mBookManager != null){
            try {
                List<Book> bookList = mBookManager.getBookList();
                Log.e(TAG,"queryBookList()...bookList size = " + bookList.size());
                printBookList(bookList);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }else {
            Toast.makeText(MainActivity.this,"Service is not connected，please connect the service first",
                    Toast.LENGTH_LONG).show();
        }
    }

    private void addBook(){

        if (mBookManager != null){
            try {
                int bookId = mBookManager.getBookList().size() + 1;
                Book newBook = new Book(bookId,"new Book #" + bookId);
                Log.e(TAG,"addBook()...newBook = " + newBook);
                mBookManager.addBook(newBook);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    private void printBookList(List<Book> list){
        for (Book book : list){
            Log.e(TAG," book is " + book);
        }
    }

    @Override
    protected void onDestroy() {
        Log.e(TAG,"onDestroy()...");
        super.onDestroy();
    }
}
