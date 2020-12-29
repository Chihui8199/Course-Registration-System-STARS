package model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

import exception.CourseRegisteredException;
import exception.ClashingRegisteredIndexNumberException;
import exception.MaxAuExceededException;
import exception.NoVacancyException;
import exception.NoVacancySwapException;
import exception.CourseInWaitListException;
import exception.SameIndexNumberSwapException;
import exception.ClashingWaitListedIndexNumberException;
import exception.PeerClashingRegisteredIndexNumberException;
import exception.PeerClashingWaitListedIndexNumberException;

/**
 * This class is responsible for modelling Student which details the attributes and methods of each
 * {@code Student} object part of the STARS Planner system.
 */
public class Student implements Serializable {

    /**
     * An integer variable declared as maxAU initialized with 21 representing the maximum number of AUs
     * a particular {@code Student} object can take in a semester
     */
    public static int maxAu = 21;

    /**
     * An String variable declared as name representing the name of the {@code Student} object
     */
    private String name;

    /**
     * An String variable declared as name representing the userId of the {@code Student} object
     */
    private String userId;

    /**
     * An String variable declared as matricNumber representing the matriculation number of the {@code Student} object
     */
    private String matricNumber;

    /**
     * An String variable declared as nationality representing the nationality of the {@code Student} object
     */
    private String nationality;

    /**
     * An String variable declared as emailAddress representing the email address of the {@code Student} object
     */
    private String emailAddress;

    /**
     * A {@code Gender} variable declared as gender representing the gender of the {@code Student} object
     * @see Gender
     */
    private Gender gender;

    /**
     * A {@code AccessTime} variable declared as accessTime representing the access timings of
     * the {@code Student} object
     * @see AccessTime
     */
    private AccessTime accessTime;

    /**
     * A {@code TimeTable} variable declared as timeTable representing the timetable of the {@code Student} object
     * @see TimeTable
     */
    private TimeTable timeTable;

    /**
     * The {@code INotification} interface declared as preferredNotification which enables email notification
     * functionalities
     * @see INotification
     */
    private INotification preferredNotification;

    /**
     * A constructor which constructs the {@code Student} object.
     * @param name The name of the {@code Student} object.
     * @param userId The userId of the {@code Student} object.
     * @param matricNumber The matricNumber of the {@code Student} object.
     * @param nationality The nationality of the {@code Student} object.
     * @param emailAddress The email address of the {@code Student} object.
     * @param gender The {@code Gender} of the {@code Student} object.
     * @param accessTime The {@code AccessTime} of the {@code Student} object.
     * Constructor initializers a {@code TimeTable} for that particular {@code Student} object
     * as well as creates an instance of the {@code EmailNotification} to enable email notification
     * features for the {@code Student} object
     * @see Gender
     * @see AccessTime
     * @see TimeTable
     * @see EmailNotification
     */
    public Student(String name, String userId, String matricNumber,
                   String nationality, String emailAddress, Gender gender, AccessTime accessTime) {
        this.name = name;
        this.userId = userId;
        this.matricNumber = matricNumber;
        this.nationality = nationality;
        this.emailAddress = emailAddress;
        this.gender = gender;
        this.accessTime = accessTime;
        timeTable = new TimeTable(this);
        preferredNotification = EmailNotification.getInstance();
    }

    /**
     * Returns the matric number of the {@code Student} object
     * @return The String representing the matric number of the {@code Student} object
     */
    public String getMatricNumber() {
        return matricNumber;
    }

    /**
     * Returns the user id of the {@code Student} object
     * @return The String representing the userId of the {@code Student} object
     */
    public String getUserId() {
        return  userId;
    }

    /**
     * Returns an ArrayList of all the registered course codes taken by the {@code Student} object
     * @return The ArrayList of String representing the list of courses registered by the {@code Student} object
     * in terms of the respective course codes of the {@code Student} object
     */
    public ArrayList<String> getRegisteredCourseCodes() {
        return timeTable.getRegisteredCourseCodes();
    }

    /**
     * Returns an ArrayList of all the course codes in the wait list by the {@code Student} object
     * @return The ArrayList of String representing the list of courses on the waitlist from the {@code Student} object
     * in terms of the respective course codes of the {@code Student} object
     */
    public ArrayList<String> getWaitListCourseCodes() {
        return timeTable.getWaitListCourseCodes();
    }

    /**
     * Returns a {@code TimeTable} object belonging to the {@code Student} object
     * @return The {@code TimeTable} representing the timetable of the {@code Student} object
     * @see TimeTable
     */
    protected TimeTable getTimeTable() {
        return timeTable;
    }

