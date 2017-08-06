package com.m2e.cs5540.autopresence.student_enrollment;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.m2e.cs5540.autopresence.R;
import com.m2e.cs5540.autopresence.base.AsyncLoaderStatus;
import com.m2e.cs5540.autopresence.base.BaseActivity;
import com.m2e.cs5540.autopresence.context.AppContext;
import com.m2e.cs5540.autopresence.database.DatabaseUtil;
import com.m2e.cs5540.autopresence.students.StudentsActivity;
import com.m2e.cs5540.autopresence.vao.Course;
import com.m2e.cs5540.autopresence.vao.CourseEnrollment;

import java.util.List;

public class StudentEnrollment extends BaseActivity
      implements View.OnClickListener,
      LoaderManager.LoaderCallbacks<AsyncLoaderStatus> {
   private static final String TAG = "StudentEnrollment";
   private DatabaseUtil databaseUtil = DatabaseUtil.getInstance();
   private Spinner courseSpinner;
   private ArrayAdapter<Course> courseSpinnerAdapter;
   private Button courseEnrollButton;

   @Override protected void onCreate(Bundle savedInstanceState) {
      Log.i(TAG, "$$$$$$ onCreate() Invoked... ");
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_student_enrollment);

      courseSpinner = (Spinner) findViewById(R.id.courseSpinner);
      courseSpinnerAdapter = new ArrayAdapter(this,
            android.R.layout.simple_spinner_item);
      courseSpinnerAdapter.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item);
      courseSpinner.setAdapter(courseSpinnerAdapter);

      courseEnrollButton = (Button) findViewById(R.id.courseEnrollButton);
      courseEnrollButton.setOnClickListener(this);

      getLoaderManager().initLoader(333, null, this).forceLoad();
   }

   private void studentCourseEnroll(String courseId) {
      Log.i(TAG, "StudentCourseEnroll");
      courseEnrollButton.setEnabled(false);
      CourseEnrollment courseEnrollment = new CourseEnrollment();
      courseEnrollment.setUserId(
            AppContext.getCurrentAppContext().getUser().getId());
      courseEnrollment.setRole(
            AppContext.getCurrentAppContext().getUser().getRole());
      courseEnrollment.setCourseId(courseId);

      AsyncTask asyncTask =
            new AsyncTask<CourseEnrollment, Void, AsyncLoaderStatus>() {
               @Override protected AsyncLoaderStatus doInBackground(
                     CourseEnrollment... params) {
                  AsyncLoaderStatus status = new AsyncLoaderStatus();
                  if (params != null && params.length > 0) {
                     try {
                        databaseUtil.createCourseEnrollment(params[0]);
                     } catch (Exception e) {
                        e.printStackTrace();
                        status.setException(e);
                        Toast.makeText(StudentEnrollment.this,
                              "Student course " + "enrollment failed. Cause: " +
                                    e.getClass().getName() + ": " +
                                    e.getMessage(), Toast.LENGTH_LONG).show();
                     }
                  }
                  return status;
               }
            };

      asyncTask.execute(courseEnrollment);
   }

   @Override
   public Loader<AsyncLoaderStatus> onCreateLoader(int id, Bundle args) {
      Log.i(TAG, "$$$$$$ StudentEnrollment.onCreateLoader called");
      return new EnrollmentAsyncTaskLoader(this);
   }

   @Override public void onLoadFinished(Loader<AsyncLoaderStatus> loader,
         AsyncLoaderStatus loaderStatus) {
      Log.i(TAG, "$$$$$$ StudentEnrollmentActivity.onLoadFinished called");
      if (loaderStatus.hasException()) {
         Toast.makeText(this, "Error " + loaderStatus.getExceptionMessage(),
               Toast.LENGTH_LONG).show();
      } else {
         List<Course> courseList = (List<Course>) loaderStatus.getResult();
         courseSpinnerAdapter.addAll(courseList);
         courseSpinnerAdapter.notifyDataSetChanged();
      }
   }

   @Override public void onLoaderReset(Loader<AsyncLoaderStatus> loader) {

   }

   @Override public void onClick(View v) {
      studentCourseEnroll(((Course) courseSpinner.getSelectedItem()).getId());
      Toast.makeText(this, "Your course has been enrolled successfully",
            Toast.LENGTH_LONG).show();
      startActivity(new Intent(this, StudentsActivity.class));
   }
}
