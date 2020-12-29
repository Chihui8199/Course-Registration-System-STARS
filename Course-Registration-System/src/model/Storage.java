package model;

import java.io.Serializable;
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
 * This class is responsible for storing and retrieving all the {@code Student} objects and {@code Course} objects in the program.
 */
public class Storage implements Serializable {
    /**
     * A HashMap with the key being the String representation of the user id of a {@code Student} object and the value is
     * the {@code Student} object.
     * @see Student
     */
    private HashMap<String, Student> students;

    /**
     * A HashMap with the key being the String representation of the course code of a {@code Course} object and the value is
     * the {@code Course} object.
     * @see Course
     */
    private HashMap<String, Course> courses;

    /**
     * Constructs a new Storage object.
     */
    public Storage() {
        students = new HashMap<>();
        courses = new HashMap<>();
    }

    /**
     * Add a new {@code Student} object.
     * @param student The new {@code Student} object to be added.
     * @see Student
     */
    public void addStudent(Student student) {
        students.put(student.getUserId(), student);
    }

    /**
     * Returns a {@code Student} object given its user id.
     * @param userId The user id of the {@code Student} object to be retrieved.
     * @return {@code Student} object with the userId specified.
     */
    public Student getStudent(String userId) {
        return students.get(userId);
    }

    /**
     * Add a new {@code Course} object.
     * @param course The new {@code Course} object to be added.
     * @see Course
     */
    public void addCourse(Course course) {
        courses.put(course.getCourseCode(), course);
    }

    /**
     * Removes a {@code Course} object.
     * @param course The {@code Course} object to be removed.
     * @see Course
     */
    public void removeCourse(Course course) {
        courses.remove(course.getCourseCode(), course);
    }

    /**
     * Add a new {@code IndexNumber} object to an existing course.
     * @param indexNumber The new {@code IndexNumber} object to be added.
     * @param courseCode The course code of the existing course for which the indexNumber is to be added to.
     * @see IndexNumber
     */
    public void addIndexNumber(IndexNumber indexNumber, String courseCode) {
        getCourse(courseCode).addIndexNumber(indexNumber);
    }

    /**
     * Returns a {@code Course} object given its course code.
     * @param courseCode The course code of the {@code Course} object to be retrieved.
     * @return The {@code Course} object with the specified courseCode.
     */
    public Course getCourse(String courseCode) {
        return courses.get(courseCode);
    }

    /**
     * Set a new access time for a particular student.
     * @param userId The user id of the student for which the new access time is to be set for.
     * @param accessTime The new {@code AccessTime} object containing the new access time for the student.
     */
    public void setAccessTime(String userId, AccessTime accessTime) {
        getStudent(userId).setAccessTime(accessTime);
    }

    /**
     * Register a student for a course and index number.
     * @param userId The user id of the student to register the course and index number for.
     * @param courseCodeToBeAdded The course code of the course to register the student for.
     * @param indexNumberToBeAdded The {@code IndexNumber} object to register the student for.
     * @throws CourseRegisteredException if the student is already taking the course that is to be registered for.
     * @throws ClashingRegisteredIndexNumberException if the student has another index number that clashes with the index number
     * to be registered for.
     * @throws NoVacancyException if the index number to be registered for has no vacancy.
     * @throws CourseInWaitListException if the course to be registered for is already in the student's wait list.
     * @throws ClashingWaitListedIndexNumberException if the index number to be registered for clashes with another index number
     * that is already in the student's wait list.
     * @throws MaxAuExceededException if the AU of the course to be registered plus the current AU taken by the student (both registered
     * and in wait list) exceeds the maximum number of AU allowed to be taken by the student.
     */
    public void registerForCourse(String userId, String courseCodeToBeAdded, IndexNumber indexNumberToBeAdded)
            throws CourseRegisteredException, ClashingRegisteredIndexNumberException, NoVacancyException,
            CourseInWaitListException, ClashingWaitListedIndexNumberException, MaxAuExceededException {
        Student student = getStudent(userId);
        student.registerForCourse(courseCodeToBeAdded, indexNumberToBeAdded);
    }

    /**
     * Swap index number for a course that a student is registered for.
     * @param userId The user id of the student for which the index number will be swapped.
     * @param courseCodeToBeSwapped The course code of the course for which the index number will swapped for the student.
     * @param newIndexNumber The {@code IndexNumber} object that represents the new index number that the student wants
     *                       to swap to.
     * @throws ClashingRegisteredIndexNumberException if the new index number to be swapped clashes with a registered
     * index number of the student.
     * @throws NoVacancySwapException if the new index number to be swapped has no vacancies.
     * @throws SameIndexNumberSwapException if the new index number is the same as current index number of the student.
     * @throws ClashingWaitListedIndexNumberException if the new index number to be swapped clashes with an index number
     * that is in the wait list of the student.
     */
    public void swapIndexNumber(String userId, String courseCodeToBeSwapped, IndexNumber newIndexNumber)
            throws ClashingRegisteredIndexNumberException, NoVacancySwapException, SameIndexNumberSwapException, ClashingWaitListedIndexNumberException {
        Student student = getStudent(userId);
        student.swapIndexNumber(courseCodeToBeSwapped, newIndexNumber);
    }

