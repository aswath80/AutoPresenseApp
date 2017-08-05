package com.m2e.cs5540.autopresence.student_enrollment;

import android.content.Context;
import android.content.AsyncTaskLoader;
import android.util.Log;
import android.widget.EditText;


import com.m2e.cs5540.autopresence.base.AsyncLoaderStatus;
import com.m2e.cs5540.autopresence.database.DatabaseUtil;

import com.m2e.cs5540.autopresence.vao.CourseEnrollment;


/**
 * Created by Ekta on 04-08-2017.
 */

public class EnrollmentAsyncTaskLoader extends AsyncTaskLoader<AsyncLoaderStatus> {
    private static final String TAG = EnrollmentAsyncTaskLoader.class.getName();
    private DatabaseUtil databaseUtil = DatabaseUtil.getInstance();
    private String CIN;
    private String cId;
    private String term;
    private String year;

    //private static final String TAG = RegisterAsyncTaskLoader.class.getName();
    //private DatabaseUtil databaseUtil = DatabaseUtil.getInstance();
    //private String name;
    //private String email;
    //private String cin;
    //private String password;
    //private String type;

    public  EnrollmentAsyncTaskLoader(Context context, String CIN, EditText courseId,
                                      String term, String year) {
        super(context);
        this.CIN = CIN;
        this.cId       = courseId.getText().toString();
        this.term      = term;
        this.year      = year;
        onContentChanged();
        Log.d(TAG, "$$$$ EnrollmentAsyncTaskLoader created");
    }

   /* public RegisterAsyncTaskLoader(Context context, EditText nameText,
                                   EditText emailText, EditText cinText, EditText passwordText, String type) {

        super(context);
        this.name     = nameText.getText().toString();
        this.email    = emailText.getText().toString();
        this.cin      = cinText.getText().toString();
        this.password = passwordText.getText().toString();
        this.type     = type;

        onContentChanged();
        Log.d(TAG, "$$$$ RegisterAsyncTaskLoader created");
    }*/

    @Override
    protected void onStartLoading() {
        Log.d(TAG, "$$$$ EnrollmentAsyncTaskLoader onStartLoading");
        if (takeContentChanged()) {
            forceLoad();
        }
    }

    @Override
    public AsyncLoaderStatus loadInBackground() {
        AsyncLoaderStatus loaderStatus = new AsyncLoaderStatus();
        Log.d(TAG, "$$$$ EnrollmentAsyncTaskLoader loadInBackground");

        CourseEnrollment enroll = new CourseEnrollment();
        enroll.setCin(CIN);
        enroll.setCourseId(cId);
        enroll.setTerm(term);
        enroll.setYear(year);

        //UserRegistration reg = new UserRegistration();
        //reg.setName(name);
        //reg.setCin(cin);
        //reg.setEmail(email);
        //reg.setRole(type);

        try {
            databaseUtil.studentCourseEnrollment(enroll);
            //databaseUtil.updateUserRegistration(reg);
        } catch (Exception e) {
            loaderStatus.setException(e);
        }
        return loaderStatus;
    }

    @Override
    public void deliverResult(AsyncLoaderStatus data) {
        super.deliverResult(data);
        Log.d(TAG, "$$$$ EnrollmentAsyncTaskLoader deliverResult " + data);
    }
}
