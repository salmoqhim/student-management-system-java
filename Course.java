package studentmanagementsystem;

/**
 *
 * @author Sarah Almoqhim
 *
 * Course represents a course offered by the department. It stores the course
 * code, title, and credit hours. Course objects are stored directly inside
 * Student to avoid storing course data as plain text (composition).
 */
public class Course {

    // Private attributes -> encapsulation
    private String courseCode;
    private String courseTitle;
    private int creditHours;

    // Constructor -> creates a new Course with all required details
    public Course(String courseCode, String courseTitle, int creditHours) {
        this.courseCode = courseCode;
        this.courseTitle = courseTitle;
        this.creditHours = creditHours;
    }

    // Displays course information to the console
    public void displayCourseInfo() {
        System.out.println("Course Code : " + courseCode);
        System.out.println("Title       : " + courseTitle);
        System.out.println("Credit Hours: " + creditHours);

    }

    // Getters -> only for attributes needed by other classes
    public String getCourseCode() {
        return courseCode;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public int getCreditHours() {
        return creditHours;
    }

    // Setters -> allow updating course details (courseCode is excluded as it is the unique identifier)
    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public void setCreditHours(int creditHours) {
        
        this.creditHours = creditHours;
    }

    // Returns a string representation of the course (used when printing course lists in StudentManagementSystem class)
    @Override
    public String toString() {
        return courseCode + "-" + courseTitle + " (" + creditHours + " credit Hours)";
    }
}
