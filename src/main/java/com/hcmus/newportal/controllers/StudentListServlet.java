package com.hcmus.newportal.controllers;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;

import com.hcmus.newportal.models.Student;
import com.hcmus.newportal.models.StudentManagement;
import com.hcmus.newportal.models.StudentManagementSingleton;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(urlPatterns = {""}, loadOnStartup = 1)
public class StudentListServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        removeBrowserCache(response);
        try {
            String searchValue = request.getParameter("searchValue");
            if (searchValue != null) {
                if (!searchValue.equals("")) {
                    getStudentListByName(request, response, searchValue);
                } else {
                    getStudentList(request, response);
                }
            } else {
                getStudentList(request, response);
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
                String studentId = request.getParameter("studentId");
                if (studentId == null) { studentId = ""; }
                StudentManagement database = StudentManagementSingleton.getInstance();
                System.out.println("Delete student: " + studentId);
                database.deleteStudent(studentId);
                response.sendRedirect(request.getContextPath() + "/");
            }
        } catch (SQLException | ClassNotFoundException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void getStudentList(HttpServletRequest request, HttpServletResponse response) throws SQLException, ClassNotFoundException, InterruptedException, ServletException, IOException {
        // get the sort type from the request parameter
        String type = request.getParameter("sortType");
        System.out.println("Type is " + type);
        int sortType = type == null? 0 : Integer.parseInt(type);
        StudentManagement database = StudentManagementSingleton.getInstance();
        ArrayList<Student> studentList = database.getStudentList(sortType);
        // set the list of students as a request attribute
        request.setAttribute("studentList", studentList);
        request.setAttribute("sortType", sortType);

        // forward the request to the student list JSP
        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/studentList.jsp");
        dispatcher.forward(request, response);
    }

    private void getStudentListByName(HttpServletRequest request, HttpServletResponse response, String searchValue) throws SQLException, ClassNotFoundException, InterruptedException, ServletException, IOException {
        String type = request.getParameter("sortType");
        int sortType = type == null? 0 : Integer.parseInt(type);
        StudentManagement database = StudentManagementSingleton.getInstance();
        ArrayList<Student> studentList = database.getStudentsByName(searchValue, sortType);
        request.setAttribute("studentList", studentList);
        request.setAttribute("searchValue", searchValue);
        request.setAttribute("sortType", sortType);
        // forward the request to the student list JSP
        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/studentList.jsp");
        dispatcher.forward(request, response);
    }

    private void removeBrowserCache(HttpServletResponse response) {
        // Remove browser's cache
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
        response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
        response.setDateHeader("Expires", 0); // Proxies.
    }
}
