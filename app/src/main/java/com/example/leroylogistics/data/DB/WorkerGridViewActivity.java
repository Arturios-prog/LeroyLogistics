/*package com.example.leroylogistics.data.workersDB;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.wid get.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.example.leroylogistics.R;

public class WorkerGridViewActivity extends Activity implements AdapterView.OnItemSelectedListener {

    private static final int CM_DELETE_ID = 1;
    private TextView mSelectText;
    private WorkerDataAdapter mAdapter;
    WorkersDBData db;
    Cursor cursor;
    SimpleCursorAdapter scAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workers_db);

        // открываем подключение к БД
        db = new WorkersDBData(this);
        db.open();

        // получаем курсор
        cursor = db.getAllData();
        startManagingCursor(cursor);

        // формируем столбцы сопоставления
        String[] from = new String[] { WorkersDBData.COLUMN_INITIALS};
        int[] to = new int[] { R.id.text1 };

        mSelectText = (TextView) findViewById(R.id.info);
        final GridView g = (GridView) findViewById(R.id.workersGridView);
        mAdapter = new WorkerDataAdapter(getApplicationContext(),
                R.layout.list__item);
        g.setAdapter(mAdapter);
        g.setOnItemSelectedListener(this);
        g.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                // TODO Auto-generated method stub
                mSelectText.setText("Выбранный элемент: "
                        + mAdapter.getItem(position));
            }
        });
        registerForContextMenu(g);
    }

    // обработка нажатия кнопки
    public void onButtonClick(View view) {
        // добавляем запись
        db.addRec("sometext " + (cursor.getCount() + 1));
        // обновляем курсор
        cursor.requery();
    }

    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, CM_DELETE_ID, 0, R.string.delete_record);
    }

    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == CM_DELETE_ID) {
            // получаем из пункта контекстного меню данные по пункту списка
            AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            // извлекаем id записи и удаляем соответствующую запись в БД
            db.delRec(acmi.id);
            // обновляем курсор
            cursor.requery();
            return true;
        }
        return super.onContextItemSelected(item);
    }

    protected void onDestroy() {
        super.onDestroy();
        // закрываем подключение при выходе
        db.close();
    }

    @Override
    public void onItemSelected (AdapterView < ? > parent, View v,int position,
                                long id){
        mSelectText.setText("Выбранный элемент: " + mAdapter.getItem(position));
    }

    @Override
    public void onNothingSelected (AdapterView < ? > parent){
        mSelectText.setText("Выбранный элемент: ничего");
    }
}*/

