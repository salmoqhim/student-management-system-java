package studentmanagementsystem;

/**
 *
 * @author Sarah Almoqhim
 *
 * StudentManagementSystem is the main controller class. It manages the lists of
 * students and courses, handles all menu operations, and is the entry point of
 * the program.
 */
import java.util.ArrayList;
import java.util.Scanner;
import java.io.PrintWriter;

public class StudentManagementSystem {

    // In-memory storage using ArrayLists
    private ArrayList<Student> students;
    private ArrayList<Course> courses;
    private Scanner scanner;

    // Constructor -> initializes the system with empty lists
    public StudentManagementSystem() {
        students = new ArrayList<>();
        courses = new ArrayList<>();
        scanner = new Scanner(System.in);
    }

    // Private helper methods -> only used internally by other methods in this class
    // They are private because no outside class should call them directly (encapsulation)
    // Searches for a student by ID, returns null if not found
    private Student findStudentById(String id) {
        // loop iterates through each student to find a match
        for (Student student : students) {
            if (student.getId().equalsIgnoreCase(id)) {
                return student;
            }
        }
        return null;
    }

    // Searches for a course by course code, returns null if not found
    private Course findCourseByCode(String courseCode) {
        // loop iterates through each course to find a match
        for (Course course : courses) {
            if (course.getCourseCode().equalsIgnoreCase(courseCode)) {
                return course;
            }
        }
        return null;
    }

    // Reads and validates a string input from the user.   
    private String readValidatedString(String prompt, String fieldName, String pattern, String formatError) {
        String value = "";
        // Loops until the input is non-empty and matches the given pattern.
        while (true) {
            System.out.print(prompt);
            value = scanner.nextLine().trim();
            if (value.isEmpty()) {
                System.out.println("Error: " + fieldName + " cannot be empty.");

            } else if (!value.matches(pattern)) {
                System.out.println("Error: " + formatError);

            } else {
                break;
            }
        }
        return value;
    }

    // Formats a string so that the first letter of each word is capitalized and the remaining letters are lowercase 
    private String formatCapitalizationOfEachWord(String text) {
        // trim() removes leading and trailing spaces to avoid empty tokens after splitting
        String[] words = text.trim().split("\\s+");
        StringBuilder result = new StringBuilder();
        for (String word : words) {
            if (!word.isEmpty()) {
                // Capitalize the first letter of the word
                result.append(Character.toUpperCase(word.charAt(0)));
                // Lowercase the rest
                result.append(word.substring(1).toLowerCase());
                // Add a space after each word
                result.append(" ");
            }

        }
        return result.toString().trim();
    }

    // Reads and validates a positive integer input from the user.
    private int readInt(String prompt, String fieldName) {
        while (true) {
            System.out.print(prompt);
            String intString = scanner.nextLine().trim();
            try {
                // Convert the String input to an int
                int value = Integer.parseInt(intString);

                if (value <= 0) {
                    System.out.println("Error: " + fieldName + " must be greater than zero.");

                } else {
                    return value;
                }
            } catch (NumberFormatException ex) {
                System.out.println("Error: " + fieldName + " must be a valid whole number.");

            }
        }
    }

    // Reads and validates a double input from the user within a given range.
    private double readDouble(String prompt, String fieldName, double min, double max) {
        while (true) {
            System.out.print(prompt);
            String doubleString = scanner.nextLine().trim();
            try {
                // Convert the String input to a double
                double value = Double.parseDouble(doubleString);
                if (value < min || value > max) {
                    System.out.printf("Error: %s must be between %.2f and %.2f.\n", fieldName, min, max);

                } else {
                    return value;
                }
            } catch (NumberFormatException ex) {
                System.out.println("Error: " + fieldName + " must be a valid number.");

            }

        }
    }

