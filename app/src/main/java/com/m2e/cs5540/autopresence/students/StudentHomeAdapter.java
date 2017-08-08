package com.m2e.cs5540.autopresence.students;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.m2e.cs5540.autopresence.R;
import com.m2e.cs5540.autopresence.vao.CourseAttendancePercent;

import java.util.List;

/**
 * Created by maeswara on 8/5/2017.
 */
public class StudentHomeAdapter
      extends RecyclerView.Adapter<StudentHomeViewHolder> {
   private static final String TAG = "StudentHomeAdapter";
   private List<CourseAttendancePercent> courseAttendancePercentList;

   public StudentHomeAdapter() {
   }

   public void setCourseAttendancePercentList(
         List<CourseAttendancePercent> courseAttendancePercentList) {
      this.courseAttendancePercentList = courseAttendancePercentList;
      this.notifyDataSetChanged();
   }

   @Override public StudentHomeViewHolder onCreateViewHolder(ViewGroup parent,
         int viewType) {
      LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
      View studentCoursePercentView = layoutInflater.inflate(
            R.layout.student_home_course_percent_view, parent, false);
      return new StudentHomeViewHolder(studentCoursePercentView);
   }

   @Override
   public void onBindViewHolder(StudentHomeViewHolder holder, int position) {
      Log.i(TAG, "$$$ Get student course at position " + position + " for " +
            "holder " + holder);
      CourseAttendancePercent courseAttendancePercent =
            courseAttendancePercentList.get(position);
      if (courseAttendancePercent != null) {
         Log.i(TAG, "$$$ Got student courseAttendancePercent " +
               courseAttendancePercent.getCourseName());
         holder.setCourseNameText(courseAttendancePercent.getCourseName());
         holder.setCourseAttendancePercentText(
               courseAttendancePercent.getAttendancePercent());
         Log.i(TAG,
               "$$$ Done setting student course attendance percent details " +
                     courseAttendancePercent.getCourseName());
      } else {
         Log.w(TAG,
               "$$$ No course found for course position " + position + " in " +
                     "adapter ");
      }
   }

   @Override public int getItemCount() {
      int itemCount = courseAttendancePercentList != null ?
            courseAttendancePercentList.size() : 0;
      Log.i(TAG, "$$$ Student courseList getItemCount: " + itemCount);
      return itemCount;
   }
}
