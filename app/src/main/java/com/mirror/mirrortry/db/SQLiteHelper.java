package com.mirror.mirrortry.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by dllo on 16/6/20.
 * db 创建方法
 */
public class SQLiteHelper extends SQLiteOpenHelper {

    //建表语句
    private static final String CREAT_TABLE = "create table " + DBValues.TABLE_NAME +
            " ( id integer primary key autoincrement ," + DBValues.COLUMN_DATA_ID + " text ,"
            + DBValues.COLUMN_DATA_CONTEXT + " text )";


    public SQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建表
        db.execSQL(CREAT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}
