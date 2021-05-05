package filemanager;

import exception.CourseRegisteredException;
import exception.ClashingRegisteredIndexNumberException;
import exception.InvalidNewMaxException;
import exception.MaxAuExceededException;
import exception.NoVacancyException;
import exception.NoVacancySwapException;
import exception.CourseInWaitListException;
import exception.SameIndexNumberSwapException;
import exception.ClashingWaitListedIndexNumberException;
import exception.PeerClashingWaitListedIndexNumberException;
import exception.PeerClashingRegisteredIndexNumberException;
import model.AccessTime;
import model.Course;
import model.Gender;
import model.IndexNumber;
import model.Lesson;
import model.LessonType;
import model.School;
import model.Storage;
import model.Student;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class is responsible for the persistency of a Storage object. Acts as a wrapper around Storage and saves any
 * changes that is made to the Storage object.
 */
public class StorageManager implements IStorageManager {
    /**
     * The Storage object for which this class is managing.
     * @see Storage
     */
    private Storage storage;

    /**
     * Constructs a new StorageManager.
     */
    public StorageManager() {
        load();
    }

    /**
     * Retrieves all the registered courses taken by a student.
     * @param student A {@code Student} object for which we are interested in the registered courses for.
     * @return An ArrayList of {@code Course} taken by the student.
     * @see Student
     * @see Course
     */
    @Override
    public ArrayList<Course> getCoursesTakenByStudent(Student student) {
        ArrayList<Course> courses = new ArrayList<>();
        for (String courseCode: student.getRegisteredCourseCodes()) {
            courses.add(storage.getCourse(courseCode));
        }

        return courses;
    }

    /**
     * Retrieves all the courses in the wait list by a student.
     * @param student A {@code Student} object for which we are interested in the courses in the wait list for.
     * @return An ArrayList of {@code Course} containing the wait list courses by the student.
     * @see Student
     * @see Course
     */
    @Override
    public ArrayList<Course> getCoursesInWaitListByStudent(Student student) {
        ArrayList<Course> courses = new ArrayList<>();
        for (String courseCode: student.getWaitListCourseCodes()) {
            courses.add(storage.getCourse(courseCode));
        }

        return courses;
    }

    /**
     * Add a new course into the storage object and save the storage object.
     * @param course The {@code Course} object containing all the information of the new course to be added into the storage.
     * @see Course
     */
    @Override
    public void addCourse(Course course) {
        storage.addCourse(course);
        save();
    }

    /**
     * Retrieves a {@code Course} object that represents the course with a particular course code provided.
     * @param courseCode The course code of the particular {@code Course} object to be retrieved.
     * @return A {@code Course} object with the courseCode specified.
     * @see Course
     */
    @Override
    public Course getCourse(String courseCode) {
        return storage.getCourse(courseCode);
    }

