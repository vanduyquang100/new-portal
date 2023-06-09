package com.hcmus.newportal.models;

import java.sql.*;
import java.util.ArrayList;

public class StudentManagement {

    private final String username = "sa";
    private final String password = "Thanh2908$%";
    private final String databaseName = "students";
    private final String url = "jdbc:sqlserver://localhost:1433;encrypt=true;databaseName=" + databaseName + ";trustServerCertificate=true;";
    private final String url_master = "jdbc:sqlserver://localhost:1433;encrypt=true;databaseName=master;trustServerCertificate=true;";

    public void createDatabase() throws SQLException {
        try (Connection connection = DriverManager.getConnection(
                url_master, username, password)) {
            // Check if database already exists
            Statement statement = connection.createStatement();
            statement.executeUpdate("USE master");
            // Drop the database if it exists
            statement.executeUpdate("IF DB_ID('" + databaseName +
                    "') IS NOT NULL " +
                    "DROP DATABASE " + databaseName);
            Thread.sleep(2000);
            System.out.println("Database dropped successfully.");

            // Create the database
            statement.executeUpdate("CREATE DATABASE " + databaseName);
            Thread.sleep(2000);
            statement.executeUpdate(" USE " + databaseName);
            System.out.println("Database created successfully.");
            createTables();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void createTables() throws SQLException {
        try (Connection conn = connectToServer()) {
            // Create necessary tables
            String studentTable = "CREATE TABLE student (" +
                    "id CHAR(10) PRIMARY KEY," +
                    "name NVARCHAR(255)," +
                    "birthday DATE," +
                    "address NVARCHAR(255)," +
                    "notes NVARCHAR(255))";
            String courseTable = "CREATE TABLE course (" +
                    "id CHAR(10)," +
                    "name NVARCHAR(255)," +
                    "lecture NVARCHAR(255)," +
                    "year INT," +
                    "notes NVARCHAR(255)," +
                    "PRIMARY KEY (id, year))";
            String courseMemberTable = "CREATE TABLE course_members (" +
                    "course_id CHAR(10)," +
                    "student_id CHAR(10)," +
                    "year INT," +
                    "score FLOAT," +
                    "PRIMARY KEY (course_id, student_id, year))";

            Statement statement = conn.createStatement();
            statement.executeUpdate(studentTable);
            statement.executeUpdate(courseTable);
            statement.executeUpdate(courseMemberTable);
            System.out.println("Successfully created tables.");
        }
    }

    public Connection connectToServer() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    public StudentManagement() throws SQLException, ClassNotFoundException, InterruptedException {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        createDatabase(); // Comment out this line
        connectToServer();
    }

    public void createStudent(Student student) {
        String query = "INSERT INTO student (" +
                "id, name, birthday, address, notes)" +
                "VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = connectToServer();
             PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, student.getId());
            statement.setString(2, student.getName());
            statement.setDate(3, student.getBirthday());
            statement.setString(4, student.getAddress());
            statement.setString(5, student.getNotes());
            statement.executeUpdate();
            System.out.println("Successfully added a student.");
        } catch (SQLException e) {
            System.out.println("Throw error.");
            if (student.getId() == null) {
                throw new IllegalArgumentException("Id cannot be null.");
            } else {
                throw new IllegalArgumentException("Id already exists.");
            }
        }
    }

