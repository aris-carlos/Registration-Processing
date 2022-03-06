import java.util.ArrayList;

public class School {
    //Instance Variables: What kind of data will you need to store for the school?
    ArrayList<Student> students; //ArrayList of Student objects to store all students from the studentMaster file
    ArrayList<String> submissions; //ArrayList of Strings to store all student ID's from the submissions file
    /**
     * Write an appropriate constructor for this class
     */
    public School() { //Both ArrayList instance variables are intialized
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
        while (input != null) { //Reads entirety of text file
            String[] data = input.split("\t");
            int grade = Integer.valueOf(data[3]);
            students.add(new Student(data[0], data[1], data[2], grade, data[4])); //Creates Student object and adds it to the list
            input = MediaFile.readString();
        }
        sortStudents(0, students.size() - 1);
    }

    /**
     * This method will read in the given txt file that contains all the recorded registration cards submissions in the school.
     * The file will be tab delimited (data is separated by a tab file) with data:
     * studentID
     * @param file The name of the tab delimited text file (WITHOUT the .txt) to open
     */
    public void importSubmissions(String file) {
        MediaFile.setInputFile(file);
        String input = MediaFile.readString();
        while(input != null) { //Reads entirety of text file
            submissions.add(input.trim()); //Adds student ID string to the list
            input = MediaFile.readString();
        }
    }

    /**
     * This method will print the list of students that have not yet turned in their registration card.
     * A scanner is used to ask the user the name of the teacher
     * The desired teacher can be found even if the user provides only a teacher's first, last, or portion of their name as input
     */
    public void printMissingByTeacher() {
        System.out.println("What teacher's class would you like to print?");
        String input  = Main.scan.next();
        String teacher = "";
        int grade = 0;
        for(Student student : students) { //This for loop searches the students ArrayList to find the teacher being looked up
            if(student.getTeacher().trim().indexOf(input.trim()) >= 0) { //Checks if the user's input for a teacher matches the listed teachers stored in each Student
                //Using .trim() removes whitespace, using .indexOf() checks if the input is found in a teacher's name, even if either the first, last, or portion of the name is provided
                teacher = student.getTeacher(); //When the desired teacher has been found, the teacher's name and grade is stored
                grade = student.getGrade();
                break;
            }
        }
        
        System.out.println("Students with missing cards with CAP Teacher: " + teacher + " - Grade: " + grade);
        for(Student student : students) { //This for loop does the job of providing all students who have the queried CAP Teacher
            if(student.getTeacher().equals(teacher)) {
                System.out.println(student.getID() + "\t" + student.getLastName() + "\t" + student.getFirstName());
            }
        }
        
    }

