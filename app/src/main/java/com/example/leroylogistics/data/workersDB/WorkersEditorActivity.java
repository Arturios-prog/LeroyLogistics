package com.example.leroylogistics.data.workersDB;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.example.leroylogistics.data.workersDB.WorkersDBData.*;

import com.example.leroylogistics.R;

public class WorkersEditorActivity extends AppCompatActivity {
    private EditText mNameEditText;

    private Spinner spinner;

    /**
     * Уровень доступа для сотрудника. Возможные варианты:
     * 0 для -, 1 для частичного, 2 - для полного.
     */
    private int mLevel = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        mNameEditText = (EditText) findViewById(R.id.edit_guest_name);
        spinner = (Spinner) findViewById(R.id.spinner_gender);

        setupSpinner();
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
                        mLevel = WorkersDBData.GuestEntry.CONTROL_OFF; // нет доступа
                    } else if (selection.equals(getString(R.string.level_on))) {
//                        mGender = 1; // есть частичный доступ
                        mLevel = WorkersDBData.GuestEntry.CONTROL_ON; // Частичный доступ
                    } else {
//                        mGender = 2; // Полный доступ
                        mLevel = WorkersDBData.GuestEntry.CONTROL_FULL; // Полный доступ
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
                insertGuest();
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
    private void insertGuest() {
        // Считываем данные из текстовых полей
        String name = mNameEditText.getText().toString().trim();

        WorkersDBHelper mDbHelper = new WorkersDBHelper(this);

        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(GuestEntry.COLUMN_INITIALS, name);
        values.put(GuestEntry.COLUMN_LEVEL, levelIntToString(mLevel));
        // Вставляем новый ряд в базу данных и запоминаем его идентификатор
        long newRowId = db.insert(GuestEntry.DB_TABLE, null, values);

        // Выводим сообщение в успешном случае или при ошибке
        if (newRowId == -1) {
            // Если ID  -1, значит произошла ошибка
            Toast.makeText(this, "Ошибка при заведении гостя", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Гость заведён под номером: " + newRowId, Toast.LENGTH_SHORT).show();
        }
    }
}
