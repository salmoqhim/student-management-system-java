package studentmanagementsystem;

/**
 *
 * @author Sarah Almoqhim
 *
 * Student is a subclass (child class) of Person. It inherits common personal
 * information from Person and adds student-specific details such as major,
 * enrolled courses, and grades. This class is responsible for managing a
 * student's academic records.
 */
import java.util.ArrayList;

public class Student extends Person {

    private String major;
    // enrollments stores Enrollment objects (not just Course objects) because each
    // enrollment holds both the course and its corresponding grade together.
    // This is why the list is called enrollments and not enrolledCourses.
    private ArrayList<Enrollment> enrollments;

    // Note: the inherited 'id' from Person is used as the student ID (e.g. "S1001")
    // A separate studentId is not needed since this system only manages students
    public Student(String studentId, String name, String email, String major) {
        super(studentId, name, email);
        this.major = major;
        enrollments = new ArrayList<>();
    }

    // Getters are only created for attributes that need to be accessed from outside this class
    // Attributes used only internally (like grades and enrolledCourses) are managed through methods
    public String getMajor() {
        return major;
    }

    public ArrayList<Enrollment> getEnrollments() {
        return enrollments;
    }

    // Setter
    public void setMajor(String major) {
        this.major = major;
    }

    // Enrolls student in a course, prevents duplicate enrollment
    public boolean enroll(Course course) {
        // loop through each enrollment to check for duplicate course
        for (Enrollment enrollment : enrollments) {
            if (enrollment.getCourse().getCourseCode().equalsIgnoreCase(course.getCourseCode())) {
                System.out.println("Error: " + getName() + " is already enrolled in course " + course.getCourseCode());
                return false;
            }
        }
        enrollments.add(new Enrollment(this, course));
        return true;
    }
    
    // Returns true if the student is enrolled in the given course code
    public boolean isEnrolledIn(String courseCode) {
        for (Enrollment enrollment : enrollments) {
            if (enrollment.getCourse().getCourseCode().equalsIgnoreCase(courseCode)) {
                return true;
            }
        }
        return false;
    }

    // Finds the enrollment for a given course code and sets its grade
    public boolean addGrade(String courseCode, double grade) {
        // loop through each enrollment to find the matching course code
        for (Enrollment enrollment : enrollments) {
            if (enrollment.getCourse().getCourseCode().equalsIgnoreCase(courseCode)) {
                return enrollment.setGrade(grade);
            }
        }
        System.out.println("Error: " + getName() + " is not enrolled in course " + courseCode);
        return false;
    }

    // Calculates the average grade across all graded enrollments
    public double calculateAverage() {
        double total = 0;
        int count = 0;

        // loop through each enrollment to sum up all entered grades
        for (Enrollment enrollment : enrollments) {
            if (enrollment.isGradeSet()) {  // only count courses with a grade entered
                total += enrollment.getGrade();
                count++;
            }
        }
        return (count == 0) ? -1 : total / count;
    }

    // Displays the student's full academic transcriptmm
    public void displayTranscript() {

        displayInfo();
        System.out.println("Enrolled Courses:");
        if (enrollments.isEmpty()) {
            System.out.println("  No courses enrolled yet.");
        } else {
            // loop through each enrollment to display its details
            for (Enrollment enrollment : enrollments) {
                enrollment.displayEnrollment();
            }
            double average = calculateAverage();
            if (average >= 0) {
                System.out.printf("Average Grade: %.2f\n", average);
            } else {
                System.out.println("Average Grade: No grades entered.");
            }
        }

    }

    // Overrides displayInfo() from Person to include major (polymorphism)
    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Major : " + major);
    }

    // Overrides toString() from Person to include major
    @Override
    public String toString() {
        return super.toString() + " | Major: " + major;
    }
}
