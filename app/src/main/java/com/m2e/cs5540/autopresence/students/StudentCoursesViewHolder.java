package com.m2e.cs5540.autopresence.students;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import com.m2e.cs5540.autopresence.R;
import com.m2e.cs5540.autopresence.vao.CourseEnrollment;

import java.util.List;

/**
 * Created by maeswara on 8/5/2017.
 */
public class StudentCoursesViewHolder extends RecyclerView.ViewHolder
      implements OnClickListener {
   private List<CourseEnrollment> courseEnrollmentList;
   private EditText courseIdText;
   private EditText courseNameText;
   private EditText courseLocationText;
   private EditText courseDateText;
   private EditText courseTimeText;
   private EditText courseDayText;

   public StudentCoursesViewHolder(View itemView,
         List<CourseEnrollment> courseEnrollmentList) {
      super(itemView);
      this.courseEnrollmentList = courseEnrollmentList;
      courseIdText = (EditText) itemView.findViewById(R.id.studentCourseId);
      courseNameText = (EditText) itemView.findViewById(R.id.studentCourseName);
      courseLocationText = (EditText) itemView.findViewById(
            R.id.studentCourseLocation);
      courseDateText = (EditText) itemView.findViewById(R.id.studentCourseDate);
      courseTimeText = (EditText) itemView.findViewById(R.id.studentCourseTime);
      courseDayText = (EditText) itemView.findViewById(R.id.studentCourseDay);
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
