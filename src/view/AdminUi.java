package view;

import errormessage.ErrorMessage;
import exception.InvalidLessonTypeException;
import exception.InvalidAccessTimeException;
import model.AccessTime;
import model.AccountType;
import model.Course;
import model.Gender;
import model.IndexNumber;
import model.Lesson;
import model.LessonType;
import model.LoginInfo;
import model.School;
import model.Student;

import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class is responsible for the logic of getting input and output from the user for tasks related to the admin
 * panel.
 */
public class AdminUi extends Ui {
    /**
     * A String array containing all the options for the user to choose in the admin panel.
     */
    private final String[] ADMIN_MENU_OPTIONS = {
            "Welcome to the Admin panel!",
            "1. Edit student access period.",
            "2. Add a student.",
            "3. Add a course.",
            "4. Edit a course.",
            "5. Check available slot for an index number.",
            "6. Print student list by index number.",
            "7. Print student list by course.",
            "8. Quit."
    };

    /**
     * A String array containing all the options for edit course for the user to choose.
     * @param course The {@code Course} object that the user wants to edit for.
     * @return A String array containing all the options.
     */
    private String[] getEditCourseOptions(Course course) {
        return new String[] {
                "Editing " + course.toString(),
                "1. Edit course code.",
                "2. Edit course name.",
                "3. Edit school.",
                "4. Add index number.",
                "5. Edit index number vacancy."
        };
    }

    /**
     * Prints a short description of all the courses available in the system.
     * @param courses An ArrayList of all the {@code Course}.
     * @param message The message to be displayed after printing all the courses description.
     */
    public void printCourses(ArrayList<Course> courses, String message) {
        printMessageWithDivider(getCoursesDescription(courses, message));
    }

    /**
     * Prints a short description of all the students in the system.
     * @param students An ArrayList of all the {@code Student}.
     * @param message The message to be displayed after printing all the students description.
     */
    public void printStudents(ArrayList<Student> students, String message) {
        printMessageWithDivider(getStudentsDescription(students, message));
    }

    /**
     * Get user to input which index number to check the students that are registered for. The method will always get
     * the user to input a valid integer from 1 to the size of indexNumbers and return an integer from 0 to size of
     * indexNumbers - 1.
     * @param indexNumbers An ArrayList of {@code IndexNumber}.
     * @return An integer representing the index of the indexNumbers which the user has chosen.
     */
    public int getIndexOfIndexNumberToPrint(ArrayList<IndexNumber> indexNumbers) {
        int index;
        while (true) {
            index = getInputChoice("Which index number would you like to print the students?",
                    getIndexNumbersDescription(indexNumbers, "Here are the list of index numbers:"));
            if (index < 1 || index > indexNumbers.size()) {
                printErrorMessage(ErrorMessage.ERROR_INPUT_CHOICE);
                continue;
            }
            break;
        }
        return index - 1;
    }

    /**
     * Prints all the registered and wait list students for a particular index number.
     * @param indexNumber The {@code IndexNumber} to print all the students that are registered or in wait list for.
     * @see IndexNumber
     */
    public void printStudentsByIndexNumber(IndexNumber indexNumber) {
        String s = "Course: " + indexNumber.getCourse().toString() + "\n\n";
        s += indexNumber.toString() + "\n";
        s += "Registered Students:\n";
        for (Student student: indexNumber.getRegisteredStudents()) {
            s += student.toString() + "\n";
        }
        s += "\n";

        s += "Wait List Students:\n";
        for (Student student: indexNumber.getWaitListStudents()) {
            s += student.toString() + "\n";
        }
        s += "\n";
        printMessageWithDivider(s);
    }

