package com.example.leroylogistics.data.workersDB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class WorkersDBData {
    public static final class GuestEntry{
        public static final String DB_NAME = "mydb";
        public static final int DB_VERSION = 4;
        public static final String DB_TABLE = "mytab";

        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_INITIALS = "txt";
        public static final String COLUMN_LEVEL = "txt2";

        public static final int CONTROL_OFF = 0;
        public static final int CONTROL_ON = 1;
        public static final int CONTROL_FULL = 2;
    }
}
