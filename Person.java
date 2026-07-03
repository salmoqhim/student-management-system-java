package studentmanagementsystem;

/**
 *
 * @author Sarah Almoqhim
 *
 * Person is the superclass (parent class) of the Student class. It holds common
 * personal information such as id, name, and email that any person in the
 * system would have. By placing shared attributes and methods here, we avoid
 * repeating code in the Student class (inheritance).
 *
 */
public class Person {

    // Private attributes -> only accessible through getters (encapsulation)
    private String id;
    private String name;
    private String email;

    // Constructor -> initializes a Person object with the given id, name, and email
    public Person(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    // Displays basic person information to the console
    public void displayInfo() {
        System.out.println("ID    : " + id);
        System.out.println("Name  : " + name);
        System.out.println("Email : " + email);
    }

    // Getters -> allow read-only access to private attributes
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;

    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Returns a string representation of the person (used when printing person objects directly)
    @Override
    public String toString() {
        return id + " - " + name + " (" + email + ")";
    }
}
