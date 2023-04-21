<%@ page import="com.hcmus.newportal.models.Student" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.hcmus.newportal.models.Course" %>
<%@ page import="com.hcmus.newportal.models.CourseMember" %><%--
  Created by IntelliJ IDEA.
  User: vandu
  Date: 4/16/2023
  Time: 2:51 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <% Course course = (Course)request.getAttribute("course"); %>
    <title><%= course.getName() %></title>
    <!-- Add Bootstrap CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
          crossorigin="anonymous">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/css/style.css"/>
</head>
<body>
<div class="container-fluid">
    <div class="mb-0">
        <form method="get" action="${pageContext.request.contextPath}/courses">
            <button type="submit" class="btn btn-link">Back</button>
        </form>
    </div>
    <div class="row">
        <div class="ml-20 mr-20 w-100 container">
            <div class="container-fluid justify-content-center">
                <div class="row">
                    <h1><%= course.getName() %></h1>
                    <button type="button" class="ml-3 mr-2 mt-2 mb-2 btn btn-outline-secondary" disabled><%=course.getYear()%></button>
                </div>
                <h5 class="row course-id-title"><%= course.getId()%></h5>
            </div>
            <div class="row">
                <div class=" col-md-8 course-members-panel w-100">
                    <div class=" scroll-view course-members-list">
                    <table class="table course-members-table fixed-table">
                        <colgroup>
                            <col class="col-cm-id">
                            <col class="col-cm-name">
                        </colgroup>
                        <thead>
                        <tr class="course-members-row">
                            <th>ID</th>
                            <th>Name</th>
                        </tr>
                        </thead>
                        <tbody>
                        <%
                            ArrayList<CourseMember> courseMembers = (ArrayList<CourseMember>) request.getAttribute("courseMembers");
                            for (CourseMember member : courseMembers) {
                        %>
                        <tr class="course-members-row">
                            <td class="row-id-value"><%= member.getStudentId() %></td>
                            <td class="ellipsis row-name-value"><%= member.getStudentName() %></td>
                        </tr>
                        <% } %>
                        </tbody>
                    </table>
                    </div>
                    <div class="float-right d-flex mt-3 pt-2 pb-2">
                        <div class="list-options d-flex">
                            <button type="submit" class="btn btn-success add ml-2 pl-5 pr-5" onclick="updateGrades()">Update Student</button>
                            <button type="submit" class="btn btn-danger add ml-2 pl-5 pr-5" onclick="removeStudent()">Remove Student</button>
                        </div>
                    </div>
                </div>
                <div class="col-md-4 pl-4 pr-4 add-student-to-course">
                    <h2 class="mt-4 mb-3">Add Student</h2>
                    <form method="post" action="${pageContext.request.contextPath}/manage-course">
                        <input type="hidden" class="form-control" id="id" name="id" value="<%=course.getId()%>">
                        <input type="hidden" class="form-control" name="listOperation" value="add">
                        <input type="hidden" class="form-control" id="year" name="year" value="<%=course.getYear()%>">
                        <div class="form-group mt-2">
                            <label for="student-id">ID:</label>
                            <input type="text" class="form-control" id="student-id" name="studentId" placeholder="10-digit ID">
                        </div>
                        <div class="form-group mt-2">
                            <label for="student-name">Name:</label>
                            <input type="text" class="form-control" id="student-name" name="name" readonly placeholder="Student's name">
                        </div>
                        <div class="mt-6 error-message text-center"><%= request.getAttribute("error") == null? "" : request.getAttribute("error") %></div>
                        <button type="submit" id="add-student-button" class="btn btn-primary btn-block mt-5 mb-5">Add Student</button>
                    </form>
                </div>
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
    const courseId = <%= course.getId()%>;
    const courseYear = <%= course.getYear()%>;
</script>
<script src="${pageContext.request.contextPath}/views/js/course_member.js"></script>
</body>
</html>
