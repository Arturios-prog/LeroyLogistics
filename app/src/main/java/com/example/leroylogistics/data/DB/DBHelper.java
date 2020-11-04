package com.example.leroylogistics.data.DB;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.leroylogistics.data.model.Good;
import com.example.leroylogistics.data.model.Worker;
import com.example.leroylogistics.data.DB.DBData.*;

import java.util.ArrayList;
import java.util.List;

import static com.example.leroylogistics.data.DB.DBData.DB_NAME;
import static com.example.leroylogistics.data.DB.DBData.DB_VERSION;

public class DBHelper extends SQLiteOpenHelper {
    private SQLiteDatabase mDB;
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

    public List<Worker> getAllWorkers(){
        SQLiteDatabase db = this.getReadableDatabase();
        List<Worker> workerList = new ArrayList<>();

        String selectAllWorkers = "SELECT * " + "FROM " + WorkerEntry.WORKER_TABLE_NAME;
        Cursor cursor = db.rawQuery(selectAllWorkers, null);
        if(cursor.moveToFirst()){
            try{
                do{
                    Worker worker = new Worker();
                    worker.setId(cursor.getInt(0));
                    worker.setCode(cursor.getString(1));
                    worker.setName(cursor.getString(2));
                    worker.setLevel(cursor.getString(3));

                    workerList.add(worker);
                } while (cursor.moveToNext());
            } finally {
                cursor.close();
            }
        }
        return workerList;
    }

    public List<Good> getAllGoods(){
        SQLiteDatabase db = this.getReadableDatabase();
        List<Good> goodList = new ArrayList<>();

        String selectAllGoods = "SELECT * " + "FROM " + GoodEntry.GOOD_TABLE_NAME;
        Cursor cursor = db.rawQuery(selectAllGoods, null);
        if(cursor.moveToFirst()){
            try{
                do{
                    Good good = new Good();
                    good.setId(cursor.getInt(0));
                    good.setCode(cursor.getString(1));
                    good.setName(cursor.getString(2));
                    good.setLocation(cursor.getString(3));
                    good.setQuantity(cursor.getString(4));
                    good.setMinimalRemain(cursor.getString(5));

                    goodList.add(good);
                } while (cursor.moveToNext());
            } finally {
                cursor.close();
            }
        }
        return goodList;
    }

    /*public void deleteRecordWorker(long id){
        mDB.delete(WorkerEntry.WORKER_TABLE_NAME, WorkerEntry.COLUMN_ID + " = " + id, null);
    }

    public void deleteRecordGood(long id){
        mDB.delete(GoodEntry.GOOD_TABLE_NAME, GoodEntry.COLUMN_ID + " = " + id, null);
    }*/
    
}
