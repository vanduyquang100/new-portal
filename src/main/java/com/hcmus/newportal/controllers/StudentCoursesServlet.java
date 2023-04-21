package com.hcmus.newportal.controllers;

import com.hcmus.newportal.models.*;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(urlPatterns = {"/student-courses"})
public class StudentCoursesServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        removeBrowserCache(response);
        getStudentCourses(request, response);
    }

    private void getStudentCourses(HttpServletRequest request, HttpServletResponse response)  {
        try {
            String id = request.getParameter("id");
            String yearString = request.getParameter("year");
            int year = yearString == null ? 0 : Integer.parseInt(yearString);
            System.out.println("Getting student with id " + id + " a their courses in " + year);
            StudentManagement database = StudentManagementSingleton.getInstance();
            Student student = database.getStudent(id);
            ArrayList<StudentCourse> studentCourses = database.getStudentCourseList(student, year);
            // set the list of students as a request attribute
            request.setAttribute("student", student);
            request.setAttribute("studentCourses", studentCourses);

            // forward the request to the student list JSP
            RequestDispatcher dispatcher = request.getRequestDispatcher("/views/studentCourses.jsp");
            dispatcher.forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                response.sendRedirect(request.getContextPath() + "/");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        } catch (ServletException | InterruptedException |
                 ClassNotFoundException | IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private void removeBrowserCache(HttpServletResponse response) {
        // Remove browser's cache
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
        response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
        response.setDateHeader("Expires", 0); // Proxies.
    }
}
