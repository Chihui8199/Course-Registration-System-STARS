package filemanager;

import exception.WrongAccessPeriodException;
import exception.WrongLoginInfoException;
import model.LoginInfo;

/**
 * This interface is an abstraction of the methods that any class providing the login logic should implement.
 */
public interface ILoginable {
    /**
     * This method should contain the logic to verify the login information provided.
     * @param providedLoginInfo The {@code LoginInfo} object containing the login information that is to be verified.
     * @throws WrongLoginInfoException if the login information provided is invalid.
     * @throws WrongAccessPeriodException if the current time is not within the access period of the student trying
     * to log in to the program.
     */
    void verifyLoginInfo(LoginInfo providedLoginInfo) throws WrongLoginInfoException, WrongAccessPeriodException;
}
