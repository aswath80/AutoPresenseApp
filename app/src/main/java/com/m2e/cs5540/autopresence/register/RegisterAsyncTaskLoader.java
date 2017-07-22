package com.m2e.cs5540.autopresence.register;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;
import android.widget.EditText;

import com.m2e.cs5540.autopresence.base.AsyncLoaderStatus;
import com.m2e.cs5540.autopresence.database.DatabaseUtil;
import com.m2e.cs5540.autopresence.vao.UserRegistration;

/**
 * Created by Kumar on 7/20/2017.
 */

public class RegisterAsyncTaskLoader extends AsyncTaskLoader<AsyncLoaderStatus> {

    private static final String TAG = RegisterAsyncTaskLoader.class.getName();
    private DatabaseUtil databaseUtil = DatabaseUtil.getInstance();

    private String name;
    private String email;
    private String cin;
    private String password;
    private String type;

    public RegisterAsyncTaskLoader(Context context, EditText nameText,
                                   EditText emailText, EditText cinText, EditText passwordText, String type) {

        super(context);
        this.name     = nameText.getText().toString();
        this.email    = emailText.getText().toString();
        this.cin      = cinText.getText().toString();
        this.password = passwordText.getText().toString();
        this.type     = type;

        onContentChanged();
        Log.d(TAG, "$$$$ RegisterAsyncTaskLoader created");
    }

    @Override
    protected void onStartLoading() {
        Log.d(TAG, "$$$$ RegisterAsyncTaskLoader onStartLoading");
        if (takeContentChanged()) {
            forceLoad();
        }
    }

    @Override
    public AsyncLoaderStatus loadInBackground() {
        AsyncLoaderStatus loaderStatus = new AsyncLoaderStatus();
        Log.d(TAG, "$$$$ RegisterAsyncTaskLoader loadInBackground");

        UserRegistration reg = new UserRegistration();
        reg.setName(name);
        reg.setCin(cin);
        reg.setEmail(email);
        reg.setRole(type);

        try {
            databaseUtil.updateUserRegistration(reg);
        } catch (Exception e) {
            loaderStatus.setException(e);
        }
        return loaderStatus;
    }

    @Override
    public void deliverResult(AsyncLoaderStatus data) {
        super.deliverResult(data);
        Log.d(TAG, "$$$$ RegisterAsyncTaskLoader deliverResult " + data);
    }
}
