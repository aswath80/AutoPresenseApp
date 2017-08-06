package com.m2e.cs5540.autopresence.professors;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.m2e.cs5540.autopresence.R;
import com.m2e.cs5540.autopresence.base.AsyncLoaderStatus;
import com.m2e.cs5540.autopresence.courses.AddCourseActivity;
import com.m2e.cs5540.autopresence.vao.Course;

import java.util.List;

public class ProfessorActivity extends AppCompatActivity implements View.OnClickListener,
        LoaderManager.LoaderCallbacks<AsyncLoaderStatus> {

    private static final String TAG = "ProfessorActivity";
    private RecyclerView professorCoursesRecyclerView;
    private ProfessorHomeAdapter professorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professors_home);

        professorCoursesRecyclerView = (RecyclerView) findViewById(R.id.professorCourseRecyclerView);

        professorAdapter = new ProfessorHomeAdapter();
        professorCoursesRecyclerView.setAdapter(professorAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        professorCoursesRecyclerView.setLayoutManager(linearLayoutManager);

        getLoaderManager().initLoader(104, null, this).forceLoad();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.professor_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.addCourse) {
            Intent intent = new Intent(getApplicationContext(), AddCourseActivity.class);
            startActivityForResult(intent, 0);
            finish();
            return true;
        }

        if (id == R.id.addPermit) {
            /*Intent intent = new Intent(getApplicationContext(), PermitActivity.class);
            startActivityForResult(intent, 0);
            finish();*/
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<AsyncLoaderStatus> onCreateLoader(int id, Bundle args) {
        return new ProfessorActivityAsyncTaskLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<AsyncLoaderStatus> loader, AsyncLoaderStatus data) {

        Log.i(TAG, "$$$ StudentsActivity.onLoadFinished");
        if (data.getResult() != null) {
            List<Course> courseList = (List<Course>) data.getResult();
            professorAdapter.setCourseList(courseList);
            professorCoursesRecyclerView.setAdapter(professorAdapter);
            professorAdapter.notifyDataSetChanged();
            Log.i(TAG, "$$$ courseList set on adapter and notifyDataSetChanged " +"called");
        } else if (data.getException() != null) {
            Toast.makeText(this, "Could not read professor course enrollments from" +
                    " database. Cause: " + data.getException().getClass() + ": " +
                    "" + data.getException().getMessage(), Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onLoaderReset(Loader<AsyncLoaderStatus> loader) {

    }

    @Override
    public void onClick(View v) {

    }
}
