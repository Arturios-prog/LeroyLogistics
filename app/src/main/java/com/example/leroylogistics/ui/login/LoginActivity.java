package com.example.leroylogistics.ui.login;

import android.app.Activity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.leroylogistics.R;
//import com.example.leroylogistics.data.workersDB.WorkerGridViewActivity;
import com.example.leroylogistics.data.model.Worker;
import com.example.leroylogistics.data.DB.GoodActivity;
import com.example.leroylogistics.data.DB.WorkerActivity;
import com.example.leroylogistics.data.DB.DBHelper;

import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "codes";
    private LoginViewModel loginViewModel;
    public  int isAdmin = 0;
    private List<Worker> workerCodesList;
    private List<Worker> workerList;
    DBHelper dbHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginViewModel = ViewModelProviders.of(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.login);
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);

        //Подлючаем БД и берем оттуда коды сотрудников
        dbHelper = new DBHelper(this);

        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                //if (isAdmin == 0) usernameEditText.setError(getString(loginFormState.getUsernameError()));

                if (workerCodesList!= null) {
                    for (Worker worker : workerCodesList) {
                        if (worker.getCode().equals(usernameEditText.getText().toString())) {
                            isAdmin = 1;
                        } else if ("0000".equals(usernameEditText.getText().toString())) {
                            isAdmin = 2;
                            Log.d(TAG, "Присвоил админку");
                        }
                    }
                }
                if ("0000".equals(usernameEditText.getText().toString())) {
                    isAdmin = 2;
                    Log.d(TAG, "Присвоил админку");
                }

                if (loginFormState == null) {
                    return;
                }
                loginButton.setEnabled(loginFormState.isDataValid());
                if (loginFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });

        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {
                if (loginResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);
                if (loginResult.getError() != null) {
                    showLoginFailed(loginResult.getError());
                }
                if (loginResult.getSuccess() != null) {
                    //updateUiWithUser(loginResult.getSuccess());
                }
                setResult(Activity.RESULT_OK);

                //Complete and destroy login activity once successful
                if (isAdmin == 2) {
                    Intent intent = new Intent(getApplicationContext(), WorkerActivity.class);
                    Log.d(TAG, "Переходим в активность сотрудников");
                    startActivity(intent);
                }
                else if (isAdmin == 1){
                    workerList = dbHelper.getAllWorkers();
                    Intent intent = new Intent(getApplicationContext(), GoodActivity.class);
                    for (Worker worker : workerList){
                        if ( worker.getCode().equals(usernameEditText.getText().toString())){
                            intent.putExtra("currentWorkerLevel", worker.getLevel());
                            Log.d(TAG, "onChanged: Передал доступ" + worker.getLevel());
                        }
                    }

                    Log.d(TAG, "Переходим в активность товаров");
                    startActivity(intent);
                }
            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(workerCodesList, usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loginViewModel.login(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString());
                }
                return false;
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingProgressBar.setVisibility(View.VISIBLE);
                loginViewModel.login(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        });


    }

    private void updateUiWithUser(LoggedInUserView model) {
        String welcome = getString(R.string.welcome) + model.getDisplayName();
        // TODO : initiate successful logged in experience
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbHelper.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        workerCodesList = dbHelper.getAllWorkerCodes();

        for (Worker worker: workerCodesList){
            Log.d(TAG, "code " + worker.getCode());
        }
    }
}