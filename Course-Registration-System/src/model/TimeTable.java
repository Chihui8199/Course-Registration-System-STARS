package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import errormessage.ErrorMessage;
import exception.CourseRegisteredException;
import exception.CourseInWaitListException;
import exception.ClashingWaitListedIndexNumberException;
import exception.ClashingRegisteredIndexNumberException;
import exception.MaxAuExceededException;
import exception.NoVacancyException;
import exception.NoVacancySwapException;
import exception.SameIndexNumberSwapException;
import exception.PeerClashingWaitListedIndexNumberException;
import exception.PeerClashingRegisteredIndexNumberException;
import view.LoginUi;

/**
 * This class is responsible for managing all the index numbers that a particular {@code Student} object is registered or
 * in the wait list for.
 */
public class TimeTable implements Serializable {

    /**
     * A Student object representing the student which this TimeTable is associated to.
     * @see Student
     */
    private Student student;

    /**
     * A hashmap with the key being a String representation of the registered course code and the value is the {@code IndexNumber}
     * object that is associated with that registered course code.
     * @see IndexNumber
     */
    private HashMap<String, IndexNumber> registeredIndexNumbers;

    /**
     * A hashmap with the key being the String representation of the wait list course code and the value is the {@code IndexNumber}
     * object that is associated with that wait list course code.
     * @see IndexNumber
     */
    private HashMap<String, IndexNumber> waitListIndexNumbers;

    /**
     * A constructor which constructs the Timetable object.
     * @param student The {@code Student} object which this {@code TimeTable} is associated to.
     * @see Student
     */
    public TimeTable(Student student) {
        this.student = student;
        registeredIndexNumbers = new HashMap<>();
        waitListIndexNumbers = new HashMap<>();
    }

    /**
     * Returns the course codes of all the registered courses.
     * @return An ArrayList that contains the registered course codes.
     */
    public ArrayList<String> getRegisteredCourseCodes() {
        return new ArrayList<>(registeredIndexNumbers.keySet());
    }

    /**
     * Returns the HashMap of the registered index numbers.
     * @return A HashMap object containing all the registered {@code IndexNumber} objects.
     * @see IndexNumber
     */
    public HashMap<String, IndexNumber> getRegisteredIndexNumbers() {
        return registeredIndexNumbers;
    }

    /**
     * Returns the course codes of all the wait list courses.
     * @return An ArrayList that contains the wait list course codes.
     */
    public ArrayList<String> getWaitListCourseCodes() {
        return new ArrayList<>(waitListIndexNumbers.keySet());
    }

    /**
     * Returns the HashMap of the wait listed index numbers.
     * @return A Hashmap object containing all the wait listed {@code IndexNumber} objects.
     */
    public HashMap<String, IndexNumber> getWaitListIndexNumbers() {
        return waitListIndexNumbers;
    }

    /**
     * Counts the number of AU that the student associated to this timetable have been registered for.
     * @return the total AU that have been registered by the student associated to this timetable.
     */
    public int getRegisteredAu() {
        int registeredAu = 0;
        for (IndexNumber indexNumber: registeredIndexNumbers.values()) {
            registeredAu += indexNumber.getCourse().getAu();
        }
        return registeredAu;
    }

    /**
     * Counts the number of AU that the student associated to this timetable have been waitlisted for.
     * @return the total AU that have been waitlisted by the student associated to this timetable.
     */
    public int getWaitListAu() {
        int waitListAu = 0;
        for (IndexNumber indexNumber: waitListIndexNumbers.values()) {
            waitListAu += indexNumber.getCourse().getAu();
        }
        return waitListAu;
    }

    /**
     * Returns the sum of the amount of registered AUs and wait listed AUs
     * @return the total AUs that have been registered and wait listed for.
     */
    public int getTotalAuInRegisteredAndWaitList() {
        return getRegisteredAu() + getWaitListAu();
    }

    /**
     * Returns an ArrayList of {@code IndexNumber} that has been registered for.
     * @return an ArrayList of {@code IndexNumber} that has been registered.
     * @see IndexNumber
     */
    public ArrayList<IndexNumber> getAllIndexNumbersRegistered() {
        ArrayList<IndexNumber> indexNumbersRegistered = new ArrayList<>();
        for (IndexNumber indexNumber: registeredIndexNumbers.values()) {
            indexNumbersRegistered.add(indexNumber);
        }
        return indexNumbersRegistered;
    }

    /**
     * Returns an ArrayList of {@code IndexNumber} that has been wait listed for.
     * @return an ArrayList of {@code IndexNumber} that has been wait listed.
     */
    public ArrayList<IndexNumber> getAllIndexNumbersWaitListed() {
        ArrayList<IndexNumber> indexNumbersWaitListed = new ArrayList<>();
        for (IndexNumber indexNumber: waitListIndexNumbers.values()) {
            indexNumbersWaitListed.add(indexNumber);
        }
        return indexNumbersWaitListed;
    }

