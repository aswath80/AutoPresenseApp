package com.m2e.cs5540.autopresence.vao;

/**
 * Created by maeswara on 7/15/2017.
 */
public class Course {
   private String id;
   private String name;
   private String location;
   private MeetingDate meetingDate;
   private String term;
   private String year;
   private String professorId;

   public String getProfessorId() {
      return professorId;
   }

   public void setProfessorId(String professorId) {
      this.professorId = professorId;
   }

   public MeetingDate getMeetingDate() {
      return meetingDate;
   }

   public void setMeetingDate(MeetingDate meetingDates) {
      this.meetingDate = meetingDates;
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

   public String getTerm() {
      return term;
   }

   public void setTerm(String term) {
      this.term = term;
   }

   public String getYear() {
      return year;
   }

   public void setYear(String year) {
      this.year = year;
   }

   @Override public String toString() {
      return id + " - " + name;
   }
}
