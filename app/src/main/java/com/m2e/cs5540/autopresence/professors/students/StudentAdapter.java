package com.m2e.cs5540.autopresence.professors.students;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.m2e.cs5540.autopresence.R;
import com.m2e.cs5540.autopresence.students.StudentAttendanceViewHolder;
import com.m2e.cs5540.autopresence.vao.UserAttendance;

import java.util.List;

/**
 * Created by Kumar on 8/6/2017.
 */

public class StudentAdapter extends RecyclerView.Adapter<StudentHolder> {

    private static final String TAG = "StudentAdapter For Professors";
    private List<UserAttendance> attendanceList;

    public StudentAdapter() {
    }

    public void setAttendanceList(List<UserAttendance> attendanceList) {
        this.attendanceList = attendanceList;
        this.notifyDataSetChanged();
    }

    @Override
    public StudentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View studentView = layoutInflater.inflate(R.layout.professor_student_view, parent, false);
        return new StudentHolder(studentView);
    }

    @Override
    public void onBindViewHolder(StudentHolder holder, int position) {

        UserAttendance userAttendance = attendanceList.get(position);
        if (userAttendance != null) {
            holder.setStudentName(userAttendance.getUserId());
        } else {
            //Log.w(TAG, "$$$ No attendance found for position " + position + " " + "in " + "adapter ");
        }

    }

    @Override
    public int getItemCount() {
        int itemCount = attendanceList != null ? attendanceList.size() : 0;
        //Log.d(TAG, "$$$ Student attendanceList getItemCount: " + itemCount);
        return itemCount;
    }
}