    /**
     * Contains the logic to register a student for a particular course and index number.
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
    @Override
    public void registerForCourse(String userId, String courseCodeToBeAdded, IndexNumber indexNumberToBeAdded)
            throws CourseRegisteredException, ClashingRegisteredIndexNumberException, NoVacancyException,
            CourseInWaitListException, ClashingWaitListedIndexNumberException, MaxAuExceededException {
        storage.registerForCourse(userId, courseCodeToBeAdded, indexNumberToBeAdded);
        save();
    }

    /**
     * Contains the logic to drop a course and the index number and register the next student in the wait list for that
     * index number. The exceptions can be ignored since the exceptions should have been taken care of when the index number is
     * added into the student's wait list.
     * @param userId The user id of the student for which the course and index number will be dropped.
     * @param courseCodeToBeDropped The course code of the course which the student wants to drop.
     * @param indexNumberToBeDropped The {@code IndexNumber} object of the index number which the student wants to drop.
     */
    @Override
    public void dropCourseAndRegisterNextStudentInWaitList(String userId, String courseCodeToBeDropped, IndexNumber indexNumberToBeDropped)
            throws CourseInWaitListException, ClashingRegisteredIndexNumberException,
            CourseRegisteredException, NoVacancyException, ClashingWaitListedIndexNumberException, MaxAuExceededException {
        storage.dropCourseAndRegisterNextStudentInWaitList(userId, courseCodeToBeDropped, indexNumberToBeDropped);
        save();
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
    @Override
    public void swapIndexNumber(String userId, String courseCodeToBeSwapped, IndexNumber newIndexNumber)
            throws ClashingRegisteredIndexNumberException, NoVacancySwapException, SameIndexNumberSwapException, ClashingWaitListedIndexNumberException {
        storage.swapIndexNumber(userId, courseCodeToBeSwapped, newIndexNumber);
        save();
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
    @Override
    public void swapIndexWithPeer(String userId, String peerUserId, String courseCodeToBeSwapped)
            throws SameIndexNumberSwapException, ClashingWaitListedIndexNumberException,
            PeerClashingRegisteredIndexNumberException, ClashingRegisteredIndexNumberException,
            PeerClashingWaitListedIndexNumberException {
        storage.swapIndexWithPeer(userId, peerUserId, courseCodeToBeSwapped);
        save();
    }

    /**
     * Drop a course from the wait list for a particular student.
     * @param userId The user id of the student for which the course will be dropped from the wait list.
     * @param courseCodeToBeDropped The course code of the course for which it will be dropped from the wait list.
     * @param indexNumberToBeDropped The {@code IndexNumber} object for which it will be dropped from the wait list.
     */
    @Override
    public void dropCourseFromWaitList(String userId, String courseCodeToBeDropped, IndexNumber indexNumberToBeDropped) {
        storage.dropCourseFromWaitList(userId, courseCodeToBeDropped, indexNumberToBeDropped);
        save();
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
    @Override
    public void addCourseToWaitList(String userId, String courseCodeToBeAdded, IndexNumber indexNumberToBeAdded) {
        storage.addCourseToWaitList(userId, courseCodeToBeAdded, indexNumberToBeAdded);
        save();
    }

    /**
     * Set the new access time for an existing student.
     * @param userId The user id of the student for which the new access time will be set for.
     * @param newAccessTime The new {@code AccessTime} object containing the new start and end date and time for the
     *                      student.
     */
    @Override
    public void setNewAccessTime(String userId, AccessTime newAccessTime) {
        storage.setAccessTime(userId, newAccessTime);
        save();
    }

    /**
     * Set the new course code for an existing course.
     * @param newCourseCode The new course code for the existing course.
     * @param forCourseCode The course code of the existing course.
     */
    @Override
    public void setNewCourseCode(String newCourseCode, String forCourseCode) {
        Course oldCourse = getCourse(forCourseCode);
        Course updatedCourse = new Course(oldCourse.getCourseName(), newCourseCode, oldCourse.getSchool(), oldCourse.getAu());
        updatedCourse.setIndexNumbers(oldCourse.getIndexNumbers());

        storage.removeCourse(oldCourse);
        storage.addCourse(updatedCourse);

        save();
    }

    /**
     * Set the new course name for an existing course.
     * @param newCourseName The new course name for the existing course.
     * @param forCourseCode The course code of the existing course.
     */
    @Override
    public void setNewCourseName(String newCourseName, String forCourseCode) {
        getCourse(forCourseCode).setCourseName(newCourseName);
        save();
    }

    /**
     * Set the new school for which an existing course is offered by.
     * @param newSchool The new {@code School} for the existing course.
     * @param forCourseCode The course code of the existing course.
     */
    @Override
    public void setNewSchool(School newSchool, String forCourseCode) {
        getCourse(forCourseCode).setSchool(newSchool);
        save();
    }

    /**
     * Set the new maximum vacancy for an existing course.
     * @param courseCode The course code of the course for which the existing index number is associated with.
     * @param index The index of the index numbers that are associated with the course.
     * @param newMaxVacancy The new maximum vacancy of the existing index number.
     * @throws InvalidNewMaxException if the new maximum vacancy is less than the number of students registered inside the index
     * number.
     */
    @Override
    public void setNewMaxVacancy(String courseCode, int index, int newMaxVacancy) throws InvalidNewMaxException {
        getCourse(courseCode).getIndexNumbers().get(index).setMaxVacancy(newMaxVacancy);
        save();
    }

    /**
     *  Retrieves all courses in the Storage object maintained by this class.
     *  @return An ArrayList of {@code Course} containing all the courses in the Storage object.
     *  @see Course
     */
    @Override
    public ArrayList<Course> getAllCourses() {
        return storage.getAllCourses();
    }

    /**
     * Adds a new student into storage.
     * @param student The {@code Student} object containing all the information about the new student.
     * @see Student
     */
    @Override
    public void addStudent(Student student) {
        storage.addStudent(student);
        save();
    }

    /**
     * Adds a new index number to an existing course.
     * @param indexNumber The {@code IndexNumber} object representing the new index number to be added.
     * @param courseCode The course code of the course for which the new indexNumber will be added into.
     * @see IndexNumber
     */
    @Override
    public void addIndexNumber(IndexNumber indexNumber, String courseCode) {
        storage.addIndexNumber(indexNumber, courseCode);
        save();
    }

    /**
     * Retrieves a particular {@code Student} object given its user id.
     * @param userId The user id of the student which is to be retrieved.
     * @return A {@code Student} object with the userId specified.
     * @see Student
     */
    @Override
    public Student getStudent(String userId) {
        return storage.getStudent(userId);
    }

    /**
     * Retrieves all students in the Storage object maintained by this class.
     * @return An ArrayList of {@code Student} containing all the students in the Storage object.
     * @see Student
     */
    @Override
    public ArrayList<Student> getAllStudents() {
        return storage.getAllStudents();
    }

    /**
     * This method is called only when the byte-stream file "data/Storage.ser" does not exists. Initialises a new
     * Storage object and load it with all the information provided in "data/preload" folder.
     */
    private void preload() {
        try {
            Files.copy(Path.of("data/preload/StudentLoginInfo.txt"), Path.of("data/StudentLoginInfo.txt")
                    , StandardCopyOption.REPLACE_EXISTING);
            Files.copy(Path.of("data/preload/AdminLoginInfo.txt"), Path.of("data/AdminLoginInfo.txt")
                    , StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }

        storage = new Storage();
        File studentFile = new File("data/preload/Students.txt");
        try {
            Scanner sc = new Scanner(studentFile);
            while (sc.hasNext()) {
                storage.addStudent(parseStudentFromTxt(sc.nextLine()));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        File[] courseFiles = new File("data/preload/Courses").listFiles();
        for (File file: courseFiles) {
            if (file.getName().startsWith(".")) {
                continue;
            }
            try {
                Scanner sc = new Scanner(file);
                Course course = parseCourseFromTxt(sc.nextLine());
                ArrayList<IndexNumber> indexNumbers = new ArrayList<>();
                while (sc.hasNext()) {
                    indexNumbers.add(parseIndexNumberFromTxt(sc.nextLine(), course));
                }

                course.setIndexNumbers(indexNumbers);
                storage.addCourse(course);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        save();
    }

    /**
     * Parse the second line onwards from the files inside the "data/preload/Courses" folder and returns an
     * {@code IndexNumber} object.
     * @param line The second line onwards from the files inside the "data/preload/Courses" folder.
     * @param course The {@code Course} object which the index number is associated with.
     * @return The {@code IndexNumber} object containing the information from the line.
     */
    private IndexNumber parseIndexNumberFromTxt(String line, Course course) {
        String[] lineSplit = line.split("\\|");
        int id = Integer.parseInt(lineSplit[0]);
        int maxVacancy = Integer.parseInt(lineSplit[1]);
        ArrayList<Lesson> lessons = new ArrayList<>();
        for (int i = 2; i < lineSplit.length; i++) {
            String[] lessonSplit = lineSplit[i].split("-");
            LessonType lessonType;
            switch (lessonSplit[0]) {
            case "LEC":
                lessonType = LessonType.LECTURE;
                break;
            case "TUT":
                lessonType = LessonType.TUTORIAL;
                break;
            case "LAB":
                lessonType = LessonType.LAB;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + lessonSplit[0]);
            }
            DayOfWeek dayOfWeek = DayOfWeek.valueOf(lessonSplit[1]);
            String startTimeString = lessonSplit[2];
            String endTimeString = lessonSplit[3];
            LocalTime startTime = LocalTime.of(Integer.parseInt(startTimeString.substring(0, 2)),
                    Integer.parseInt(startTimeString.substring(2)));
            LocalTime endTime = LocalTime.of(Integer.parseInt(endTimeString.substring(0, 2)),
                    Integer.parseInt(endTimeString.substring(2)));

            lessons.add(new Lesson(lessonType, dayOfWeek, startTime, endTime));
        }

        return new IndexNumber(id, course, lessons, maxVacancy);
    }

    /**
     * Contains the logic to parse the first line of text from the files inside "data/preload/Courses" and return a
     * {@code Course} object containing the information specified in the line.
     * @param line The first line of text from the files inside "data/preload/Courses".
     * @return A {@code Course} object containing all the information specified in line.
     * @see Course
     */
    private Course parseCourseFromTxt(String line) {
        String[] lineSplit = line.split("\\|");
        String courseName = lineSplit[0];
        String courseCode = lineSplit[1];
        School school;
        switch (lineSplit[2]) {
        case "SCSE":
            school = School.SCSE;
            break;
        case "SSS":
            school = School.SSS;
            break;
        default:
            throw new IllegalStateException("Unexpected value: " + lineSplit[2]);
        }
        int au = Integer.parseInt(lineSplit[3]);

        return new Course(courseName, courseCode, school, au);
    }

    /**
     * Contains the logic to parse a line of text from the "data/preload/Students.txt" file.
     * @param line A line of text from the "data/preload/Students.txt" file.
     * @return A {@code Student} object containing all the information provided in line.
     * @see Student
     */
    private Student parseStudentFromTxt(String line) {
        String[] lineSplit = line.split("\\|");
        String name = lineSplit[0];
        String userId = lineSplit[1];
        String matricNumber = lineSplit[2];
        String nationality = lineSplit[3];
        Gender gender = lineSplit[4].equals("MALE") ? Gender.MALE : Gender.FEMALE;
        String emailAddress = lineSplit[5];
        String[] accessStartTimeSplit = lineSplit[6].split("-");
        String[] accessEndTimeStringSplit = lineSplit[7].split("-");
        LocalDateTime acccessStartTime = LocalDateTime.of(Integer.parseInt(accessStartTimeSplit[0]),
                Integer.parseInt(accessStartTimeSplit[1]), Integer.parseInt(accessStartTimeSplit[2]),
                Integer.parseInt(accessStartTimeSplit[3]), Integer.parseInt(accessStartTimeSplit[4]));
        LocalDateTime accessEndTime = LocalDateTime.of(Integer.parseInt(accessEndTimeStringSplit[0]),
                Integer.parseInt(accessEndTimeStringSplit[1]), Integer.parseInt(accessEndTimeStringSplit[2]),
                Integer.parseInt(accessEndTimeStringSplit[3]), Integer.parseInt(accessEndTimeStringSplit[4]));

        return new Student(name, userId, matricNumber, nationality, emailAddress, gender,
                new AccessTime(acccessStartTime, accessEndTime));
    }

    /**
     * Attempts to look if an existing byte-stream file named "Storage.ser" inside "data" folder exists. If it exists,
     * de-serialize it and load it into the Storage object that this class is managing. Otherwise, we attempt to create
     * a new Storage object by loading it with some pre-loaded information provided inside the "data/preload" folder.
     */
    private void load() {
        try {
            FileInputStream fis = new FileInputStream("data/Storage.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);
            storage = (Storage) ois.readObject();
            ois.close();
            fis.close();
        } catch (FileNotFoundException e) {
            preload();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InvalidClassException e) {
            preload();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Contains the logic to save the Storage object managed by this class into a byte-stream file named "Storage.ser"
     * inside "data" folder.
     */
    private void save() {
        try {
            FileOutputStream fos = new FileOutputStream("data/Storage.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(storage);
            oos.close();
            fos.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

}
