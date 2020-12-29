package view;

import errormessage.ErrorMessage;

import model.AccountType;

/**
 * This class is responsible for the logic of getting input and output from the user for their login information.
 */
public class LoginUi extends Ui {
    /**
     * The logo to be displayed when the program starts.
     */
    private final String STARS_LOGO = "  _________________________ __________  _________\n" +
            " /   _____/\\__    ___/  _  \\\\______   \\/   _____/\n" +
            " \\_____  \\   |    | /  /_\\  \\|       _/\\_____  \\ \n" +
            " /        \\  |    |/    |    \\    |   \\/        \\\n" +
            "/_______  /  |____|\\____|__  /____|_  /_______  /\n" +
            "        \\/                 \\/       \\/        \\/ \n";

    /**
     * The welcome message that is displayed when the program starts.
     */
    private final String WELCOME_MESSAGE = "Welcome to NTU Stars Planner. Please login.";

    /**
     * A String array containing all the options for the user to choose in the login panel.
     */
    private final String[] LOGIN_OPTIONS = {
            "1. Student Login",
            "2. Admin Login"
    };

    /**
     * Prints the logo and the welcome message for the program.
     */
    public void printWelcomeMessage() {
        print(STARS_LOGO);
        print(WELCOME_MESSAGE);
    }

    /**
     * Get the user to input whether they want to log in as an admin or student.
     * @return A {@code AccountType} representing either admin or student.
     */
    public AccountType getAccountType() {
        int choice;
        AccountType accountType;

        printWelcomeMessage();
        while (true) {
            choice = getInputChoice("Enter your choice:", LOGIN_OPTIONS);
            if (choice == 1) {
                accountType = AccountType.STUDENT;
                break;
            } else if (choice == 2) {
                accountType = AccountType.ADMIN;
                break;
            } else {
                printErrorMessage(ErrorMessage.ERROR_INPUT_CHOICE);
            }
        }

        return accountType;
    }

}
