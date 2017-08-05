package com.m2e.cs5540.autopresence.students;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.m2e.cs5540.autopresence.R;
import com.m2e.cs5540.autopresence.base.AsyncLoaderStatus;
import com.m2e.cs5540.autopresence.student_enrollment.StudentEnrollment;
import com.m2e.cs5540.autopresence.vao.CourseEnrollment;

import java.util.List;

public class StudentsActivity extends AppCompatActivity
      implements View.OnClickListener,
      LoaderManager.LoaderCallbacks<AsyncLoaderStatus> {
   private RecyclerView studentCoursesRecyclerView;
   private StudentCoursesAdapter studentCoursesAdapter;

   @Override protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_students_home);
      studentCoursesRecyclerView = (RecyclerView) findViewById(
            R.id.studentCourseRecyclerView);
      getLoaderManager().initLoader(222, null, this);
   }

   @Override public boolean onCreateOptionsMenu(Menu menu) {
      //Inflate the menu; this adds the items to the action bar if present.
      getMenuInflater().inflate(R.menu.student_menu_home, menu);
      return true;
   }

   //Determine f action bar item was selected. If true then do corresponding action.
   @Override public boolean onOptionsItemSelected(MenuItem item) {
      //handle press on the action bar
      switch (item.getItemId()) {

         case R.id.add_course:
            startActivity(new Intent(this, StudentEnrollment.class));
            return true;
      }
      return super.onOptionsItemSelected(item);
   }

   @Override
   public Loader<AsyncLoaderStatus> onCreateLoader(int id, Bundle args) {
      return new StudentActivityAsyncTaskLoader(this);
   }

   @Override public void onLoadFinished(Loader<AsyncLoaderStatus> loader,
         AsyncLoaderStatus data) {
      if (data.getResult() != null) {
         List<CourseEnrollment> courseEnrollmentList =
               (List<CourseEnrollment>) data.getResult();
         studentCoursesAdapter = new StudentCoursesAdapter(
               courseEnrollmentList);
         studentCoursesRecyclerView.setAdapter(studentCoursesAdapter);
         studentCoursesAdapter.notifyDataSetChanged();
      } else if (data.getException() != null) {
         Toast.makeText(this, "Could not read student course enrollments from" +
               " database. Cause: " + data.getException().getClass() + ": " +
               "" + data.getException().getMessage(), Toast.LENGTH_LONG).show();
      }
   }

   @Override public void onLoaderReset(Loader<AsyncLoaderStatus> loader) {

   }

   @Override public void onClick(View v) {

   }
}
