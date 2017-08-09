package com.m2e.cs5540.autopresence.students;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.m2e.cs5540.autopresence.R;
import com.m2e.cs5540.autopresence.base.AsyncLoaderStatus;
import com.m2e.cs5540.autopresence.base.BaseActivity;
import com.m2e.cs5540.autopresence.context.AppContext;
import com.m2e.cs5540.autopresence.login.LoginActivity;
import com.m2e.cs5540.autopresence.service.LocationUpdateService;
import com.m2e.cs5540.autopresence.student_enrollment.StudentEnrollmentDialogFragment;
import com.m2e.cs5540.autopresence.vao.CourseAttendancePercent;

import java.util.List;

/**
 * Created by maeswara on 8/7/2017.
 */
public class StudentHomeActivity extends BaseActivity
      implements LoaderManager.LoaderCallbacks<AsyncLoaderStatus>,
      View.OnClickListener {
   private static final String TAG = "StudentHomeActivity";
   private RecyclerView studentCoursePercentRecyclerView;
   private StudentHomeAdapter studentHomeAdapter;
   private CardView studentCourseCountCardView;
   private TextView studentEnrolledCourseCountTextView;
   private TextInputLayout noDataLayout;
   private Button studentLogoutButton;

   @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_student_home);
      noDataLayout = (TextInputLayout) findViewById(
            R.id.noStudentAttendancePercentDataLayout);
      studentCourseCountCardView = (CardView) findViewById(
            R.id.studentHomeCourseCardView);
      studentEnrolledCourseCountTextView = (TextView) findViewById(
            R.id.studentHomeCourseCount);
      studentLogoutButton = (Button) findViewById(R.id.studentLogoutButton);
      studentCoursePercentRecyclerView = (RecyclerView) findViewById(
            R.id.studentHomeAttendancePercentRecyclerView);
      LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL, false);
      studentCoursePercentRecyclerView.setLayoutManager(linearLayoutManager);
      studentHomeAdapter = new StudentHomeAdapter();
      studentCoursePercentRecyclerView.setAdapter(studentHomeAdapter);

      studentCourseCountCardView.setOnClickListener(this);
      studentLogoutButton.setOnClickListener(this);

      getLoaderManager().initLoader(108, null, this).forceLoad();

      showProgressDialog("Loading data...");
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
      return new StudentHomeAsyncTaskLoader(this);
   }

   @Override public void onLoadFinished(Loader<AsyncLoaderStatus> loader,
         AsyncLoaderStatus data) {
      if (data.getException() == null) {
         List<CourseAttendancePercent> courseAttendancePercentList =
               (List<CourseAttendancePercent>) data.getResult();
         Log.i(TAG, "$$$ courseAttendancePercentList = " + "" +
               courseAttendancePercentList);
         if (courseAttendancePercentList != null &&
               courseAttendancePercentList.size() > 0) {
            noDataLayout.setVisibility(View.GONE);
            studentHomeAdapter.setCourseAttendancePercentList(
                  courseAttendancePercentList);
            studentEnrolledCourseCountTextView.setText(String.valueOf(
                  courseAttendancePercentList.get(0).getCourseCount()));
         } else {
            studentEnrolledCourseCountTextView.setText("0");
            noDataLayout.setVisibility(View.VISIBLE);
         }
      } else {
         Toast.makeText(this, "Could not calculate student course " +
               "attendance percentage details. Cause: " +
               data.getException().getClass() + ": " +
               data.getException().getMessage(), Toast.LENGTH_LONG).show();
      }
      hideProgressDialog();
   }

   @Override public void onLoaderReset(Loader<AsyncLoaderStatus> loader) {

   }

   @Override public void onClick(View v) {
      switch (v.getId()) {
         case R.id.studentHomeCourseCardView:
            startActivity(new Intent(this, StudentCoursesActivity.class));
            break;
         case R.id.studentLogoutButton:
            AppContext.logout();
            Intent locationServiceIntent = new Intent(this,
                  LocationUpdateService.class);
            stopService(locationServiceIntent);
            startActivity(new Intent(this, LoginActivity.class));
      }
   }

   @Override public void onBackPressed() {
      if (!AppContext.isUserLoggedIn()) {
         super.onBackPressed();
      }
   }
}
