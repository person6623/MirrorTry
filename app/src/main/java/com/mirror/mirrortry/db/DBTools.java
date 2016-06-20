package com.mirror.mirrortry.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by dllo on 16/6/20.
 * 关于db方法的封装类
 */
public class DBTools {
    private SQLiteDatabase database;
    private SQLiteHelper helper;
    private Context context;

    public DBTools(Context context) {
        this.context = context;
        //创建库
        helper = new SQLiteHelper(context, DBValues.DB_NAME, null, 1);
        //获得写入权限
        database = helper.getWritableDatabase();
    }

    //添加数据
    public void addData(String dbName, DBBean dbBean) {
        //插入用键值对
        ContentValues contentValues = new ContentValues();
        //contentValues.put(列名,列值);

        database.insert(dbName, null, contentValues);

    }

    //删除数据
    public void delData(String dbName, DBBean dbBean) {
        //删除语句
        database.delete(dbName, DBValues.COLUMN_DATA_ID + "= ?", new String[]{dbBean.getTest()});
    }

    //更新数据
    public void upData(String dbName, DBBean dbBean) {
        //插入用键值对
        ContentValues contentValues = new ContentValues();
        //contentValues.put(列名,列值);

        database.update(dbName, contentValues, dbName + "= ?", new String[]{dbBean.getTest()});
    }

    //查找数据
    public void getData(String dbName, DBBean dbBean) {
        //方法1 精确查找
        Cursor cursor = database.rawQuery(
                "select * from " + DBValues.DB_NAME
                        + " where "
                        + DBValues.COLUMN_DATA_ID
                        + " = ?", new String[]{dbBean.getTest()});

        //方法2 全体获得
        Cursor cursor1 = database.query(DBValues.TABLE_NAME,
                null, null, null, null, null, null);
        while (cursor1.moveToNext()) {
        }
    }

}
