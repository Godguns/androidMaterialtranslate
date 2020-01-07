package com.example.ds6;



import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelper extends SQLiteOpenHelper{

    /**数据库名称*/
    private static final String DATABASE_NAME="MyAppDB";
    /**数据库版本*/
    private static final int DATABASE_VERSION=1;
    /** 创建数据表语句*/
    private static final String DDL_CREATE_TABLE_APPINFO="CREATE TABLE IF NOT EXISTS AppInfo (appId integer primary key autoincrement,appName text,appDescription text, remark text)";

    /**
     * 实例化数据库连接.
     * @param context
     */
    public DBOpenHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        /**初始化数据表*/
        this.getWritableDatabase().execSQL(DDL_CREATE_TABLE_APPINFO);

    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
    }
}

