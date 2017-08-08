package com.m2e.cs5540.autopresence.students;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.m2e.cs5540.autopresence.base.AsyncLoaderStatus;
import com.m2e.cs5540.autopresence.context.AppContext;
import com.m2e.cs5540.autopresence.database.DatabaseUtil;
import com.m2e.cs5540.autopresence.vao.Course;
import com.m2e.cs5540.autopresence.vao.CourseEnrollment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maeswara on 8/5/2017.
 */
public class StudentCoursesAsyncTaskLoader
      extends AsyncTaskLoader<AsyncLoaderStatus> {
   public StudentCoursesAsyncTaskLoader(Context context) {
      super(context);
   }

   @Override public AsyncLoaderStatus loadInBackground() {
      AsyncLoaderStatus loaderStatus = new AsyncLoaderStatus();
      try {
         List<CourseEnrollment> courseEnrollmentList =
               DatabaseUtil.getInstance().getCourseEnrollmentsByUserId(
                     AppContext.getCurrentAppContext().getUser().getId());
         if (courseEnrollmentList != null) {
            List<Course> courseList = new ArrayList<>();
            for (int i = 0; i < courseEnrollmentList.size(); i++) {
               CourseEnrollment courseEnrollment = courseEnrollmentList.get(i);
               Course course = DatabaseUtil.getInstance().getCourse(
                     courseEnrollment.getCourseId());
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

   @Override public void deliverResult(AsyncLoaderStatus data) {
      super.deliverResult(data);
   }
}