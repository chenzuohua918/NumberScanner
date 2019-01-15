package com.example.qrcode.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ExpressDBHelper extends SQLiteOpenHelper {
    // 数据库文件名
    public static final String DB_NAME = "express.db";
    // 数据库表名
    public static final String TABLE_NAME = "scan_history";
    // 数据库版本号
    public static final int DB_VERSION = 1;

    public static final String EXPRESS_NUM = "express_num";

    public ExpressDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // 建表
        String sql = "create table " +
                TABLE_NAME +
                "(_id integer primary key autoincrement, " +
                EXPRESS_NUM + " varchar" +
                ")";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {}
}
