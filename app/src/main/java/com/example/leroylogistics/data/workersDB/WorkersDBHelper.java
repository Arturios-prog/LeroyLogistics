package com.example.leroylogistics.data.workersDB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.leroylogistics.data.workersDB.WorkersDBData.*;

public class WorkersDBHelper extends SQLiteOpenHelper {

    private static final String DB_CREATE =
            "create table " + GuestEntry.DB_TABLE + "(" +
                    GuestEntry.COLUMN_ID + " integer primary key autoincrement, " +
                    GuestEntry.COLUMN_INITIALS + " text, " +
                    GuestEntry.COLUMN_LEVEL + " text not null" +
                    ");";

    public WorkersDBHelper(Context context ) {
        super(context, GuestEntry.DB_NAME, null, GuestEntry.DB_VERSION);

    }

    // создаем и заполняем БД
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DB_CREATE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + GuestEntry.DB_TABLE);
        onCreate(db);
    }

}
