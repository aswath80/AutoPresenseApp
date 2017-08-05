package com.m2e.cs5540.autopresence.students;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.m2e.cs5540.autopresence.R;
import com.m2e.cs5540.autopresence.student_enrollment.StudentEnrollment;

public class StudentsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students_home);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate the menu; this adds the items to the action bar if present.
        getMenuInflater().inflate(R.menu.student_menu_home, menu);
        return true;
    }

    //Determine f action bar item was selected. If true then do corresponding action.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //handle press on the action bar
        switch(item.getItemId()) {

            case R.id.add_course:
                startActivity(new Intent(this, StudentEnrollment.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
