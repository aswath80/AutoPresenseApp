package com.m2e.cs5540.autopresence.professors;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.m2e.cs5540.autopresence.R;

/**
 * Created by Kumar on 8/5/2017.
 */

public class ProfessorViewHolder  extends RecyclerView.ViewHolder
        implements View.OnClickListener {

    private TextView courseIdText;
    private TextView courseNameText;

    public void setCourseIdText(String courseIdText) {

        this.courseIdText.setText(courseIdText);
    }

    public void setCourseNameText(String courseNameText) {
        this.courseNameText.setText(courseNameText);
    }

    public ProfessorViewHolder(View itemView) {
        super(itemView);
        courseIdText    = (TextView) itemView.findViewById(R.id.CourseId);
        courseNameText  = (TextView) itemView.findViewById(R.id.CourseName);
    }

    @Override
    public void onClick(View v) {

    }
}
