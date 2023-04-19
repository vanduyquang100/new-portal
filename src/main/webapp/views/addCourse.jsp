<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add Course</title>
    <!-- Add Bootstrap CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
          crossorigin="anonymous">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/css/style.css"/>
</head>
<body>
<div class="container">
    <div class="justify-content-center">
        <div class="add-course">
            <h1 class="mt-4 mb-3">Add Course</h1>
            <form method="post" action="${pageContext.request.contextPath}/add-course">
                <div class="form-group mt-2">
                    <label for="id">ID:</label>
                    <input type="text" class="form-control" id="id" name="id" placeholder="10-digit ID">
                </div>
                <div class="form-group mt-2">
                    <label for="name">Name:</label>
                    <input type="text" class="form-control" id="name" name="name" placeholder="Course's name">
                </div>
                <div class="form-group mt-2">
                    <label for="year">Year:</label>
                    <input type="text" class="form-control" id="year" placeholder="Course's year" name="year">
                </div>
                <div class="form-group mt-2">
                    <label for="lecture">Lecture:</label>
                    <input type="text" class="form-control" id="lecture" name="lecture" placeholder="Lecture's name">
                </div>
                <div class="form-group mt-2">
                    <label for="notes">Notes:</label>
                    <input type="text" class="form-control" id="notes" name="notes"placeholder="Add notes">
                </div>
                <div class="mt-6 error-message text-center"><%= request.getAttribute("error") == null? "" : request.getAttribute("error") %></div>
                <button type="submit" class="btn btn-primary btn-block mt-3 mb-5">Add Course</button>
            </form>
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

<script src="${pageContext.request.contextPath}/views/js/course.js"></script>
</body>
</html>
