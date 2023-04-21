package com.hcmus.newportal.controllers;

import com.hcmus.newportal.models.Course;
import com.hcmus.newportal.models.StudentGrade;
import com.hcmus.newportal.models.StudentManagement;
import com.hcmus.newportal.models.StudentManagementSingleton;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(urlPatterns = {"/edit-grades"})
public class EditGradesServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        removeBrowserCache(response);
        try {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/views/editGrades.jsp");
            dispatcher.forward(request, response);
        }catch (IOException | ServletException e) {
            throw new RuntimeException(e);
        }
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            // retrieve the parameters from the request
            String courseId = request.getParameter("courseId");
            String studentId = request.getParameter("studentId");
            int year = Integer.parseInt(request.getParameter("year"));
            float grades = Float.parseFloat(request.getParameter("grades"));

            // add the student to the database
            StudentManagement database = StudentManagementSingleton.getInstance();
            Course course = database.getCourse(courseId, year);
            if (course == null) {
                throw new IllegalArgumentException("This course doesn't exist.");
            }
            StudentGrade gradeTest = new StudentGrade(studentId, courseId, course.getName(), year, grades);
            database.updateGrades(courseId, year, studentId, grades);
            // redirect back to the student list page
            response.sendRedirect(request.getContextPath() + "/manage-course?id=" + courseId + "&year=" + year);
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
