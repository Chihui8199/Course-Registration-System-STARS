import controller.AdminController;
import controller.LoginController;
import controller.StudentController;
import filemanager.ILoginInfoFileManager;
import filemanager.ILoginable;
import filemanager.IStorageManager;
import filemanager.LoginInfoFileManager;
import filemanager.LoginManager;
import filemanager.StorageManager;
import model.LoginInfo;

/**
 * The main entry point for the program.
 */
public class Main {

    /**
     * The main entry into the program. First initialises the relevant objects which are injected as
     * dependencies into the relevant controllers. The LoginController is first instantiated to get login
     * information from the user, after which either the AdminController or StudentController will be
     * instantiated, depending if the user logging in is a student or admin.
     * @param args The supplied command line arguments as an array of String objects.
     */
    public static void main(String[] args) {
        IStorageManager storageManager = new StorageManager();
        ILoginInfoFileManager loginInfoFileManager = new LoginInfoFileManager();
        ILoginable loginManager = new LoginManager(loginInfoFileManager, storageManager);

        LoginController lc = new LoginController(loginManager);
        LoginInfo providedLoginInfo = lc.run();
        switch (providedLoginInfo.getAccountType()) {
        case ADMIN:
            AdminController ac = new AdminController(storageManager, loginInfoFileManager);
            ac.run();
            break;
        case STUDENT:
            StudentController sc = new StudentController(providedLoginInfo.getUserId(), storageManager, loginManager);
            sc.run();
            break;
        default:
            assert false : "Invalid accountType returned from LoginController!";
        }
    }

}
