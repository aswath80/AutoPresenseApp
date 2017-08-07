package com.m2e.cs5540.autopresence.students;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.m2e.cs5540.autopresence.R;
import com.m2e.cs5540.autopresence.vao.UserAttendance;

import java.util.List;

/**
 * Created by maeswara on 8/5/2017.
 */
public class StudentAttendanceAdapter
      extends RecyclerView.Adapter<StudentAttendanceViewHolder> {
   private static final String TAG = "StudentAttendAdapter";
   private List<UserAttendance> attendanceList;

   public StudentAttendanceAdapter() {
   }

   public void setAttendanceList(List<UserAttendance> attendanceList) {
      this.attendanceList = attendanceList;
      this.notifyDataSetChanged();
   }

   @Override
   public StudentAttendanceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
      View studentCourseView = layoutInflater.inflate(
            R.layout.student_attendance_view, parent, false);
      return new StudentAttendanceViewHolder(studentCourseView);
   }

   @Override public void onBindViewHolder(StudentAttendanceViewHolder holder, int position) {
      Log.i(TAG, "$$$ Get student course at position " + position + " for " + "holder " + holder);
      UserAttendance userAttendance = attendanceList.get(position);
      if (userAttendance != null) {
         Log.i(TAG, "$$$ Got student attendance " +
               userAttendance.getAttendanceDate() + " - " +
               userAttendance.getAttendanceTime());
         holder.setAttendanceDateText(userAttendance.getAttendanceDate());
         holder.setAttendanceTimeText(userAttendance.getAttendanceTime());
         Log.i(TAG, "$$$ Done setting student attendance for " +
               userAttendance.getAttendanceDate());
      } else {
         Log.w(TAG, "$$$ No attendance found for position " + position + " " +
               "in " + "adapter ");
      }
   }

   @Override public int getItemCount() {
      int itemCount = attendanceList != null ? attendanceList.size() : 0;
      Log.i(TAG, "$$$ Student attendanceList getItemCount: " + itemCount);
      return itemCount;
   }
}
