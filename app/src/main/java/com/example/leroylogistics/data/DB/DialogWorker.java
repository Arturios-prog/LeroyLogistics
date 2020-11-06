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
import com.example.leroylogistics.data.model.Worker;

import java.sql.Ref;
import java.util.List;

public class DialogWorker extends DialogFragment implements View.OnClickListener {
    private List<Worker> workerList;
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

    public void onClick(View v) {
        Log.d(LOG_TAG, "Dialog 1: " + ((Button) v).getText());
        workerList = dbHelper.getAllWorkers();
        code = "";
        for (Worker worker : workerList){
            if (worker.getCode().equals(editText.getText().toString())){
                code = worker.getCode();
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
