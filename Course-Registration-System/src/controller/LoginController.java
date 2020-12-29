package controller;

import filemanager.ILoginable;
import model.AccountType;
import model.LoginInfo;
import view.LoginUi;
import exception.WrongAccessPeriodException;
import exception.WrongLoginInfoException;

/**
 * This class is responsible for handling the login process.
 */
public class LoginController {
    /**
     * A LoginUi object responsible for handling input/output to the user.
     * @see LoginUi
     */
    private LoginUi loginUi;

    /**
     * A realization of ILoginable and provides the logic to verify the login details provided by the user.
     * @see ILoginable
     */
    private ILoginable loginManager;

    /**
     * Creates a new LoginController by injecting an object of a class that implements ILoginable and instantiating
     * the LoginUi object.
     * @param loginManager An object that implements ILoginable.
     * @see ILoginable
     */
    public LoginController(ILoginable loginManager) {
        loginUi = new LoginUi();
        this.loginManager = loginManager;
    }

    /**
     * This method runs a loop to get user login information, verifies the login information and returns the login
     * information only if the user provided a correct login information.
     * @return A LoginInfo object containing the user id, hashed password and account type that has been verified to be correct.
     */
    public LoginInfo run() {
        LoginInfo providedLoginInfo;
        while (true) {
            AccountType accountType = loginUi.getAccountType();
            providedLoginInfo = loginUi.getLoginInfo(accountType);

            try {
                loginManager.verifyLoginInfo(providedLoginInfo);
                break;
            } catch (WrongAccessPeriodException | WrongLoginInfoException e) {
                loginUi.printErrorMessage(e.getMessage());
            }
        }

        return providedLoginInfo;
    }

}
