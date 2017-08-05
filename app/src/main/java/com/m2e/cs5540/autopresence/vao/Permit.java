package com.m2e.cs5540.autopresence.vao;

/**
 * Created by Kumar on 8/5/2017.
 */

public class Permit {

    private String cin;
    private String courseId;
    private String semester;
    private String year;

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
