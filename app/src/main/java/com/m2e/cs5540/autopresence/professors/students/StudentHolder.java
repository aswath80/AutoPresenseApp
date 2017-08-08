package com.m2e.cs5540.autopresence.professors.students;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.m2e.cs5540.autopresence.R;

/**
 * Created by Kumar on 8/6/2017.
 */

public class StudentHolder extends RecyclerView.ViewHolder {

    private static final String TAG = "StudentHolder";
    private TextView studentName;
    private TextView loggedTime;

    public StudentHolder(View itemView) {
        super(itemView);
        studentName = (TextView) itemView.findViewById(R.id.studentName);
        loggedTime = (TextView) itemView.findViewById(R.id.loggedTime);
    }

    public void setStudentName(String studentName) {
        this.studentName.setText(studentName);
    }
    public void setLoggedTime(String loggedTime) { this.loggedTime.setText(loggedTime);  }
}
