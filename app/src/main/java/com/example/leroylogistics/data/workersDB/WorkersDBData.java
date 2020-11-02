package com.example.leroylogistics.data.workersDB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class WorkersDBData {

    public static final String DB_NAME = "mydb";
    public static final int DB_VERSION = 7;

    public static final class WorkerEntry {

        public static final String WORKER_TABLE_NAME = "worker";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_CODE = "code";
        public static final String COLUMN_INITIALS = "initials";
        public static final String COLUMN_LEVEL = "level";

        public static final int CONTROL_OFF = 0;
        public static final int CONTROL_ON = 1;
        public static final int CONTROL_FULL = 2;
    }

    public static final class GoodEntry{
        public static final String GOOD_TABLE_NAME = "good";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_CODE = "good_code";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_LOCATION = "location";
        public static final String COLUMN_AMOUNT = "amount";
        public static final String COLUMN_MINIMAL_REMAIN = "minimal_remain";
    }
}