    // Prompts for a student ID, finds and returns the matching Student, or null if not found.
    private Student findStudentFromInput() {
        String studentId = readValidatedString("Enter Student ID: ", "Student ID", ".+", "").toUpperCase();
        Student student = findStudentById(studentId);
        if (student == null) {
            System.out.println("Error: Student with ID " + studentId + " not found.");
        }
        return student;
    }

    // Prompts for a course code, finds and returns the matching Course, or null if not found.
    private Course findCourseFromInput() {
        String courseCode = readValidatedString("Enter Course Code: ", "Course Code", ".+", "").toUpperCase();
        Course course = findCourseByCode(courseCode);
        if (course == null) {
            System.out.println("Error: Course with code " + courseCode + " not found.");
        }
        return course;
    }

    // Adds a new student to the system after validating unique ID
    public void addStudent() {
        System.out.println("\n----- Add New Student -----");

        String id = readValidatedString("Enter Student ID: ", "Student ID",
                "[a-zA-Z0-9]+", "Student ID must contain only letters and digits, with no spaces or symbols.").toUpperCase();

        if (findStudentById(id) != null) {
            System.out.println("Error: Student with ID " + id + " already exists.");
            return;
        }
        String name = readValidatedString("Enter Name: ", "Name",
                "[a-zA-Z\\s\\-]+", "Name must contain only letters, spaces, or hyphens.");
        name = formatCapitalizationOfEachWord(name);

        String email = readValidatedString("Enter Email: ", "Email",
                "[^\\s@]+@[^\\s@]+\\.(com|edu|net|org|sa)(\\.[a-zA-Z]{2,3})?", "Email must be a valid address (e.g. example@domain.com).");

        // For fields with no format requirement, use pattern ".+" which accepts any non-empty input.
        String major = readValidatedString("Enter Major: ", "Major", ".+", "");
        major = formatCapitalizationOfEachWord(major);

        students.add(new Student(id, name, email, major));
        System.out.println("Student Added: " + id + " - " + name);
    }

    // Adds a new course to the system after validating unique course code
    public void addCourse() {
        System.out.println("\n----- Add New Course -----");
        String courseCode = readValidatedString("Enter Course Code: ", "Course Code", "[a-zA-Z]{2,3}[0-9]{3}",
                "Course code must start with 2 to 3 letters followed by exactly 3 digits "
                + "(e.g. SE120, CS101).").toUpperCase(); // Capitalize the course code for consistency.

        if (findCourseByCode(courseCode) != null) {
            System.out.println("Error: Course with code " + courseCode + " already exists.");
            return;
        }
        String courseTitle = readValidatedString("Enter Course Title: ", "Course Title", ".+", "");

        int creditHours = readInt("Enter Credit Hours: ", "Credit Hours");

        courses.add(new Course(courseCode, courseTitle, creditHours));
        System.out.println("Course Added: " + courseCode + " - " + courseTitle);
    }

    // Enrolls a student in a course after validating that both exist
    public void enrollStudent() {
        System.out.println("\n----- Enroll Student in Course -----");
        Student student = findStudentFromInput();
        // Stop the operation if the student was not found.
        if (student == null) {
            return;
        }
        Course course = findCourseFromInput();
        // Stop the operation if the course was not found.
        if (course == null) {
            return;
        }

        boolean success = student.enroll(course);
        if (success) {
            System.out.println("Course Registered: " + student.getName() + " -> " + course.getCourseCode());
        }

    }

    // Enters or updates a grade for a student in a specific course
    public void enterGrade() {
        System.out.println("\n----- Enter / Update Grade -----");
        Student student = findStudentFromInput();
        // Stop the operation if the student was not found.
        if (student == null) {
            return;
        }
        Course course = findCourseFromInput();
        // Stop the operation if the course was not found.
        if (course == null) {
            return;
        }
        // Check enrollment before asking for the grade
        if (!student.isEnrolledIn(course.getCourseCode())) {
            System.out.println("Error: " + student.getName()
                    + " is not enrolled in course " + course.getCourseCode());
            return;
        }

        double grade = readDouble("Enter Grade (0-100): ", "Grade", 0, 100);

        boolean success = student.addGrade(course.getCourseCode(), grade);
        if (success) {
            System.out.printf("Grade Entered: %.2f\n", grade);
        }
    }

