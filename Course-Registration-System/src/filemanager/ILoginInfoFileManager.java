package filemanager;

import model.LoginInfo;

import java.util.ArrayList;

/**
 * This interface is an abstraction of the methods that any class providing the logic to retrieve and store login
 * information should implement.
 */
public interface ILoginInfoFileManager {
    /**
     * This method should contain the logic to add a new login information for a new student.
     * @param loginInfo The {@code LoginInfo} object that contains the new login information for the new student.
     * @see LoginInfo
     */
    void addLoginInfoForNewStudent(LoginInfo loginInfo);

    /**
     * This method should contain the logic to retrieve all the login information of all the students.
     * @return An ArrayList of {@code LoginInfo} that contains the login information of all the students.
     * @see LoginInfo
     */
    ArrayList<LoginInfo> retrieveStudentLoginInfoList();

    /**
     * This method should contain the logic to retrieve all the login information of all the admin.
     * @return An ArrayList of {@code LoginInfo} that contains the login information of all the admin.
     * @see LoginInfo
     */
    ArrayList<LoginInfo> retrieveAdminLoginInfoList();
}
