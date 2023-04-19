<%@ page import="com.hcmus.newportal.models.Student" %>
<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: vandu
  Date: 4/16/2023
  Time: 2:51 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Student List</title>
    <!-- Add Bootstrap CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
          crossorigin="anonymous">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/css/style.css"/>
</head>
<body>
<div class="container-fluid">
    <div class="row">
        <div class="ml-20 mr-20 student-panel w-100">
            <div class="d-flex justify-content-between mb-4">
                <h1>Student List</h1>
            </div>
            <form method="get" action="${pageContext.request.contextPath}/">
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
                                    sortText = "Sort By Grade";
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

            <div class="scroll-view student-list">
                <table class="table student-table fixed-table">
                    <colgroup>
                        <col class="col-id">
                        <col class="col-name">
                        <col class="col-birthday">
                        <col class="col-grade">
                        <col class="col-address">
                        <col class="col-notes">
                    </colgroup>
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Birthday</th>
                        <th>Grade</th>
                        <th>Address</th>
                        <th>Notes</th>
                    </tr>
                    </thead>
                    <tbody>
                    <%
                        ArrayList<Student> studentList = (ArrayList<Student>) request.getAttribute("studentList");
                        for (Student student : studentList) {
                    %>
                    <tr class="student-row">
                        <td class="row-id-value"><%= student.getId() %></td>
                        <td class="ellipsis"><%= student.getName() %></td>
                        <td><%= student.getBirthday() %></td>
                        <td><%= student.getGrade() %></td>
                        <td class="ellipsis"><%= student.getAddress() %></td>
                        <td class="ellipsis"><%= student.getNotes() %></td>
                    </tr>
                    <% } %>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
    <div class="float-right d-flex pt-2 pb-2">
        <div class="list-options d-flex">
            <button type="submit" class="btn btn-primary ml-1 mr-2" onclick="editStudent()">Edit</button>
            <button type="submit" class="btn btn-danger" onclick="deleteStudent()">Delete</button>
        </div>
            <button type="submit" class="btn btn-primary add ml-2 pl-5 pr-5" onclick="addStudent()">Add Student</button>
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
<script src="${pageContext.request.contextPath}/views/js/student.js"></script>
</body>
</html>
