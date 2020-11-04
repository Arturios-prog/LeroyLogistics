package com.example.leroylogistics.data.DB;

public class DBData {

    public static final String DB_NAME = "mydb";
    public static final int DB_VERSION = 10;

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
        public static final String COLUMN_QUANTITY = "amount";
        public static final String COLUMN_MINIMAL_REMAIN = "minimal_remain";
    }
}