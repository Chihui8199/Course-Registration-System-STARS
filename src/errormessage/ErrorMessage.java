package errormessage;

/**
 * This class contains all the error messages displayed in the program.
 */
public class ErrorMessage {
    // Common error message
    public static final String ERROR_INPUT_CHOICE = "Invalid input choice, please input a valid input choice!";

    // Error messages related to the login panel
    public static final String WRONG_LOGIN_INFO = "Wrong UserID and/or Password. Please try again!";
    public static final String WRONG_ACCESS_PERIOD = "You are not authorised to access STARS! Your access period is:";

    // Error messages related to the admin panel
    public static final String INVALID_SCHOOL = "Invalid school! Please try again.";
    public static final String INVALID_INDEX_NUMBER = "Invalid index number! Please try again.";
    public static final String INVALID_LESSON_TYPE = "Invalid lesson type! Please try again.";
    public static final String INVALID_TIME = "Invalid time! Please try again.";
    public static final String INVALID_DATE_TIME = "Invalid date and time! Please try again.";
    public static final String INVALID_DAY_OF_WEEK = "Invalid day of week! Please try again.";
    public static final String INVALID_MAX_VACANCY = "Invalid maximum vacancy! Please try again.";
    public static final String INVALID_NEW_MAX_VACANCY = "The new maximum vacancy for this index is less than"
            + "the number of registered students.\n" + "Please enter a new value.";
    public static final String INVALID_ACCESS_TIME = "Invalid Access Time! The start time is later than end time!";
    public static final String INVALID_GENDER_TYPE = "Invalid gender! Please try again!";
    public static final String USER_ID_EXISTS = "User id already exists! Please enter a new user id.";
    public static final String COURSE_CODE_EXISTS = "Course code already exists! Please enter a new course code.";
    public static final String INVALID_COURSE_CODE = "Please enter a valid course code! (no special characters e.g. *, !, {)";

    // Error messages related to the student panel
    public static final String NO_REGISTERED_COURSES = "You have no registered courses!";
    public static final String NO_WAITLIST_COURSES = "You have no courses on the wait list!";
    public static final String COURSE_ALREADY_REGISTERED = "You are already registered for the course!";
    public static final String CLASHING_REGISTERED_INDEX_NUMBER = "The index number you have selected clashes with the " +
            "timetable of the courses you are registered for! Please try again.";
    public static final String CLASHING_WAITLISTED_INDEX_NUMBER = "The index number you have selected clashes with the " +
            "timetable of the courses you are waitlisted for! Please try again.";
    public static final String NO_VACANCY = "There is no vacancy for that index number! You will be placed on the " +
            "waitlist.";
    public static final String NO_VACANCY_SWAP = "There is no vacancy for that index number! Please try again.";
    public static final String COURSE_ALREADY_IN_WAITLIST = "You have already added this course to your waitlist! " +
            "To change index number, please drop the course first.";
    public static final String SAME_INDEX_SWAP = "You have selected the same index number that you are currently " +
            "registered for. Please try again.";
    public static final String PEER_DOES_NOT_TAKE_COURSE = "Your peer does not take this course. Please try again.";
    public static final String PEER_REGISTERED_CLASHING_INDEX = "Your peer has registered course that clashes with your index. Please try again.";
    public static final String PEER_WAITLISTED_CLASHING_INDEX ="Your peer has wait listed course that clashes with your index. Please try again.";
    public static final String INVALID_CONFIRM_SWAP_PEER = "Invalid input. Please try again.";
    public static final String MAX_AU_EXCEEDED = "You have exceeded the maximum AU allowed! Please drop your " +
            "\nregistered courses or wait list courses before trying again.";
    public static final String INVALID_AU = "Invalid AU input. Please try again.";
}
