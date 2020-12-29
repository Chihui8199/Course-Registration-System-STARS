package view;

import errormessage.ErrorMessage;
import model.AccountType;
import model.Course;
import model.IndexNumber;
import model.LoginInfo;
import model.Student;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class is responsible for the logic of getting input and output from the user for tasks related to the Student
 * panel.
 */
public class StudentUi extends Ui {
    /**
     * A String array containing all the options for the user to choose in the student panel.
     */
    private final String[] STUDENT_MENU_OPTIONS = {
            "Welcome to the STARS!",
            "1. Add a course.",
            "2. Drop a registered course.",
            "3. Drop a wait list course.",
            "4. Check/Print Courses Registered.",
            "5. Check Vacancies Available.",
            "6. Change Index Number of Course.",
            "7. Swap Index Number with Another Student.",
            "8. Quit."
    };

    /**
     * Prints a welcome message when the user first comes into the student panel.
     * @param studentName Name of the student which logged in.
     */
    public void printWelcomeMessage(String studentName) {
        printMessageWithDivider(
                "Welcome, " + studentName + "!"
        );
    }

    /**
     * Prints all the options for the user in the student panel and get the user to input a choice.
     * @return A integer representing the user's input choice.
     */
    public int getMenuInputChoice() {
        return getInputChoice("Enter your choice:", STUDENT_MENU_OPTIONS);
    }

    /**
     * Displays all the registered courses and get the user to input which course he/she would like to change. This
     * method will always get the user to input a valid integer from 1 to size of courses and return an integer from
     * 0 to size of courses - 1.
     * @param courses An ArrayList of {@code Course} containing all the registered courses for a student.
     * @return The integer representing the index of courses for which the student has chosen.
     */
    public int getIndexOfCourseToChange(ArrayList<Course> courses) {
        int index;
        while (true) {
            index = getInputChoice("Which course would you like to change?",
                    getCoursesDescription(courses, "Here are your registered courses:"));
            if (index < 1 || index > courses.size()) {
                printErrorMessage(ErrorMessage.ERROR_INPUT_CHOICE);
                continue;
            }
            break;
        }
        return index - 1;
    }

    /**
     * Displays all the index numbers for a particular course for which the student wants to change the index number
     * for. The method will always get the user to input a valid integer from 1 to size of indexNumbers and return
     * an integer from 0 to size of indexNumbers - 1.
     * @param indexNumbers An ArrayList of {@code IndexNumber} that is associated with the course that the student
     *                     wants to change index number for.
     * @return The integer representing the index of the indexNumbers for which the student has chosen.
     */
    public int getIndexOfIndexNumberToChange(ArrayList<IndexNumber> indexNumbers) {
        int index;
        while (true) {
            index = getInputChoice("Which index number would you like to change to:?",
                    getIndexNumbersDescription(indexNumbers, "Here are the index numbers:"));
            if (index < 1 || index > indexNumbers.size()) {
                printErrorMessage(ErrorMessage.ERROR_INPUT_CHOICE);
                continue;
            }
            break;
        }
        return index - 1;
    }

    /**
     * Displays all the courses registered or all the courses in wait list and ask the user to input which course
     * he/she would like to drop.
     * @param courses An ArrayList of {@code Course}.
     * @param dropRegistered true if the user wants to drop a registered course; false if the user wants to drop a course
     *                       on the wait list.
     * @return The integer representing the index of the courses for which the user wants to drop.
     */
    public int getIndexOfCourseToDrop(ArrayList<Course> courses, boolean dropRegistered) {
        int index;
        while (true) {
            if (dropRegistered) {
                index = getInputChoice("Which course would you like to drop?",
                        getCoursesDescription(courses, "Here are your registered courses:"));
            } else {
                index = getInputChoice("Which course would you like to drop?",
                        getCoursesDescription(courses, "Here are your wait listed courses:"));
            }
            if (index < 1 || index > courses.size()) {
                printErrorMessage(ErrorMessage.ERROR_INPUT_CHOICE);
                continue;
            }
            break;
        }
        return index - 1;
    }