    /**
     * Prints all the courses and get the user to input which course they would like to see all the students that
     * are registered or in the wait list for. The method will always get user to input a valid integer from 1 to size
     * of the courses and return a integer from 0 to size of courses - 1.
     * @param courses An ArrayList of all the {@code Course} in the system.
     * @return The integer representing the index of the courses which the user has chosen.
     */
    public int getIndexOfCourseToPrint(ArrayList<Course> courses) {
        int index;
        while (true) {
            String[] coursesDescription = getCoursesDescription(courses, "Here are the list of courses:");
            index = getInputChoice("Which course would you like to print the students?",
                    coursesDescription);
            if (index < 1 || index > courses.size()) {
                printErrorMessage(ErrorMessage.ERROR_INPUT_CHOICE);
                continue;
            }
            break;
        }
        return index - 1;
    }

    /**
     * Prints all the students registered and in wait list for a particular course.
     * @param course The {@code Course} for which to print all the students registered and in wait list for.
     * @see Course
     */
    public void printStudentsByCourse(Course course) {
        String s = "Course: " + course.toString() + "\n\n";
        s += "Registered Students:\n";
        for (Student student: course.getRegisteredStudents()) {
            s += student.toString() + "\n";
        }
        s += "\n";

        s += "Wait List Students:\n";
        for (Student student: course.getWaitListStudents()) {
            s += student.toString() + "\n";
        }
        s += "\n";
        printMessageWithDivider(s);
    }

    /**
     * Displays all the options for admin panel and get the user to input their choice.
     * @return An integer representing the user's input choice.
     */
    public int getMenuInputChoice() {
        return getInputChoice("Enter your choice:", ADMIN_MENU_OPTIONS);
    }

    /**
     * Displays all the students in the system and ask the user which student he/she would like to edit the access
     * time for. The method will always get the user to input a valid integer from 1 to size of students and return an
     * integer from 0 to size of students - 1.
     * @param students An ArrayList of all the {@code Student}.
     * @return The integer representing the index of the students which the user has chosen.
     */
    public int getStudentChoiceEditAccessTime(ArrayList<Student> students) {
        while (true) {
            int index = getInputChoice("Which student would you like to edit the access time?",
                    getStudentsDescription(students, "Here are the list of students"));
            if (index < 1 || index > students.size()) {
                printErrorMessage(ErrorMessage.ERROR_INPUT_CHOICE);
                continue;
            }
            return index - 1;
        }
    }

    /**
     * Get the user to input all the necessary information for a new student.
     * @param userId The user id of the new student.
     * @return A {@code Student} object representing the new student to be added.
     * @see Student
     */
    public Student getNewStudent(String userId) {
        Scanner sc = new Scanner(System.in);
        print("Enter the name:");
        String name = sc.nextLine();
        print("Enter the matric number:");
        String matricNumber = sc.next();
        print("Enter the nationality:");
        String nationality = sc.next();
        print("Enter the email address:");
        String emailAddress = sc.next();
        AccessTime accessTime = getAccessTime(null,
                "Enter the start date and time for the access period in "
                        + "\"dd-MM-yyyy HH:mm\" format (e.g. 10-12-2020 23:59):",
                "Enter the end date and time for the access period in "
                        + "\"dd-MM-yyyy HH:mm\" format (e.g. 10-12-2020 23:59):");
        Gender gender;
        getGenderLoop: while (true) {
            print("Enter the gender (MALE, FEMALE):");
            String genderString = sc.next();
            switch (genderString) {
            case "FEMALE":
                gender = Gender.FEMALE;
                break getGenderLoop;
            case "MALE":
                gender = Gender.MALE;
                break getGenderLoop;
            default:
                printErrorMessage(ErrorMessage.INVALID_GENDER_TYPE);
            }
        }

        return new Student(name, userId, matricNumber, nationality, emailAddress, gender, accessTime);
    }

    /**
     * Get the new login information of the new student.
     * @return A {@code LoginInfo} object containing the login information of the new student.
     * @see LoginInfo
     */
    public LoginInfo getLoginInfoOfNewStudent() {
        LoginInfo loginInfo;
        while (true) {
            loginInfo = getLoginInfo(AccountType.STUDENT);
            Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
            Matcher m = p.matcher(loginInfo.getUserId());
            if (m.find()) {
                printErrorMessage("Please enter a valid user id! (no special characters e.g. *, !, {)");
                continue;
            }
            break;
        }
        return loginInfo;
    }

