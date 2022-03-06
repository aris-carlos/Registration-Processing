public class Student {
    //This is the student class, where each data value of a student stored in the studentMaster text file is stored in a Student object
    private String studentID;
    private String lastName;
    private String firstName;
    private int studentGrade;
    private String capTeacher;

    public Student(String id, String first, String last, int grade, String tchr) {
        studentID = id;
        firstName = first;
        lastName = last;
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
