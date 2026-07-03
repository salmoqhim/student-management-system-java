package studentmanagementsystem;

/**
 *
 * @author Sarah Almoqhim
 *
 * Enrollment represents the relationship between a Student and a Course.
 * Instead of storing grades directly in Student, each Enrollment object holds a
 * Course and its corresponding grade. This is cleaner OOP design as a grade
 * belongs to an enrollment, not directly to a student.
 */
public class Enrollment {

    // Private attributes -> encapsulation
    private Student student;
    private Course course;
    private double grade;
    private boolean gradeSet; // tracks whether a grade has been entered

    // Constructor -> creates an enrollment linking a student to a course
    public Enrollment(Student student, Course course) {
        this.student = student;
        this.course = course;
        grade = -1; // -1 means no grade entered yet, as 0 is a valid grade
        gradeSet = false; // false means no grade has been entered yet
    }

    // Getters -> allow read-only access to private attributes
    public Student getStudent() {
        return student;
    }

    public Course getCourse() {
        return course;
    }

    public double getGrade() {
        return grade;
    }

    // Returns true if a grade has been entered for this enrollment
    public boolean isGradeSet() {
        return gradeSet;
    }

    // Sets the grade if it is within the valid range (0-100)
    // Returns true if grade was valid and saved, false otherwise
    public boolean setGrade(double grade) {
        if (grade > 100 || grade < 0) {
            System.out.println("Error: Grade must be between 0 and 100.");
            return false;
        }
        this.grade = grade;
        this.gradeSet = true;
        return true;
    }

    // Converts the numeric grade to a letter grade (A/B/C/D/F)
    public String getLetterGrade() {
        if (!gradeSet) {
            return "Not entered yet.";
        } else if (grade >= 90) {
            return "A";
        } else if (grade >= 80) {
            return "B";
        } else if (grade >= 70) {
            return "C";
        } else if (grade >= 60) {
            return "D";
        } else {
            return "F";
        }
    }

    // Displays enrollment details including grade and letter grade if entered
    public void displayEnrollment() {
        
        if (gradeSet) {         
            System.out.printf("  %s - %s | Grade: %.2f (%s)\n", course.getCourseCode(),course.getCourseTitle(),grade,getLetterGrade());
        } else {
            System.out.println("  " + course.getCourseCode() + " - " + course.getCourseTitle() + " | Grade: Not entered yet.");
        }

    }
}
