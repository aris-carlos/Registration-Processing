import java.util.ArrayList;
import java.util.Scanner;

public class School {
    //Instance Variables: What kind of data will you need to store for the school?
    ArrayList<Student> students;
    ArrayList<String> submissions;
    /**
     * Write an appropriate constructor for this class
     */
    public School() {
        students = new ArrayList<Student>();
    }
    /**
     * This method will read in the given txt file that contains all the students in the school.
     * The file will be tab delimited (data is separated by a tab file) with data:
     * studentID    studentFirstName    studentLastName studentGradeLevel   studentCAPTeacher
     * @param file The name of the tab delimited text file (WITHOUT the .txt) to open
     */
    public void importData(String file) {
        MediaFile.setInputFile(file);
        String input = MediaFile.readString();
        while (input != null) {
            String[] data = input.split("\t");
            int grade = Integer.valueOf(data[3]);
            students.add(new Student(data[0], data[1], data[2], grade, data[4]));
            input = MediaFile.readString();
        }
    }

    public void importSubmissions(String file) {
        MediaFile.setInputFile(file);
        String input = MediaFile.readString();
        while(input != null) {
            submissions.add(input);
            input = MediaFile.readString();
        }
    }

    /**
     * This method will print the list of students that have not yet turned in their registration card.
     * A scanner is used to ask the user the name of the teacher
     */
    public void printMissingByTeacher() {
        Scanner scan = new Scanner(System.in);
        System.out.println("What teacher's class would you like to print?");
        String input  = scan.next();



    }

    public void writeAllMissing(String file) {
        MediaFile.setOutputFile(file);
        for(Student student : students) {
            if(submissions.indexOf(student.getID()) == -1) { //fix
                String grade = "" + student.getGrade();
                MediaFile.writeString(student.getID(), "\t", false);
                MediaFile.writeString(student.getLastName(), "\t", false);
                MediaFile.writeString(student.getFirstName(), "\t", false);
                MediaFile.writeString(grade, "\t", false);
                MediaFile.writeString(student.getTeacher(), "\t", true);
            }
        }
    }
    
    public ArrayList<Student> getStudents() {
        return students;
    }
    //Add other methods as needed to create the desired output
}
