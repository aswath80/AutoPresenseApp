package com.m2e.cs5540.autopresence.vao;

/**
 * Created by maeswara on 7/15/2017.
 */
public class CourseEnrollment {
   private String userId;
   private String courseId;
   private UserRole role;

   public String getUserId() {
      return userId;
   }

   public void setUserId(String userId) {
      this.userId = userId;
   }

   public String getCourseId() {
      return courseId;
   }

   public void setCourseId(String courseId) {
      this.courseId = courseId;
   }

   public UserRole getRole() {
      return role;
   }

   public void setRole(UserRole userRole) {
      this.role = userRole;
   }
}
