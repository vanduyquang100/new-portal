package com.hcmus.newportal.models;

import java.sql.Date;

public class Student {
    String id;
    String name;
    Date birthday;
    String address;
    String notes;
    public Student(String id, String name,
                   Date birthday, String address, String notes) {
        this.id = id;
        this.name = name;
        this.birthday = new Date(birthday.getTime());
        this.address = address;
        this.notes = notes;
    }
}
