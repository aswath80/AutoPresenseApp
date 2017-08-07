package com.m2e.cs5540.autopresence.professors;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.m2e.cs5540.autopresence.R;
import com.m2e.cs5540.autopresence.context.AppContext;
import com.m2e.cs5540.autopresence.students.StudentAttendanceActivity;

/**
 * Created by Kumar on 8/5/2017.
 */

public class ProfessorViewHolder extends RecyclerView.ViewHolder
      implements View.OnClickListener {

   private static final String TAG = "ProfessorViewHolder";
   private TextView courseIdText;
   private TextView courseNameText;

   public ProfessorViewHolder(View itemView) {
      super(itemView);
      courseIdText = (TextView) itemView.findViewById(R.id.CourseId);
      courseNameText = (TextView) itemView.findViewById(R.id.CourseName);
      itemView.setOnClickListener(this);
   }

   public void setCourseIdText(String courseIdText) {

      this.courseIdText.setText(courseIdText);
   }

   public void setCourseNameText(String courseNameText) {
      this.courseNameText.setText(courseNameText);
   }

   @Override public void onClick(View view) {
      if (view != null) {
         Intent intent = new Intent(view.getContext(),ProfessorAttendanceActivity.class);
         intent.putExtra("courseId", courseIdText.getText());
         view.getContext().startActivity(intent);
      } else {
         Log.i(TAG, "$$$ Null view in ProfessorViewHolder.onClick");
      }
   }
}
