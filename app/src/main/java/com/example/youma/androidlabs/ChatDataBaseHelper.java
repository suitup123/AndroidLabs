package com.example.youma.androidlabs;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ChatDataBaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Message_Management";
    public static final int VERSION_NUM = 7;
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
        Log.i(ACTIVITY_NAME, "Calling onCreate");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1){
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
        Log.i(ACTIVITY_NAME, "Calling onUpgrade, oldVersion="+ i + "newVersion=" + i1);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }

    //get database by select statement, return the cursor
    public Cursor getData(String query){
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery(query,null);
    }

    public static String getTableName(){
        return TABLE_NAME;
    }
}
