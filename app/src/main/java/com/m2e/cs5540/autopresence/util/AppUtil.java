package com.m2e.cs5540.autopresence.util;

import android.util.Log;

import com.m2e.cs5540.autopresence.exception.AppException;
import com.m2e.cs5540.autopresence.vao.MeetingDate;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by maeswara on 7/16/2017.
 */
public class AppUtil {
   private static final String TAG = "AppUtil";
   //private static final SimpleDateFormat meetingDateFormat =
   //      new SimpleDateFormat("dd-MMM-yyyy");
   private static final SimpleDateFormat meetingDateFormat =
         new SimpleDateFormat("MM/dd/yyyy");
   private static final SimpleDateFormat meetingTimeFormat =
         new SimpleDateFormat("HH:mm");

   public static String encryptPassword(String password) {
      try {
         MessageDigest digest = MessageDigest.getInstance("MD5");
         digest.update(password.getBytes(), 0, password.length());
         String encryptedPass = new BigInteger(1, digest.digest()).toString(16);
         return encryptedPass;
      } catch (NoSuchAlgorithmException e) {
         throw new AppException("Error encrypting the user password", e);
      }
   }

   public static boolean isCurrentTimeInMeetingTime(MeetingDate meetingDate) {
      if (meetingDate != null) {
         try {
            Date fromDate = meetingDateFormat.parse(meetingDate.getStartDate());
            Date toDate = meetingDateFormat.parse(meetingDate.getEndDate());
            Date today = new Date();
            if (today.after(fromDate) && today.before(toDate)) {
               Log.i(TAG, "Date matched!");
               Date fromTime = meetingTimeFormat.parse(
                     meetingDate.getStartTime());
               Calendar fromTimeCal = Calendar.getInstance();
               fromTimeCal.setTime(fromTime);

               Date toTime = meetingTimeFormat.parse(meetingDate.getEndTime());
               Calendar toTimeCal = Calendar.getInstance();
               toTimeCal.setTime(toTime);

               Calendar todayDayCal = Calendar.getInstance();
               todayDayCal.setTime(today);

               Date todayTime = meetingTimeFormat.parse(
                     meetingTimeFormat.format(today));
               Calendar todayTimeCal = Calendar.getInstance();
               todayTimeCal.setTime(todayTime);

               if (todayTimeCal.getTime().after(fromTimeCal.getTime()) &&
                     todayTimeCal.getTime().before(toTimeCal.getTime())) {
                  Log.i(TAG, "Time matched!");
                  int todayDayOfWeek = todayDayCal.get(Calendar.DAY_OF_WEEK);
                  Log.i(TAG, "todayDayOfWeek = " + todayDayOfWeek);
                  Log.i(TAG, "meetingDay flag on todayDayOfWeek = " +
                        meetingDate.getMeetingDays()
                              .substring(todayDayOfWeek - 1, todayDayOfWeek));
                  return meetingDate.getMeetingDays().substring(
                        todayDayOfWeek - 1, todayDayOfWeek).equals("1");
               }
            }
         } catch (ParseException e) {
            e.printStackTrace();
            Log.e(TAG, "Parsing of meeting date failed. Cause: " +
                  e.getClass().getName() + ": " + e.getMessage(), e);
         }
      }
      return false;
   }

   public String convertToMeetingDateString(Date date) {
      if (date != null) {
         return meetingDateFormat.format(date);
      }
      return null;
   }
}
