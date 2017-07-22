package com.m2e.cs5540.autopresence.vao;

/**
 * Created by maeswara on 7/15/2017.
 */

public enum UserRole {
   STUDENT_ROLE("student"), PROFESSOR_ROLE("professor");

   private String roleName;

   private UserRole(String roleName) {
      this.roleName = roleName;
   }

   public static UserRole getEnum(String roleName) {
      if (STUDENT_ROLE.toString().equals(roleName)) {
         return STUDENT_ROLE;
      } else if (PROFESSOR_ROLE.toString().equals(roleName)) {
         return PROFESSOR_ROLE;
      }
      throw new IllegalArgumentException("Unrecognized user role " + roleName);
   }

   @Override public String toString() {
      return roleName;
   }
}