    /**
     * Displays all the courses and get the user to choose which course they would like to add. The method will always
     * get the user to input a valid integer from 1 to size of courses and return an integer from 0 to size of courses
     * - 1.
     * @param courses An ArrayList of {@code Course}.
     * @return The integer representing the index of courses that the user wants to register for.
     */
    public int getIndexOfCourseToRegister(ArrayList<Course> courses) {
        int index;
        while (true) {
            index = getInputChoice("Which course would you like to add?",
                    getCoursesDescription(courses, "Here are the available courses: "));
            if (index < 1 || index > courses.size()) {
                printErrorMessage(ErrorMessage.ERROR_INPUT_CHOICE);
                continue;
            }
            break;
        }
        return index - 1;
    }

    /**
     * Displays all the index numbers associated with a particular course and get the user to input which index number
     * he/she wants to register for. The method will always get the user to input a valid integer from 1 to size of
     * indexNumbers and return an integer from 0 to size of indexNumbers - 1.
     * @param indexNumbers An ArrayList of {@code IndexNumber} that are associated with the course that the user wants
     *                     to register for.
     * @return The integer representing the index of the indexNumbers for which the user wants to register for.
     */
    public int getIndexOfIndexNumberToRegister(ArrayList<IndexNumber> indexNumbers) {
        int index;
        while (true) {
            index = getInputChoice("Which index number would you like to add?",
                    getIndexNumbersDescription(indexNumbers, "Here are the index numbers:"));
            if (index < 1 || index > indexNumbers.size()) {
                printErrorMessage(ErrorMessage.ERROR_INPUT_CHOICE);
                continue;
            }
            break;
        }
        return index - 1;
    }

    /**
     * Get confirmation from the user that he/she is going to swap the index number for a particular course with another
     * peer.
     * @param student The {@code Student} who wants to swap an index number with the peer.
     * @param peer The peer who is going to swap an index number with the student.
     * @param courseToBeSwapped The {@code Course} for which the student and peer is going to be swapped.
     * @return true if the user confirms the swap; false if the user do not confirm the swap.
     */
    public boolean confirmSwapWithPeer(Student student, Student peer, Course courseToBeSwapped) {
        IndexNumber yourIndexNumber = student.getRegisteredIndexNumbers().get(courseToBeSwapped.getCourseCode());
        String yourIndexNumberMessage = "Your index number:\n";
        yourIndexNumberMessage += yourIndexNumber.getFullDescription();

        IndexNumber peerIndexNumber  = peer.getRegisteredIndexNumbers().get(courseToBeSwapped.getCourseCode());
        String peerIndexNumberMessage = "Your peer " + peer.getName() + "'s index number:\n";
        peerIndexNumberMessage += peerIndexNumber.getFullDescription();

        Scanner sc = new Scanner(System.in);
        while (true) {
            printMessageWithDivider("Confirm swap for " + courseToBeSwapped.getCourseCode() + " "
                    + courseToBeSwapped.getCourseName() + " with peer?\n", yourIndexNumberMessage, peerIndexNumberMessage);
            print("Enter (Y for yes, N for no): ");
            String input = sc.next();
            if (input.trim().toUpperCase().equals("Y")) {
                return true;
            } else if (input.trim().toUpperCase().equals("N")) {
                return false;
            } else {
                printErrorMessage(ErrorMessage.INVALID_CONFIRM_SWAP_PEER);
            }
        }
    }

    /**
     * Get the login information of the peer.
     * @return A {@code LoginInfo} object containing the login information for the peer.
     * @see LoginInfo
     */
    public LoginInfo getLoginInfoOfPeer() {
        return getLoginInfo(AccountType.STUDENT);
    }
}

