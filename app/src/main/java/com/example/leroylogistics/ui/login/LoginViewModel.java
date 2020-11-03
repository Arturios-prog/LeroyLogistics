package com.example.leroylogistics.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.util.Log;

import com.example.leroylogistics.data.LoginRepository;
import com.example.leroylogistics.data.Result;
import com.example.leroylogistics.data.model.LoggedInUser;
import com.example.leroylogistics.R;
import com.example.leroylogistics.data.model.Worker;

import java.util.List;

public class LoginViewModel extends ViewModel {

    private static final String TAG = "codes";
    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
    private LoginRepository loginRepository;

    LoginViewModel(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }

    public void login(String username, String password) {
        // can be launched in a separate asynchronous job
        Result<LoggedInUser> result = loginRepository.login(username, password);

        if (result instanceof Result.Success) {
            LoggedInUser data = ((Result.Success<LoggedInUser>) result).getData();
            loginResult.setValue(new LoginResult(new LoggedInUserView(data.getDisplayName())));
        } else {
            loginResult.setValue(new LoginResult(R.string.login_failed));
        }
    }

    public void loginDataChanged(List<Worker> workerCodesList, String username, String password) {
        if (isUserNameValid(workerCodesList, username) == 0) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_username, null));
        }
        /*else if (isUserNameValid(workerCodesList, username) == 1){
            loginFormState.setValue(new LoginFormState(R.string.login_worker, null));
        }
        else if (isUserNameValid(workerCodesList, username) == 2){
            loginFormState.setValue(new LoginFormState(R.string.login_admin, null));
        }*/
        else if (!isPasswordValid(password)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
        } else {
            loginFormState.setValue(new LoginFormState(true));
        }
    }

    // A placeholder username validation check
    private int isUserNameValid(List<Worker> workerCodesList, String username) {

        if (username != null) {
            if (workerCodesList != null) {
                for (Worker worker : workerCodesList) {
                    Log.d(TAG, "Проверяю код " + worker.getCode() + " и " + username);
                    if (worker.getCode().equals(username)) {
                        Log.d(TAG, "Есть совпадение!");
                        return 1;
                    }
                }
            }
        }
        if (username.equals("0000")){
            Log.d(TAG, "Есть совпадение!");
            return 2;
        }
        else{
            return 0;
        }
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }


}