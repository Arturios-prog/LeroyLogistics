package com.example.leroylogistics.data.DB;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.leroylogistics.R;
import com.example.leroylogistics.data.model.Good;
import com.example.leroylogistics.data.model.Worker;

import java.util.List;

public class GoodActivity extends AppCompatActivity {

    private static final int DB_DELETE_RECORD = 1;
    private static final int DB_EDIT_RECORD = 2;
    ListView listView;
    List<Good> goodList;
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
        listView = (ListView) findViewById(R.id.lvDataGood);
        registerForContextMenu(listView);
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDataBaseInfo();
        goodList = dbHelper.getAllGoods();
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
        listView.setAdapter(scAdapter);
    }

    public void addGood(View view) {
        Intent intent = new Intent(GoodActivity.this, GoodEditorActivity.class);
        startActivity(intent);
    }

    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(Menu.NONE, DB_DELETE_RECORD, Menu.NONE, R.string.delete_record);
        menu.add(Menu.NONE, DB_EDIT_RECORD, Menu.NONE, R.string.edit_record);
    }

    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case DB_DELETE_RECORD:
                // получаем из пункта контекстного меню данные по пункту списка
                // извлекаем id записи и удаляем соответствующую запись в БД
                db.delete(DBData.GoodEntry.GOOD_TABLE_NAME, DBData.GoodEntry.COLUMN_ID + " = " + acmi.id, null);
                // обновляем курсор
                cursor.requery();
                return true;
            case DB_EDIT_RECORD:
                for (Good good : goodList) {
                    if (acmi.id == good.getId()) {
                        Intent intent_edit = new Intent(getApplicationContext(), GoodEditorActivity.class);
                        intent_edit.putExtra("goodId", good.getId());
                        intent_edit.putExtra("goodCode", good.getCode());
                        intent_edit.putExtra("goodName", good.getName());
                        intent_edit.putExtra("goodLocation", good.getLocation());
                        intent_edit.putExtra("goodQuantity", good.getQuantity());
                        intent_edit.putExtra("goodMinimalRemain", good.getMinimalRemain());
                        startActivity(intent_edit);
                    }
                }
        }
        return super.onContextItemSelected(item);
    }
}
