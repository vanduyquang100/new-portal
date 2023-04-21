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

@WebServlet(urlPatterns = {"/manage-course"})
public class CourseMembersServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        removeBrowserCache(response);
        try {
            getCourseMembers(request, response);
        }catch ( IOException | ServletException |
                InterruptedException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            System.out.println("doPost was called.");
            String type = request.getParameter("listOperation");
            String courseId = request.getParameter("id");

            String studentId = request.getParameter("studentId");
            if (type == null || studentId == null) {
                response.setStatus(400);
                return;
            }

            StudentManagement database = StudentManagementSingleton.getInstance();
            Student student = database.getStudent(studentId);
            if (student == null) {
                throw new IllegalArgumentException("Student doesn't exist.");
            }

            System.out.println("Type of POST is: " + type);
            if (type.equals("getName")) {
                String result = student.getName();
                response.setContentType("text/plain");
                response.getWriter().write(result);
            } else if (type.equals("add")) {
                int year = Integer.parseInt(request.getParameter("year"));
                database.addStudent(courseId, year, studentId);
                response.sendRedirect(request.getContextPath() + "/manage-course?id=" + courseId + "&year=" + year);
            } else if (type.equals("remove")) {
                int year = Integer.parseInt(request.getParameter("year"));
                database.removeStudent(courseId, year, studentId);
                response.sendRedirect(request.getContextPath() + "/manage-course?id=" + courseId + "&year=" + year);
            }
        } catch (ClassNotFoundException | InterruptedException | SQLException e) {
            throw new RuntimeException(e);
        } catch (IllegalArgumentException e) {
            request.setAttribute("error", e.getMessage());
            doGet(request, response);
        }
    }

    private void getCourseMembers(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, InterruptedException, ServletException, IOException {
        try {
            String id = request.getParameter("id");
            int year = Integer.parseInt(request.getParameter("year"));
            StudentManagement database = null;
                database = StudentManagementSingleton.getInstance();
            Course course = database.getCourse(id, year);
            ArrayList<CourseMember> courseMembers = database.getCourseMembers(course);
            // set the list of students as a request attribute
            request.setAttribute("course", course);
            request.setAttribute("courseMembers", courseMembers);

            // forward the request to the student list JSP
            RequestDispatcher dispatcher = request.getRequestDispatcher("/views/courseMembers.jsp");
            dispatcher.forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/courses");
        }
    }

    private void removeBrowserCache(HttpServletResponse response) {
        // Remove browser's cache
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
        response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
        response.setDateHeader("Expires", 0); // Proxies.
    }
}
