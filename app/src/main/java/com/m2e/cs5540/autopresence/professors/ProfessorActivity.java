package com.m2e.cs5540.autopresence.professors;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.m2e.cs5540.autopresence.R;
import com.m2e.cs5540.autopresence.courses.AddCourseActivity;
import com.m2e.cs5540.autopresence.permit.PermitActivity;

public class ProfessorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professors_home);
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
            return true;
        }

        if (id == R.id.addPermit) {
            Intent intent = new Intent(getApplicationContext(), PermitActivity.class);
            startActivityForResult(intent, 0);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
