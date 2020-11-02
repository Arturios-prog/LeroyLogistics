package com.example.leroylogistics.data.workersDB;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import androidx.appcompat.app.AppCompatActivity;

import com.example.leroylogistics.R;
import com.example.leroylogistics.data.workersDB.WorkersDBData.*;

public class WorkerListViewActivity extends AppCompatActivity {
    private static final int CM_DELETE_ID = 1;
    private WorkersDBHelper mDbHelper;
    ListView lvData;
    WorkersDBHelper dbHelper;
    SQLiteDatabase db;
    SimpleCursorAdapter scAdapter;
    Cursor cursor;

    /**
     * Called when the activity is first created.
     */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workers2_db);
        dbHelper = new WorkersDBHelper(this);
        //registerForContextMenu(lvData);
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDataBaseInfo();
    }

    private void displayDataBaseInfo() {

        db = dbHelper.getWritableDatabase();

        // формируем столбцы сопоставления
        String[] from = new String[]{WorkerEntry.COLUMN_CODE, WorkerEntry.COLUMN_INITIALS, WorkerEntry.COLUMN_LEVEL};
        int[] to = new int[]{R.id.textWorkerCode, R.id.tvText, R.id.tvLevel};

        cursor = db.query(WorkerEntry.WORKER_TABLE_NAME, null, null, null, null, null, null);
        startManagingCursor(cursor);

        // создааем адаптер и настраиваем список
            scAdapter = new SimpleCursorAdapter(this, R.layout.list_listview_item, cursor, from, to);
        lvData = (ListView) findViewById(R.id.lvData);
        lvData.setAdapter(scAdapter);
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    public void addWorker(View view) {
        Intent intent = new Intent(WorkerListViewActivity.this, WorkersEditorActivity.class);
        startActivity(intent);
    }
}