    /**
     * Get the user to input the start time and end time of the access period of a student. The method will get user
     * to input a valid {@code LocalDateTime} object for both start and end time.
     * @param currentAccessPeriodMessage A message to print the current access period of a student, if any.
     * @param newStartMessage The message to print before getting the user to input the start date and time of the
     *                        access period.
     * @param newEndMessage The message to print before getting the user to input the end date and time of the access
     *                      period.
     * @return An {@code AccessTime} object containing the start and end time for the access period.
     * @see AccessTime
     */
    public AccessTime getAccessTime(String currentAccessPeriodMessage,
                                    String newStartMessage, String newEndMessage) {
        while (true) {
            if (currentAccessPeriodMessage != null) {
                printMessageWithDivider(currentAccessPeriodMessage);
            }
            LocalDateTime newStartDateTime = getDateTime(newStartMessage);
            LocalDateTime newEndDateTime = getDateTime(newEndMessage);
            try {
                AccessTime newAccessTime = new AccessTime(newStartDateTime, newEndDateTime);
                return newAccessTime;
            } catch (InvalidAccessTimeException e) {
                printErrorMessage(e.getMessage());
            }
        }
    }

    /**
     * Get the new access time for a particular student.
     * @param student The {@code Student} object which the access time is to be changed.
     * @return An {@code AccessTime} object containing the new start and end time for student.
     * @see Student
     * @see AccessTime
     */
    public AccessTime getNewAccessTime(Student student) {
        return getAccessTime("The current access period for " + student.getName()
                        + " is:\n" + student.getAccessTime().toString(),
                "Enter the new start date and time in "
                        + "\"dd-MM-yyyy HH:mm\" format (e.g. 10-12-2020 23:59):",
                "Enter the new end date and time in "
                        + "\"dd-MM-yyyy HH:mm\" format (e.g. 10-12-2020 23:59):");
    }

    /**
     * Displays the list of courses and ask the user to input which course he/she wants to edit. The method will
     * always get the user to input a valid integer from 1 to size of courses.
     * @param courses An ArrayList of all the {@code Course}.
     * @return A String containing the course code of the course which the user wants to edit.
     */
    public String getCourseToEdit(ArrayList<Course> courses) {
        while (true) {
            try {
                int index = getInputChoice("Which course do you want to edit?",
                        getCoursesDescription(courses, "Here are your list of courses:"));
                if (index < 1 || index > courses.size()) {
                    printErrorMessage(ErrorMessage.ERROR_INPUT_CHOICE);
                    continue;
                }

                return courses.get(index - 1).getCourseCode();
            }  catch (IndexOutOfBoundsException e) {
                printErrorMessage(ErrorMessage.ERROR_INPUT_CHOICE);
            }
        }
    }

    /**
     * Displays all the options for edit course and get the user to input a choice.
     * @param course The {@code Course} that is to be edited.
     * @return The integer representing the choice of the user.
     */
    public int getEditCourseChoice(Course course) {
        while (true) {
            int choice = getInputChoice("Enter your choice:", getEditCourseOptions(course));
            if (choice < 1 || choice > 5) {
                printErrorMessage(ErrorMessage.ERROR_INPUT_CHOICE);
                continue;
            }

            return choice;
        }
    }

    /**
     * Get the user to enter the course code for a new course.
     * @return A String containing the course code for the new course.
     */
    private String getCourseCode() {
        return getCourseCode("Enter course code:");
    }

    /**
     * Get the user to enter the new course code for an existing course.
     * @param course The existing {@code Course} for which the course code is to be changed.
     * @return A String containing the new course code for the existing course.
     */
    public String getNewCourseCode(Course course) {
        return getCourseCode("Enter the new course code (currently: " + course.getCourseCode() + "):");
    }

