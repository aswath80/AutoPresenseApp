package com.m2e.cs5540.autopresence.vao;

/**
 * Created by maeswara on 8/3/2017.
 */
public class UserAttendance {
   private String courseId;
   private String userId;
   private String attendanceDate;
   private String attendanceTime;
   private String distance;

   public String getCourseId() {
      return courseId;
   }

   public void setCourseId(String courseId) {
      this.courseId = courseId;
   }

   public String getUserId() {
      return userId;
   }

   public void setUserId(String userId) {
      this.userId = userId;
   }

   public String getAttendanceDate() {
      return attendanceDate;
   }

   public void setAttendanceDate(String attendanceDate) {
      this.attendanceDate = attendanceDate;
   }

   public String getAttendanceTime() {
      return attendanceTime;
   }

   public void setAttendanceTime(String attendanceTime) {
      this.attendanceTime = attendanceTime;
   }

   public String getDistance() {
      return distance;
   }

   public void setDistance(String distance) {
      this.distance = distance;
   }
}
