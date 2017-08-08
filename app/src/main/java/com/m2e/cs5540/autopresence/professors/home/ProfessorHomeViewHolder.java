package com.m2e.cs5540.autopresence.professors.home;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.m2e.cs5540.autopresence.R;

/**
 * Created by Ekta on 08-08-2017.
 */

public class ProfessorHomeViewHolder extends RecyclerView.ViewHolder {

    private static final String TAG = "ProfessorHomeViewHolder";
    private TextView courseNameText;
    private TextView courseAttendancePercentText;

    public ProfessorHomeViewHolder(View itemView) {
        super(itemView);
        courseNameText = (TextView) itemView.findViewById(R.id.professorHomeCoursePercentCourseName);
        courseAttendancePercentText = (TextView) itemView.findViewById(R.id.professorHomeCoursePercentCoursePercent);
    }

    public void setCourseNameText(String courseNameText) {
        this.courseNameText.setText(courseNameText);
    }

    public void setCourseAttendancePercentText(String courseLocationText) {
        this.courseAttendancePercentText.setText(courseLocationText);
    }
}