    /**
     * Get the user to input a course code. The method will always ensure that the user does not input special characters,
     * e.g. *, !, {, ).
     * @param message The message to be displayed before asking the user for the course code.
     * @return A String containing the course code.
     */
    private String getCourseCode(String message) {
        Scanner sc = new Scanner(System.in);
        String courseCode;

        while (true) {
            print(message);
            courseCode = sc.next();

            Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
            Matcher m = p.matcher(courseCode);
            if (m.find()) {
                printErrorMessage(ErrorMessage.INVALID_COURSE_CODE);
                continue;
            }
            break;
        }

        return courseCode;
    }

    /**
     * Get user to input the course name for a new course.
     * @return A String containing the course name for the new course.
     */
    private String getCourseName() {
        return getCourseName("Enter course name:");
    }

    /**
     * Get user to input a new course name for an existing course.
     * @param course The existing {@code Course} for which the course name is to be changed.
     * @return A String containing the new course name for the existing course.
     */
    public String getNewCourseName(Course course) {
        return getCourseName("Enter the new course name (currently: " + course.getCourseName() + "):");
    }

    /**
     * Get the user to input a course name.
     * @param message The message to be displayed before asking the user for the course name.
     * @return A String containing the course name.
     */
    private String getCourseName(String message) {
        Scanner sc = new Scanner(System.in);
        print(message);
        String courseName = sc.nextLine();

        return courseName;
    }

    /**
     * Get the user to input the school that is offering the new course.
     * @return A {@code School} representing the school that is offering the new course.
     * @see School
     */
    private School getSchool() {
        return getSchool("Enter the school offering the course (SCSE, SSS):");
    }

    /**
     * Get the user to input the new school that is offering the existing course.
     * @param course The existing {@code Course} for which the school is to be changed.
     * @return A {@code School} representing the new school that is offering the existing course.
     * @see School
     */
    public School getNewSchool(Course course) {
        return getSchool("Enter the school offering the course (SCSE, SSS) (Currently: "
                + course.getSchool().toString() + "):");
    }

    /**
     * Get the user to input a school. This method will ensure that the user inputs a valid school, based on the values
     * in the {@code School} enumeration.
     * @param message The message to be displayed before getting the user to input a school.
     * @return A {@code School} representing the school offering a particular course.
     * @see School
     */
    private School getSchool(String message) {
        Scanner sc = new Scanner(System.in);
        School school;
        getSchoolLoop: while(true) {
            print(message);
            String schoolString = sc.next();
            switch (schoolString) {
            case "SCSE":
                school = School.SCSE;
                break getSchoolLoop;
            case "SSS":
                school = School.SSS;
                break getSchoolLoop;
            default:
                printErrorMessage(ErrorMessage.INVALID_SCHOOL);
                break;
            }
        }

        return school;
    }

    /**
     * Get user to input the number of AU of a course. The method will always return an integer more than 0.
     * @return An integer representing the number of AU of a course.
     */
    private int getAuOfCourse() {
        int au;
        while (true) {
            print("Enter the number of AU of this course:");
            try {
                Scanner sc = new Scanner(System.in);
                au = sc.nextInt();
                if (au <= 0) {
                    printErrorMessage(ErrorMessage.INVALID_AU);
                    continue;
                }
            } catch (InputMismatchException e) {
                printErrorMessage(ErrorMessage.INVALID_AU);
                continue;
            }
            break;
        }
        return au;
    }

    /**
     * Get user to input all the information required for a new course.
     * @return A {@code Course} object containing all the information for the new course.
     * @see Course
     */
    public Course getCourseToAdd() {
        String courseName = getCourseName();
        String courseCode = getCourseCode();
        School school = getSchool();
        int au = getAuOfCourse();

        Course course = new Course(courseName, courseCode, school, au);
        course.setIndexNumbers(getIndexNumbers(course));

        return course;
    }

