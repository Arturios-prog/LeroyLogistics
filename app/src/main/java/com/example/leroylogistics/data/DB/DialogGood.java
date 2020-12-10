package com.example.leroylogistics.data.DB;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;

import com.example.leroylogistics.R;
import com.example.leroylogistics.data.model.Good;
import com.example.leroylogistics.data.model.Worker;

import java.util.List;
/**
 * Данный класс отвечает за генерацию диалогового окна поиска товаров по коду
 */
public class DialogGood extends DialogFragment implements View.OnClickListener{

    private List<Good> goodList;
    private DBHelper dbHelper;
    private EditText editText;
    public String code;
    final String LOG_TAG = "myLogs";

    RefreshInterface refreshInterface;

    public void setRefreshInterface(RefreshInterface refreshInterface) {
        this.refreshInterface = refreshInterface;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().setTitle("Поиск по коду сотрудника");
        dbHelper = new DBHelper(getContext());
        View v = inflater.inflate(R.layout.dialog_worker, null);
        v.findViewById(R.id.btnYes).setOnClickListener(this);
        editText = v.findViewById(R.id.edit_worker_dialog_code);
        return v;
    }

    /** Получаем все коды товаров из базы данных, а затем сверяем каждый из них с введенным
     Если есть совпадение, присваиваем это код переменной code и обновляем список*/
    public void onClick(View v) {
        Log.d(LOG_TAG, "Dialog 1: " + ((Button) v).getText());
        goodList = dbHelper.getAllGoods();
        code = editText.getText().toString();
        for (Good good : goodList){
            if (good.getCode().equals(editText.getText().toString())){
                code = good.getCode();
            }
        }

        refreshInterface.refresh();
        code = null;
        dismiss();
    }

    public String getDialogCode() {
        return code;
    }
}
