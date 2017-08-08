package com.m2e.cs5540.autopresence.students;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import com.m2e.cs5540.autopresence.base.AsyncLoaderStatus;
import com.m2e.cs5540.autopresence.context.AppContext;
import com.m2e.cs5540.autopresence.database.DatabaseUtil;
import com.m2e.cs5540.autopresence.exception.AppException;
import com.m2e.cs5540.autopresence.vao.Course;
import com.m2e.cs5540.autopresence.vao.CourseAttendancePercent;
import com.m2e.cs5540.autopresence.vao.CourseEnrollment;
import com.m2e.cs5540.autopresence.vao.UserAttendance;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by maeswara on 8/5/2017.
 */
public class StudentHomeAsyncTaskLoader
      extends AsyncTaskLoader<AsyncLoaderStatus> {
   private static final String TAG = "StudentHomeAsyncLoader";
   private static final SimpleDateFormat meetingDateFormat =
         new SimpleDateFormat("MM/dd/yyyy");

   public StudentHomeAsyncTaskLoader(Context context) {
      super(context);
   }

   @Override public AsyncLoaderStatus loadInBackground() {
      AsyncLoaderStatus loaderStatus = new AsyncLoaderStatus();
      try {
         List<CourseEnrollment> courseEnrollmentList =
               DatabaseUtil.getInstance().getCourseEnrollmentsByUserId(
                     AppContext.getCurrentAppContext().getUser().getId());
         Log.i(TAG, "Student course enrollment " + courseEnrollmentList);
         if (courseEnrollmentList != null) {
            List<CourseAttendancePercent> courseAttendancePercentList =
                  new ArrayList<>();
            for (int i = 0; i < courseEnrollmentList.size(); i++) {
               CourseAttendancePercent courseAttendancePercent =
                     new CourseAttendancePercent();
               CourseEnrollment courseEnrollment = courseEnrollmentList.get(i);
               Course course = DatabaseUtil.getInstance().getCourse(
                     courseEnrollment.getCourseId());
               courseAttendancePercent.setCourseCount(
                     courseEnrollmentList.size());
               courseAttendancePercent.setCourseName(course.getName());
               courseAttendancePercent.setAttendancePercent(
                     String.format("%.2f %%", calculateAttendancePercent
                           (course)));
               courseAttendancePercentList.add(courseAttendancePercent);
            }
            loaderStatus.setResult(courseAttendancePercentList);
         }
      } catch (Exception e) {
         e.printStackTrace();
         loaderStatus.setException(e);
      }
      return loaderStatus;
   }

   private double calculateAttendancePercent(Course course) {
      Log.i(TAG, "$$$ Calculating attendance percent for course " + course);
      List<UserAttendance> userAttendanceList =
            DatabaseUtil.getInstance().getUserAttendances(course.getId(),
                  AppContext.getCurrentAppContext().getUser().getId());
      if (userAttendanceList != null && userAttendanceList.size() > 0) {
         String meetingStartDate = course.getMeetingDate().getStartDate();
         try {
            Date fromDate = meetingDateFormat.parse(meetingStartDate);
            long msDiff = new Date().getTime() - fromDate.getTime();
            long daysDiff = msDiff / (1000 * 60 * 60 * 24L);
            int classDaysInAWeek = calculateNoOfClassDays(
                  course.getMeetingDate().getMeetingDays());
            double classDays = (double) daysDiff / classDaysInAWeek;
            Log.i(TAG,
                  "$$$ msDiff = " + msDiff + " daysDiff = " + daysDiff + " " +
                        "classDaysInAWeek = " + classDaysInAWeek);
            return userAttendanceList.size() * 100 / classDays;
         } catch (ParseException e) {
            e.printStackTrace();
            throw new AppException(
                  "Error parsing meeting date " + meetingStartDate);
         }
      }
      return 0;
   }

   private int calculateNoOfClassDays(String meetingDays) {
      int count = 0;
      char[] cs = meetingDays.toCharArray();
      for (char c : cs) {
         if (c == '1') {
            count++;
         }
      }
      return count;
   }

   @Override public void deliverResult(AsyncLoaderStatus data) {
      super.deliverResult(data);
   }
}
