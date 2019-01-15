package com.example.qrcode.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.qrcode.MyApplication;
import com.example.qrcode.bean.ExpressScanHistory;

import java.util.ArrayList;
import java.util.List;

public class ExpressDBManager {
    private static ExpressDBManager instance;
    private ExpressDBHelper mDBHelper;
    private SQLiteDatabase mDatabase;

    private ExpressDBManager() {
        mDBHelper = new ExpressDBHelper(MyApplication.getInstance(), ExpressDBHelper.DB_NAME, null, ExpressDBHelper.DB_VERSION);
        mDatabase = mDBHelper.getWritableDatabase();
    }

    private static synchronized void syncInit() {
        if (null == instance) {
            instance = new ExpressDBManager();
        }
    }

    public static ExpressDBManager getInstance() {
        if (null == instance) {
            syncInit();
        }
        return instance;
    }

    public void insertExpress(ExpressScanHistory history) {
        if (existExpress(history)) {
            deleteExpress(history.express_num);
        }
        ContentValues values = new ContentValues();
        values.put(ExpressDBHelper.EXPRESS_NUM, history.express_num);
        mDatabase.insert(ExpressDBHelper.TABLE_NAME, null, values);
    }

    public void deleteExpress(String express_num) {
        mDatabase.delete(ExpressDBHelper.TABLE_NAME, ExpressDBHelper.EXPRESS_NUM + " = ?", new String[]{express_num});
    }

    public void updateExpress(ExpressScanHistory history) {
        ContentValues values = new ContentValues();
        values.put(ExpressDBHelper.EXPRESS_NUM, history.express_num);
        mDatabase.update(ExpressDBHelper.TABLE_NAME, values, ExpressDBHelper.EXPRESS_NUM + " = ?", new String[]{history.express_num});
    }

    public List<ExpressScanHistory> queryExpress() {
        Cursor cursor = mDatabase.query(ExpressDBHelper.TABLE_NAME, null, null, null, null, null, null);
        List<ExpressScanHistory> data = new ArrayList<>();
        if (null != cursor) {
            while (cursor.moveToNext()) {
                data.add(new ExpressScanHistory(cursor.getString(cursor.getColumnIndex(ExpressDBHelper.EXPRESS_NUM))));
            }
        }
        return data;
    }

    public boolean existExpress(ExpressScanHistory history) {
        Cursor cursor = mDatabase.query(ExpressDBHelper.TABLE_NAME, null, ExpressDBHelper.EXPRESS_NUM + " = ?", new String[]{history.express_num}, null, null, null);
        return cursor.getCount() > 0;
    }
}
