package com.m2e.cs5540.autopresence.permit;

import android.app.LoaderManager;
import android.app.ProgressDialog;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.m2e.cs5540.autopresence.R;
import com.m2e.cs5540.autopresence.base.AsyncLoaderStatus;
import com.m2e.cs5540.autopresence.database.DatabaseUtil;
import com.m2e.cs5540.autopresence.vao.Permit;

import java.util.List;

public class PermitActivity extends AppCompatActivity implements View.OnClickListener,
        LoaderManager.LoaderCallbacks<AsyncLoaderStatus> {

    private static final String TAG = PermitActivity.class.getName();
    private DatabaseUtil databaseUtil = DatabaseUtil.getInstance();

    private EditText cin;
    private Button submitButton;

    private Spinner course;
    private ArrayAdapter<String> courseAdapter;
    private List<String> courses;

    private Spinner semester;
    private ArrayAdapter<CharSequence> semesterAdapter;

    private Spinner year;
    private ArrayAdapter<CharSequence> YearAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.d(TAG, "$$$$$$ onCreate() Invoked... ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_permit);

        this.cin       = (EditText) findViewById(R.id.studentCin);

        this.semester = (Spinner) findViewById(R.id.semester);
        semesterAdapter = ArrayAdapter.createFromResource(this, R.array.semester, android.R.layout.simple_spinner_item);
        semesterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        semester.setAdapter(semesterAdapter);

        this.year = (Spinner) findViewById(R.id.year);
        YearAdapter = ArrayAdapter.createFromResource(this, R.array.year, android.R.layout.simple_spinner_item);
        YearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        year.setAdapter(YearAdapter);

        try {
            courses = databaseUtil.getAllCourse();
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.course = (Spinner) findViewById(R.id.courseId);
        courseAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, courses);
        courseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        course.setAdapter(courseAdapter);

        this.submitButton = (Button)findViewById(R.id.assign);
        submitButton.setOnClickListener(this);

        //courseAdapter = ArrayAdapter.createFromResource(this, courses, android.R.layout.simple_spinner_item);
        //courseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


    }

    @Override
    public Loader<AsyncLoaderStatus> onCreateLoader(int id, Bundle args) {

        Log.d(TAG, "$$$$$$ RegisterActivity.onCreateLoader called");
        Permit permit = new Permit();
        permit.setCin(cin.getText().toString());
        permit.setSemester(semester.getSelectedItem().toString());
        //permit.setCourseId(course.getSelectedItem().toString());
        permit.setYear(year.getSelectedItem().toString());
        return new PermitAsyncTaskLoader(this, permit);
    }

    @Override
    public void onLoadFinished(Loader<AsyncLoaderStatus> loader, AsyncLoaderStatus loaderStatus) {
        Log.d(TAG, "$$$$$$ PermitActivity.onLoadFinished called");
        if (loaderStatus.hasException()) {
            Toast.makeText(this, "Error " + loaderStatus.getExceptionMessage(),
                    Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Permit has been added successfully.",
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onLoaderReset(Loader<AsyncLoaderStatus> loader) {

    }

    @Override
    public void onClick(View v) {
        addPermit();

    }

    public void addPermit(){

        Log.d(TAG, "addPermit");
        submitButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(PermitActivity.this, R.style.Theme_AppCompat_Light_DarkActionBar);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        // TODO: Implementing your own signup logic here.
        getLoaderManager().initLoader(0, null, this);

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        //onSignupSuccess();
                        // onSignupFailed();
                        progressDialog.dismiss();
                    }
                }, 1000);
        progressDialog.hide();
    }
}