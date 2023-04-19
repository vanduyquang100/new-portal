package com.hcmus.newportal.controllers;

import com.hcmus.newportal.models.Course;
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
import java.util.ArrayList;

@WebServlet(urlPatterns = {"/courses"})
public class CourseListServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        removeBrowserCache(response);
        try {
            String searchValue = request.getParameter("searchValue");
            if (searchValue != null) {
                if (!searchValue.equals("")) {
                    getCourseListByName(request, response, searchValue);
                } else {
                    getCourseList(request, response);
                }
            } else {
                getCourseList(request, response);
            }
        }catch (SQLException | IOException | ServletException |
                InterruptedException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            System.out.println("doPost was called.");
            String type = request.getParameter("listOperation");
            if (type == null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            System.out.println("Type of POST is: " + type);
            if (type.equals("delete")) {
                String courseId = request.getParameter("courseId");
                if (courseId == null) { courseId = ""; }
                StudentManagement database = StudentManagementSingleton.getInstance();
                System.out.println("Delete course: " + courseId);
                database.deleteCourse(courseId);
                response.sendRedirect(request.getContextPath() + "/courses");
            }
        } catch (SQLException | ClassNotFoundException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void getCourseList(HttpServletRequest request, HttpServletResponse response) throws SQLException, ClassNotFoundException, InterruptedException, ServletException, IOException {
        // get the sort type from the request parameter
        String type = request.getParameter("sortType");
        System.out.println("Type is " + type);
        int sortType = type == null? 0 : Integer.parseInt(type);
        StudentManagement database = StudentManagementSingleton.getInstance();
        ArrayList<Course> courseList = database.getCourseList(sortType);

        request.setAttribute("courseList", courseList);
        request.setAttribute("sortType", sortType);


        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/courseList.jsp");
        dispatcher.forward(request, response);
    }

    private void getCourseListByName(HttpServletRequest request, HttpServletResponse response, String searchValue) throws SQLException, ClassNotFoundException, InterruptedException, ServletException, IOException {
        String type = request.getParameter("sortType");
        int sortType = type == null? 0 : Integer.parseInt(type);
        StudentManagement database = StudentManagementSingleton.getInstance();
        ArrayList<Course> courseList = database.getCoursesByName(searchValue, sortType);
        request.setAttribute("courseList", courseList);
        request.setAttribute("searchValue", searchValue);
        request.setAttribute("sortType", sortType);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/courseList.jsp");
        dispatcher.forward(request, response);
    }

    private void removeBrowserCache(HttpServletResponse response) {
        // Remove browser's cache
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
        response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
        response.setDateHeader("Expires", 0); // Proxies.
    }
}
