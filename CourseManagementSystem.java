import java.sql.*;
import java.util.Scanner;

public class CourseManagementSystem {

    // DB connection details – change user / password as per your MySQL setup
    private static final String URL = "jdbc:mysql://localhost:3306/course_db";
    private static final String USER = "root";
    private static final String PASSWORD = "28072005";

    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        // Optional: Load MySQL driver (for older Java versions)
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found. Add the jar to your project.");
            return;
        }

        int choice;
        do {
            System.out.println("\n===== COURSE MANAGEMENT SYSTEM =====");
            System.out.println("1. Add Course");
            System.out.println("2. View All Courses");
            System.out.println("3. Update Course Fee");
            System.out.println("4. Delete Course");
            System.out.println("5. Search Course by Title");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            choice = readInt();

            switch (choice) {
                case 1:
                    addCourse();
                    break;
                case 2:
                    viewCourses();
                    break;
                case 3:
                    updateCourseFee();
                    break;
                case 4:
                    deleteCourse();
                    break;
                case 5:
                    searchCourseByTitle();
                    break;
                case 6:
                    System.out.println("Exiting... Thank you!");
                    break;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }

        } while (choice != 6);
    }

    // Helper to safely read int
    private static int readInt() {
        while (true) {
            try {
                int value = Integer.parseInt(sc.nextLine());
                return value;
            } catch (NumberFormatException e) {
                System.out.print("Please enter a valid number: ");
            }
        }
    }

    // Helper to safely read double
    private static double readDouble() {
        while (true) {
            try {
                double value = Double.parseDouble(sc.nextLine());
                return value;
            } catch (NumberFormatException e) {
                System.out.print("Please enter a valid number: ");
            }
        }
    }

    // 1. Add Course
    private static void addCourse() {
        System.out.println("\n--- Add New Course ---");
        System.out.print("Enter Course ID: ");
        String courseId = sc.nextLine();

        System.out.print("Enter Title: ");
        String title = sc.nextLine();

        System.out.print("Enter Duration (e.g. 3 months): ");
        String duration = sc.nextLine();

        System.out.print("Enter Fee: ");
        double fee = readDouble();

        String sql = "INSERT INTO courses (course_id, title, duration, fee) VALUES (?, ?, ?, ?)";

        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, courseId);
            ps.setString(2, title);
            ps.setString(3, duration);
            ps.setDouble(4, fee);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("Course added successfully!");
            } else {
                System.out.println("Failed to add course.");
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Course ID already exists. Please use a different Course ID.");
        } catch (SQLException e) {
            System.out.println("Error while adding course: " + e.getMessage());
        }
    }

    // 2. View All Courses
    private static void viewCourses() {
        System.out.println("\n--- All Courses ---");
        String sql = "SELECT course_id, title, duration, fee FROM courses";

        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            System.out.printf("%-10s %-30s %-15s %-10s%n", "CourseID", "Title", "Duration", "Fee");
            System.out.println("---------------------------------------------------------------------");

            boolean any = false;
            while (rs.next()) {
                any = true;
                String courseId = rs.getString("course_id");
                String title = rs.getString("title");
                String duration = rs.getString("duration");
                double fee = rs.getDouble("fee");

                System.out.printf("%-10s %-30s %-15s %-10.2f%n", courseId, title, duration, fee);
            }

            if (!any) {
                System.out.println("No courses found.");
            }

        } catch (SQLException e) {
            System.out.println("Error while fetching courses: " + e.getMessage());
        }
    }

    // 3. Update Course Fee
    private static void updateCourseFee() {
        System.out.println("\n--- Update Course Fee ---");
        System.out.print("Enter Course ID to update fee: ");
        String courseId = sc.nextLine();

        System.out.print("Enter new fee: ");
        double newFee = readDouble();

        String sql = "UPDATE courses SET fee = ? WHERE course_id = ?";

        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setDouble(1, newFee);
            ps.setString(2, courseId);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("Course fee updated successfully!");
            } else {
                System.out.println("No course found with the given Course ID.");
            }

        } catch (SQLException e) {
            System.out.println("Error while updating fee: " + e.getMessage());
        }
    }

    // 4. Delete Course
    private static void deleteCourse() {
        System.out.println("\n--- Delete Course ---");
        System.out.print("Enter Course ID to delete: ");
        String courseId = sc.nextLine();

        String sql = "DELETE FROM courses WHERE course_id = ?";

        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, courseId);
            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Course deleted successfully!");
            } else {
                System.out.println("No course found with the given Course ID.");
            }

        } catch (SQLException e) {
            System.out.println("Error while deleting course: " + e.getMessage());
        }
    }

    // 5. Search Course by Title
    private static void searchCourseByTitle() {
        System.out.println("\n--- Search Course By Title ---");
        System.out.print("Enter title or part of title: ");
        String titlePart = sc.nextLine();

        String sql = "SELECT course_id, title, duration, fee FROM courses WHERE title LIKE ?";

        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, "%" + titlePart + "%");
            try (ResultSet rs = ps.executeQuery()) {

                System.out.printf("%-10s %-30s %-15s %-10s%n", "CourseID", "Title", "Duration", "Fee");
                System.out.println("---------------------------------------------------------------------");

                boolean any = false;
                while (rs.next()) {
                    any = true;
                    String courseId = rs.getString("course_id");
                    String title = rs.getString("title");
                    String duration = rs.getString("duration");
                    double fee = rs.getDouble("fee");

                    System.out.printf("%-10s %-30s %-15s %-10.2f%n", courseId, title, duration, fee);
                }

                if (!any) {
                    System.out.println("No courses matched your search.");
                }
            }

        } catch (SQLException e) {
            System.out.println("Error while searching courses: " + e.getMessage());
        }
    }
}
