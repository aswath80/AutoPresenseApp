package com.m2e.cs5540.autopresence.professors;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.m2e.cs5540.autopresence.R;
import com.m2e.cs5540.autopresence.professors.students.StudentAdapter;
import com.m2e.cs5540.autopresence.vao.UserAttendance;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kumar on 8/6/2017.
 */

public class ProfessoAttendanceAdapter extends RecyclerView.Adapter<ProfessoAttendanceAdapter.AttendanceViewHolder>{

    private static final String TAG = "ProfAttendanceAdapter";
    private List<UserAttendance> attendanceList =  new ArrayList<>();
    private Context context;

    public ProfessoAttendanceAdapter() {
    }

    public void setAttendanceList(List<UserAttendance> attendanceList) {
        this.attendanceList = attendanceList;
        this.notifyDataSetChanged();
    }

    public List<UserAttendance> getAttendanceList() {
        return attendanceList;
    }

    @Override
    public AttendanceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View profAttenView = layoutInflater.inflate(R.layout.professor_attendance_view, parent, false);
        return new AttendanceViewHolder(profAttenView);
    }

    @Override
    public int getItemCount() {
        int itemCount = attendanceList != null ? attendanceList.size() : 0;
        Log.d(TAG, "$$$ Student attendanceList getItemCount: " + itemCount);
        return itemCount;
    }

    @Override
    public void onBindViewHolder(AttendanceViewHolder holder, int position) {

        UserAttendance userAttendance = attendanceList.get(position);
        if (userAttendance != null) {
            holder.setStudentAttendanceDate(userAttendance.getAttendanceDate());
            Log.i(TAG, "$$$ Done setting student attendance for " +  userAttendance.getAttendanceDate());
        } else {
            Log.w(TAG, "$$$ No attendance found for position " + position + " " +  "in " + "adapter ");
        }
    }

    class AttendanceViewHolder extends RecyclerView.ViewHolder {

        private static final String TAG = "AttendanceViewHolder";
        private TextView studentAttendanceDate;
        private RecyclerView studrecyclerview;
        private StudentAdapter studentAdapter;

        public void setStudentAttendanceDate(String studentAttendanceDate) {
            this.studentAttendanceDate.setText(studentAttendanceDate);
        }

        public AttendanceViewHolder(View itemView) {
            super(itemView);
            studentAttendanceDate   = (TextView) itemView.findViewById(R.id.studentAttendanceDate);
            studrecyclerview        = (RecyclerView) itemView.findViewById(R.id.StudentAttendanceRecyclerView);

            studentAdapter = new StudentAdapter();
            studentAdapter.setAttendanceList(attendanceList);
            studrecyclerview.setAdapter(studentAdapter);
            studentAdapter.notifyDataSetChanged();
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(itemView.getContext(),
                    LinearLayoutManager.VERTICAL, false);
            studrecyclerview.setLayoutManager(linearLayoutManager);
        }

    }
}