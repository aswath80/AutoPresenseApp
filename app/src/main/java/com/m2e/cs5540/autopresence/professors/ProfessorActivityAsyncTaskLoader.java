package com.m2e.cs5540.autopresence.professors;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.m2e.cs5540.autopresence.base.AsyncLoaderStatus;
import com.m2e.cs5540.autopresence.context.AppContext;
import com.m2e.cs5540.autopresence.database.DatabaseUtil;
import com.m2e.cs5540.autopresence.vao.Course;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kumar on 8/5/2017.
 */

public class ProfessorActivityAsyncTaskLoader
      extends AsyncTaskLoader<AsyncLoaderStatus> {

   private DatabaseUtil db = DatabaseUtil.getInstance();

   public ProfessorActivityAsyncTaskLoader(Context context) {
      super(context);
   }

   @Override public AsyncLoaderStatus loadInBackground() {
      AsyncLoaderStatus loaderStatus = new AsyncLoaderStatus();
      try {
         List<Course> courses = db.getCoursesByProfId(
               AppContext.getCurrentAppContext().getUser().getId());
         if (courses != null) {
            List<Course> courseList = new ArrayList<>();
            for (Course co : courses) {
               Course course = DatabaseUtil.getInstance().getCourse(co.getId());
               courseList.add(course);
            }
            loaderStatus.setResult(courseList);
         }
      } catch (Exception e) {
         e.printStackTrace();
         loaderStatus.setException(e);
      }
      return loaderStatus;
   }
}
