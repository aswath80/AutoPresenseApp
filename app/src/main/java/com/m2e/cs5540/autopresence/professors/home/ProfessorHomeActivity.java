package com.m2e.cs5540.autopresence.professors.home;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.m2e.cs5540.autopresence.R;
import com.m2e.cs5540.autopresence.base.AsyncLoaderStatus;
import com.m2e.cs5540.autopresence.context.AppContext;
import com.m2e.cs5540.autopresence.login.LoginActivity;
import com.m2e.cs5540.autopresence.professors.ProfessorActivity;
import com.m2e.cs5540.autopresence.service.LocationUpdateService;
import com.m2e.cs5540.autopresence.vao.CourseAttendancePercent;

import java.util.List;

public class ProfessorHomeActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<AsyncLoaderStatus>,
        View.OnClickListener {

    private static final String TAG = "ProfessorHomeActivity";
    private RecyclerView professorCoursePercentRecyclerView;
    private ProfessorHomeAdapter professorHomeAdapter;
    private CardView professorCourseCountCardView;
    private TextView professorEnrolledCourseCountTextView;
    private TextInputLayout noDataLayout;
    private Button professorLogoutButton;

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professor_home);
        noDataLayout = (TextInputLayout) findViewById(
                R.id.noprofessorAttendancePercentDataLayout);
        professorCourseCountCardView = (CardView) findViewById(
                R.id.professorHomeCourseCardView);
        professorEnrolledCourseCountTextView = (TextView) findViewById(
                R.id.professorHomeCourseCount);
        professorLogoutButton = (Button) findViewById(R.id.professorLogoutButton);
        professorCoursePercentRecyclerView = (RecyclerView) findViewById(
                R.id.professorHomeAttendancePercentRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        professorCoursePercentRecyclerView.setLayoutManager(linearLayoutManager);
        professorHomeAdapter = new ProfessorHomeAdapter();
        professorCoursePercentRecyclerView.setAdapter(professorHomeAdapter);

        professorCourseCountCardView.setOnClickListener(this);
        professorLogoutButton.setOnClickListener(this);

        getLoaderManager().initLoader(109, null, this).forceLoad();
    }

    @Override
    public Loader<AsyncLoaderStatus> onCreateLoader(int id, Bundle args) {
        return new ProfessorHomeAsyncTaskLoader(this);
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
                professorHomeAdapter.setCourseAttendancePercentList(
                        courseAttendancePercentList);
                professorEnrolledCourseCountTextView.setText(String.valueOf(
                        courseAttendancePercentList.get(0).getCourseCount()));
            } else {
                professorEnrolledCourseCountTextView.setText("No data available");
                noDataLayout.setVisibility(View.VISIBLE);
            }
        } else {
            Toast.makeText(this, "Could not calculate professor course " +
                    "attendance percentage details. Cause: " +
                    data.getException().getClass() + ": " +
                    data.getException().getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override public void onLoaderReset(Loader<AsyncLoaderStatus> loader) {

    }

    @Override public void onClick(View v) {
        switch (v.getId()) {
            case R.id.professorHomeCourseCardView:
                startActivity(new Intent(this, ProfessorActivity.class));
                break;
            case R.id.professorLogoutButton:
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
