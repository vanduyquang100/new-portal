package com.hcmus.newportal.controllers;

import com.hcmus.newportal.models.Student;
import com.hcmus.newportal.models.StudentManagement;
import com.hcmus.newportal.models.StudentManagementSingleton;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(urlPatterns = {"/add-student"})
public class AddStudentServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        removeBrowserCache(response);
        try {

            RequestDispatcher dispatcher = request.getRequestDispatcher("/views/addStudent.jsp");
            dispatcher.forward(request, response);
        }catch (IOException | ServletException e) {
            throw new RuntimeException(e);
        }
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            // retrieve the parameters from the request
            String id = request.getParameter("id");
            String name = request.getParameter("name");
            String birthdayStr = request.getParameter("birthday");
            if (birthdayStr.equals("")) {
                birthdayStr = "2000-01-01";
            }
            Date birthday = Date.valueOf(birthdayStr);
            String address = request.getParameter("address");
            String notes = request.getParameter("notes");

            // create a new Student object
            Student student = new Student(id, name, birthday, 0.0f, address, notes);

            // add the student to the database
            StudentManagement database = StudentManagementSingleton.getInstance();
            database.createStudent(student);
            // redirect back to the student list page
            response.sendRedirect(request.getContextPath());
        } catch (ClassNotFoundException | InterruptedException | SQLException e) {
            throw new RuntimeException(e);
        } catch (IllegalArgumentException e) {
            request.setAttribute("error", e.getMessage());
            doGet(request, response);
        }
    }

    private void removeBrowserCache(HttpServletResponse response) {
        // Remove browser's cache
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
        response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
        response.setDateHeader("Expires", 0); // Proxies.
    }
}
