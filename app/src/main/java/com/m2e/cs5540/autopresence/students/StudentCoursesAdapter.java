package com.m2e.cs5540.autopresence.students;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.m2e.cs5540.autopresence.R;
import com.m2e.cs5540.autopresence.vao.Course;

import java.util.List;

/**
 * Created by maeswara on 8/5/2017.
 */
public class StudentCoursesAdapter
      extends RecyclerView.Adapter<StudentCoursesViewHolder> {
   private static final String TAG = "StudentCoursesAdapter";
   private List<Course> courseList;
   private String[] daysInWeek = {"Su", "Mo", "Tu", "We", "Th", "Fr", "Sa"};

   public StudentCoursesAdapter() {
   }

   public void setCourseList(List<Course> courseList) {
      this.courseList = courseList;
   }

   @Override
   public StudentCoursesViewHolder onCreateViewHolder(ViewGroup parent,
         int viewType) {
      LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
      View studentCourseView = layoutInflater.inflate(
            R.layout.student_course_view, parent, false);
      return new StudentCoursesViewHolder(studentCourseView);
   }

   @Override
   public void onBindViewHolder(StudentCoursesViewHolder holder, int position) {
      Course course = courseList.get(position);
      if (course != null) {
         Log.i(TAG, "$$$ Got student course " + course.getName());
         holder.setCourseIdText(course.getId());
         holder.setCourseNameText(course.getName());
         holder.setCourseLocationText(course.getLocation());
         holder.setCourseDateText(
               course.getMeetingDate().getStartDate() + " - " +
                     course.getMeetingDate().getEndDate());
         holder.setCourseTimeText(
               course.getMeetingDate().getStartTime() + " - " +
                     course.getMeetingDate().getEndTime());
         holder.setCourseDayText(
               decodeMeetingDays(course.getMeetingDate().getMeetingDays()));
         Log.i(TAG,
               "$$$ Done setting student course details " + course.getName());
      } else {
         Log.w(TAG,
               "$$$ No course found for course position " + position + " in " +
                     "adapter ");
      }
   }

   private String decodeMeetingDays(String meetingDays) {
      if (meetingDays != null) {
         StringBuilder builder = new StringBuilder();
         char ca[] = meetingDays.toCharArray();
         for (int i = 0; i < ca.length; i++) {
            if (ca[i] == '1') {
               builder.append(daysInWeek[i] + ",");
            }
         }
         if (builder.length() > 0) {
            builder.deleteCharAt(builder.length() - 1);
         }
         return builder.toString();
      }
      return null;
   }

   @Override public int getItemCount() {
      return courseList != null ? courseList.size() : 0;
   }
}
