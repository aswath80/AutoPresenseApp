package com.m2e.cs5540.autopresence.courses;

import android.app.LoaderManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.m2e.cs5540.autopresence.R;
import com.m2e.cs5540.autopresence.base.AsyncLoaderStatus;
import com.m2e.cs5540.autopresence.base.BaseActivity;
import com.m2e.cs5540.autopresence.login.LoginActivity;
import com.m2e.cs5540.autopresence.permit.PermitActivity;

public class AddCourseActivity extends BaseActivity implements View.OnClickListener,
        LoaderManager.LoaderCallbacks<AsyncLoaderStatus> {

    private static final String TAG = AddCourseActivity.class.getName();

    private EditText courseId;
    private EditText courseName;
    private EditText classTime;
    private EditText classLocation;

    private Spinner semester;
    private ArrayAdapter<CharSequence> spinnerAdapter;
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.d(TAG, "$$$$$$ onCreate() Invoked... ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);

        this.courseId       = (EditText) findViewById(R.id.cId);
        this.courseName     = (EditText) findViewById(R.id.cName);
        this.classTime      = (EditText) findViewById(R.id.cTime);
        this.classLocation  = (EditText) findViewById(R.id.cLocation);

        this.semester   = (Spinner) findViewById(R.id.semester);
        spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.semester, android.R.layout.simple_spinner_dropdown_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        semester.setAdapter(spinnerAdapter);

        this.submitButton = (Button)findViewById(R.id.btn_Add_Course);
        submitButton.setOnClickListener(this);
    }

    @Override
    public Loader<AsyncLoaderStatus> onCreateLoader(int id, Bundle args) {
        Log.d(TAG, "$$$$$$ AddCourseActivity.onCreateLoader called");
        return new AddCourseAsyncTaskLoader(this, courseId, courseName, classLocation, classTime,
                semester.getSelectedItem().toString());
    }

    @Override
    public void onLoadFinished(Loader<AsyncLoaderStatus> loader, AsyncLoaderStatus data) {
    }

    @Override
    public void onLoaderReset(Loader<AsyncLoaderStatus> loader) {
    }

    @Override
    public void onClick(View v) {
        addCourse();
    }



    public void addCourse(){
        Log.d(TAG, "Adding Course");
        submitButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(AddCourseActivity.this,
                R.style.Theme_AppCompat_Light_DarkActionBar);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Adding Course...");
        progressDialog.show();

        getLoaderManager().initLoader(0, null, this);

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        progressDialog.dismiss();
                    }
                }, 3000);
        progressDialog.hide();
    }
}
