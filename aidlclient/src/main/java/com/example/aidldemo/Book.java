package com.example.aidldemo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by bing on 2017/8/20.
 */

public class Book implements Parcelable {
    public int bookId;
    public String bookName;

    public Book(int bookId, String bookName){
        this.bookId = bookId;
        this.bookName = bookName;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(bookId);
        dest.writeString(bookName);
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel source) {
            return new Book(source);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    protected Book(Parcel in){
        bookId = in.readInt();
        bookName = in.readString();
    }

    @Override
    public String toString() {
        return bookId + " : " + bookName;
    }
}
