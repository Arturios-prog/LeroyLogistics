package com.example.leroylogistics.data.DB;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import com.example.leroylogistics.data.DB.DBData.*;

import com.example.leroylogistics.R;
import com.example.leroylogistics.data.model.Worker;

import java.util.List;

public class WorkersEditorActivity extends AppCompatActivity {
    private static final String TAG = "worker";
    private EditText mNameEditText;
    private EditText mCodeEditText;
    private List<Worker> workerList;
    private Spinner spinner;
    private DBHelper dbHelper;

    /**
     * Уровень доступа для сотрудника. Возможные варианты:
     * 0 для -, 1 для частичного, 2 - для полного.
     */
    private int mLevel = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        dbHelper = new DBHelper(this);
        mNameEditText = (EditText) findViewById(R.id.edit_guest_name);
        mCodeEditText = (EditText) findViewById(R.id.edit_worker_code);
        spinner = (Spinner) findViewById(R.id.spinner_gender);
        setupSpinner();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: Запустил");
        workerList = dbHelper.getAllWorkers();
        Log.d(TAG, "onStart: Получил");
        Bundle arguments = getIntent().getExtras();
        Log.d(TAG, "onStart: получаю интент");
        for (Worker worker : workerList){
            try {
                String id = arguments.get("workerId").toString();
                Log.d(TAG, "onStart: получаю следующий id " + id);
                if (Integer.toString(worker.getId()).equals(id)){
                    String codeIntent = arguments.get("workerCode").toString();
                    String nameIntent = arguments.get("workerName").toString();
                    String level = arguments.get("workerLevel").toString();
                    mNameEditText.setText(nameIntent);
                    mCodeEditText.setText(codeIntent);
                    if (level.equals("Полный")) spinner.setSelection(2);
                    else if (level.equals("Частичный")) spinner.setSelection(1);
                    else if (level.equals("-")) spinner.setSelection(0);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * Настраиваем spinner для установки уровня доступа
     */
    private void setupSpinner() {

        ArrayAdapter genderSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_gender_options, android.R.layout.simple_spinner_item);

        genderSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        spinner.setAdapter(genderSpinnerAdapter);
        spinner.setSelection(2);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.level_off))) {
//                        mLevel = 0; // нет доступа
                        mLevel = WorkerEntry.CONTROL_OFF; // нет доступа
                    } else if (selection.equals(getString(R.string.level_on))) {
//                        mGender = 1; // есть частичный доступ
                        mLevel = WorkerEntry.CONTROL_ON; // Частичный доступ
                    } else {
//                        mGender = 2; // Полный доступ
                        mLevel = WorkerEntry.CONTROL_FULL; // Полный доступ
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mLevel = 2; // Unknown
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                Intent intent_save = getIntent();

                workerList = dbHelper.getAllWorkers();
                try {
                    int id = intent_save.getExtras().getInt("workerId");
                    for (Worker worker : workerList){
                        if (worker.getId() == id){
                            Log.d(TAG, "onOptionsItemSelected: Сохраняю изменения");
                            saveWorker(id);
                            finish();
                            return true;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.d(TAG, "Я вставлю сотрудника");
                insertWorker();
                // Закрываем активность
                finish();
                return true;
            // Respond to a click on the "Delete" menu option
            case R.id.action_delete:
                // Do nothing for now
                return true;
            // Respond to a click on the "Up" arrow button in the app bar
            case android.R.id.home:
                // Navigate back to parent activity (CatalogActivity)
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private String levelIntToString(int mLevel){
        String levelStr = "";
        switch (mLevel) {
            case 0:
                levelStr = "-";
                break;
            case 1:
                levelStr = "Частичный";
                break;
            default:
                levelStr = "Полный";
                break;
        }
        return levelStr;
    }
    /**
     * Сохраняем введенные данные в базе данных.
     */
    private void insertWorker() {
        // Считываем данные из текстовых полей
        String name = mNameEditText.getText().toString().trim();
        String code = mCodeEditText.getText().toString().trim();

        DBHelper mDbHelper = new DBHelper(this);

        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(WorkerEntry.COLUMN_CODE, code);
        values.put(WorkerEntry.COLUMN_INITIALS, name);
        values.put(WorkerEntry.COLUMN_LEVEL, levelIntToString(mLevel));
        // Вставляем новый ряд в базу данных и запоминаем его идентификатор
        long newRowId = db.insert(WorkerEntry.WORKER_TABLE_NAME, null, values);

        // Выводим сообщение в успешном случае или при ошибке
        if (newRowId == -1) {
            // Если ID  -1, значит произошла ошибка
            Toast.makeText(this, "Ошибка при заведении сотрудника", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Сотрудник заведён под номером: " + newRowId, Toast.LENGTH_SHORT).show();
        }
    }

    private void saveWorker(int id) {
        // Считываем данные из текстовых полей
        String name = mNameEditText.getText().toString().trim();
        String code = mCodeEditText.getText().toString().trim();
        ContentValues values = new ContentValues();

        values.put(WorkerEntry.COLUMN_CODE, code);
        values.put(WorkerEntry.COLUMN_INITIALS, name);
        values.put(WorkerEntry.COLUMN_LEVEL, levelIntToString(mLevel));

        DBHelper mDbHelper = new DBHelper(this);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        long newRowId = db.update(WorkerEntry.WORKER_TABLE_NAME, values, "_id=" + id, null);
        if (newRowId == -1) {
            // Если ID  -1, значит произошла ошибка
            Toast.makeText(this, "Ошибка при редактировании сотрудника", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Сотрудник отредактирован под номером: " + newRowId, Toast.LENGTH_SHORT).show();
        }
    }
}
