package com.hcmus.newportal.models;

public class CourseMember {
    private final String courseId;
    private final String studentId;
    private final String studentName;
    private final int year;

    CourseMember(String courseId, String studentId, String studentName, int year) {
        this.courseId = courseId;
        this.studentId = studentId;
        this.studentName = studentName;
        this.year = year;
    }

    public String getCourseId() {
        return this.courseId;
    }

    public String getStudentId() {
        return this.studentId;
    }
    public int getYear() { return this.year; }

    public String getStudentName() {
        return this.studentName;
    }
}
