package com.m2e.cs5540.autopresence.professors;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.provider.ContactsContract;

import com.m2e.cs5540.autopresence.base.AsyncLoaderStatus;
import com.m2e.cs5540.autopresence.context.AppContext;
import com.m2e.cs5540.autopresence.database.DatabaseUtil;
import com.m2e.cs5540.autopresence.vao.Course;
import com.m2e.cs5540.autopresence.vao.CourseEnrollment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kumar on 8/5/2017.
 */

public class ProfessorActivityAsyncTaskLoader
      extends AsyncTaskLoader<AsyncLoaderStatus> {

   public ProfessorActivityAsyncTaskLoader(Context context) {
      super(context);
   }

   @Override public AsyncLoaderStatus loadInBackground() {
      AsyncLoaderStatus loaderStatus = new AsyncLoaderStatus();
      try {
         List<Course> coursesList = DatabaseUtil.getInstance().getCoursesByProfId(
                 AppContext.getCurrentAppContext().getUser().getId());
         if (coursesList != null) {
            List<Course> courseList = new ArrayList<>();
               for (Course co : coursesList) {
                  Course course = DatabaseUtil.getInstance().getCourse(co.getId());
                  courseList.add(course);
               }
            loaderStatus.setResult(courseList);
         }
      }catch (Exception e) {
         e.printStackTrace();
         loaderStatus.setException(e);
      }
      return loaderStatus;
   }

   @Override public void deliverResult(AsyncLoaderStatus data) {
      super.deliverResult(data);
   }
}
