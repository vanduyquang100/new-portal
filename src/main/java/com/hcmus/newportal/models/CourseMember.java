package com.hcmus.newportal.models;

public class CourseMember {
    private final String courseId;
    private final String studentId;
    private final String studentName;

    CourseMember(String courseId, String studentId, String studentName) {
        this.courseId = courseId;
        this.studentId = studentId;
        this.studentName = studentName;
    }

    String getCourseId() {
        return this.courseId;
    }

    String getStudentId() {
        return this.studentId;
    }

    String getStudentName() {
        return this.studentName;
    }
}
