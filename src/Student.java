public class Student {
    private String studentID;
    private String lastName;
    private String firstName;
    private int studentGrade;
    private String capTeacher;

    public Student(String id, String last, String first, int grade, String tchr) {
        studentID = id;
        lastName = last;
        firstName = first;
        studentGrade = grade;
        capTeacher = tchr;
    }

    public String getID() {
        return studentID;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public int getGrade() {
        return studentGrade;
    }

    public String getTeacher() {
        return capTeacher;
    }

    public String toString() {
        return "ID: " + studentID + " Name (Last, First): " + lastName
        + ", " + firstName + " Grade: " + studentGrade + " CAP Teacher: "
        + capTeacher + "\n";
    }
}