    // Displays the full transcript for a specific student
    public void displayTranscript() {
        Student student = findStudentFromInput();
        // Stop the operation if the student was not found.
        if (student == null) {
            return;
        }
        System.out.println("\n----- Transcript -----");
        student.displayTranscript();
        System.out.println("----------------------\n");
    }

    // Displays all students with their transcripts and all courses
    public void displayReports() {

        System.out.println("\n===== Report of All Students (" + students.size() + " students) =====\n");
        if (students.isEmpty()) {
            System.out.println("No students registered yet.");
        } else {

            // iterates through each student to display their transcript
            for (int i = 0; i < students.size(); i++) {

                System.out.println("Student " + (i + 1) + ":");
                students.get(i).displayTranscript();
                System.out.println();
            }
        }

        System.out.println("\n===== Report of All Courses (" + courses.size() + " courses) =====\n");
        if (courses.isEmpty()) {

            System.out.println("No courses added yet.");
        } else {

            // iterates through each course to display its info
            for (int i = 0; i < courses.size(); i++) {
                System.out.println("Course " + (i + 1) + ":");
                courses.get(i).displayCourseInfo();
                System.out.println();
            }
        }
        System.out.println("===============================================\n");
    }

    // Displays all students ranked by their average grade from highest to lowest.
    public void displayStudentsSortedByAverageGrade() {
        System.out.println("\n===== Students Sorted by Average Grade (Highest to Lowest) =====\n");
        if (students.isEmpty()) {
            System.out.println("No students registered yet.");
            System.out.println("=================================================================\n");
            return;
        }

        // Copy student references into a temporary list so the original list is not reordered
        ArrayList<Student> sortedStudents = new ArrayList<>();
        for (Student s : students) {
            sortedStudents.add(s);
        }

        for (int i = 0; i < sortedStudents.size() - 1; i++) {
            int maxIndex = i;
            for (int j = i + 1; j < sortedStudents.size(); j++) {
                double averageJ = sortedStudents.get(j).calculateAverage();
                double averageMax = sortedStudents.get(maxIndex).calculateAverage();

                if (averageJ > averageMax) {
                    maxIndex = j;
                } else if (averageJ == averageMax) { // if averages are equal, order the students' names alphabetically
                    // compareToIgnoreCase returns negative if j's name comes before maxIndex's name
                    String nameJ = sortedStudents.get(j).getName();
                    String nameMax = sortedStudents.get(maxIndex).getName();
                    if (nameJ.compareToIgnoreCase(nameMax) < 0) {
                        maxIndex = j;
                    }

                }

            }
            // Swap sortedStudents at index i and sortedStudents at index maxIndex
            Student temp = sortedStudents.get(i);
            sortedStudents.set(i, sortedStudents.get(maxIndex));
            sortedStudents.set(maxIndex, temp);
        }

        //Display the sorted list
        // Students with the same average share the same rank number.
        int rank = 1;
        for (int i = 0; i < sortedStudents.size(); i++) {
            Student student = sortedStudents.get(i);
            double average = student.calculateAverage();

            if (average < 0) {
                // No grades entered -> not ranked
                System.out.print("N/A  : " + student.getName() + " (" + student.getId() + ")");
                System.out.println(" | Average: No grades entered yet.");
            } else {
                //Increment rank only when this student's average differs from the previous student's average
                if (i > 0 && average != sortedStudents.get(i - 1).calculateAverage()) {
                    rank++;
                }
                System.out.print("Rank " + rank + ": " + student.getName() + " (" + student.getId() + ")");
                System.out.printf(" | Average: %.2f%n", average);
            }

        }
        System.out.println("=================================================================\n");
    }