    /**
     * A method that registers the student for a specific course and index number and sends an email to notify the student
     * if he/she has been successfully registered for the course.
     * @param courseCodeToBeAdded course code of the course to be registered for.
     * @param indexNumberToBeAdded {@code IndexNumber} object that is to be registered for.
     * @throws CourseRegisteredException if the course selected is already registered.
     * @throws ClashingRegisteredIndexNumberException if the index number that has been selected clashes with the current
     * courses that have been registered already.
     * @throws NoVacancyException if the index of the course selected does not have vacancy.
     * @throws CourseInWaitListException if the course selected is already added to the wait list.
     * @throws ClashingWaitListedIndexNumberException if the index number of the course that have been selected clashes
     * with the timetable of the courses that have already been wait listed for.
     * @throws MaxAuExceededException throws exception when user is still trying to add course when he/she already has
     * reached the maximum AU allowable.
     * @see IndexNumber
     */
    public void registerForCourse(String courseCodeToBeAdded, IndexNumber indexNumberToBeAdded)
            throws CourseRegisteredException, ClashingRegisteredIndexNumberException,
            NoVacancyException, CourseInWaitListException, ClashingWaitListedIndexNumberException,
            MaxAuExceededException {
        for (String courseCode: getRegisteredCourseCodes()) {
            if (courseCode.equals(courseCodeToBeAdded)) {
                throw new CourseRegisteredException();
            }
        }

        if (getTotalAuInRegisteredAndWaitList() + indexNumberToBeAdded.getCourse().getAu() > Student.maxAu) {
            throw new MaxAuExceededException();
        }

        for (String courseCode: getWaitListCourseCodes()) {
            if (courseCode.equals(courseCodeToBeAdded)) {
                throw new CourseInWaitListException();
            }
        }

        if (!getClashingRegisteredIndexNumbers(indexNumberToBeAdded).isEmpty()) {
            throw new ClashingRegisteredIndexNumberException();
        }

        if (!getClashingWaitListedIndexNumbers(indexNumberToBeAdded).isEmpty()) {
            throw new ClashingWaitListedIndexNumberException();
        }

        if (indexNumberToBeAdded.getAvailableVacancy() <= 0) {
            throw new NoVacancyException();
        }

        addCourse(courseCodeToBeAdded, indexNumberToBeAdded);

        String messageToSend = "You have successfully registered for the course:\n\n"
                + indexNumberToBeAdded.getCourse().toString() + "\n\n" + indexNumberToBeAdded.getFullDescription();
        student.notify(messageToSend);
    }

    /**
     * Method that adds the selected course and index number for the student.
     * @param courseCodeToBeAdded course code of the course to be added.
     * @param indexNumberToBeAdded {@code IndexNumber} object which is to be added.
     */
    private void addCourse(String courseCodeToBeAdded, IndexNumber indexNumberToBeAdded) {
        registeredIndexNumbers.put(courseCodeToBeAdded, indexNumberToBeAdded);
        indexNumberToBeAdded.registerStudent(student);
    }

    /**
     * Method that drops the course and index number for the student.
     * @param courseCodeToBeDropped course code of the course to be dropped.
     * @param indexNumberToBeDropped {@code IndexNumber} object which is to be dropped.
     */
    private void dropCourse(String courseCodeToBeDropped, IndexNumber indexNumberToBeDropped) {
        indexNumberToBeDropped.deregisterStudent(student);
        registeredIndexNumbers.remove(courseCodeToBeDropped);
    }

