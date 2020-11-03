package com.example.leroylogistics.data.DB;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.leroylogistics.R;

public class GoodActivity extends AppCompatActivity {

    ListView listView;
    DBHelper dbHelper;
    SQLiteDatabase db;
    SimpleCursorAdapter scAdapter;
    Cursor cursor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_db);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        dbHelper = new DBHelper(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDataBaseInfo();
    }

    private void displayDataBaseInfo() {

        db = dbHelper.getWritableDatabase();

        // формируем столбцы сопоставления
        String[] from = new String[]{DBData.GoodEntry.COLUMN_CODE,
                DBData.GoodEntry.COLUMN_NAME, DBData.GoodEntry.COLUMN_LOCATION,
                DBData.GoodEntry.COLUMN_QUANTITY, DBData.GoodEntry.COLUMN_MINIMAL_REMAIN};
        int[] to = new int[]{R.id.textGoodCode, R.id.textGoodName, R.id.textGoodLocation,
                R.id.textGoodQuantity, R.id.textGoodMinimalRemain};

        cursor = db.query(DBData.GoodEntry.GOOD_TABLE_NAME, null, null, null, null, null, null);
        startManagingCursor(cursor);

        // создааем адаптер и настраиваем список
        scAdapter = new SimpleCursorAdapter(this, R.layout.list_goods_item, cursor, from, to);
        listView = (ListView) findViewById(R.id.lvDataGood);
        listView.setAdapter(scAdapter);
    }

    public void addGood(View view) {
        Intent intent = new Intent(GoodActivity.this, GoodEditorActivity.class);
        startActivity(intent);
    }

}