    /**
     * This method will write the information of all the students who have not turned in their registration cards to the user's desired file
     * The file will be tab delimited (data is separated by a tab file) with data:
     * studentID    studentFirstName    studentLastName studentGradeLevel   studentCAPTeacher
     * @param file The name of the file that the information of the students who have not turned in their registration cards will be written to
     */
    public void writeAllMissing(String file) {
        MediaFile.setOutputFile(file);
        for(Student student : students) { //Iterates through all stored Student objects in the list
            String id = student.getID();
            if(submissions.indexOf(id) == -1) { //Checks if the student's ID is not present in the submissions list
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

    /**
     * This method provides a summary by grade level of all students who have submitted and not submitted their cards to the user's desired file
     * A summary is provided for each grade level (9-11) that contains the following data:
     * Total number of students in the grade
     * Total number of students who have submitted their registration cards
     * Total number of students whose registration cards are missing
     * Total number of missing registration cards of the entire school at the end
     * @param file The name of the file that the summaries of each grade level will be written to
     */
    public void provideSummary(String file) {
        MediaFile.setOutputFile(file);
        int[] submitted = new int[3]; //Use of Integer arrays stores the total submitted and missing cards by each grade into different slots
        int[] missing = new int[3];
        for(Student student : students) { //Iterates through all stored Student objects in the list
            int grade = student.getGrade();
            if(submissions.indexOf(student.getID()) == -1) { //Checks if student's ID is present in the submissions list
                missing[grade - 9]++; //Subtracting 9 from the student's grade provides a suitable index value where the total values can be stored in the array
            } else {
                submitted[grade - 9]++;
            }
        }

        int totalMissing = 0;
        for(int i = 0; i < 3; i++) { //For loop writes the grade summary three times, one for each grade level
            MediaFile.writeString("Grade " + (i + 9) + " - Total Students: " + (submitted[i] + missing[i]), "", true);
            MediaFile.writeString("Submitted: " + submitted[i], "", true);
            MediaFile.writeString("Missing: " + missing[i], "\n", true);
            totalMissing += missing[i];
        }
        MediaFile.writeString("Total Missing Cards: " + totalMissing, "\n", true);
        MediaFile.saveAndClose();
        System.out.println("Results saved in '" + Main.summaryFile + ".txt'");
    }

    /**
     * This method marks a student's registration card as submitted
     * The student's ID will be added to the list of submissions if it is not present
     * The submissions text file will also be updated
     * @param studentID The ID of the student whose registration card will be marked as submitted
     */
    public void markCardSubmitted(String studentID) {
        boolean valid = false;
        for(Student student : students) { //Iterates through all stored Student objects in the list
            if(student.getID().trim().equals(studentID.trim())) { //Checks that the student exists in the list of students (from studentMaster)
                valid = true;
                break;
            }
        }
        if(valid) {
            if(submissions.indexOf(studentID) == -1) { //Checks if the student is currently not in the submissions list
                submissions.add(studentID); //Adds the desired studentID to the submissions list
                MediaFile.setOutputFile(Main.submissionsFile); //Rewrites submissions file
                for(String id : submissions) {
                    MediaFile.writeString(id, "\t", true);
                }
                MediaFile.saveAndClose();
                System.out.println("Card with ID " + studentID + " marked as submitted! (" + Main.submissionsFile + ".txt updated!)");
                Main.setMissingCardsFile();
                Main.setSummaryFile();
                writeAllMissing(Main.missingCardsFile); //Updates missing cards file and summary file
                provideSummary(Main.summaryFile);
            } else {
                System.out.println("Student has already submitted their card!");
            }
        } else {
            System.out.println("No student found with the ID " + studentID);
        }
    }

    /**
     * This method marks a student's registration card as unsubmitted or missing
     * The student's ID will be removed from the list of submissions if it is present
     * The submissions text file will also be updated
     * @param studentID The ID of the student whose registration card will be marked as unsubmitted or missing
     */
    public void removeCard(String studentID) {
        boolean removed = submissions.remove(studentID); //removes the desired studentID from the submissions list
        if(removed) { //Checks that the desired student was found in the list and removed
            MediaFile.setOutputFile(Main.submissionsFile); //Rewrites submissions file
            for(String id : submissions) {
                MediaFile.writeString(id, "\t", true);
            }
            MediaFile.saveAndClose();
            System.out.println("Card with ID " + studentID + " marked as unsubmitted! (" + Main.submissionsFile + ".txt updated!)");
            Main.setMissingCardsFile();
            Main.setSummaryFile();
            writeAllMissing(Main.missingCardsFile); //Updates missing cards file and summary file
            provideSummary(Main.summaryFile);
        } else {
            System.out.println(studentID + " not found!");
        }
    }

    /**
     * This method withdraws a student from the school
     * The student whose ID matches the method's input will be removed from the list of students if it is present
     * The student master file will also be updated with the withdrawal of the student
     * @param studentID The ID of the student who will be withdrawn from the school
     */
    public void withdrawStudent(String studentID) {
        for(Student student : students) { //Iterates through all stored Student objects in the list
            if(student.getID().trim().equals(studentID.trim())) { //Checks if there is a student that has the matching desired student ID
                System.out.println(student);
                String confirm = Main.scan.next();
                 System.out.println("Confirm student's withdrawal from the school? (Type 'yes' to confirm)");
               if(confirm.equals("yes")) {
                    students.remove(student); //removes the student from the list
                    System.out.println("Student (ID: " + studentID + ") has been withdrawn from the school");
                    writeToMaster(); //update student master file
                    System.out.println("Master Student file '" + Main.studentMasterFile + ".txt' updated!");
                    break;
                } else {
                    System.out.println("Canceled!");
                    break;
                }
            }
        }
    }

    /**
     * This method writes the data stored in the students ArrayList to the student faster file (used to update the file when called)
     */
    public void writeToMaster() {
        MediaFile.setOutputFile(Main.studentMasterFile);
        for(Student student : students) { //Iterates through all stored Student objects in the list
            String grade = "" + student.getGrade();
            MediaFile.writeString(student.getID(), "\t", false);
            MediaFile.writeString(student.getFirstName(), "\t", false);
            MediaFile.writeString(student.getLastName(), "\t", false);
            MediaFile.writeString(grade, "\t", false);
            MediaFile.writeString(student.getTeacher(), "\t", true);
        }
        MediaFile.saveAndClose();
    }

    //Sorting algorithm: Quicksort
    //Quicksort algorithm is used here for better time complexity in sorting through all student's last names with the given dataset (works better with large numbers of students)
    /**
     * 
     * @param lowIdx
     * @param highIdx
     */
    public void sortStudents(int lowIdx, int highIdx) {
        int l = lowIdx;
        int h = highIdx;
        String pivot = students.get(lowIdx + (highIdx - lowIdx) / 2).getLastName(); //Gets the last name of the student where the list will be split into two
        
        while(l <= h) {
            while(students.get(l).getLastName().compareTo(pivot) < 0) { //Going left to right, checks if selected last name is ordered alphabetically or not with the last name at the split
                l++;
            }

            while(students.get(h).getLastName().compareTo(pivot) > 0) { //Going right to left, checks if selected last name is ordered alphabetically or not with the last name at the split
                h--;
            }
            if(l <= h) { //Swaps the students from their old position to their sorted position
                Student temp = students.get(l);
                students.set(l, students.get(h));
                students.set(h, temp);
                l++;
                h--;
            }
        }

        //Use of recursion here repeats the sorting process until the entire list has been sorted to the point of the split
        if(lowIdx < h) {
            sortStudents(lowIdx, h);
        }
        if(l < highIdx) {
            sortStudents(l, highIdx);
        }
    }
}
