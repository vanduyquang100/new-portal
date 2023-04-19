package com.hcmus.newportal.models;

public class Course {
    private final String id;
    private final String name;
    private final int year;
    private final String lecture;
    private final String notes;
    public Course(String id, String name,
                   int year, String address, String notes) {
        this.id = id;
        this.name = name;
        if (year < 0) {
            throw new IllegalArgumentException("Course's year is invalid.");
        }
        this.year = year;
        this.lecture = address;
        this.notes = notes;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getLecture() {
        return this.lecture;
    }

    public String getNotes() {
        return this.notes;
    }

    public int getYear() {
        return this.year;
    }
}
