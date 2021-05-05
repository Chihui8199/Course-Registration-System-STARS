package filemanager;

import model.AccessTime;
import model.Course;
import model.IndexNumber;
import model.School;
import model.Student;

import java.util.ArrayList;

/**
 * This interface contains the abstraction of the methods that any class providing the logic to retrieve and store
 * data should implement.
 */
public interface IStorageManager {
    /**
     * This method should contain the logic to retrieve all the registered courses taken by a student.
     * @param student A {@code Student} object for which we are interested in the registered courses for.
     * @return An ArrayList of {@code Course} taken by the student.
     * @see Student
     * @see Course
     */
    ArrayList<Course> getCoursesTakenByStudent(Student student);

    /**
     * This method should contain the logic to retrieve all the courses in the wait list for a student.
     * @param student A {@code Student} object for which we are interested in the courses in the wait list for.
     * @return An ArrayList of {@code Course} in the wait list by the student.
     */
    ArrayList<Course> getCoursesInWaitListByStudent(Student student);

    /**
     * This method should contain the logic to add a new course into the storage.
     * @param course The {@code Course} object containing all the information of the new course to be added into the storage.
     * @see Course
     */
    void addCourse(Course course);

    /**
     * This method should contain the logic to retrieve a particular course based on its course code.
     * @param courseCode The course code of the particular {@code Course} object to be retrieved.
     * @return A {@code Course} object with the courseCode specified.
     * @see Course
     */
    Course getCourse(String courseCode);

    /**
     * This method should contain the logic to register a student for a particular course and index number.
     * @param userId The user id of the student to register the course and index number for.
     * @param courseCodeToBeAdded The course code of the course to register the student for.
     * @param indexNumberToBeAdded The {@code IndexNumber} object to register the student for.
     * @throws Exception if there are clashes with any of the existing courses taken by the student, or if the student
     * is already registered or in the wait list for the same course or if there is no vacancies for the particular
     * index number, etc.
     */
    void registerForCourse(String userId, String courseCodeToBeAdded, IndexNumber indexNumberToBeAdded)
            throws Exception;

    /**
     * This method should contain the logic to drop a particular course and index number from the student and register the
     * next student in the wait list for that index number.
     * @param userId The user id of the student for which the course and index number will be dropped.
     * @param courseCodeToBeDropped The course code of the course which the student wants to drop.
     * @param indexNumberToBeDropped The {@code IndexNumber} object of the index number which the student wants to drop.
     * @throws Exception if there are any issues with registering the next student in the wait list.
     */
    void dropCourseAndRegisterNextStudentInWaitList(String userId, String courseCodeToBeDropped, IndexNumber indexNumberToBeDropped)
            throws Exception;

    /**
     * This method should provide the logic to swap index number for a particular course for a student.
     * @param userId The user id of the student for which the index number will be swapped.
     * @param courseCodeToBeSwapped The course code of the course for which the index number will swapped for the student.
     * @param newIndexNumber The {@code IndexNumber} object that represents the new index number that the student wants
     *                       to swap to.
     * @throws Exception if there are any issues with swapping the index number, e.g. no vacancies for the new index
     * number, clashes with any of the existing courses, etc.
     */
    void swapIndexNumber(String userId, String courseCodeToBeSwapped, IndexNumber newIndexNumber)
            throws Exception;

    /**
     * This method should contain the logic to swap an index number with another peer.
     * @param userId The user id of the student initiating the swap.
     * @param peerUserId The user id of the peer that is swapping with the student.
     * @param courseCodeToBeSwapped The course code of the course that is to be swapped by the student and the peer.
     * @throws Exception if there is any issues with the swap, e.g. clashes with any of the existing courses taken by
     * the student or the peer.
     */
    void swapIndexWithPeer(String userId, String peerUserId, String courseCodeToBeSwapped)
            throws Exception;

    /**
     * This method should contain the logic to drop a course from the wait list for a particular student.
     * @param userId The user id of the student for which the course will be dropped from the wait list.
     * @param courseCodeToBeDropped The course code of the course for which it will be dropped from the wait list.
     * @param indexNumberToBeDropped The {@code IndexNumber} object for which it will be dropped from the wait list.
     */
    void dropCourseFromWaitList(String userId, String courseCodeToBeDropped, IndexNumber indexNumberToBeDropped);

    /**
     * This method should contain the logic to add a course to wait list for a particular student.
     * @param userId The user id of the student for which the course will be added to the wait list for.
     * @param courseCodeToBeAdded The course code of the course for which it will be added to the wait list for the
     *                            student.
     * @param indexNumberToBeAdded The {@code IndexNumber} object for which it will be added to the wait list for the
     *                             student.
     */
    void addCourseToWaitList(String userId, String courseCodeToBeAdded, IndexNumber indexNumberToBeAdded);

    /**
     * This method should contain the logic to set the new access time for a particular student.
     * @param userId The user id of the student for which the new access time will be set for.
     * @param newAccessTime The new {@code AccessTime} object containing the new start and end date and time for the
     *                      student.
     */
    void setNewAccessTime(String userId, AccessTime newAccessTime);

    /**
     * This method should contain the logic to set the new course code for an existing course.
     * @param newCourseCode The new course code for the existing course.
     * @param forCourseCode The course code of the existing course.
     */
    void setNewCourseCode(String newCourseCode, String forCourseCode);

    /**
     * This method should contain the logic to set the new course name for an existing course.
     * @param newCourseName The new course name for the existing course.
     * @param forCourseCode The course code of the existing course.
     */
    void setNewCourseName(String newCourseName, String forCourseCode);

    /**
     * This method should contain the logic to set the new school for which an existing course is offered by.
     * @param newSchool The new {@code School} for the existing course.
     * @param forCourseCode The course code of the existing course.
     * @see School
     */
    void setNewSchool(School newSchool, String forCourseCode);

    /**
     * This method should contain the logic for setting the new max vacancy for an existing index number.
     * @param courseCode The course code of the course for which the existing index number is associated with.
     * @param index The index of the index numbers that are associated with the course.
     * @param newMaxVacancy The new maximum vacancy of the existing index number.
     * @throws Exception if the new maximum vacancy is less than the number of students registered inside the index
     * number.
     */
    void setNewMaxVacancy(String courseCode, int index, int newMaxVacancy) throws Exception;

    /**
     * This method should contain the logic to retrieve all the courses in the system.
     * @return {@code ArrayList<Course>} containing all courses.
     * @see Course
     */
    ArrayList<Course> getAllCourses();

    /**
     * This method should contain the logic to add a new student into the system.
     * @param student The {@code Student} object containing all the information about the new student.
     * @see Student
     */
    void addStudent(Student student);

    /**
     * This method should contain the logic to add a new index number to an existing course.
     * @param indexNumber The {@code IndexNumber} object representing the new index number to be added.
     * @param courseCode The course code of the course for which the new indexNumber will be added into.
     */
    void addIndexNumber(IndexNumber indexNumber, String courseCode);

    /**
     * This method should contain the logic to retrieve a particular student based on the user id.
     * @param userId The user id of the student which is to be retrieved.
     * @return The {@code Student} object with the user id equals to userId specified.
     */
    Student getStudent(String userId);

    /**
     * This method should contain the logic to retrieve all the students in the system.
     * @return An ArrayList of {@code Student} containing all the students in the system.
     * @see Student
     */
    ArrayList<Student> getAllStudents();
}
