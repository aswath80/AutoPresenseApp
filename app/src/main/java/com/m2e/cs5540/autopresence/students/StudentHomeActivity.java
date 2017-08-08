package com.m2e.cs5540.autopresence.students;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.m2e.cs5540.autopresence.R;

/**
 * Created by maeswara on 8/7/2017.
 */
public class StudentHomeActivity extends AppCompatActivity {
   private RecyclerView studentCoursePercentRecyclerView;

   @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_student_home);
      studentCoursePercentRecyclerView = (RecyclerView) findViewById(R.id
            .studentHomeAttendancePercentRecyclerView);
   }
}
