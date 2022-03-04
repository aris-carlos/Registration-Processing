import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    public static String studentMasterFile;
    public static String submissionsFile;
    public static String missingCardsFile;
    public static String summaryFile;
    public static Scanner scan = new Scanner(System.in);
    
    //This main method is NOT COMPLETE.  Add to it as needed
    public static void main(String[] args) {

        
        System.out.println("Enter name of Student Master file:");
        studentMasterFile = scan.next();
        System.out.println("Enter name of Submissions file");
        submissionsFile = scan.next();
        School school = new School();  //Modify this line as needed
        school.importData(studentMasterFile);
        school.importSubmissions(submissionsFile);


        printOptions();
        int choice = scan.nextInt();
        while(choice != -1) {
            switch(choice) {
                case 1:
                    setMissingCardsFile();
                    school.writeAllMissing(missingCardsFile);
                    break;
                case 2:
                    setSummaryFile();
                    school.provideSummary(summaryFile);
                    break;
                case 3:
                    
                    printOptions2();
                    int status = scan.nextInt();
                    if(status == 1) {
                        System.out.println("Please provide student ID:");
                        String id = scan.next();
                        school.markCardSubmitted(id);
                    } else if(status == 2) {
                        System.out.println("Please provide student ID:");
                        String id = scan.next();
                        school.removeCard(id);
                    } else {
                        System.out.println("Canceled!");
                    }
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    break;
                default:
                    System.out.println("Not a valid option! Please select again!");
                    break;
            }



            printOptions();
            choice = scan.nextInt();
        }

    }

    public static void printOptions() {
        System.out.println("What would you like to do?  (-1 to end)");
        System.out.println("1) See list of all missing registration forms");
        System.out.println("2) See a summary of missing registration cards by grade level");
        System.out.println("3) Change the status of a registration card");
        System.out.println("4) Withdraw a student from school");
        System.out.println("5) Print a list of students missing by CAP Teacher");
        System.out.println("6) Sort list by student last name");
    }

    private static void printOptions2() {
        System.out.println("What would you like to do? (-1 to end)");
        System.out.println("1) Mark a card as submitted");
        System.out.println("2) Mark a card as unsubmitted");
        
    }

    public static void setMissingCardsFile() {
        if(missingCardsFile == null || missingCardsFile.isEmpty()) {
            System.out.println("Please provide a file name to view stored results:");
            missingCardsFile = scan.next();
            System.out.println("File '" + missingCardsFile + "' will be used to store and view Missing Card results");
        }
    }

    public static void setSummaryFile() {
        if(summaryFile == null || summaryFile.isEmpty()) {
            System.out.println("Please provide a file name to view stored results:");
            summaryFile = scan.next();
            System.out.println("File '" + summaryFile + "' will be used to store and view Grade Summary results");
        }
    }

}
