package com.example.youma.androidlabs;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ChatDataBaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Message_Management";
    public static final int VERSION_NUM = 2;
    public static final String TABLE_NAME = "Messages";
    public static final String KEY_ID = "ID";
    public static final String KEY_MESSAGE = "message";
    private static final String SQL = "CREATE TABLE "+TABLE_NAME + " ( "+ KEY_ID +" integer primary key autoincrement, "+ KEY_MESSAGE +" TEXT);";
    private static final String ACTIVITY_NAME ="ChatDataBaseHelper";

    public ChatDataBaseHelper(Context ctx){
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(SQL);
        Log.i("ChatDataBaseHelper", "Calling onCreate");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1){
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
        Log.i("ChateDataBaseHelper", "Calling onUpgrade, oldVersion="+ i + "newVersion=" + i1);
    }
}
