package com.m2e.cs5540.autopresence.students;

import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.m2e.cs5540.autopresence.R;
import com.m2e.cs5540.autopresence.base.AsyncLoaderStatus;
import com.m2e.cs5540.autopresence.base.BaseActivity;
import com.m2e.cs5540.autopresence.vao.UserAttendance;

import java.util.List;

/**
 * Created by maeswara on 8/6/2017.
 */
public class StudentAttendanceActivity extends BaseActivity
      implements LoaderManager.LoaderCallbacks<AsyncLoaderStatus> {
   private static final String TAG = "StudentAttendActivity";
   private RecyclerView studentAttendanceRecyclerView;
   private StudentAttendanceAdapter studentAttendanceAdapter;

   @Override protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_students_home);
      studentAttendanceRecyclerView = (RecyclerView) findViewById(
            R.id.studentCourseRecyclerView);
      studentAttendanceAdapter = new StudentAttendanceAdapter();
      studentAttendanceRecyclerView.setAdapter(studentAttendanceAdapter);
      LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL, false);
      studentAttendanceRecyclerView.setLayoutManager(linearLayoutManager);

      getLoaderManager().initLoader(107, null, this).forceLoad();
   }

   @Override
   public Loader<AsyncLoaderStatus> onCreateLoader(int id, Bundle args) {
      String courseId = getIntent().getStringExtra("courseId");
      String userId = getIntent().getStringExtra("userId");
      if (courseId == null || userId == null) {
         Toast.makeText(this, "StudentAttendanceActivity received null " +
               "courseId/userId from the intent!", Toast.LENGTH_LONG).show();
         return null;
      } else {
         return new StudentAttendanceAsyncTaskLoader(this, courseId, userId);
      }
   }

   @Override public void onLoadFinished(Loader<AsyncLoaderStatus> loader,
         AsyncLoaderStatus data) {
      if (data != null) {
         if (data.getException() == null) {
            studentAttendanceAdapter.setAttendanceList(
                  (List<UserAttendance>) data.getResult());
         } else {
            Exception e = data.getException();
            Toast.makeText(this, "Error getting attendance data. Cause: " +
                        e.getClass().getName() + ": " + e.getMessage(),
                  Toast.LENGTH_LONG).show();
         }
      }
   }

   @Override public void onLoaderReset(Loader<AsyncLoaderStatus> loader) {

   }
}
