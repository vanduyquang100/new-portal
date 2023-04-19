package com.hcmus.newportal.models;

import java.sql.SQLException;

public class StudentManagementSingleton {
    private static StudentManagement instance = null;

    private StudentManagementSingleton() throws SQLException, ClassNotFoundException, InterruptedException {
        instance = new StudentManagement();
    }

    public static StudentManagement getInstance() throws SQLException, ClassNotFoundException, InterruptedException {
        if (instance == null) {
            synchronized (StudentManagementSingleton.class) {
                if (instance == null) {
                    instance = new StudentManagement();
                }
            }
        }
        return instance;
    }
}