package com.example.leroylogistics.data.DB;

import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import com.example.leroylogistics.R;
import com.example.leroylogistics.data.model.Good;

import java.util.List;

public class GoodEditorActivity extends AppCompatActivity {
    private EditText editTextCode;
    private EditText editTextName;
    private EditText editTextLocation;
    private EditText editTextQuantity;
    private EditText editTextMinimalRemain;

    DBHelper dbHelper;
    private List<Good> goodList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_editor);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        dbHelper = new DBHelper(this);
        editTextCode = (EditText) findViewById(R.id.edit_good_code);
        editTextName = (EditText) findViewById(R.id.edit_good_name);
        editTextLocation = (EditText) findViewById(R.id.edit_good_location);
        editTextQuantity = (EditText) findViewById(R.id.edit_good_quantity);
        editTextMinimalRemain = (EditText) findViewById(R.id.edit_good_minimal_remain);
    }

    @Override
    protected void onStart() {
        super.onStart();
        goodList = dbHelper.getAllGoods();

        Bundle arguments = getIntent().getExtras();

        for (Good good : goodList){
            try {
                String id = arguments.get("goodId").toString();
                if (Integer.toString(good.getId()).equals(id)){
                    String codeIntent = arguments.get("goodCode").toString();
                    String nameIntent = arguments.get("goodName").toString();
                    String locationIntent = arguments.get("goodLocation").toString();
                    String QuantityIntent = arguments.get("goodQuantity").toString();
                    String MinimalRemainIntent = arguments.get("goodMinimalRemain").toString();
                    String currentWorkerLevel = arguments.get("currentWorkerLevel").toString();
                    editTextCode.setText(codeIntent);
                    editTextName.setText(nameIntent);
                    editTextLocation.setText(locationIntent);
                    editTextQuantity.setText(QuantityIntent);
                    editTextMinimalRemain.setText(MinimalRemainIntent);
                    if (currentWorkerLevel.equals("Частичный")){
                        editTextCode.setEnabled(false);
                        editTextName.setEnabled(false);
                        editTextLocation.setEnabled(false);
                        editTextMinimalRemain.setEnabled(false);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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
                goodList = dbHelper.getAllGoods();
                Intent intent = getIntent();
                try {
                    int id = intent.getExtras().getInt("goodId");
                    for (Good good : goodList){
                        if (good.getId() == id) {
                            saveGood(id);
                            finish();
                            return true;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                insertGood();
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

    private void saveGood(int id) {
        // Считываем данные из текстовых полей
        String code = editTextCode.getText().toString().trim();
        String name = editTextName.getText().toString().trim();
        String location = editTextLocation.getText().toString().trim();
        String quantity = editTextQuantity.getText().toString().trim();
        String minimalRemain = editTextMinimalRemain.getText().toString().trim();


        DBHelper mDbHelper = new DBHelper(this);

        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DBData.GoodEntry.COLUMN_CODE, code);
        values.put(DBData.GoodEntry.COLUMN_NAME, name);
        values.put(DBData.GoodEntry.COLUMN_LOCATION, location);
        values.put(DBData.GoodEntry.COLUMN_QUANTITY, quantity);
        values.put(DBData.GoodEntry.COLUMN_MINIMAL_REMAIN, minimalRemain);
        // Вставляем новый ряд в базу данных и запоминаем его идентификатор
        long newRowId = db.update(DBData.GoodEntry.GOOD_TABLE_NAME, values, "_id=" + id, null);

        // Выводим сообщение в успешном случае или при ошибке
        if (newRowId == -1) {
            // Если ID  -1, значит произошла ошибка
            Toast.makeText(this, "Ошибка при сохранении товара", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Товар сохранен под номером: " + newRowId, Toast.LENGTH_SHORT).show();
        }
    }

    private void insertGood() {
        // Считываем данные из текстовых полей
        String code = editTextCode.getText().toString().trim();
        String name = editTextName.getText().toString().trim();
        String location = editTextLocation.getText().toString().trim();
        String quantity = editTextQuantity.getText().toString().trim();
        String minimalRemain = editTextMinimalRemain.getText().toString().trim();


        DBHelper mDbHelper = new DBHelper(this);

        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DBData.GoodEntry.COLUMN_CODE, code);
        values.put(DBData.GoodEntry.COLUMN_NAME, name);
        values.put(DBData.GoodEntry.COLUMN_LOCATION, location);
        values.put(DBData.GoodEntry.COLUMN_QUANTITY, quantity);
        values.put(DBData.GoodEntry.COLUMN_MINIMAL_REMAIN, minimalRemain);
        // Вставляем новый ряд в базу данных и запоминаем его идентификатор
        long newRowId = db.insert(DBData.GoodEntry.GOOD_TABLE_NAME, null, values);

        // Выводим сообщение в успешном случае или при ошибке
        if (newRowId == -1) {
            // Если ID  -1, значит произошла ошибка
            Toast.makeText(this, "Ошибка при заведении товара", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Товар заведён под номером: " + newRowId, Toast.LENGTH_SHORT).show();
        }
    }
}
