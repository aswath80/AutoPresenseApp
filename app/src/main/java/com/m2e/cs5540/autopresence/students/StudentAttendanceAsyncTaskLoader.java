package com.m2e.cs5540.autopresence.students;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.m2e.cs5540.autopresence.base.AsyncLoaderStatus;
import com.m2e.cs5540.autopresence.database.DatabaseUtil;
import com.m2e.cs5540.autopresence.vao.UserAttendance;

import java.util.List;

/**
 * Created by maeswara on 8/5/2017.
 */
public class StudentAttendanceAsyncTaskLoader
      extends AsyncTaskLoader<AsyncLoaderStatus> {
   private static final String TAG = "StudentAttAsyncLoader";
   private String courseId;
   private String userId;

   public StudentAttendanceAsyncTaskLoader(Context context, String courseId,
         String userId) {
      super(context);
      this.courseId = courseId;
      this.userId = userId;
   }

   @Override public AsyncLoaderStatus loadInBackground() {
      AsyncLoaderStatus loaderStatus = new AsyncLoaderStatus();
      try {
         if (courseId != null && userId != null) {
            List<UserAttendance> userAttendanceList =
                  DatabaseUtil.getInstance().getUserAttendances(courseId,
                        userId);
            loaderStatus.setResult(userAttendanceList);
         }
      } catch (Exception e) {
         e.printStackTrace();
         loaderStatus.setException(e);
      }
      return loaderStatus;
   }

   @Override public void deliverResult(AsyncLoaderStatus data) {
      super.deliverResult(data);
   }
}