    /**
     * Returns the name of the {@code Student} object
     * @return The String representing the name of the {@code Student} object
     */
    public String getName() {
        return name;
    }

    /**
     * Returns a {@code AccessTime} object representing the access time of the {@code Student} object
     * @return The {@code AccessTime} representing the access time of the {@code Student} object
     */
    public AccessTime getAccessTime() {
        return accessTime;
    }

    /**
     *  Sets the {@code AccessTime} of the {@code Student} object
     *  @param accessTime The {@code AccessTime} of the {@code Student} object to be set to.
     *  @see AccessTime
     */
    public void setAccessTime(AccessTime accessTime) {
        this.accessTime = accessTime;
    }

    /**
     *  Checks if the localDateTime in the parameter matches with the {@code Student} object's access time
     *  @param localDateTime The {@code LocalDateTime} to be compared with the Student's access time
     *  @return The boolean value representing whether or not the localDateTime parameter matches with the
     *  {@code Student} object's access time
     */
    public boolean isWithinAccessTime(LocalDateTime localDateTime) {
        return accessTime.isWithinAccessTime(localDateTime);
    }

    /**
     * Returns the HashMap with the key being a String corresponding to a course code and the value is a {@code IndexNumber}
     * object in which the student is registered for that {@code IndexNumber} object for that course code
     * @return A HashMap, with the key being a String corresponding to a course code and the value is a {@code IndexNumber}
     * object in which the student is registered in that {@code IndexNumber} object for that course code
     * @see IndexNumber
     * @see TimeTable
     */
    public HashMap<String, IndexNumber> getRegisteredIndexNumbers() {
        return timeTable.getRegisteredIndexNumbers();
    }

    /**
     * Returns the HashMap with the key being a String corresponding to a course code and the value is a {@code IndexNumber}
     * object in which the student is in the wait list for that {@code IndexNumber} object for that course code
     * @return A HashMap, with the key being a String corresponding to a course code and the value is a {@code IndexNumber}
     * object in which the student is in the wait list for that {@code IndexNumber} object for that course code
     * IndexNumbers on wait list of the student's timetable
     * @see IndexNumber
     * @see TimeTable
     */
    public HashMap<String, IndexNumber> getWaitListIndexNumbers() {
        return timeTable.getWaitListIndexNumbers();
    }

    /**
     * Returns the number of AUs in which the student is registered for
     * @return The integer representing the total amount of AUs from the registered courses of the {@code Student}
     */
    public int getRegisteredAu() {
        return timeTable.getRegisteredAu();
    }

    /**
     * Returns the number of AUs in which the student is in the wait list for
     * @return The integer representing the total amount of AUs from the courses on wait list of the {@code Student}
     */
    public int getWaitListAu() {
        return timeTable.getWaitListAu();
    }

    /**
     * Returns the total number of AUs in which the student is in the wait list and registered for
     * @return The integer representing the total number of AUs from the courses on wait list
     * and the registered courses of the {@code Student}
     */
    public int getTotalAuInRegisteredAndWaitList() {
        return timeTable.getTotalAuInRegisteredAndWaitList();
    }

    /**
     * Registers a course for the {@code Student} and updating the {@code TimeTable}
     * @param courseCodeToBeAdded The String representing the course code of the course to be added
     * @param indexNumberToBeAdded The {@code IndexNumber} of the course to be added
     * @throws CourseRegisteredException if course is already registered
     * @throws CourseInWaitListException if course is already in wait list
     * @throws NoVacancyException if there are no more vacancies in the course
     * @throws ClashingRegisteredIndexNumberException if the new index number to be registered clashes with any of
     * the already registered index numbers
     * @throws ClashingWaitListedIndexNumberException if the new index number to be registered clashes with any of
     * the wait listed  index numbers
     * @throws MaxAuExceededException if Student has reached the maximum amount of AUs
     * @see IndexNumber
     * @see TimeTable
     */
    public void registerForCourse(String courseCodeToBeAdded, IndexNumber indexNumberToBeAdded)
            throws CourseRegisteredException, ClashingRegisteredIndexNumberException,
            NoVacancyException, CourseInWaitListException, ClashingWaitListedIndexNumberException,
            MaxAuExceededException {
        timeTable.registerForCourse(courseCodeToBeAdded, indexNumberToBeAdded);
    }

