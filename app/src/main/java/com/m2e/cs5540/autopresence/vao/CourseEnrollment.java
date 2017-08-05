package com.m2e.cs5540.autopresence.vao;

/**
 * Created by Ekta on 04-08-2017.
 */

public class CourseEnrollment {
    private String cin;
    private String courseId;
    private String term;
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

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
