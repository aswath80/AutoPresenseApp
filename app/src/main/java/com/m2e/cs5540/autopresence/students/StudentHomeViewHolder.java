package com.m2e.cs5540.autopresence.students;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.m2e.cs5540.autopresence.R;

/**
 * Created by maeswara on 8/5/2017.
 */
public class StudentHomeViewHolder extends RecyclerView.ViewHolder {
   private static final String TAG = "StudentHomeViewHolder";
   private TextView courseNameText;
   private TextView courseAttendancePercentText;

   public StudentHomeViewHolder(View itemView) {
      super(itemView);
      courseNameText = (TextView) itemView.findViewById(
            R.id.studentHomeCoursePercentCourseName);
      courseAttendancePercentText = (TextView) itemView.findViewById(
            R.id.studentHomeCoursePercentCoursePercent);
   }

   public void setCourseNameText(String courseNameText) {
      this.courseNameText.setText(courseNameText);
   }

   public void setCourseAttendancePercentText(String courseLocationText) {
      this.courseAttendancePercentText.setText(courseLocationText);
   }
}
