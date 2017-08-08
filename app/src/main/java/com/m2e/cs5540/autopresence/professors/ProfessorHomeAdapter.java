package com.m2e.cs5540.autopresence.professors;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.m2e.cs5540.autopresence.R;
import com.m2e.cs5540.autopresence.students.StudentCoursesViewHolder;
import com.m2e.cs5540.autopresence.vao.Course;

import java.util.List;

/**
 * Created by Kumar on 8/5/2017.
 */

public class ProfessorHomeAdapter
      extends RecyclerView.Adapter<ProfessorViewHolder> {
   private static final String TAG = "ProfessorHomeAdapter";
   private List<Course> courseList;
   private String[] daysInWeek = {"Su", "Mo", "Tu", "We", "Th", "Fr", "Sa"};

   public ProfessorHomeAdapter() {
   }

   public void setCourseList(List<Course> courseList) {
      this.courseList = courseList;
      this.notifyDataSetChanged();
   }

   public List<Course> getCourseList() {
      return courseList;
   }

   @Override
   public ProfessorViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
      LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
      View courseView = layoutInflater.inflate(R.layout.professor_course_view,  parent, false);
      return new ProfessorViewHolder(courseView);
   }

   @Override
   public void onBindViewHolder(ProfessorViewHolder holder, int position) {
      Log.i(TAG, "$$$ Get professor course at position " + position + " for " +
              "holder " + holder);
      Course course = courseList.get(position);
      if (course != null) {
         Log.i(TAG, "$$$ Got professor course " + course);
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
                 "$$$ Done setting professor course details " + course.getName());
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
      int itemCount = courseList != null ? courseList.size() : 0;
      Log.i(TAG, "$$$ Professor courseList getItemCount: " + itemCount);
      return itemCount;
   }
}