    /**
     * Get the user to input which index number to edit. The method will always get a valid integer from 1 to number of
     * index numbers in a particular course and return an integer from 0 to the number of index numbers in the particular
     * course - 1.
     * @param course The {@code Course} for which a particular index number in it is to be edited.
     * @return The integer representing the index of the index numbers in the course to be edited.
     */
    public int getIndexOfIndexNumberToEdit(Course course) {
        String[] messages = new String[course.getIndexNumbers().size() + 1];
        messages[0] = "Index numbers for: " + course.toString();
        ArrayList<IndexNumber> allIndexNumbers = course.getIndexNumbers();
        for (int i = 1; i < messages.length; i++) {
            messages[i] = (i) + ". Index number: " + allIndexNumbers.get(i-1).getId()
                    + " (Current Max Vacancy: "
                    + allIndexNumbers.get(i-1).getMaxVacancy()
                    + ")";
        }

        int index;
        while (true) {
            index = getInputChoice("Which one would you like to change?", messages);
            if (index < 1 || index > allIndexNumbers.size()) {
                printErrorMessage(ErrorMessage.ERROR_INPUT_CHOICE);
                continue;
            }

            break;
        }

        return index - 1;
    }

    /**
     * Get the new maximum vacancy of of a particular index number in an existing course.
     * @param course The {@code Course} for which a particular index number in it has a new maximum vacancy.
     * @param index The index of the index numbers in course for which has a new maximum vacancy.
     * @return The integer representing the new maximum vacancy of the particular index number in an existing course.
     */
    public int getNewMaxVacancy(Course course, int index) {
        return getMaxVacancy("Enter the new maximum vacancy for index "
                + course.getIndexNumbers().get(index).getId()
                + " (Currently: "
                + course.getIndexNumbers().get(index).getMaxVacancy() + "):");
    }

    /**
     * Get the maximum vacancy for a course. This method will always return a valid integer more than 0.
     * @param message The message to be displayed before asking the user for input.
     * @return The integer representing the maximum vacancy for an index number.
     */
    private int getMaxVacancy(String message) {
        while (true) {
            try {
                Scanner sc = new Scanner(System.in);
                print(message);
                int maxVacancy = sc.nextInt();
                if (maxVacancy <= 0) {
                    printErrorMessage(ErrorMessage.INVALID_MAX_VACANCY);
                    continue;
                }
                return maxVacancy;
            } catch (InputMismatchException e) {
                printErrorMessage(ErrorMessage.INVALID_MAX_VACANCY);
            }
        }
    }

    /**
     * Get the user to input all the information required for an index number.
     * @param isAdd true if adding an index number to an existing course; false if adding an index number to a new course.
     * @param course The {@code Course} for which the index number input is associated with.
     * @return The {@code IndexNumber} object containing all the information for the index number.
     * @see IndexNumber
     */
    public IndexNumber getIndexNumber(boolean isAdd, Course course) {
        Scanner sc = new Scanner(System.in);
        String input;

        while (true) {
            try {
                if (isAdd) {
                    print("Enter the new index number to add:");
                }
                input = sc.next();
                if (input.equals("Q")) {
                    return null;
                }
                int id = Integer.parseInt(input);

                int maxVacancy;
                while (true) {
                    maxVacancy = getMaxVacancy("Enter the maximum vacancy of this index:");
                    if (maxVacancy <= 0) {
                        printErrorMessage(ErrorMessage.INVALID_MAX_VACANCY);
                        continue;
                    }
                    break;
                }

                ArrayList<Lesson> lessons = getLessons(id);

                return new IndexNumber(id, course, lessons, maxVacancy);
            } catch (NumberFormatException e) {
                printErrorMessage(ErrorMessage.INVALID_INDEX_NUMBER);
            }
        }
    }

    /**
     * A loop to get the user to input all the index numbers in a course
     * @param course The {@code Course} for which the index number input is associated with.
     * @return An ArrayList of {@code IndexNumber}.
     * @see IndexNumber
     */
    private ArrayList<IndexNumber> getIndexNumbers(Course course) {
        ArrayList<IndexNumber> indexNumbers = new ArrayList<>();
        int counter = 1;

        while (true) {
            try {
                print("Enter the " + ordinal(counter) + " index number (Enter Q if you are done):");

                IndexNumber indexNumber = getIndexNumber(false, course);
                if (indexNumber == null) {
                    break;
                }

                indexNumbers.add(indexNumber);

                counter++;
            } catch (NumberFormatException e) {
                printErrorMessage(ErrorMessage.INVALID_INDEX_NUMBER);
            }
        }

        return indexNumbers;
    }

