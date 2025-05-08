package com.example.pingmonitor.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "ping_logs.db";
    private static final int DB_VERSION = 1;

    public static final String TABLE_LOGS = "logs";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_LOGS + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "timestamp INTEGER, " +
                "ip TEXT, " +
                "api TEXT, " +
                "icmpSuccess INTEGER, " +
                "httpSuccess INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGS);
        onCreate(db);
    }
}
