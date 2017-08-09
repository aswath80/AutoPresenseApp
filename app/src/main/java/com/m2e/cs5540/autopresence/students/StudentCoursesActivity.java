package com.m2e.cs5540.autopresence.students;

import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.m2e.cs5540.autopresence.R;
import com.m2e.cs5540.autopresence.base.AsyncLoaderStatus;
import com.m2e.cs5540.autopresence.base.BaseActivity;
import com.m2e.cs5540.autopresence.context.AppContext;
import com.m2e.cs5540.autopresence.student_enrollment.StudentEnrollmentDialogFragment;
import com.m2e.cs5540.autopresence.vao.Course;

import java.util.List;

public class StudentCoursesActivity extends BaseActivity
      implements View.OnClickListener,
      LoaderManager.LoaderCallbacks<AsyncLoaderStatus> {
   private static final String TAG = "StudentCoursesActivity";
   private TextInputLayout noDataTextLayout;
   private RecyclerView studentCoursesRecyclerView;
   private StudentCoursesAdapter studentCoursesAdapter;

   @Override protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_students_courses);
      studentCoursesRecyclerView = (RecyclerView) findViewById(
            R.id.studentCourseRecyclerView);
      studentCoursesAdapter = new StudentCoursesAdapter();
      studentCoursesRecyclerView.setAdapter(studentCoursesAdapter);
      LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL, false);
      studentCoursesRecyclerView.setLayoutManager(linearLayoutManager);

      noDataTextLayout = (TextInputLayout) findViewById(
            R.id.noStudentCourseDataTextLayout);

      getLoaderManager().initLoader(106, null, this).forceLoad();

      showProgressDialog("Loading data...");
   }

   @Override public void onBackPressed() {
      if (!AppContext.isUserLoggedIn()) {
         super.onBackPressed();
      }
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
            FragmentManager fm = getSupportFragmentManager();
            StudentEnrollmentDialogFragment frag =
                  new StudentEnrollmentDialogFragment();
            frag.setParentLoader(getLoaderManager().getLoader(106));
            frag.show(fm, "StudentEnrollmentFragment");
            return true;
      }
      return super.onOptionsItemSelected(item);
   }

   @Override
   public Loader<AsyncLoaderStatus> onCreateLoader(int id, Bundle args) {
      return new StudentCoursesAsyncTaskLoader(this);
   }

   @Override public void onLoadFinished(Loader<AsyncLoaderStatus> loader,
         AsyncLoaderStatus data) {
      Log.i(TAG, "$$$ StudentCoursesActivity.onLoadFinished");
      if (data.getResult() != null) {
         List<Course> courseList = (List<Course>) data.getResult();
         Log.i(TAG, "$$$ StudentCoursesActivity.courseList: " + courseList);
         if (courseList != null && courseList.size() > 0) {
            noDataTextLayout.setVisibility(View.GONE);
            studentCoursesAdapter.setCourseList(courseList);
            Log.i(TAG,
                  "$$$ courseList set on adapter and notifyDataSetChanged " +
                        "called");
         } else {
            noDataTextLayout.setVisibility(View.VISIBLE);
         }
      } else if (data.getException() != null) {
         Toast.makeText(this, "Could not read student course enrollments from" +
               " database. Cause: " + data.getException().getClass() + ": " +
               "" + data.getException().getMessage(), Toast.LENGTH_LONG).show();
      }
      hideProgressDialog();
   }

   @Override public void onLoaderReset(Loader<AsyncLoaderStatus> loader) {

   }

   @Override public void onClick(View v) {

   }
}
