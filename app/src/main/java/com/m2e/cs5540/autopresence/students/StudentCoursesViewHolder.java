package com.m2e.cs5540.autopresence.students;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.m2e.cs5540.autopresence.R;

/**
 * Created by maeswara on 8/5/2017.
 */
public class StudentCoursesViewHolder extends RecyclerView.ViewHolder
      implements OnClickListener {

   private TextView courseIdText;
   private TextView courseNameText;
   private TextView courseLocationText;
   private TextView courseDateText;
   private TextView courseTimeText;
   private TextView courseDayText;

   public StudentCoursesViewHolder(View itemView) {
      super(itemView);
      courseIdText = (TextView) itemView.findViewById(R.id.studentCourseId);
      courseNameText = (TextView) itemView.findViewById(R.id.studentCourseName);
      courseLocationText = (TextView) itemView.findViewById(
            R.id.studentCourseLocation);
      courseDateText = (TextView) itemView.findViewById(R.id.studentCourseDate);
      courseTimeText = (TextView) itemView.findViewById(R.id.studentCourseTime);
      courseDayText = (TextView) itemView.findViewById(R.id.studentCourseDay);
   }

   @Override public void onClick(View view) {
      //Show attendances for the selected course in another activity
   }

   public void setCourseIdText(String courseIdText) {
      this.courseIdText.setText(courseIdText);
   }

   public void setCourseNameText(String courseNameText) {
      this.courseNameText.setText(courseNameText);
   }

   public void setCourseLocationText(String courseLocationText) {
      this.courseLocationText.setText(courseLocationText);
   }

   public void setCourseDateText(String courseDateText) {
      this.courseDateText.setText(courseDateText);
   }

   public void setCourseTimeText(String courseTimeText) {
      this.courseTimeText.setText(courseTimeText);
   }

   public void setCourseDayText(String courseDayText) {
      this.courseDayText.setText(courseDayText);
   }
}
