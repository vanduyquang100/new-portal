package com.hcmus.newportal.models;

public class StudentCourse {
    private final String studentId;
    private final String courseId;
    private final String courseName;
    private final int courseYear;

    StudentCourse(String studentId, String courseId,
                  String courseName, int courseYear) {
        this.courseId = courseId;
        this.studentId = studentId;
        this.courseName = courseName;
        if (courseYear < 0) {
            throw new IllegalArgumentException("Course's year is invalid.");
        }
        this.courseYear = courseYear;
    }

    public String getCourseId() {
        return this.courseId;
    }

    public String getStudentId() {
        return this.studentId;
    }

    public String getCourseName() {
        return this.courseName;
    }

    public int getCourseYear() {
        return this.courseYear;
    }
}