    public void createCourse(Course course) {
        String query = "INSERT INTO course (" +
                "id, name, lecture, year, notes)" +
                "VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = connectToServer();
             PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, course.getId());
            statement.setString(2, course.getName());
            statement.setString(3, course.getLecture());
            statement.setInt(4, course.getYear());
            statement.setString(5, course.getNotes());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Throw error.");
            if (course.getId() == null) {
                throw new IllegalArgumentException("Id cannot be null.");
            } else {
                throw new IllegalArgumentException("Id already exists.");
            }
        }
    }

    public void addStudent(String courseId, int year, String studentId) {
        String query = "INSERT INTO course_members (" +
                "course_id, student_id, year, score) " +
                "VALUES (?, ?, ?, 0.0)";
        try (Connection conn = connectToServer();
             PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, courseId);
            statement.setString(2, studentId);
            statement.setInt(3, year);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateGrades(String courseId, int year, String studentId, double grade) {
        String query = "UPDATE course_members SET score = ? WHERE course_id = ? AND year = ? AND student_id = ?";
        try (Connection conn = connectToServer();
             PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setDouble(1, grade);
            statement.setString(2, courseId);
            statement.setInt(3, year);
            statement.setString(4, studentId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteStudent(String studentId) throws SQLException {
        String query = "DELETE FROM student WHERE id = ?";
        try (Connection conn = connectToServer();
             PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, studentId);
            statement.executeUpdate();
        }
    }

    public void deleteCourse(String courseId) throws SQLException {
        String query = "DELETE FROM course WHERE id = ?";
        try (Connection conn = connectToServer();
             PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, courseId);
            statement.executeUpdate();
        }
    }

    public void removeStudent(String courseId, int year, String studentId) {
        String query = "DELETE FROM course_members WHERE course_id = ? AND student_id = ? AND year = ?";
        try (Connection conn = connectToServer();
             PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, courseId);
            statement.setString(2, studentId);
            statement.setInt(3, year);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void updateStudent(Student student) throws SQLException {
        String query = "UPDATE student SET name = ?, birthday = ?, address = ?, notes = ? WHERE id = ?";
        try (Connection conn = connectToServer();
             PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, student.getName());
            statement.setDate(2, student.getBirthday());
            statement.setString(3, student.getAddress());
            statement.setString(4, student.getNotes());
            statement.setString(5, student.getId());
            statement.executeUpdate();
        }
    }

    public void updateCourse(Course course) throws SQLException {
        String query = "UPDATE course SET name = ?, lecture = ?, year = ?, notes = ? WHERE id = ?";
        try (Connection conn = connectToServer();
             PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, course.getName());
            statement.setString(2, course.getLecture());
            statement.setInt(3, course.getYear());
            statement.setString(4, course.getNotes());
            statement.setString(5, course.getId());
            statement.executeUpdate();
        }
    }

    public ArrayList<Student> getStudentsByName(String studentName, int sortType) throws SQLException {
        ArrayList<Student> students = new ArrayList<>();
        String query = "SELECT * FROM student s LEFT JOIN " +
                "(SELECT student_id, AVG(score) as grade " +
                "FROM course_members " +
                "GROUP BY student_id) cm ON s.id = cm.student_id " +
                "WHERE s.name LIKE ? ";
        if (sortType == 0) { // sort by name
            query += " ORDER BY s.name";
        } else if (sortType == 1) {
            query += "ORDER BY grade";
        }
        try (Connection conn = connectToServer();
             PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, "%" + studentName + "%");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String name = resultSet.getString("name");
                Date birthday = resultSet.getDate("birthday");
                float grade = resultSet.getFloat("grade");
                String address = resultSet.getString("address");
                String notes = resultSet.getString("notes");
                Student student = new Student(id, name, birthday, grade, address, notes);
                students.add(student);
            }
        }
        return students;
    }

    public ArrayList<Course> getCoursesByName(String courseName, int sortType) throws SQLException {
        ArrayList<Course> courses = new ArrayList<>();
        String query = "SELECT * FROM course c WHERE name LIKE ? ";
        if (sortType == 0) { // sort by name
            query += " ORDER BY c.name";
        } else if (sortType == 1) {
            query += "ORDER BY c.id";
        }
        try (Connection conn = connectToServer();
             PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, "%" + courseName + "%");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String name = resultSet.getString("name");
                String lecture = resultSet.getString("lecture");
                int year = resultSet.getInt("year");
                String notes = resultSet.getString("notes");
                Course course = new Course(id, name, year, lecture, notes);
                courses.add(course);
            }
        }
        return courses;
    }

    public ArrayList<Student> getStudentList(int sortType) throws SQLException {
        ArrayList<Student> students = new ArrayList<>();
        String query = "SELECT * FROM student s LEFT JOIN " +
                "(SELECT student_id, AVG(score) as grade " +
                "FROM course_members " +
                "GROUP BY student_id) cm ON s.id = cm.student_id";
        if (sortType == 0) { // sort by name
            query += " ORDER BY s.name";
        } else if (sortType == 1) {
            query += " ORDER BY grade";
        }
        try (Connection conn = connectToServer();
             PreparedStatement statement = conn.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String name = resultSet.getString("name");
                Date birthday = resultSet.getDate("birthday");
                float grade = resultSet.getFloat("grade");
                String address = resultSet.getString("address");
                String notes = resultSet.getString("notes");
                Student student = new Student(id, name, birthday, grade, address, notes);
                students.add(student);
            }
        }
        System.out.println("Found " + students.size() + " student(s) in the list.");
        return students;
    }

    public ArrayList<Course> getCourseList(int sortType) throws SQLException {
        ArrayList<Course> courses = new ArrayList<>();
        String query = "SELECT * FROM course";
        if (sortType == 0) { // sort by name
            query += " ORDER BY course.name";
        } else {
            query += " ORDER BY course.id";
        }
        try (Connection conn = connectToServer();
             PreparedStatement statement = conn.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String name = resultSet.getString("name");
                String lecture = resultSet.getString("lecture");
                int year = resultSet.getInt("year");
                String notes = resultSet.getString("notes");
                Course course = new Course(id, name, year, lecture, notes);
                courses.add(course);
            }
        }
        return courses;
    }

    public ArrayList<CourseMember> getCourseMembers(Course course) throws SQLException {
        ArrayList<CourseMember> members = new ArrayList<>();
        String query = "SELECT * " +
                "FROM course_members sc " +
                "JOIN student ON sc.student_id = student.id " +
                "WHERE sc.course_id = " + course.getId() +
                "AND sc.year = " + course.getYear();
        try (Connection conn = connectToServer();
             PreparedStatement statement = conn.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String studentId = resultSet.getString("student_id");
                String courseId = resultSet.getString("course_id");
                String studentName = resultSet.getString("name");
                int year = resultSet.getInt("year");
                CourseMember member = new CourseMember(courseId, studentId, studentName, year);
                members.add(member);
            }
        }
        return members;
    }

    public ArrayList<StudentCourse> getStudentCourseList(Student student, int year) throws SQLException {
        ArrayList<StudentCourse> courses = new ArrayList<>();
        String query = "SELECT * " +
                "FROM course_members cm " +
                "JOIN course ON cm.course_id = course.id " +
                "WHERE cm.student_id = " + student.getId();
        if (year != 0) { // get only a year
            query += " AND course.year = " + year;
        }
        try (Connection conn = connectToServer();
             PreparedStatement statement = conn.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String studentId = resultSet.getString("student_id");
                String courseId = resultSet.getString("course_id");
                String courseName = resultSet.getString("name");
                int courseYear = resultSet.getInt("year");
                StudentCourse course = new StudentCourse(studentId, courseId, courseName, courseYear);
                courses.add(course);
            }
        }
        return courses;
    }

    public ArrayList<StudentGrade> getStudentGrades(Student student, int year) throws SQLException {
        ArrayList<StudentGrade> grades = new ArrayList<>();
        String query = "SELECT * " +
                "FROM course_members cm " +
                "JOIN course ON cm.course_id = course.id " +
                "WHERE cm.student_id = " + student.getId();
        if (year != 0) { // get only a year
            query += " AND course.year = " + year;
        }
        try (Connection conn = connectToServer();
             PreparedStatement statement = conn.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String studentId = resultSet.getString("student_id");
                String courseId = resultSet.getString("course_id");
                String courseName = resultSet.getString("name");
                int courseYear = resultSet.getInt("year");
                float score = resultSet.getFloat("score");
                StudentGrade grade = new StudentGrade(studentId, courseId, courseName, courseYear, score);
                grades.add(grade);
            }
        }
        return grades;
    }

    public Course getCourse(String courseId, int courseYear) {
        String query = "SELECT * " +
                "FROM course c " +
                "WHERE c.id = " + courseId +
                " AND c.year = " + courseYear;

        try (Connection conn = connectToServer();
             PreparedStatement statement = conn.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String id = resultSet.getString("id");
                String name = resultSet.getString("name");
                String lecture = resultSet.getString("lecture");
                int year = resultSet.getInt("year");
                String notes = resultSet.getString("notes");
                Course course = new Course(id, name, year, lecture, notes);
                return course;
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Student getStudent(String studentId) {
        String query = "SELECT * FROM student s WHERE s.id = " + studentId;
        try (Connection conn = connectToServer();
             PreparedStatement statement = conn.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String id = resultSet.getString("id");
                String name = resultSet.getString("name");
                Date birthday = resultSet.getDate("birthday");
                String address = resultSet.getString("address");
                String notes = resultSet.getString("notes");
                return new Student(id, name, birthday, 0.0f, address, notes);
            }
            return null;
        } catch (SQLException e) {
            System.out.print("Exception:" + e.toString());
            throw new RuntimeException(e);
        }
    }
}

