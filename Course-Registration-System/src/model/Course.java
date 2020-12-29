package model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class is responsible for modelling a course which details the attributes and methods of each
 *  * {@code Course} object part of the STARS Planner system.
 */
public class Course implements Serializable {
    /**
     * String that represents the course name of this particular {@code Course} object.
     */
    private String courseName;

    /**
     * String that represents the course code of this particular {@code Course} object.
     */
    private String courseCode;

    /**
     * ArrayList holding the different {@code IndexNumber} objects for this particular {@code Course} object.
     */
    private ArrayList<IndexNumber> indexNumbers;

    /**
     * School object representing the school that teaches this particular {@code Course} object.
     */
    private School school;

    /**
     * Integer holding the Academic Units awarded for this particular {@code Course} object.
     */
    private int au;

    /**
     * Creates a new {@code Course} object.
     * @param courseName a String that represents the course name of the {@code Course} object.
     * @param courseCode a String that represents the course code of the {@code Course} object.
     * @param school a {@code School} that represents the School that teaches the {@code Course} object.
     * @param au an integer that represents the Academic Units awarded for the {@code Course} object.
     * @see School
     */
    public Course(String courseName, String courseCode, School school, int au) {
        this.courseName = courseName;
        this.courseCode = courseCode;
        this.school = school;
        this.au = au;
    }

    /**
     * Gets the ArrayList of {@code IndexNumber} objects that is held by this particular {@code Course} object.
     * @return the ArrayList of {@code IndexNumber} objects that is held by this particular {@code Course} object.
     */
    public ArrayList<IndexNumber> getIndexNumbers() {
        return indexNumbers;
    }

    /**
     * Assigns {@code IndexNumber} objects to the particular {@code Course} object.
     * @param indexNumbers ArrayList of {@code IndexNumber} objects that is held by the particular {@code Course} object.
     * @see IndexNumber
     */
    public void setIndexNumbers(ArrayList<IndexNumber> indexNumbers) {
        this.indexNumbers = indexNumbers;
    }

    /**
     * Adds a new {@code IndexNumber} object to the current ArrayList of {@code IndexNumber} objects held by the particular {@code Course} object.
     * @param indexNumber {@code IndexNumber} object to be added to the current ArrayList of {@code IndexNumber} objects.
     */
    public void addIndexNumber(IndexNumber indexNumber) {
        indexNumbers.add(indexNumber);
    }

    /**
     * Gets the course code String from the particular {@code Course} object.
     * @return String representing the course code for the particular {@code Course} object.
     */
    public String getCourseCode() {
        return courseCode;
    }

    /**
     * Gets the course name String from the particular {@code Course} object.
     * @return String representing the course name for the particular {@code Course} object.
     */
    public String getCourseName() {
        return courseName;
    }

    /**
     * Assigns String representing course name to the particular {@code Course} object.
     * @param courseName String representing the new course name for the particular {@code Course} object.
     */
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    /**
     * Gets the assigned {@code School} for the particular {@code Course} object.
     * @return assigned {@code School} for the particular {@code Course} object.
     */
    public School getSchool() {
        return school;
    }

    /**
     * Gets the assigned Academic Units for the particular {@code Course} object.
     * @return an integer representing the Academic Units for the particular {@code Course} object.
     */
    public int getAu() {
        return au;
    }

    /**
     * Assigns a new {@code School} representing the new school that teaches the particular {@code Course} object.
     * @param school The new {@code School} representing the new school that teaches the particular {@code Course} object.
     */
    public void setSchool(School school) {
        this.school = school;
    }

    /**
     * Gets the registered {@code Student} objects for the particular {@code Course} object in an ArrayList.
     * @return ArrayList of registered {@code Student} objects.
     */
    public ArrayList<Student> getRegisteredStudents() {
        ArrayList<Student> registeredStudents = new ArrayList<>();
        for (IndexNumber indexNumber: getIndexNumbers()) {
            for (Student student: indexNumber.getRegisteredStudents()) {
                registeredStudents.add(student);
            }
        }
        return registeredStudents;
    }

    /**
     * Gets the wait listed {@code Student} objects for the particular {@code Course} object in an ArrayList.
     * @return ArrayList of wait listed {@code Student} objects.
     */
    public ArrayList<Student> getWaitListStudents() {
        ArrayList<Student> waitListStudents = new ArrayList<>();
        for (IndexNumber indexNumber: getIndexNumbers()) {
            for (Student student: indexNumber.getWaitListStudents()) {
                waitListStudents.add(student);
            }
        }
        return waitListStudents;
    }

    /**
     * String representation of a {@code Course} object which includes the course code and course name.
     * @return A String containing the course code and course name of the {@code Course} object.
     */
    @Override
    public String toString() {
        return courseCode + " " + courseName;
    }
}
