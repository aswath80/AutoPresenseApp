package com.m2e.cs5540.autopresence.vao;

/**
 * Created by maeswara on 7/15/2017.
 */
public class Course {
   private String id;
   private String name;
   private String location;
   private String semester;
   private String time;

   public String getSemester() {
      return semester;
   }

   public void setSemester(String semester) {
      this.semester = semester;
   }

   public String getTime() {
      return time;
   }

   public void setTime(String time) {
      this.time = time;
   }

   private MeetingDate[] meetingDates;

   public String getId() {
      return id;
   }

   public void setId(String id) {
      this.id = id;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getLocation() {
      return location;
   }

   public void setLocation(String location) {
      this.location = location;
   }

   public MeetingDate[] getMeetingDates() {
      return meetingDates;
   }

   public void setMeetingDates(MeetingDate[] meetingDates) {
      this.meetingDates = meetingDates;
   }
}
