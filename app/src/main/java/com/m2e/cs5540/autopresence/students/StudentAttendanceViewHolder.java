package com.m2e.cs5540.autopresence.students;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.m2e.cs5540.autopresence.R;

/**
 * Created by maeswara on 8/5/2017.
 */
public class StudentAttendanceViewHolder extends RecyclerView.ViewHolder {
   private static final String TAG = "StudentAttendViewHolder";
   private TextView attendanceDateText;
   private TextView attendanceTimeText;

   public StudentAttendanceViewHolder(View itemView) {
      super(itemView);
      attendanceDateText = (TextView) itemView.findViewById(
            R.id.studentAttendanceDate);
      attendanceTimeText = (TextView) itemView.findViewById(
            R.id.studentAttendanceTime);
   }

   public void setAttendanceDateText(String attendanceDateText) {
      this.attendanceDateText.setText(attendanceDateText);
   }

   public void setAttendanceTimeText(String attendanceTimeText) {
      this.attendanceTimeText.setText(attendanceTimeText);
   }
}
