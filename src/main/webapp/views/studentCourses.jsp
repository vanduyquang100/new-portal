<%@ page import="java.util.ArrayList" %>
<%@ page import="com.hcmus.newportal.models.Course" %>
<%@ page import="com.hcmus.newportal.models.Student" %>
<%@ page import="com.hcmus.newportal.models.StudentCourse" %>
<%@ page import="java.time.Year" %>
<%@ page import="java.util.Calendar" %><%--
  Created by IntelliJ IDEA.
  User: vandu
  Date: 4/16/2023
  Time: 2:51 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <% Student student = (Student)request.getAttribute("student"); %>
    <title><%= student.getName() %></title>
    <!-- Add Bootstrap CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
          crossorigin="anonymous">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/css/style.css"/>
</head>
<body>
<div class="container-fluid">
    <div class="mb-0">
        <form method="get" action="${pageContext.request.contextPath}/">
            <button type="submit" class="btn btn-link">Back</button>
        </form>
    </div>
    <div class="row">
        <div class="ml-20 mr-20 student-panel w-100">
            <div class="container-fluid justify-content-center">
                <div class="row">
                    <h1><%= student.getName() %></h1>
                    <button type="button" class="ml-3 mr-2 mt-2 mb-2 btn btn-outline-secondary" disabled><%=student.getId()%></button>
                </div>
                <form id="yearSubmit" method="get" class="mt-2 row" action="${pageContext.request.contextPath}/student-courses">
                    <input type="hidden" id="id" name="id" value="<%=student.getId()%>"/>
                    <label for="year" class="mr-3">Enter a year:</label>
                    <%
                        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
                    %>

                    <select id="year" class="selectpicker pt-1 pt-1 pl-2 pl-2" name="year">
                        <option value="0">All</option>
                        <% for (int i = 2000; i <= currentYear; i++) { %>
                        <option value="<%= i %>"><%= i %></option>
                        <% } %>
                    </select>
                </form>

<%--                <h5 class="row course-id-title"><%= course.getId()%></h5>--%>
            </div>
            <div class="scroll-view student-courses-list">
                <table class="table student-courses-table fixed-table">
                    <colgroup>
                        <col class="col-student-courses-id">
                        <col class="col-student-courses-name">
                        <col class="col-student-courses-year">
                    </colgroup>
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Course name</th>
                        <th>Year</th>
                    </tr>
                    </thead>
                    <tbody>
                    <%
                        ArrayList<StudentCourse> studentCourses = (ArrayList<StudentCourse>) request.getAttribute("studentCourses");
                        for (StudentCourse studentCourse : studentCourses) {
                    %>
                    <tr class="student-courses-row">
                        <td class="row-id-value"><%= studentCourse.getCourseId() %></td>
                        <td class="ellipsis"><%= studentCourse.getCourseName() %></td>
                        <td class="row-year-value"><%= studentCourse.getCourseYear() %></td>
                    </tr>
                    <% } %>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>

<!-- Bootstrap core JavaScript -->
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
        integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
        crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"
        integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
        crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"
        integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
        crossorigin="anonymous"></script>

<script>
    const contextPath = "${pageContext.request.contextPath}";
</script>
<script src="${pageContext.request.contextPath}/views/js/student_courses.js"></script>
</body>
</html>
