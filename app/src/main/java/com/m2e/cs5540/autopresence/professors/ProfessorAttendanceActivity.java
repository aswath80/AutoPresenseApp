package com.m2e.cs5540.autopresence.professors;

import android.app.LoaderManager;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.m2e.cs5540.autopresence.R;
import com.m2e.cs5540.autopresence.base.AsyncLoaderStatus;
import com.m2e.cs5540.autopresence.vao.UserAttendance;

import java.util.List;

public class ProfessorAttendanceActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<AsyncLoaderStatus>{

    private static final String TAG = "ProfessorAttendanceActivity";
    private RecyclerView studentAttendanceRecyclerView;
    private ProfessoAttendanceAdapter professorMainAdaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professor_attendance);

        studentAttendanceRecyclerView = (RecyclerView) findViewById(R.id.professorCourseRecyclerView);
        professorMainAdaptor = new ProfessoAttendanceAdapter();
        studentAttendanceRecyclerView.setAdapter(professorMainAdaptor);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        studentAttendanceRecyclerView.setLayoutManager(linearLayoutManager);

        getLoaderManager().initLoader(107, null, this).forceLoad();
    }

    @Override
    public Loader<AsyncLoaderStatus> onCreateLoader(int id, Bundle args) {

        String courseId = getIntent().getStringExtra("courseId");
        if (courseId == null ) {
            Toast.makeText(this, "StudentAttendanceActivity received null " +
                    "courseId/userId from the intent!", Toast.LENGTH_LONG).show();
            return null;
        } else {
            return new ProfessorAttendanceAsyncTaskLoader(this, courseId);
        }
    }

    @Override
    public void onLoadFinished(Loader<AsyncLoaderStatus> loader, AsyncLoaderStatus data) {
        if (data != null) {
            if (data.getException() == null) {
                List<UserAttendance> attendanceList = (List<UserAttendance>) data.getResult();
                if (attendanceList != null && attendanceList.size() > 0) {
                    professorMainAdaptor.setAttendanceList(attendanceList);
                }
            } else {
                Exception e = data.getException();
                Toast.makeText(this, "Error getting attendance data. Cause: " +
                                e.getClass().getName() + ": " + e.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<AsyncLoaderStatus> loader) {

    }
}
