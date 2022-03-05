import java.util.ArrayList;
import java.util.Scanner;
import java.time.*;

public class School {
    //Instance Variables: What kind of data will you need to store for the school?
    ArrayList<Student> students;
    ArrayList<String> submissions;
    /**
     * Write an appropriate constructor for this class
     */
    public School() {
        students = new ArrayList<Student>();
        submissions = new ArrayList<String>();
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
            students.add(new Student(data[0].trim(), data[1].trim(), data[2].trim(), grade, data[4].trim()));
            input = MediaFile.readString();
        }
    }

    public void importSubmissions(String file) {
        MediaFile.setInputFile(file);
        String input = MediaFile.readString();
        while(input != null) {
            submissions.add(input.trim());
            input = MediaFile.readString();
        }
    }

    /**
     * This method will print the list of students that have not yet turned in their registration card.
     * A scanner is used to ask the user the name of the teacher
     */
    public void printMissingByTeacher() {
        System.out.println("What teacher's class would you like to print?");
        String input  = Main.scan.next();
        String teacher = "";
        int grade = 0;
        for(Student student : students) {
            if(student.getTeacher().trim().indexOf(input.trim()) >= 0) {
                teacher = student.getTeacher();
                grade = student.getGrade();
                break;
            }
        }
        
        System.out.println("Students with missing cards with CAP Teacher: " + teacher + " - Grade: " + grade);
        for(Student student : students) {
            if(student.getTeacher().equals(teacher)) {
                System.out.println(student.getID() + "\t" + student.getLastName() + "\t" + student.getFirstName());
            }
        }
        
    }

    public void writeAllMissing(String file) {
        MediaFile.setOutputFile(file);
        for(Student student : students) {
            String id = student.getID();
            if(submissions.indexOf(id) == -1) { //fix
                String grade = "" + student.getGrade();
                MediaFile.writeString(student.getID(), "\t", false);
                MediaFile.writeString(student.getLastName(), "\t", false);
                MediaFile.writeString(student.getFirstName(), "\t", false);
                MediaFile.writeString(grade, "\t", false);
                MediaFile.writeString(student.getTeacher(), "\t", true);
            }
        }
        MediaFile.saveAndClose();
        System.out.println("Results saved in 'missingCards.txt'");
    }

    public void provideSummary(String file) {
        MediaFile.setOutputFile(file);
        int[] submitted = new int[3];
        int[] missing = new int[3];
        for(Student student : students) {
            int grade = student.getGrade();
            if(submissions.indexOf(student.getID()) == -1) {
                missing[grade - 9]++;
            } else {
                submitted[grade - 9]++;
            }
        }

        int totalMissing = 0;
        for(int i = 0; i < 3; i++) {
            MediaFile.writeString("Grade " + (i + 9) + " - Total Students: " + (submitted[i] + missing[i]), "", true);
            MediaFile.writeString("Submitted: " + submitted[i], "", true);
            MediaFile.writeString("Missing: " + missing[i], "\n", true);
            totalMissing += missing[i];
        }
        MediaFile.writeString("Total Missing Cards: " + totalMissing, "\n", true);
        MediaFile.saveAndClose();
        System.out.println("Results saved in 'summary.txt'");

    }

    public void markCardSubmitted(String studentID) {
        boolean valid = false;
        for(Student student : students) {
            if(student.getID().trim().equals(studentID.trim())) {
                valid = true;
                break;
            }
        }
        if(valid) {
            int idx = submissions.indexOf(studentID);
            System.out.println(idx);
            if(submissions.indexOf(studentID) == -1) {
                submissions.add(studentID);
                MediaFile.setOutputFile(Main.submissionsFile);
                for(String id : submissions) {
                    MediaFile.writeString(id, "\t", true);
                }
                MediaFile.saveAndClose();
                System.out.println("Card with ID " + studentID + " marked as submitted! (" + Main.submissionsFile + ".txt updated!)");
                Main.setMissingCardsFile();
                Main.setSummaryFile();
                writeAllMissing(Main.missingCardsFile);
                provideSummary(Main.summaryFile);
            } else {
                System.out.println("Student has already submitted their card!");
            }
        } else {
            System.out.println("No student found with the ID " + studentID);
        }
        
        
    }

    public void removeCard(String studentID) {
        boolean removed = submissions.remove(studentID);
        if(removed) {
            MediaFile.setOutputFile(Main.submissionsFile);
            for(String id : submissions) {
                MediaFile.writeString(id, "\t", true);
            }
            MediaFile.saveAndClose();
            System.out.println("Card with ID " + studentID + " marked as unsubmitted! (" + Main.submissionsFile + ".txt updated!)");
            Main.setMissingCardsFile();
            Main.setSummaryFile();
            writeAllMissing(Main.missingCardsFile);
            provideSummary(Main.summaryFile);
        } else {
            System.out.println(studentID + " not found!");
        }
    }

    public void withdrawStudent(String studentID) {
        for(Student student : students) {
            if(student.getID().trim().equals(studentID.trim())) {
                System.out.println(student);
                System.out.println("Confirm student's withdrawal from the school? (Type 'yes' to confirm)");
                String confirm = Main.scan.next();
                if(confirm.equals("yes")) {
                    students.remove(student);
                    System.out.println("Student (ID: " + studentID + ") has been withdrawn from the school");
                    writeToMaster();
                    System.out.println("Master Student file '" + Main.studentMasterFile + ".txt' updated!");
                    break;
                } else {
                    System.out.println("Canceled!");
                    break;
                }
            }
        }
    }

    public void writeToMaster() {
        MediaFile.setOutputFile(Main.studentMasterFile);
        for(Student student : students) {
            String grade = "" + student.getGrade();
            MediaFile.writeString(student.getID(), "\t", false);
            MediaFile.writeString(student.getLastName(), "\t", false);
            MediaFile.writeString(student.getFirstName(), "\t", false);
            MediaFile.writeString(grade, "\t", false);
            MediaFile.writeString(student.getTeacher(), "\t", true);
        }
        MediaFile.saveAndClose();
    }
    
    public ArrayList<Student> getStudents() {
        return students;
    }
    //Add other methods as needed to create the desired output

    public ArrayList<String> getSubmissions() {
        return submissions;
    }
}
