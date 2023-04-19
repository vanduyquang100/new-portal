package com.hcmus.newportal.models;

import java.sql.Date;

public class Student {
    private final String id;
    private final String name;
    private final Date birthday;
    private final String address;
    private final float grade;
    private final String notes;
    public Student(String id, String name,
                   Date birthday, Float grade, String address, String notes) {
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }
        if (id.length() != 10) {
            throw new IllegalArgumentException("Id must have 10 characters.");
        }
        this.id = id;
        this.name = name;
        this.birthday = new Date(birthday.getTime());
        this.address = address;
        this.grade = grade;
        this.notes = notes;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getAddress() {
        return this.address;
    }

    public float getGrade() {
        return this.grade;
    }

    public String getNotes() {
        return this.notes;
    }

    public Date getBirthday() {
        return this.birthday;
    }
}
