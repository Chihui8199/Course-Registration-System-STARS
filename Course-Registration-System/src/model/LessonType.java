package model;

import exception.InvalidLessonTypeException;

/**
 * This is one of the enum data types we created - LessonType
 * The pre-defined group of constants under our Gender data type are: LECTURE, LAB, TUTORIAL
 * since there are generally 3 types of lessons: lecture, lab and tutorial.
 */
public enum LessonType {
    LECTURE,
    LAB,
    TUTORIAL;

    /**
     * Returns a String representation of all the different lesson types available
     * @return The String detailing all of the different categories of LessonType
     */
    public static String getAllLessonType() {
        return LECTURE.name() + ", " + LAB.name() + ", " + TUTORIAL.name();
    }

    /**
     * A method which converts the data type from String to {@code LessonType}
     * @param lessonTypeString The String representing the name of the type of lesson
     * @return {@code LessonType} representing the lessonTypeString converted to data type {@code LessonType}
     * @throws InvalidLessonTypeException if lessonTypeString does not correspond to a valid LessonType
     */
    public static LessonType getLessonType(String lessonTypeString) throws InvalidLessonTypeException {
        try {
            return LessonType.valueOf(lessonTypeString);
        } catch (IllegalArgumentException e) {
            throw new InvalidLessonTypeException();
        }
    }
}
