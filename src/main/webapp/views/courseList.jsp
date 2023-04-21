<%@ page import="java.util.ArrayList" %>
<%@ page import="com.hcmus.newportal.models.Course" %><%--
  Created by IntelliJ IDEA.
  User: vandu
  Date: 4/16/2023
  Time: 2:51 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Course List</title>
    <!-- Add Bootstrap CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
          crossorigin="anonymous">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/css/style.css"/>
</head>
<body>
<div class="container-fluid">
    <div class="mb-0">
        <form class="d-flex justify-content-end" method="get" action="${pageContext.request.contextPath}/">
            <button type="submit" class="btn btn-outline-primary pl-4 pr-4">Student List</button>
        </form>
    </div>
    <div class="row">
        <div class="ml-20 mr-20 student-panel w-100">
            <div class="d-flex justify-content-between mb-4">
                <h1>Course List</h1>
            </div>
            <form method="get" action="${pageContext.request.contextPath}/courses">
                <div class="row">
                    <div class="col">
                        <div class=" d-flex w-100">
                            <%  int sortType = (int)request.getAttribute("sortType");
                                String sortText;
                                if (request.getAttribute("sortType") == null) {
                                    sortType = 0;
                                }
                                if (sortType == 0) {
                                    sortText = "Sort By Name";
                                } else {
                                    sortText = "Sort By Id";
                            }%>
                            <input type="hidden" name="sortType" id="sortTypeValue" value="<%= sortType  %>"/>
                            <button class="btn" type="submit"  onclick="switchSortType()"><%= sortText %></button>
                            <%String searchText = request.getParameter("searchValue");%>
                            <input type="text" class="form-control ml-2 mr-2" value="<%= searchText != null ? searchText : "" %>" placeholder="Search by name" name="searchValue">
                            <div class="input-group-append">
                                <button class="btn btn-primary" type="submit">Search</button>
                            </div>
                        </div>
                    </div>
                </div>
            </form>

            <div class="scroll-view course-list">
                <table class="table course-table fixed-table">
                    <colgroup>
                        <col class="col-course-id">
                        <col class="col-course-name">
                        <col class="col-course-year">
                        <col class="col-course-lecture">
                        <col class="col-course-notes">
                    </colgroup>
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Year</th>
                        <th>Lecture</th>
                        <th>Notes</th>
                    </tr>
                    </thead>
                    <tbody>
                    <%
                        ArrayList<Course> courseList = (ArrayList<Course>) request.getAttribute("courseList");
                        for (Course course : courseList) {
                    %>
                    <tr class="course-row">
                        <td class="row-id-value"><%= course.getId() %></td>
                        <td class="ellipsis"><%= course.getName() %></td>
                        <td class="row-year-value"><%= course.getYear() %></td>
                        <td class="ellipsis"><%= course.getLecture() %></td>
                        <td class="ellipsis"><%= course.getNotes() %></td>
                    </tr>
                    <% } %>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
    <div class="float-right d-flex pt-2 pb-2">
        <div class="list-options d-flex">
            <button type="submit" class="btn btn btn-success ml-1 mr-2" onclick="manageCourse()">Manage Course</button>
            <button type="submit" class="btn btn-primary mr-2" onclick="editCourse()">Edit</button>
            <button type="submit" class="btn btn-danger" onclick="deleteCourse()">Delete</button>
        </div>
            <button type="submit" class="btn btn-primary add ml-2 pl-5 pr-5" onclick="addCourse()">Add Course</button>
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
<script src="${pageContext.request.contextPath}/views/js/course.js"></script>
</body>
</html>