    /**
     * This method performs the swapping of an index number with a peer when all conditions are met.
     * @param courseCodeToBeSwapped course code of the course to be swapped with peer.
     * @param peer {@code Student} object that the current user will be swapping index with.
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
    public void swapIndexNumberWithPeer(String courseCodeToBeSwapped, Student peer)
            throws ClashingRegisteredIndexNumberException, SameIndexNumberSwapException,
            ClashingWaitListedIndexNumberException, PeerClashingRegisteredIndexNumberException,
            PeerClashingWaitListedIndexNumberException {
        IndexNumber myIndexNumber = registeredIndexNumbers.get(courseCodeToBeSwapped);
        IndexNumber peerIndexNumber = peer.getRegisteredIndexNumbers().get(courseCodeToBeSwapped);

        if (myIndexNumber == peerIndexNumber) {
            throw new SameIndexNumberSwapException();
        }

        // deregister myself
        dropCourse(courseCodeToBeSwapped, myIndexNumber);
        // deregister peer
        peer.getTimeTable().dropCourse(courseCodeToBeSwapped, peerIndexNumber);

        if (!getClashingRegisteredIndexNumbers(peerIndexNumber).isEmpty()) {
            // register back myself
            addCourse(courseCodeToBeSwapped, myIndexNumber);
            // register back peer
            peer.getTimeTable().addCourse(courseCodeToBeSwapped, peerIndexNumber);
            throw new ClashingRegisteredIndexNumberException();
        }

        if (!getClashingWaitListedIndexNumbers(peerIndexNumber).isEmpty()) {
            // register back myself
            addCourse(courseCodeToBeSwapped, myIndexNumber);
            // register back peer
            peer.getTimeTable().addCourse(courseCodeToBeSwapped, peerIndexNumber);
            throw new ClashingWaitListedIndexNumberException();
        }

        if (!peer.getTimeTable().getClashingRegisteredIndexNumbers(myIndexNumber).isEmpty()) {
            // register back myself
            addCourse(courseCodeToBeSwapped, myIndexNumber);
            // register back peer
            peer.getTimeTable().addCourse(courseCodeToBeSwapped, peerIndexNumber);
            throw new PeerClashingRegisteredIndexNumberException();
        }

        if (!peer.getTimeTable().getClashingWaitListedIndexNumbers(myIndexNumber).isEmpty()) {
            // register back myself
            addCourse(courseCodeToBeSwapped, myIndexNumber);
            // register back peer
            peer.getTimeTable().addCourse(courseCodeToBeSwapped, peerIndexNumber);
            throw new PeerClashingWaitListedIndexNumberException();
        }

        addCourse(courseCodeToBeSwapped, peerIndexNumber);
        peer.getTimeTable().addCourse(courseCodeToBeSwapped, myIndexNumber);

        String messageToSend = "You have successfully swapped index with your peer, " + peer.getName() + ", for the course:\n\n"
                + myIndexNumber.getCourse().toString() + "\n\n"
                + "Your old index number was: " + myIndexNumber.getFullDescription() + "\n\n"
                + "You are now registered for index number: " + peerIndexNumber.getFullDescription();
        student.notify(messageToSend);
        String peerMessageToSend = "You have successfully swapped index with your peer, " + student.getName() + ", for the course:\n\n"
                + myIndexNumber.getCourse().toString() + "\n\n"
                + "Your old index number was: " + peerIndexNumber.getFullDescription() + "\n\n"
                + "You are now registered for index number: " + myIndexNumber.getFullDescription();
        peer.notify(peerMessageToSend);
    }

    /**
     * Swap index number for a course that the student is registered for.
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
    public void swapIndexNumber(String courseCodeToBeSwapped, IndexNumber newIndexNumber)
            throws ClashingRegisteredIndexNumberException, NoVacancySwapException, SameIndexNumberSwapException, ClashingWaitListedIndexNumberException {
        IndexNumber indexNumberToBeSwapped = registeredIndexNumbers.get(courseCodeToBeSwapped);
        if (indexNumberToBeSwapped == newIndexNumber) {
            throw new SameIndexNumberSwapException();
        }

        dropCourse(courseCodeToBeSwapped, indexNumberToBeSwapped);

        if (!getClashingRegisteredIndexNumbers(newIndexNumber).isEmpty()) {
            addCourse(courseCodeToBeSwapped, indexNumberToBeSwapped);
            throw new ClashingRegisteredIndexNumberException();
        }

        if (!getClashingWaitListedIndexNumbers(newIndexNumber).isEmpty()) {
            addCourse(courseCodeToBeSwapped, indexNumberToBeSwapped);
            throw new ClashingWaitListedIndexNumberException();
        }

        if (newIndexNumber.getAvailableVacancy() <= 0) {
            addCourse(courseCodeToBeSwapped, indexNumberToBeSwapped);
            throw new NoVacancySwapException();
        }

        addCourse(newIndexNumber.getCourse().getCourseCode(), newIndexNumber);

        String messageToSend = "You have successfully swapped index for the course:\n\n"
                + indexNumberToBeSwapped.getCourse().toString() + "\n\n"
                + "Your old index number was:" + indexNumberToBeSwapped.getFullDescription() + "\n\n"
                + "You are now registered for index number:" + newIndexNumber.getFullDescription();
        student.notify(messageToSend);
    }

    /**
     * Add a course to the wait list for the student.
     * @param courseCodeToBeAdded The course code of the course for which it will be added to the wait list for the
     *                            student.
     * @param indexNumberToBeAdded The {@code IndexNumber} object for which it will be added to the wait list for the
     *                             student.
     */
    public void addCourseToWaitList(String courseCodeToBeAdded, IndexNumber indexNumberToBeAdded) {
        waitListIndexNumbers.put(courseCodeToBeAdded, indexNumberToBeAdded);
        indexNumberToBeAdded.addStudentToWaitList(student);

        String messageToSend = "You have added to the wait list for the course:\n\n"
                + indexNumberToBeAdded.getCourse().toString() + "\n\n"
                + indexNumberToBeAdded.getFullDescription();
        student.notify(messageToSend);
    }

