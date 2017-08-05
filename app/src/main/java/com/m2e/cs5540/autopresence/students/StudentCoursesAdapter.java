package com.m2e.cs5540.autopresence.students;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.m2e.cs5540.autopresence.R;
import com.m2e.cs5540.autopresence.database.DatabaseUtil;
import com.m2e.cs5540.autopresence.vao.Course;
import com.m2e.cs5540.autopresence.vao.CourseEnrollment;

import java.util.List;

/**
 * Created by maeswara on 8/5/2017.
 */
public class StudentCoursesAdapter
      extends RecyclerView.Adapter<StudentCoursesViewHolder> {
   private List<CourseEnrollment> courseEnrollmentList;
   private StudentCoursesViewHolder studentCourseViewHolder;
   private String[] daysInWeek = {"Su", "Mo", "Tu", "We", "Th", "Fr", "Sa"};

   public StudentCoursesAdapter(List<CourseEnrollment> courseEnrollmentList) {
      this.courseEnrollmentList = courseEnrollmentList;
   }

   @Override
   public StudentCoursesViewHolder onCreateViewHolder(ViewGroup parent,
         int viewType) {
      LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
      View studentCourseView = layoutInflater.inflate(
            R.layout.student_course_view, parent, false);
      studentCourseViewHolder = new StudentCoursesViewHolder(studentCourseView,
            courseEnrollmentList);
      return studentCourseViewHolder;
   }

   @Override
   public void onBindViewHolder(StudentCoursesViewHolder holder, int position) {
      CourseEnrollment courseEnrollment = courseEnrollmentList.get(position);
      Course course = DatabaseUtil.getInstance().getCourse(
            courseEnrollment.getCourseId());
      studentCourseViewHolder.setCourseIdText(course.getId());
      studentCourseViewHolder.setCourseNameText(course.getName());
      studentCourseViewHolder.setCourseLocationText(course.getLocation());
      studentCourseViewHolder.setCourseDateText(
            course.getMeetingDate().getStartDate() + " - " +
                  course.getMeetingDate().getEndDate());
      studentCourseViewHolder.setCourseTimeText(
            course.getMeetingDate().getStartTime() + " - " +
                  course.getMeetingDate().getEndTime());
      studentCourseViewHolder.setCourseDayText(
            decodeMeetingDays(course.getMeetingDate().getMeetingDays()));
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
      return courseEnrollmentList != null ? courseEnrollmentList.size() : 0;
   }
}