    // Saves a student's transcript to a text file 
    public void saveTranscriptToFile() {
        Student student = findStudentFromInput();
        if (student == null) {
            return;
        }

        // File is named after the student ID for easy identification
        String fileName = student.getId() + "_transcript.txt";

        PrintWriter writer = null;
        try {
            // Create a PrintWriter for the file 
            writer = new PrintWriter(fileName);

            writer.println("===== Student Transcript =====");
            writer.println("ID    : " + student.getId());
            writer.println("Name  : " + student.getName());
            writer.println("Email : " + student.getEmail());
            writer.println("Major : " + student.getMajor());
            writer.println("------------------------------");
            writer.println("Enrolled Courses:");

            if (student.getEnrollments().isEmpty()) {
                writer.println("  No courses enrolled yet.");
            } else {
                for (Enrollment enrollment : student.getEnrollments()) {
                    Course course = enrollment.getCourse();
                    String courseInfo = "  " + course.getCourseCode()
                            + " - " + course.getCourseTitle();
                    if (enrollment.isGradeSet()) {
                        writer.printf("%s | Grade: %.2f (%s)%n",
                                courseInfo,
                                enrollment.getGrade(),
                                enrollment.getLetterGrade());
                    } else {
                        writer.println(courseInfo + " | Grade: Not entered yet.");
                    }
                }
                writer.println("------------------------------");
                double average = student.calculateAverage();
                if (average >= 0) {
                    writer.printf("Average Grade: %.2f%n", average);
                } else {
                    writer.println("Average Grade: No grades entered.");
                }
            }

            writer.println("==============================");
            System.out.println("Transcript saved to: " + fileName);

        } catch (Exception e) {
            System.out.println("Error: Could not save transcript to file.");
        } finally {
            // Close the file to ensure all data is saved 
            if (writer != null) {
                writer.close();
            }
        }
    }

    // Main menu loop -> keeps running until the user chooses to exit
    public void run() {
        boolean isRunning = true;
        while (isRunning) {
            System.out.println("\n----- Student Management System -----");
            System.out.println("1. Add Student");
            System.out.println("2. Add Course");
            System.out.println("3. Enroll Student in Course");
            System.out.println("4. Enter / Update Grade");
            System.out.println("5. Display Student Transcript");
            System.out.println("6. Display All Records (Students & Courses)");
            System.out.println("7. Display Students Sorted by Average Grade");
            System.out.println("8. Save Transcript To File");
            System.out.println("9. Exit");
            System.out.println("=====================================");
            // choice is read as a String instead of an int so that if the user types
            // letters or invalid input, it safely hits the default case instead of crashing
            String choice = readValidatedString("Choose an option (1-9): ", "Option", ".+", "");
            System.out.println();

            switch (choice) {
                case "1":
                    addStudent();
                    break;
                case "2":
                    addCourse();
                    break;
                case "3":
                    enrollStudent();
                    break;
                case "4":
                    enterGrade();
                    break;
                case "5":
                    displayTranscript();
                    break;
                case "6":
                    displayReports();
                    break;
                case "7":
                    displayStudentsSortedByAverageGrade();
                    break;

                case "8":
                    saveTranscriptToFile();
                    break;
                case "9":
                    System.out.print("Are you sure you want to exit? All unsaved data will be lost. (yes/no): ");
                    String confirmation = scanner.nextLine().trim().toLowerCase();
                    if (confirmation.equals("yes")) {
                        System.out.println("Thank you for using the Student Management System. Goodbye.");
                        isRunning = false;
                    } else if (confirmation.equals("no")) {
                        System.out.println("Exit cancelled. Returning to menu . . .");
                    } else {
                        System.out.println("Invalid input. Please enter \"yes\" or \"no\".");
                    }
                    break;

                default:
                    System.out.println("Invalid option. Please choose an option 1-9.");
                    break;
            }
        }
        scanner.close();
    }

    // Entry point of the program -> creates the system and starts the menu loop
    public static void main(String[] args) {
        StudentManagementSystem system = new StudentManagementSystem();
        system.run();
    }

}
