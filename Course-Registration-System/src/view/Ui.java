package view;

import errormessage.ErrorMessage;
import model.AccountType;
import model.Course;
import model.IndexNumber;
import model.LoginInfo;
import model.Student;
import utility.SHA256Hasher;

import java.io.Console;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * The base class for all Ui classes. Contains all the basic methods relevant for input/output to the user.
 */
public class Ui {
    /**
     * Divider line used to contain normal messages printed to the user.
     */
    private final String DIVIDER_LINE = "____________________________________________________________";

    /**
     * Divider line used to contain error messages printed to the user.
     */
    private final String ERROR_DIVIDER_LINE = "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!";

    /**
     * Prints a message.
     * @param message The message to be shown to the user.
     */
    public void print(String message) {
        System.out.println(message);
    }

    /**
     * Prints the normal divider line.
     */
    private void printDivider() {
        System.out.println(DIVIDER_LINE);
    }

    /**
     * Prints the error divider line.
     */
    private void printErrorDivider() {
        System.out.println(ERROR_DIVIDER_LINE);
    }

    /**
     * Prints messages contained inside two normal divider line.
     * @param messages The messages to be shown to the user.
     */
    public void printMessageWithDivider(String... messages) {
        printDivider();
        for (String message: messages) {
            print(message);
        }
        printDivider();
    }

    /**
     * Prints message contained inside two error divider line.
     * @param messages The error messages to be shown to the user.
     */
    public void printErrorMessage(String... messages) {
        printErrorDivider();
        print("Error Message: ");
        for (String message: messages) {
            print(message);
        }
        printErrorDivider();
    }

    /**
     * Prints the good bye message when user quits the program.
     */
    public void printGoodBye() {
        printMessageWithDivider("Logging off... Hope to see you again in STARS!");
    }

    /**
     * Displays a list of options for the user to select and gets the input choice of the user.
     * @param finalMessageForInput The final message to be displayed to the user asking for his/her input
     * @param options The description of the options available to the user.
     * @return An integer input by the user.
     */
    public int getInputChoice(String finalMessageForInput, String... options) {
        int choice;
        while (true) {
            try {
                if (options.length != 0) {
                    printMessageWithDivider(options);
                }
                print(finalMessageForInput);
                Scanner sc = new Scanner(System.in);
                choice = sc.nextInt();
                break;
            } catch (InputMismatchException e) {
                printErrorMessage(ErrorMessage.ERROR_INPUT_CHOICE);
            }
        }

        return choice;
    }

    /**
     * Formats the description of a list of courses and returns a String array containing the descriptions of the
     * individual courses.
     * @param courses An ArrayList of {@code Course} to generate the descriptions for.
     * @param message The message to be displayed before the list of courses.
     * @return A String array containing descriptions of each course in courses.
     */
    public String[] getCoursesDescription(ArrayList<Course> courses, String message) {
        ArrayList<String> coursesDescriptionList = new ArrayList<>();
        if (message != null) {
            coursesDescriptionList.add(message);
        }
        int index = 1;
        for (Course course: courses) {
            coursesDescriptionList.add((index++) + ". " + course.toString());
        }

        return coursesDescriptionList.toArray(String[]::new);
    }

    /**
     * Formats the description of a list of index numbers and returns a String array containing the descriptions
     * of the individual index numbers.
     * @param indexNumbers An ArrayList of {@code IndexNumber} to generate the descriptions for.
     * @param message The message to be displayed before the list of index numbers.
     * @return A string array containing descriptions of each index number in indexNumbers.
     */
    public String[] getIndexNumbersDescription(ArrayList<IndexNumber> indexNumbers, String message) {
        ArrayList<String> indexNumbersDescriptionList = new ArrayList<>();
        if (message != null) {
            indexNumbersDescriptionList.add(message);
        }
        int index = 1;
        for (IndexNumber indexNumber: indexNumbers) {
            indexNumbersDescriptionList.add((index++) + ". " + indexNumber.getFullDescription());
        }

        return indexNumbersDescriptionList.toArray(String[]::new);
    }

    /**
     * Formats the description of a list of students and returns a String array containing the descriptions of the
     * individual students.
     * @param students An ArrayList of {@code Student} to generate the descriptions for.
     * @param message The message to be displayed before displaying the list of students.
     * @return A String array containing descriptions of each student in students.
     */
    public String[] getStudentsDescription(ArrayList<Student> students, String message) {
        ArrayList<String> studentsDescriptionList = new ArrayList<>();
        if (message != null) {
            studentsDescriptionList.add(message);
        }
        int index = 1;
        for (Student student: students) {
            studentsDescriptionList.add((index++) + ". " + student.toString());
        }

        return studentsDescriptionList.toArray(String[]::new);
    }

    public void checkVacancyOfIndexNumber(ArrayList<Course> courses) {
        int choice;
        while (true) {
            choice = getInputChoice("Which course do you want to check?",
                    getCoursesDescription(courses, "Here are the list of courses:"));
            if (choice < 1 || choice > courses.size()) {
                printErrorMessage(ErrorMessage.ERROR_INPUT_CHOICE);
                continue;
            }
            break;
        }
        printIndexNumberVacancies(courses.get(choice-1));
    }

    /**
     * Prints the vacancies of each index numbers in a course.
     * @param course The {@code Course} to print the vacancies of each index numbers for.
     */
    private void printIndexNumberVacancies(Course course) {
        List<String> indexNumbersString = course.getIndexNumbers()
                .stream()
                .map((i) -> i.toString())
                .collect(Collectors.toList());
        indexNumbersString.add(0, "Here are the vacancies for " + course.toString());
        printMessageWithDivider(indexNumbersString.toArray(String[]::new));
    }

    /**
     * Gets the login information from the user.
     * @param accountType The account type specified by the user to log in as.
     * @return A LoginInfo object containing the user id, hashed password and account type.
     * @see LoginInfo
     */
    public LoginInfo getLoginInfo(AccountType accountType) {
        Scanner sc = new Scanner(System.in);
        Console con = System.console();
        String userId;
        char[] passwordCharArray;
        String passwordString;
        //String password;

        print("Enter user id:");
        userId = sc.next();

        print("Enter password:");
        //password = sc.next();
        passwordCharArray = con.readPassword();
        passwordString = String.valueOf(passwordCharArray);

        return new LoginInfo(accountType, userId, SHA256Hasher.hash(passwordString));
    }

    /**
     * Formats a integer into its ordinal number, i.e. 1 to "1st", 2 to "2nd", etc.
     * @param i The integer to be formatted into its ordinal number representation.
     * @return The String containing the ordinal number representation of i.
     */
    protected String ordinal(int i) {
        String[] suffixes = new String[] { "th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th" };
        switch (i % 100) {
        case 11:
        case 12:
        case 13:
            return i + "th";
        default:
            return i + suffixes[i % 10];
        }
    }

}
