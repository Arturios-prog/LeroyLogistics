package com.example.leroylogistics.data.DB;

import android.content.Intent;
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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.leroylogistics.R;
import com.example.leroylogistics.data.DB.DBData.*;
import com.example.leroylogistics.data.model.Worker;

import java.util.List;

public class WorkerActivity extends AppCompatActivity {
    private static final int DB_DELETE_RECORD = 1;
    private static final int DB_EDIT_RECORD = 2;
    private static final String TAG = "worker";
    public ListView lvData;
    Dialog dlg1;

    DBHelper dbHelper;
    SQLiteDatabase db;
    SimpleCursorAdapter scAdapter;
    Cursor cursor;
    List<Worker> workerList;
    long longId;

    /**
     * Called when the activity is first created.
     */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workers2_db);
        dbHelper = new DBHelper(this);
        lvData = (ListView) findViewById(R.id.lvData);
        registerForContextMenu(lvData);
        dlg1 = new Dialog();
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
                db.delete(WorkerEntry.WORKER_TABLE_NAME, WorkerEntry.COLUMN_ID + " = " + acmi.id, null);
                // обновляем курсор
                cursor.requery();
                return true;
            case DB_EDIT_RECORD:
                Log.d(TAG, "Выбрал редактировать");
                for (Worker worker: workerList){
                    Log.d(TAG, "Взял следующие данные: " + worker.getId() + " "
                            + worker.getCode()+ " " + worker.getName()+ " " + worker.getLevel());
                    Log.d(TAG, "Сопоставляю со следующим id: " + acmi.id);
                    if (acmi.id == worker.getId()){
                        Log.d(TAG, "Открыл следующего сотрудника: " + worker.getName());
                        Intent intent_edit = new Intent(getApplicationContext(), WorkersEditorActivity.class);
                        intent_edit.putExtra("workerId", worker.getId());
                        Log.d(TAG, "Отправил следующий id: " + worker.getId());
                        intent_edit.putExtra("workerCode", worker.getCode());
                        Log.d(TAG, "Отправил следующий код: " + worker.getCode());
                        intent_edit.putExtra("workerName", worker.getName());
                        Log.d(TAG, "Отправил следующий name: " + worker.getName());
                        intent_edit.putExtra("workerLevel", worker.getLevel());
                        Log.d(TAG, "Отправил следующий level: " + worker.getLevel());
                        startActivity(intent_edit);
                    }
                }

        }
        return super.onContextItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onResume() {
        super.onResume();
        workerList = dbHelper.getAllWorkers();
        displayDataBaseInfo();
    }

    private void displayDataBaseInfo() {
        db = dbHelper.getWritableDatabase();

        if (dlg1.getDialogCode() != null){
            for (Worker worker : workerList){
                if (dlg1.getDialogCode().equals(worker.getCode())){
                    String[] from = new String[]{WorkerEntry.COLUMN_CODE, WorkerEntry.COLUMN_INITIALS, WorkerEntry.COLUMN_LEVEL};
                    int[] to = new int[]{R.id.textWorkerCode, R.id.tvText, R.id.tvLevel};

                    cursor = db.query(WorkerEntry.WORKER_TABLE_NAME, null, "code = ?", new String[] {dlg1.getDialogCode()}, null, null, null);
                    // создааем адаптер и настраиваем список
                    scAdapter = new SimpleCursorAdapter(this, R.layout.list_worker_item, cursor, from, to);
                    lvData.setAdapter(scAdapter);
                    scAdapter.notifyDataSetChanged();
                }
            }
        }
        else {
            // формируем столбцы сопоставления
            String[] from = new String[]{WorkerEntry.COLUMN_CODE, WorkerEntry.COLUMN_INITIALS, WorkerEntry.COLUMN_LEVEL};
            int[] to = new int[]{R.id.textWorkerCode, R.id.tvText, R.id.tvLevel};

            cursor = db.query(WorkerEntry.WORKER_TABLE_NAME, null, null, null, null, null, null);
            startManagingCursor(cursor);

            // создааем адаптер и настраиваем список
            scAdapter = new SimpleCursorAdapter(this, R.layout.list_worker_item, cursor, from, to);
            lvData.setAdapter(scAdapter);
            scAdapter.notifyDataSetChanged();
        }
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    public void addWorker(View view) {
        Intent intent = new Intent(WorkerActivity.this, WorkersEditorActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_find, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        dlg1.show(getSupportFragmentManager(), "dlg1");
        return true;
    }
}
