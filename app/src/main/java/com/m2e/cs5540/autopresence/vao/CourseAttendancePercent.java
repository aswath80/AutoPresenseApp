package com.m2e.cs5540.autopresence.vao;

/**
 * Created by maeswara on 8/7/2017.
 */
public class CourseAttendancePercent {
   private int courseCount;
   private String courseName;
   private String attendancePercent;

   public int getCourseCount() {
      return courseCount;
   }

   public void setCourseCount(int courseCount) {
      this.courseCount = courseCount;
   }

   public String getCourseName() {
      return courseName;
   }

   public void setCourseName(String courseName) {
      this.courseName = courseName;
   }

   public String getAttendancePercent() {
      return attendancePercent;
   }

   public void setAttendancePercent(String attendancePercent) {
      this.attendancePercent = attendancePercent;
   }

   @Override public String toString() {
      return "{" + courseName + ":" + attendancePercent + "}";
   }
}
