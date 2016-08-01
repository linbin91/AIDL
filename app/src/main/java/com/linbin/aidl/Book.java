package com.linbin.aidl;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016/8/1.
 */
public class Book implements Parcelable {

    public int id;
    public String name;

    public Book(int id,String name){
        this.id = id;
        this.name = name;
    }

    //注意写入变量和读取变量的顺序应该一致 不然得不到正确的结果
    protected Book(Parcel in) {
        id = in.readInt();
        name = in.readString();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    //注意读取变量和写入变量的顺序应该一致 不然得不到正确的结果
    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(id);
        out.writeString(name);
    }
}