    /**
     * Drop a course from the wait list for the student.
     * @param courseCodeToBeDropped The course code of the course for which it will be dropped from the wait list.
     * @param indexNumberToBeDropped The {@code IndexNumber} object for which it will be dropped from the wait list.
     */
    public void dropCourseFromWaitList(String courseCodeToBeDropped, IndexNumber indexNumberToBeDropped) {
        waitListIndexNumbers.remove(courseCodeToBeDropped);
        indexNumberToBeDropped.removeStudentFromWaitList(student);

        String messageToSend = "You have dropped from the wait list for the course:\n\n"
                + indexNumberToBeDropped.getCourse().toString() + "\n\n"
                + indexNumberToBeDropped.getFullDescription();
        student.notify(messageToSend);
    }

    /**
     * Contains the logic to drop a course and the index number and register the next student in the wait list for that
     * index number. The exceptions can be ignored since the exceptions should have been taken care of when the index number is
     *      * added into the next student in line's wait list.
     * @param course The {@code Course} object that is to be dropped.
     * @param indexNumberToBeDropped The {@code IndexNumber} object of the index number which the student wants to drop.
     */
    public void dropCourseAndRegisterNextStudentInWaitList(Course course, IndexNumber indexNumberToBeDropped)
            throws CourseInWaitListException, ClashingRegisteredIndexNumberException,
            CourseRegisteredException, NoVacancyException, ClashingWaitListedIndexNumberException, MaxAuExceededException {
        indexNumberToBeDropped.deregisterStudent(student);
        registeredIndexNumbers.remove(course.getCourseCode());

        indexNumberToBeDropped.registerNextStudentInWaitList();

        String messageToSend = "You have successfully dropped from the course:\n\n"
                + indexNumberToBeDropped.getCourse().toString() + "\n\n"
                + indexNumberToBeDropped.getFullDescription();
        student.notify(messageToSend);
    }

    /**
     * Method that returns the wait listed index numbers that are clashing with the index number that the student wants to add
     * @param indexNumberToBeAdded {@code IndexNumber} object to be added by the current student
     * @return An ArrayList of {@code IndexNumber} containing all the wait listed index numbers that clashes with the index number
     * that is to be added.
     */
    public ArrayList<IndexNumber> getClashingWaitListedIndexNumbers(IndexNumber indexNumberToBeAdded) {
        ArrayList<IndexNumber> clashingIndexNumbers = new ArrayList<>();
        for (IndexNumber indexNumber: getAllIndexNumbersWaitListed()) {
            if (checkIndexNumberClash(indexNumberToBeAdded, indexNumber)) {
                clashingIndexNumbers.add(indexNumber);
            }
        }

        return clashingIndexNumbers;
    }

    /**
     * Method that returns the registered index numbers that are clashing with the index number that the student wants to add
     * @param indexNumberToBeAdded {@code IndexNumber} object to be added by the current student
     * @return An ArrayList of {@code IndexNumber} containing all the registered index numbers that clashes with the index number
     * that is to be added.
     */
    public ArrayList<IndexNumber> getClashingRegisteredIndexNumbers(IndexNumber indexNumberToBeAdded) {
        ArrayList<IndexNumber> clashingIndexNumbers = new ArrayList<>();
        for (IndexNumber indexNumber: getAllIndexNumbersRegistered()) {
            if (checkIndexNumberClash(indexNumberToBeAdded, indexNumber)) {
                clashingIndexNumbers.add(indexNumber);
            }
        }

        return clashingIndexNumbers;
    }

    /**
     * Method that checks if the index number that the student wants to add clashes with another index number.
     * @param indexNumberToBeAdded {@code IndexNumber} object to be added by the student
     * @param indexNumberToCheck {@code IndexNumber} object that is to be checked if there are clashes with.
     * @return a boolean true if there are clashes and false if there are no clashes.
     */
    private boolean checkIndexNumberClash(IndexNumber indexNumberToBeAdded, IndexNumber indexNumberToCheck) {
        for (Lesson lesson: indexNumberToCheck.getLessons()) {
            for (Lesson lessonToBeAdded: indexNumberToBeAdded.getLessons()) {
                if (lessonToBeAdded.getDayOfWeek() == lesson.getDayOfWeek()) {
                    if (lessonToBeAdded.getStartTime().isBefore(lesson.getEndTime())  &&
                            lesson.getStartTime().isBefore(lessonToBeAdded.getEndTime())) {
                        return true;
                    }
                }
            }
        }

        return false;
    }
}
