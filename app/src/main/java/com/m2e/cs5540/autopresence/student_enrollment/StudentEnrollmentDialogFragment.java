package com.m2e.cs5540.autopresence.student_enrollment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.m2e.cs5540.autopresence.R;
import com.m2e.cs5540.autopresence.base.AsyncLoaderStatus;
import com.m2e.cs5540.autopresence.context.AppContext;
import com.m2e.cs5540.autopresence.database.DatabaseUtil;
import com.m2e.cs5540.autopresence.vao.Course;
import com.m2e.cs5540.autopresence.vao.CourseEnrollment;

import java.util.List;

public class StudentEnrollmentDialogFragment extends DialogFragment
      implements View.OnClickListener,
      LoaderManager.LoaderCallbacks<AsyncLoaderStatus> {
   private static final String TAG = "StudentEnrollmentDialog";
   private DatabaseUtil databaseUtil = DatabaseUtil.getInstance();
   private View fragmentView;
   private Spinner courseSpinner;
   private ArrayAdapter<Course> courseSpinnerAdapter;
   private Button courseEnrollButton;
   private android.content.Loader<Object> parentLoader;

   public StudentEnrollmentDialogFragment() {

   }

   public void setParentLoader(android.content.Loader<Object> parentLoader) {
      this.parentLoader = parentLoader;
   }

   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container,
         Bundle savedInstanceState) {
      Log.i(TAG, "$$$$$$ onCreateView() Invoked... ");
      fragmentView = inflater.inflate(R.layout.activity_student_enrollment,
            container, false);
      super.onCreate(savedInstanceState);

      courseSpinner = (Spinner) fragmentView.findViewById(R.id.courseSpinner);
      courseSpinnerAdapter = new ArrayAdapter(getContext(),
            android.R.layout.simple_spinner_item);
      courseSpinnerAdapter.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item);
      courseSpinner.setAdapter(courseSpinnerAdapter);

      courseEnrollButton = (Button) fragmentView.findViewById(
            R.id.courseEnrollButton);
      courseEnrollButton.setOnClickListener(this);

      getLoaderManager().initLoader(333, null, this).forceLoad();

      return fragmentView;
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
                     }
                  }
                  return status;
               }

               @Override
               protected void onPostExecute(AsyncLoaderStatus loaderStatus) {
                  super.onPostExecute(loaderStatus);
                  if (loaderStatus.getException() != null) {
                     Toast.makeText(fragmentView.getContext(),
                           "Course enrollment failed. Cause: " +
                                 loaderStatus.getException().getClass()
                                       .getName() + ": " +
                                 loaderStatus.getException().getMessage(),
                           Toast.LENGTH_LONG).show();
                     dismiss();
                  } else {
                     Toast.makeText(getContext(),
                           "You have successfully " + "enrolled in the course",
                           Toast.LENGTH_LONG).show();
                     if (parentLoader != null) {
                        parentLoader.forceLoad();
                     }
                     dismiss();
                  }
               }
            };

      asyncTask.execute(new CourseEnrollment[]{courseEnrollment});
   }

   @Override
   public Loader<AsyncLoaderStatus> onCreateLoader(int id, Bundle args) {
      Log.i(TAG,
            "$$$$$$ StudentEnrollmentDialogFragment.onCreateLoader called");
      return new EnrollmentAsyncTaskLoader(getContext());
   }

   @Override public void onLoadFinished(Loader<AsyncLoaderStatus> loader,
         AsyncLoaderStatus loaderStatus) {
      Log.i(TAG, "$$$$$$ StudentEnrollmentActivity.onLoadFinished called");
      if (loaderStatus.hasException()) {
         Toast.makeText(getContext(),
               "Error " + loaderStatus.getExceptionMessage(), Toast.LENGTH_LONG)
               .show();
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
   }
}
