package com.m2e.cs5540.autopresence.vao;

import java.util.List;

/**
 * Created by maeswara on 7/15/2017.
 */
public class Course {
   private String id;
   private String name;
   private String location;
   private String weekdays;
   private MeetingDate meetingDates;

   public MeetingDate getMeetingDates() {
      return meetingDates;
   }

   public void setMeetingDates(MeetingDate meetingDates) {
      this.meetingDates = meetingDates;
   }

   public String getWeekdays() {
      return weekdays;
   }

   public void setWeekdays(String weekdays) {
      this.weekdays = weekdays;
   }

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

}
