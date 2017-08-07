package com.m2e.cs5540.autopresence.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;

import com.m2e.cs5540.autopresence.R;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by maeswara on 8/6/2017.
 */
public class AttendanceNotification {
   public static void showAttendanceRegisteredNotification(Context context,
         String courseId) {
      NotificationManager notificationManager =
            (NotificationManager) context.getSystemService(
                  NOTIFICATION_SERVICE);
      NotificationCompat.Builder notificationBuilder =
            new NotificationCompat.Builder(context).setSmallIcon(
                  R.drawable.ic_ap_notification).setContentTitle(
                  "Attendance Registered").setContentText(
                  "Attendance for class " + courseId + "registered!");
      Notification notification = notificationBuilder.build();
      notificationManager.notify(201, notification);
   }
}