    /**
     * Swaps IndexNumber for a course with another {@code Student} and updating the {@code TimeTable}
     * @param courseCodeToBeSwapped The String representing the course code of the IndexNumber to be swapped with peer
     * @param peer The {@code Student} in which this instance of {@code Student} is swapping the IndexNumber with
     * @throws SameIndexNumberSwapException if the student and the peer has the same index number.
     * @throws ClashingWaitListedIndexNumberException if the peer's index number clashes with an index number that is in
     * the student's wait list.
     * @throws PeerClashingRegisteredIndexNumberException if the student's index number clashes with an index number
     * that is in the peer's registered list.
     * @throws ClashingRegisteredIndexNumberException if the peer's index number clashes with an index number that is in
     * the student's registered list.
     * @throws PeerClashingWaitListedIndexNumberException if the student's index number clashes with an index number
     * that is in the peer's wait list.
     * @see Student
     * @see TimeTable
     */
    public void swapIndexNumberWithPeer(String courseCodeToBeSwapped, Student peer)
            throws ClashingRegisteredIndexNumberException, SameIndexNumberSwapException,
            ClashingWaitListedIndexNumberException, PeerClashingRegisteredIndexNumberException,
            PeerClashingWaitListedIndexNumberException {
        timeTable.swapIndexNumberWithPeer(courseCodeToBeSwapped, peer);
    }

    /**
     * Swaps IndexNumber that is in {@code Student} object's {@code TimeTable} and updating the {@code TimeTable}
     * @param courseCodeToBeSwapped The String representing the original course code of the IndexNumber
     * to be swapped with
     * @param newIndexNumber The new {@code IndexNumber} to be swapped with the original
     * @throws ClashingRegisteredIndexNumberException if the new index number to be swapped clashes with any of
     * the already registered index numbers
     * @throws ClashingWaitListedIndexNumberException if the new index number to be swapped clashes with any of the
     * wait listed index numbers
     * @throws NoVacancySwapException if there are no more vacancies in the new index number to be swapped to
     * @throws SameIndexNumberSwapException if the pair of index numbers are the same
     * @see IndexNumber
     * @see TimeTable
     */
    public void swapIndexNumber(String courseCodeToBeSwapped, IndexNumber newIndexNumber)
            throws ClashingRegisteredIndexNumberException, NoVacancySwapException, SameIndexNumberSwapException, ClashingWaitListedIndexNumberException {
        timeTable.swapIndexNumber(courseCodeToBeSwapped, newIndexNumber);
    }

    /**
     * Adds a course into the wait list for the {@code Student} and updating the {@code TimeTable}
     * @param courseCodeToBeAdded The String representing the course code of the course to be added
     * @param indexNumberToBeAdded The {@code IndexNumber} of the course to be added
     * @see IndexNumber
     * @see TimeTable
     */
    public void addCourseToWaitList(String courseCodeToBeAdded, IndexNumber indexNumberToBeAdded) {
        timeTable.addCourseToWaitList(courseCodeToBeAdded, indexNumberToBeAdded);
    }

    /**
     * Drops a course into the wait list for the {@code Student} and updating the {@code TimeTable}
     * @param courseCodeToBeDropped The String representing the course code of the course to be dropped
     * @param indexNumberToBeDropped The {@code IndexNumber} of the course to be dropped
     * @see IndexNumber
     * @see TimeTable
     */
    public void dropCourseFromWaitList(String courseCodeToBeDropped, IndexNumber indexNumberToBeDropped) {
        timeTable.dropCourseFromWaitList(courseCodeToBeDropped, indexNumberToBeDropped);
    }

    /**
     * Drops a {@code Course} from a {@code Student} and registers the front most {@code Student} in the
     * wait list for that particular {@code Course} IndexNumber and updating the {@code TimeTable}. The exceptions can
     * be ignored since the exceptions should have been taken care of when the index number is added into the student's
     * wait list.
     * @param course The {@code Course} to be dropped
     * @param indexNumberToBeDropped The {@code IndexNumber} of the course to be dropped
     * @see IndexNumber
     * @see Course
     * @see TimeTable
     */
    public void dropCourseAndRegisterNextStudentInWaitList(Course course, IndexNumber indexNumberToBeDropped)
            throws CourseInWaitListException, ClashingRegisteredIndexNumberException,
            CourseRegisteredException, NoVacancyException, ClashingWaitListedIndexNumberException, MaxAuExceededException {
        timeTable.dropCourseAndRegisterNextStudentInWaitList(course, indexNumberToBeDropped);
    }

    /**
     * Sends an email containing a message to the email address of the {@code Student}
     * @param messageToSend The String which is the message to be sent to the student.
     */
    protected void notify(String messageToSend) {
        preferredNotification.send(emailAddress, "Dear " + name + ",\n\n" + messageToSend);
    }

    /**
     * Converts the {@code Student} name, gender, and nationality into a single String
     * @return The String representing the converted {@code Student} name, gender, and nationality
     */
    @Override
    public String toString() {
        return "Name: " + name + ", Gender: " + gender + ", Nationality: " + nationality;
    }
}
