package com.m2e.cs5540.autopresence.student_enrollment;

import android.app.LoaderManager;
import android.app.ProgressDialog;
import android.content.Loader;
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
import com.m2e.cs5540.autopresence.base.BaseActivity;
import com.m2e.cs5540.autopresence.database.DatabaseUtil;

public class StudentEnrollment extends BaseActivity implements View.OnClickListener,
        LoaderManager.LoaderCallbacks<AsyncLoaderStatus> {



    private DatabaseUtil databaseUtil = DatabaseUtil.getInstance();
    private static final String TAG = StudentEnrollment.class.getName();
    private EditText courseIdText;
    private Spinner termText;
    private Spinner yearText;
    private ArrayAdapter<CharSequence> spinnerAdapter1;
    private ArrayAdapter<CharSequence> spinnerAdapter2;
    private Button addButton;
    private String CIN = "305875887";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "$$$$$$ onCreate() Invoked... ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_enrollment);

        this.courseIdText = (EditText) findViewById(R.id.stuCourseId);


        this.termText = (Spinner) findViewById(R.id.stuTerm);
        spinnerAdapter1 = ArrayAdapter.createFromResource(this, R.array.term_array, android.R.layout.simple_spinner_item);
        spinnerAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        termText.setAdapter(spinnerAdapter1);


        this.yearText = (Spinner) findViewById(R.id.stuYear);
        spinnerAdapter2 = ArrayAdapter.createFromResource(this, R.array.year_array, android.R.layout.simple_spinner_item);
        spinnerAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearText.setAdapter(spinnerAdapter2);



       /* this.courseIdText = (EditText) findViewById(R.id.stuCourseId);

        this.termText = (Spinner) findViewById(R.id.stuTerm);
        spinnerAdapter1 = ArrayAdapter.createFromResource(this, R.array.term_array, android.R.layout.simple_spinner_item);
        spinnerAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        termText.setAdapter(spinnerAdapter1);

        this.yearText = (Spinner) findViewById(R.id.stuYear);
        spinnerAdapter2 = ArrayAdapter.createFromResource(this, R.array.year_array, android.R.layout.simple_spinner_item);
        spinnerAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearText.setAdapter(spinnerAdapter2);*/

        this.addButton = (Button) findViewById(R.id.btn_courseAdd);
        addButton.setOnClickListener(this);

    }

    private void studentCourseEnroll() {

        Log.i(TAG, "StudentCourseEnroll");
        addButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(StudentEnrollment.this, R.style.Theme_AppCompat_Light_DarkActionBar);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Enrolling courses for student...");
        progressDialog.show();

        // TODO: Implementing your own course enroll logic here.
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
                }, 3000);
        progressDialog.hide();
    }

    @Override
    public Loader<AsyncLoaderStatus> onCreateLoader(int id, Bundle args) {
        Log.i(TAG, "$$$$$$ StudentEnrollment.onCreateLoader called");
        return new EnrollmentAsyncTaskLoader(this, CIN, courseIdText,
                termText.getSelectedItem().toString(), yearText.getSelectedItem().toString());
    }

    @Override
    public void onLoadFinished(Loader<AsyncLoaderStatus> loader, AsyncLoaderStatus loaderStatus) {

        Log.i(TAG, "$$$$$$ StudentEnrollmentActivity.onLoadFinished called");
        if (loaderStatus.hasException()) {
            Toast.makeText(this, "Error " + loaderStatus.getExceptionMessage(),
                    Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Your course has been enrolled successfully, need to show on landing for Student.",
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onLoaderReset(Loader<AsyncLoaderStatus> loader) {

    }

    @Override
    public void onClick(View v) {

       /*   String courseId = courseIdText.getText().toString();
        String term = termText.getSelectedItem().toString();
        String year = yearText.getSelectedItem().toString();
        Boolean flag = false;

        if( courseId == null || courseId.isEmpty()) {
            Toast.makeText(this, "Enter a valid " + "course ID!", Toast.LENGTH_SHORT).show();
        }
        else {
//            studentcourseEnroll();
            //List<String> e = new ArrayList<>();
            List<String> e = databaseUtil.courseEnroll(CIN);
            System.out.println("fuck it"+e.size());
           if (e.size() > 0)
                flag = true;
            else
               Toast.makeText(this, "Permit not granted for" + courseId, Toast.LENGTH_SHORT).show();
            /*for (CourseEnrollment c : e) {
                System.out.println(c.getCIN());
                if(c.getCIN().equals(CIN) && c.getCourseId().equals(courseId)
                        && c.getTerm().equals(term) && c.getYear().equals(year)) {
                    flag = true;
                    break;
                }
            }*///end-of-for-loop
        //}

        //if(flag == true)

            studentCourseEnroll();


    }


}
