import java.util.Scanner;

public class Main {
    public static String studentMasterFile;
    public static String submissionsFile;
    public static String missingCardsFile;
    public static String summaryFile;
    public static Scanner scan = new Scanner(System.in);
    
    public static void main(String[] args) {
        System.out.println("Enter name of Student Master file:");
        studentMasterFile = scan.next();
        System.out.println("Enter name of Submissions file");
        submissionsFile = scan.next();
        School school = new School();  
        school.importData(studentMasterFile); //Importing data from files
        school.importSubmissions(submissionsFile);

        printOptions();
        int choice = scan.nextInt();
        while(choice != -1) {
            switch(choice) { //Switch-case statement for checking what options have been selected
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
                    System.out.println("Please provide student ID:");
                    String id = scan.next();
                    school.withdrawStudent(id);
                    break;
                case 5:
                    school.printMissingByTeacher();
                    break;
                default:
                    System.out.println("Not a valid option! Please select again!");
                    break;
            }
            printOptions();
            choice = scan.nextInt();
        }
        school.writeToMaster();
        System.out.println("Updating Master Student file '" + studentMasterFile + ".txt'");
        System.out.println("Master Student file '" + studentMasterFile + ".txt' updated!");
    }

    public static void printOptions() {
        System.out.println("What would you like to do?  (-1 to end)");
        System.out.println("1) See list of all missing registration forms");
        System.out.println("2) See a summary of missing registration cards by grade level");
        System.out.println("3) Change the status of a registration card");
        System.out.println("4) Withdraw a student from school");
        System.out.println("5) Print a list of students missing by CAP Teacher");
    }

    private static void printOptions2() {
        System.out.println("What would you like to do? (-1 to end)");
        System.out.println("1) Mark a card as submitted");
        System.out.println("2) Mark a card as unsubmitted");
        
    }
    /**
     * This method asks the user for a file name to view the missing cards if no file name was given
     */
    public static void setMissingCardsFile() {
        if(missingCardsFile == null || missingCardsFile.isEmpty()) {
            System.out.println("Please provide a file name to view stored Missing Cards results:");
            missingCardsFile = scan.next();
            System.out.println("File '" + missingCardsFile + "' will be used to store and view Missing Card results");
        }
    }

    /**
     * This method asks the user for a file name to view the summary if no file name was given
     */
    public static void setSummaryFile() {
        if(summaryFile == null || summaryFile.isEmpty()) {
            System.out.println("Please provide a file name to view stored Summary results:");
            summaryFile = scan.next();
            System.out.println("File '" + summaryFile + "' will be used to store and view Grade Summary results");
        }
    }
}
