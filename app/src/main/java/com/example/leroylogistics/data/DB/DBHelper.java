package com.example.leroylogistics.data.DB;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.leroylogistics.data.model.Worker;
import com.example.leroylogistics.data.DB.DBData.*;

import java.util.ArrayList;
import java.util.List;

import static com.example.leroylogistics.data.DB.DBData.DB_NAME;
import static com.example.leroylogistics.data.DB.DBData.DB_VERSION;

public class DBHelper extends SQLiteOpenHelper {

    private static final String CREATE_TABLE_WORKER =
            "create table " + WorkerEntry.WORKER_TABLE_NAME + "(" +
                    WorkerEntry.COLUMN_ID + " integer primary key autoincrement, " +
                    WorkerEntry.COLUMN_CODE + " text not null, " +
                    WorkerEntry.COLUMN_INITIALS + " text, " +
                    WorkerEntry.COLUMN_LEVEL + " text not null" +
                    ");";
    public static final String CREATE_TABLE_GOOD =
            "create table " + GoodEntry.GOOD_TABLE_NAME + "(" +
                    GoodEntry.COLUMN_ID + " integer primary key autoincrement, " +
                    GoodEntry.COLUMN_CODE + " text not null, " +
                    GoodEntry.COLUMN_NAME + " text not null, " +
                    GoodEntry.COLUMN_QUANTITY + " text not null, " +
                    GoodEntry.COLUMN_LOCATION + " text not null, " +
                    GoodEntry.COLUMN_MINIMAL_REMAIN + " text not null" +
                    ");";
    public static final String ADD_ADMIN = "INSERT INTO " + WorkerEntry.WORKER_TABLE_NAME +
            "(" + WorkerEntry.COLUMN_CODE +", " + WorkerEntry.COLUMN_INITIALS + ")"+ " values "
            + "(" + "'0000', " + "'Admin'" + ");";

    public DBHelper(Context context ) {
        super(context, DB_NAME, null, DB_VERSION);

    }

    // создаем и заполняем БД
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_WORKER);
        db.execSQL(CREATE_TABLE_GOOD);
        //db.execSQL(ADD_ADMIN);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + WorkerEntry.WORKER_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + GoodEntry.GOOD_TABLE_NAME);
        onCreate(db);
    }

    public List<Worker> getAllWorkerCodes() {
        SQLiteDatabase db = this.getReadableDatabase();

        List<Worker> workerCodeList = new ArrayList<>();

        String selectAllWorkerCodes = "SELECT " + WorkerEntry.COLUMN_CODE + " FROM " + WorkerEntry.WORKER_TABLE_NAME;
        Cursor cursor = db.rawQuery(selectAllWorkerCodes, null);
        if (cursor.moveToFirst()) {
            try {
                do {
                    Worker worker = new Worker();
                    worker.setCode(cursor.getString(0));

                    workerCodeList.add(worker);
                } while (cursor.moveToNext());
            } finally {
                cursor.close();
            }

        }
        return workerCodeList;
    }
    
}
