package com.m2e.cs5540.autopresence.professors;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.m2e.cs5540.autopresence.R;
import com.m2e.cs5540.autopresence.vao.Course;

import java.util.List;

/**
 * Created by Kumar on 8/5/2017.
 */

public class ProfessorHomeAdapter
      extends RecyclerView.Adapter<ProfessorViewHolder> {

   private static final String TAG = "ProfessorHomeAdapter";
   private List<Course> courseList;

   public ProfessorHomeAdapter() {
   }

   public List<Course> getCourseList() {
      return courseList;
   }

   public void setCourseList(List<Course> courseList) {
      this.courseList = courseList;
   }

   @Override public ProfessorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

      LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
      View courseView = layoutInflater.inflate(R.layout.professor_course_view,  parent, false);
      return new ProfessorViewHolder(courseView);
   }

   @Override
   public void onBindViewHolder(ProfessorViewHolder holder, int position) {

      Course course = courseList.get(position);
      Log.i(TAG, "$$$ Professor course holder " + holder);
      Log.i(TAG, "$$$ Got student course " + course.getName() + " for " +
            "position " + position);

      holder.setCourseIdText(course.getId());
      holder.setCourseNameText(course.getName());
   }

   @Override public int getItemCount() {
      return courseList != null ? courseList.size() : 0;
   }
}
