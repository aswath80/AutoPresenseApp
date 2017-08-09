package com.m2e.cs5540.autopresence.professors.students;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.m2e.cs5540.autopresence.R;
import com.m2e.cs5540.autopresence.vao.UserAttendance;

import java.util.List;

/**
 * Created by Kumar on 8/6/2017.
 */

public class StudentAdapter extends RecyclerView.Adapter<StudentHolder> {

   private static final String TAG = "StudentAdapter For Professors";
   private List<UserAttendance> attendanceList;
   private String date;

   public StudentAdapter() {
   }

   public void setDate(String date) {
      this.date = date;
   }

   public void setAttendanceList(List<UserAttendance> attendanceList) {
      this.attendanceList = attendanceList;
      this.notifyDataSetChanged();
   }

   @Override
   public StudentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
      View studentView = layoutInflater.inflate(R.layout.professor_student_view,
            parent, false);
      return new StudentHolder(studentView);
   }

   @Override public void onBindViewHolder(StudentHolder holder, int position) {

      UserAttendance userAttendance = attendanceList.get(position);
      if (userAttendance != null) {
         holder.setStudentName(userAttendance.getUserId());
         holder.setLoggedTime(userAttendance.getAttendanceTime());
      } else {
         //Log.w(TAG, "$$$ No attendance found for position " + position + " " + "in " + "adapter ");
      }

   }

   @Override public int getItemCount() {
      if (attendanceList == null || attendanceList.size() == 0) {
         return 0;
      }

      Log.i("Manish-Inner", "$$$ attendanceList " + attendanceList);
      return attendanceList.size();

      //Log.d(TAG, "$$$ Student attendanceList getItemCount: " + itemCount);
   }
}
