import java.util.Scanner;
import java.util.ArrayList;

public class Main {

    //This main method is NOT COMPLETE.  Add to it as needed
    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);

        School mohs = new School();  //Modify this line as needed
        mohs.importData("studentMaster");
        mohs.importSubmissions("submissions");


        printOptions();
        int choice = scan.nextInt();
        while(choice != -1) {
            switch(choice) {
                case 1:
                    //System.out.print(mohs.getStudents());
                    System.out.println("Enter file name for results");
                    String fileName = scan.next();
                    mohs.writeAllMissing(fileName);
                    
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
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
        System.out.println("2) Print a summary of missing registration cards for a given CAP teacher");
        System.out.println("3) Mark an additional card as submitted");
        System.out.println("4) Withdraw a student from school");
        System.out.println("5) Sort list by student last name");
    }

    private static void printOptions2() {
        System.out.println("");
        System.out.println("");
        
    }

}