    /**
     * Get the user to input a date and time. The method will ensure that the user input in the "dd-MM-yyyy HH:mm"
     * format and returns a {@code LocalDateTime} object.
     * @param message The message to be displayed before asking the user for input.
     * @return A {@code LocalDateTime} object representing the date and time input by the user.
     * @see LocalDateTime
     */
    private LocalDateTime getDateTime(String message) {
        LocalDateTime dateTime;
        while (true) {
            print(message);
            Scanner sc = new Scanner(System.in);
            String dateTimeString = sc.nextLine().trim();
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
                dateTime = LocalDateTime.parse(dateTimeString, formatter);
                break;
            } catch (DateTimeException e) {
                printErrorMessage(ErrorMessage.INVALID_DATE_TIME);
            }
        }

        return dateTime;
    }

    /**
     * Get the user to input a time.
     * @param message The message to be displayed before asking the user for input.
     * @return A {@code LocalTime} object representing the time which the user input.
     */
    private LocalTime getTime(String message) {
        LocalTime time;
        while (true) {
            print(message);
            Scanner sc = new Scanner(System.in);
            String startTimeString = sc.next();
            try {
                time = LocalTime.of(Integer.parseInt(startTimeString.substring(0, 2)),
                        Integer.parseInt(startTimeString.substring(2)));
                break;
            } catch (NumberFormatException | DateTimeException | StringIndexOutOfBoundsException e) {
                printErrorMessage(ErrorMessage.INVALID_TIME);
            }
        }

        return time;
    }

    /**
     * Get the user to input all the lessons for an index number.
     * @param id The id of the index number for which the lessons are associated with.
     * @return An ArrayList of {@code Lesson}.
     * @see Lesson
     */
    private ArrayList<Lesson> getLessons(int id) {
        ArrayList<Lesson> lessons = new ArrayList<>();
        int counter = 1;
        getLessonLoop: while (true) {
            print(ordinal(counter) + " Lesson for index " + id);

            LessonType lessonType;
            getLessonTypeLoop: while(true) {
                Scanner sc = new Scanner(System.in);
                print("Enter the type of lesson " + LessonType.getAllLessonType()
                        + " (Enter Q if you are done):");

                String lessonTypeString = sc.next();
                if (lessonTypeString.equals("Q")) {
                    break getLessonLoop;
                }
                try {
                    lessonType = LessonType.getLessonType(lessonTypeString);
                    break getLessonTypeLoop;
                } catch (InvalidLessonTypeException e) {
                    printErrorMessage(e.getMessage());
                }
            }

            DayOfWeek dayOfWeek;
            while (true) {
                Scanner sc = new Scanner(System.in);
                print("1. Monday\n" + "2. Tuesday\n" +
                        "3. Wednesday\n" + "4. Thursday\n" +
                        "5. Friday\n" + "6. Saturday\n" + "7. Sunday");
                print("Enter the day of the week of the lesson (e.g. 1 for Monday, 2 for Tuesday):");

                try {
                    int day = sc.nextInt();
                    if (day < 1 || day > 7) {
                        printErrorMessage(ErrorMessage.INVALID_DAY_OF_WEEK);
                        continue;
                    }

                    dayOfWeek = DayOfWeek.of(day);
                    break;
                } catch (InputMismatchException e) {
                    printErrorMessage(ErrorMessage.INVALID_DAY_OF_WEEK);
                }
            }

            LocalTime startTime = getTime("Enter the start time (in 24 hours format e.g. 0830):");
            LocalTime endTime = getTime("Enter the end time (in 24 hours format e.g. 2341):");

            counter++;
            lessons.add(new Lesson(lessonType, dayOfWeek, startTime, endTime));
        }

        return lessons;
    }

}
