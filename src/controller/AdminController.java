package controller;

import errormessage.ErrorMessage;
import filemanager.ILoginInfoFileManager;
import filemanager.IStorageManager;
import filemanager.LoginInfoFileManager;
import model.Course;
import model.IndexNumber;
import model.LoginInfo;
import model.Student;
import view.AdminUi;

import java.util.ArrayList;

/**
 * This class is responsible for handling tasks related to the admin panel.
 */
public class AdminController {
    /**
     * An AdminUi object responsible for handling input/output to the user.
     */
    private AdminUi adminUi;

    /**
     * An object that implements IStorageManager and provides the logic to retrieve/save data.
     * @see IStorageManager
     */
    private IStorageManager storageManager;

    /**
     * An object that implements ILoginInfoFileManager and provides the logic to retrieve/store login information
     * of users.
     * @see ILoginInfoFileManager
     */
    private ILoginInfoFileManager loginInfoFileManager;

    /**
     * Creates a new AdminController by injecting the dependencies.
     * @param storageManager An object that implements IStorageManager.
     * @param loginInfoFileManager An object that implements ILoginInfoFileManager
     * @see ILoginInfoFileManager
     * @see IStorageManager
     */
    public AdminController(IStorageManager storageManager, ILoginInfoFileManager loginInfoFileManager) {
        adminUi = new AdminUi();
        this.storageManager = storageManager;
        this.loginInfoFileManager = loginInfoFileManager;
    }

    /**
     * Runs the main loop for the admin panel to get user's input on what tasks they want to do.
     */
    public void run() {
        int choice;
        do {
            choice = adminUi.getMenuInputChoice();

            switch (choice) {
            case 1:
                editStudentAccessPeriod();
                break;
            case 2:
                addStudent();
                break;
            case 3:
                addCourse();
                break;
            case 4:
                editCourse();
                break;
            case 5:
                checkAvailableSlot();
                break;
            case 6:
                printStudentsByIndexNumber();
                break;
            case 7:
                printStudentsByCourses();
                break;
            case 8:
                adminUi.printGoodBye();
                break;
            default:
                adminUi.printErrorMessage(ErrorMessage.ERROR_INPUT_CHOICE);
            }
        } while (choice != 8);
    }

    /**
     * Get user to input all the information required for a new course and add the new course.
     */
    private void addCourse() {
        Course course;
        while (true) {
            course = adminUi.getCourseToAdd();
            if (checkCourseCodeExists(course.getCourseCode())) {
                adminUi.printErrorMessage(ErrorMessage.COURSE_CODE_EXISTS);
                continue;
            }
            break;
        }
        storageManager.addCourse(course);
        adminUi.printCourses(storageManager.getAllCourses(), "Added " + course.toString() + "!");
    }

    /**
     * Get user input on which course to edit, what information about that course to edit and finally saving the
     * new information provided by the user.
     */
    private void editCourse() {
        String courseCode = adminUi.getCourseToEdit(storageManager.getAllCourses());
        Course course = storageManager.getCourse(courseCode);
        int choice = adminUi.getEditCourseChoice(course);

        switch (choice) {
        case 1:
            storageManager.setNewCourseCode(adminUi.getNewCourseCode(course), course.getCourseCode());
            break;
        case 2:
            storageManager.setNewCourseName(adminUi.getNewCourseName(course), course.getCourseCode());
            break;
        case 3:
            storageManager.setNewSchool(adminUi.getNewSchool(course), course.getCourseCode());
            break;
        case 4:
            storageManager.addIndexNumber(adminUi.getIndexNumber(true, course), course.getCourseCode());
            break;
        case 5:
            int index = adminUi.getIndexOfIndexNumberToEdit(course);
            int newMaxVacancy = adminUi.getNewMaxVacancy(course, index);
            try {
                storageManager.setNewMaxVacancy(course.getCourseCode(), index, newMaxVacancy);
            } catch (Exception e) {
                adminUi.printMessageWithDivider(e.getMessage());
            }
            break;
        }
    }

    /**
     * Get user to input which course they would like to check for the vacancy availability and print the vacancies
     * of each of the index numbers in that course.
     */
    private void checkAvailableSlot() {
        adminUi.checkVacancyOfIndexNumber(storageManager.getAllCourses());
    }

    /**
     * Get user to input which student that they want to change the access period of, then get the new access period
     * and update the access period for that student.
     */
    private void editStudentAccessPeriod() {
        ArrayList<Student> students = storageManager.getAllStudents();
        int index = adminUi.getStudentChoiceEditAccessTime(students);
        Student student = students.get(index);
        storageManager.setNewAccessTime(student.getUserId(),
                adminUi.getNewAccessTime(student));
        adminUi.printMessageWithDivider("Updated access period for " + student.getName() + "!");
    }

    /**
     * Get user to input the necessary information for a new student and add the new student.
     */
    private void addStudent() {
        LoginInfo loginInfoForStudent;
        while (true) {
            loginInfoForStudent = adminUi.getLoginInfoOfNewStudent();
            if (checkUserIdExists(loginInfoForStudent.getUserId())) {
                adminUi.printErrorMessage(ErrorMessage.USER_ID_EXISTS);
                continue;
            }
            break;
        }
        Student student = adminUi.getNewStudent(loginInfoForStudent.getUserId());
        storageManager.addStudent(student);
        loginInfoFileManager.addLoginInfoForNewStudent(loginInfoForStudent);
        adminUi.printStudents(storageManager.getAllStudents(), "Added " + student.getName() + "!");
    }

    /**
     * Checks if a user id already exists for an existing student.
     * @param userId The user id of the new student to be added.
     * @return true if userId of the new student to be added does not yet exist; false otherwise.
     */
    private boolean checkUserIdExists(String userId) {
        LoginInfoFileManager loginInfoFileManager = new LoginInfoFileManager();
        for (LoginInfo loginInfo: loginInfoFileManager.retrieveStudentLoginInfoList()) {
            if (loginInfo.getUserId().equals(userId)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Checks if a course code already exists for an existing course.
     * @param courseCode The course code of the new course to be added.
     * @return true if courseCode of the new course to be added does not yet exist; false otherwise.
     */
    private boolean checkCourseCodeExists(String courseCode) {
        if (storageManager.getCourse(courseCode) != null) {
            return true;
        }

        return false;
    }

    /**
     * Get user to input which course and prints all the students registered and in wait list for that course.
     */
    private void printStudentsByCourses() {
        ArrayList<Course> allCourses = storageManager.getAllCourses();
        int index = adminUi.getIndexOfCourseToPrint(allCourses);
        Course selectedCourse = allCourses.get(index);
        adminUi.printStudentsByCourse(selectedCourse);
    }

    /**
     * Gets user to input which course and index number of that course, then prints all the students registered and
     * in wait list for that index number.
     */
    private void printStudentsByIndexNumber() {
        ArrayList<Course> allCourses = storageManager.getAllCourses();
        int index = adminUi.getIndexOfCourseToPrint(allCourses);

        Course selectedCourse = allCourses.get(index);
        index = adminUi.getIndexOfIndexNumberToPrint(selectedCourse.getIndexNumbers());

        ArrayList<IndexNumber> allIndexNumbers = selectedCourse.getIndexNumbers();
        IndexNumber indexNumberSelected = allIndexNumbers.get(index);

        adminUi.printStudentsByIndexNumber(indexNumberSelected);
    }
}
