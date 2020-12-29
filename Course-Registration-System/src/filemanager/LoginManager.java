package filemanager;

import model.LoginInfo;
import exception.WrongLoginInfoException;
import exception.WrongAccessPeriodException;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * This class is responsible for handling the login logic.
 */
public class LoginManager implements ILoginable {
    /**
     * A realization of ILoginInfoFileManager that provides the logic to handle storage and retrieval of login information.
     * @see ILoginInfoFileManager
     */
    private ILoginInfoFileManager loginInfoFileManager;

    /**
     * An object that implements IStorageManager.
     * @see IStorageManager
     */
    private IStorageManager storageManager;

    /**
     * Constructs a new LoginManager and injects the dependencies using constructor injections.
     * @param loginInfoFileManager An object that implements ILoginInfoFileManager.
     * @param storageManager An object that implements IStorageManager.
     * @see ILoginInfoFileManager
     * @see IStorageManager
     */
    public LoginManager(ILoginInfoFileManager loginInfoFileManager, IStorageManager storageManager) {
        this.loginInfoFileManager = loginInfoFileManager;
        this.storageManager = storageManager;
    }

    /**
     * Contains the main logic to verify the provided login information.
     * @param providedLoginInfo The {@code LoginInfo} object containing the login information that is to be verified.
     * @throws WrongLoginInfoException if the login information provided is invalid.
     * @throws WrongAccessPeriodException if the current time is not within the access period of the student trying
     * to log in to the program.
     */
    @Override
    public void verifyLoginInfo(LoginInfo providedLoginInfo)
        throws WrongLoginInfoException, WrongAccessPeriodException {
        switch (providedLoginInfo.getAccountType()) {
            case ADMIN:
                verifyAdminLoginInfo(providedLoginInfo);
                break;
            case STUDENT:
                verifyStudentLoginInfo(providedLoginInfo);
                break;
            default:
                assert false : "Invalid account type!";
        }
    }

    /**
     * This method is called if the provided login information is for student. Contains the logic to verify if the
     * login information for the student is valid and the current time is within the access period of that student.
     * @param providedLoginInfo The {@code LoginInfo} object containing the login information that is to be verified.
     * @throws WrongAccessPeriodException if the login information provided is invalid.
     * @throws WrongLoginInfoException if the current time is not within the access period of the student trying
     * to log in to the program.
     */
    private void verifyStudentLoginInfo(LoginInfo providedLoginInfo)
            throws WrongAccessPeriodException, WrongLoginInfoException {
        ArrayList<LoginInfo> loginInfoList = loginInfoFileManager.retrieveStudentLoginInfoList();
        for (LoginInfo loginInfo: loginInfoList) {
            if (loginInfo.equals(providedLoginInfo)) {
                if (isWithinAccessTimeForStudent(loginInfo.getUserId())) {
                    return;
                }

                throw new WrongAccessPeriodException(storageManager.getStudent(loginInfo.getUserId()).getAccessTime());
            }
        }

        throw new WrongLoginInfoException();
    }

    /**
     * This method is called if the provided login information is for admin. Contains the logic to verify if the provided
     * login information for the admin is valid.
     * @param providedLoginInfo The {@code LoginInfo} object containing the login information that is to be verified.
     * @throws WrongLoginInfoException if the login information provided is invalid.
     */
    private void verifyAdminLoginInfo(LoginInfo providedLoginInfo) throws WrongLoginInfoException {
        ArrayList<LoginInfo> loginInfoList = loginInfoFileManager.retrieveAdminLoginInfoList();
        for (LoginInfo loginInfo: loginInfoList) {
            if (loginInfo.equals(providedLoginInfo)) {
                return;
            }
        }

        throw new WrongLoginInfoException();
    }

    /**
     * Checks if the current time is within the access time for a particular student.
     * @param userId The user id of the student to check if the time now is within access time of the student.
     * @return true if the current time is within access time if the student; false otherwise.
     */
    private boolean isWithinAccessTimeForStudent(String userId) {
        LocalDateTime dateTimeNow = LocalDateTime.now();
        return storageManager.getStudent(userId).isWithinAccessTime(dateTimeNow);
    }
}
