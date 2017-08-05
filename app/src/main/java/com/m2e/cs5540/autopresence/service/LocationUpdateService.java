package com.m2e.cs5540.autopresence.service;

import android.Manifest;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.m2e.cs5540.autopresence.database.DatabaseUtil;
import com.m2e.cs5540.autopresence.location.AppLocationListener;
import com.m2e.cs5540.autopresence.util.AppUtil;
import com.m2e.cs5540.autopresence.vao.Course;
import com.m2e.cs5540.autopresence.vao.CourseEnrollment;
import com.m2e.cs5540.autopresence.vao.MeetingDate;
import com.m2e.cs5540.autopresence.vao.User;
import com.m2e.cs5540.autopresence.vao.UserAttendance;
import com.m2e.cs5540.autopresence.vao.UserCoordinate;
import com.m2e.cs5540.autopresence.vao.UserRole;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by maeswara on 7/15/2017.
 */
public class LocationUpdateService extends IntentService {
   private static final String TAG = "LocationUpdateService";
   private static final SimpleDateFormat dateOnlySdf = new SimpleDateFormat(
         "dd-MMM-yyyy");
   private static final SimpleDateFormat timeOnlySdf = new SimpleDateFormat(
         "HH:mm");
   private static final SimpleDateFormat dateAndTimeSdf = new SimpleDateFormat(
         "dd-MMM-yyyy HH:mm:ss");
   private boolean run = true;
   private AppLocationListener locationListener = new AppLocationListener();
   private LocationManager locationManager;
   private String userId;

   public LocationUpdateService() {
      super("LocationUpdateService");
   }

   @Override public void onCreate() {
      super.onCreate();
      intiLocationManager();
      registerLocationListener();
   }

   private void intiLocationManager() {
      locationManager = (LocationManager) getSystemService(
            Context.LOCATION_SERVICE);
   }

   @Override protected void onHandleIntent(@Nullable Intent intent) {
      this.userId = intent.getStringExtra("userId");
      while (run) {
         try {
            Thread.sleep(10 * 1000);
         } catch (InterruptedException e) {
            e.printStackTrace();
         }
         Location location = locationListener.getLocation();
         if (location != null) {
            sendLocationUpdateToDatabase(location);
            updateUserAttendance(location);
         }
      }
   }

   private void sendLocationUpdateToDatabase(Location location) {
      DatabaseUtil databaseUtil = DatabaseUtil.getInstance();
      UserCoordinate userCoordinate = new UserCoordinate();
      userCoordinate.setUserId(userId);
      userCoordinate.setLastUpdateTime(dateAndTimeSdf.format(new Date()));
      userCoordinate.setCurrentLatitude(location.getLatitude());
      userCoordinate.setCurrentLongitude(location.getLongitude());
      databaseUtil.updateUserCoordinate(userCoordinate);
   }

   private void updateUserAttendance(Location location) {
      DatabaseUtil databaseUtil = DatabaseUtil.getInstance();
      List<CourseEnrollment> courseRegistrationList =
            databaseUtil.getCourseEnrollmentsByUserId(userId);
      for (CourseEnrollment courseReg : courseRegistrationList) {
         Course course = DatabaseUtil.getInstance().getCourse(
               courseReg.getCourseId());
         if (course != null) {
            if (course.getMeetingDate() != null) {
               MeetingDate meetingDate = course.getMeetingDate();
               if (AppUtil.isCurrentTimeInMeetingTime(meetingDate)) {
                  List<Float> professorDistanceList = getProfessorDistances(
                        location, course);
                  for (float dist : professorDistanceList) {
                     if (dist <= 50) {
                        UserAttendance userAttendance = new UserAttendance();
                        userAttendance.setUserId(userId);
                        userAttendance.setCourseId(course.getId());
                        userAttendance.setAttendanceDate(
                              dateOnlySdf.format(new Date()));
                        userAttendance.setAttendanceTime(
                              timeOnlySdf.format(new Date()));
                        DatabaseUtil.getInstance().createUserAttendance(
                              userAttendance);
                     }
                  }
               }
            }
         }
      }
   }

   private List<Float> getProfessorDistances(Location currLocation,
         Course course) {
      List<Float> distanceList = new ArrayList<>();
      List<CourseEnrollment> courseRegistrationList =
            DatabaseUtil.getInstance().getCourseEnrollmentsByCourseId(
                  course.getId());
      for (CourseEnrollment courseReg : courseRegistrationList) {
         if (courseReg.getRole() == UserRole.PROFESSOR) {
            User user = DatabaseUtil.getInstance().getUserById(
                  courseReg.getUserId());
            UserCoordinate profCoordinate =
                  DatabaseUtil.getInstance().getUserCoordinate(user.getId());
            if (profCoordinate != null) {
               float[] results = new float[3];
               Location.distanceBetween(currLocation.getLatitude(),
                     currLocation.getLongitude(),
                     profCoordinate.getCurrentLatitude(),
                     profCoordinate.getCurrentLongitude(), results);
               distanceList.add(results[0]);
               Log.i(TAG, "Current distance from professor " + user.getName() +
                     ": " + results[0]);
            }
         }
      }
      return distanceList;
   }

   private void registerLocationListener() {
      if (ActivityCompat.checkSelfPermission(this,
            Manifest.permission.ACCESS_FINE_LOCATION) ==
            PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(this,
                  Manifest.permission.ACCESS_COARSE_LOCATION) ==
                  PackageManager.PERMISSION_GRANTED) {
         locationManager.requestLocationUpdates(
               LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
         locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,
               0, locationListener);
      }
   }

   @Override public void onDestroy() {
      super.onDestroy();
      locationManager.removeUpdates(locationListener);
      run = false;
   }
}