    /**
     * Swap an index number with another peer.
     * @param userId The user id of the student initiating the swap.
     * @param peerUserId The user id of the peer that is swapping with the student.
     * @param courseCodeToBeSwapped The course code of the course that is to be swapped by the student and the peer.
     * @throws SameIndexNumberSwapException if the student and the peer has the same index number.
     * @throws ClashingWaitListedIndexNumberException if the peer's index number clashes with an index number that is in
     * the student's wait list.
     * @throws PeerClashingRegisteredIndexNumberException if the student's index number clashes with an index number
     * that is in the peer's registered list.
     * @throws ClashingRegisteredIndexNumberException if the peer's index number clashes with an index number that is in
     * the student's registered list.
     * @throws PeerClashingWaitListedIndexNumberException if the student's index number clashes with an index number
     * that is in the peer's wait list.
     */
    public void swapIndexWithPeer(String userId, String peerUserId, String courseCodeToBeSwapped)
            throws PeerClashingWaitListedIndexNumberException, SameIndexNumberSwapException,
            ClashingWaitListedIndexNumberException, ClashingRegisteredIndexNumberException,
            PeerClashingRegisteredIndexNumberException {
        Student student = getStudent(userId);
        Student peer = getStudent(peerUserId);
        student.swapIndexNumberWithPeer(courseCodeToBeSwapped, peer);
    }

    /**
     * Drop a course from the wait list for a particular student.
     * @param userId The user id of the student for which the course will be dropped from the wait list.
     * @param courseCodeToBeDropped The course code of the course for which it will be dropped from the wait list.
     * @param indexNumberToBeDropped The {@code IndexNumber} object for which it will be dropped from the wait list.
     */
    public void dropCourseFromWaitList(String userId, String courseCodeToBeDropped, IndexNumber indexNumberToBeDropped) {
        Student student = getStudent(userId);
        student.dropCourseFromWaitList(courseCodeToBeDropped, indexNumberToBeDropped);
    }

    /**
     * Contains the logic to drop a course and the index number and register the next student in the wait list for that
     * index number. The exceptions can be ignored since the exceptions should have been taken care of when the index number is
     *      * added into the student's wait list.
     * @param userId The user id of the student for which the course and index number will be dropped.
     * @param courseCodeToBeDropped The course code of the course which the student wants to drop.
     * @param indexNumberToBeDropped The {@code IndexNumber} object of the index number which the student wants to drop.
     */
    public void dropCourseAndRegisterNextStudentInWaitList(String userId, String courseCodeToBeDropped, IndexNumber indexNumberToBeDropped)
            throws CourseInWaitListException, ClashingRegisteredIndexNumberException,
            CourseRegisteredException, NoVacancyException, ClashingWaitListedIndexNumberException, MaxAuExceededException {
        Student student = getStudent(userId);
        student.dropCourseAndRegisterNextStudentInWaitList(getCourse(courseCodeToBeDropped), indexNumberToBeDropped);
    }

    /**
     * Add a course to the wait list for a student.
     * @param userId The user id of the student for which the course will be added to the wait list for.
     * @param courseCodeToBeAdded The course code of the course for which it will be added to the wait list for the
     *                            student.
     * @param indexNumberToBeAdded The {@code IndexNumber} object for which it will be added to the wait list for the
     *                             student.
     * @see IndexNumber
     */
    public void addCourseToWaitList(String userId, String courseCodeToBeAdded, IndexNumber indexNumberToBeAdded) {
        Student student = getStudent(userId);
        student.addCourseToWaitList(courseCodeToBeAdded, indexNumberToBeAdded);
    }

    /**
     * Retrieves all courses in storage.
     * @return An ArrayList of {@code Course} containing all all courses in storage.
     * @see Course
     */
    public ArrayList<Course> getAllCourses() {
        ArrayList<Course> coursesArrayList = new ArrayList<>();
        for (Course course: courses.values()) {
            coursesArrayList.add(course);
        }

        return coursesArrayList;
    }

    /**
     * Retrieves all students in Storage object maintained.
     * @return An ArrayList of {@code Student} containing all the students in the Storage object.
     * @see Student
     */
    public ArrayList<Student> getAllStudents() {
        ArrayList<Student> studentsArrayList = new ArrayList<>();
        for (Student student: students.values()) {
            studentsArrayList.add(student);
        }

        return studentsArrayList;
    }
}
