package com.hcmus.newportal.models;

public class StudentScore {
    private final String studentId;
    private final String courseId;
    private final String courseName;
    private final int courseYear;
    private final float score;

    StudentScore(String studentId, String courseId,
                 String courseName, int courseYear, float score) {
        this.courseId = courseId;
        this.studentId = studentId;
        this.courseName = courseName;
        if (courseYear < 0) {
            throw new IllegalArgumentException("Course's year is invalid.");
        }
        this.courseYear = courseYear;
        if (score < 0) {
            throw new IllegalArgumentException("Score is invalid.");
        }
        this.score = score;
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
    public float getScore() {
        return this.score;
    }
}
