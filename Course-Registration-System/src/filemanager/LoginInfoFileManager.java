package filemanager;

import model.AccountType;
import model.LoginInfo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class is responsible for the logic that handles the storage and retrieval of login information.
 */
public class LoginInfoFileManager implements ILoginInfoFileManager {
    /**
     * The {@code File} object that represents the file in which student login information is stored in.
     * @see File
     */
    private File studentLoginInfoFile;

    /**
     * The {@code File} object that represents the file in which admin login information is stored in.
     * @see File
     */
    private File adminLoginInfoFile;

    /**
     * Constructs a new LoginInfoFileManager object by instantiating the studentLoginInfoFile and adminLoginInfoFile.
     */
    public LoginInfoFileManager() {
        adminLoginInfoFile = new File("data/AdminLoginInfo.txt");
        studentLoginInfoFile = new File("data/StudentLoginInfo.txt");
    }

    /**
     * Contains the logic to save the new login information of the new student.
     * @param loginInfo The {@code LoginInfo} object that contains the new login information for the new student.
     * @see LoginInfo
     */
    @Override
    public void addLoginInfoForNewStudent(LoginInfo loginInfo) {
        try {
            FileWriter fw = new FileWriter(studentLoginInfoFile, true);
            fw.write(loginInfo.getUserId() + "|" + loginInfo.getPassword() + "\n");
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Contains the logic to retrieve all the login information of all the students.
     * @return An ArrayList of {@code LoginInfo} that contains the login information of all the students.
     * @see LoginInfo
     */
    @Override
    public ArrayList<LoginInfo> retrieveStudentLoginInfoList() {
        return retrieveLoginInfoList(studentLoginInfoFile, AccountType.STUDENT);
    }

    /**
     * Contains the logic to retrieve all the login information of all the admin.
     * @return An ArrayList of {@code LoginInfo} that contains the login information of all the admin.
     * @see LoginInfo
     */
    @Override
    public ArrayList<LoginInfo> retrieveAdminLoginInfoList() {
        return retrieveLoginInfoList(adminLoginInfoFile, AccountType.ADMIN);
    }

    /**
     * Retrieves all the login information stored in the {@code File} object and what account type the {@code File} object
     * stores.
     * @param file A {@code File} object that stores all the login information.
     * @param accountType The {@code AccountType} which the file stores.
     * @return An ArrayList of {@code LoginInfo} that contains all the login information of accountType.
     * @see File
     * @see AccountType
     */
    private ArrayList<LoginInfo> retrieveLoginInfoList(File file, AccountType accountType) {
        ArrayList<LoginInfo> adminLoginInfoList = new ArrayList<>();
        try {
            Scanner sc = new Scanner(file);

            while (sc.hasNext()) {
                String[] lineSplit = sc.nextLine().split("\\|");
                adminLoginInfoList.add(new LoginInfo(accountType, lineSplit[0], lineSplit[1]));
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return adminLoginInfoList;
    }

}
