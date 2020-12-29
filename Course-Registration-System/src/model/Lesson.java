package model;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalTime;

/**
 * This class is responsible for modelling a lesson which details the attributes and methods of each {@code Lesson} object.
 */
public class Lesson implements Serializable {

    /**
     * A {@code LessonType} variable declared as lessonType representing the type of the Lesson object.
     * @see LessonType
     */
    private LessonType lessonType;

    /**
     * A DayOfWeek variable declared as dayOfWeek representing the day of week in which Lesson Object is held.
     * @see DayOfWeek
     */
    private DayOfWeek dayOfWeek;

    /**
     * A LocalTime variable declared as startTime representing the start time of the Lesson Object.
     * @see LocalTime
     */
    private LocalTime startTime;

    /**
     * A LocalTime variable declared as endTime representing the end time of the Lesson Object.
     * @see LocalTime
     */
    private LocalTime endTime;

    /**
     * A constructor which constructs the Lesson object using the following parameters.
     * @param lessonType The {@code LessonType} of the Lesson.
     * @param dayOfWeek A {@code DayOfWeek} object representing the day of the week of the Lesson.
     * @param startTime A {@code LocalTime} object representing the start time of the Lesson.
     * @param endTime A {@code LocalTime } object representing the end time of the Lesson.
     * @see LessonType
     * @see DayOfWeek
     * @see LocalTime
     */
    public Lesson(LessonType lessonType, DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime) {
        this.lessonType = lessonType;
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Returns the start time of the {@code Lesson} object.
     * @return The {@code LocalTime} object representing the start time of the {@code Lesson} object.
     * @see LocalTime
     */
    public LocalTime getStartTime() {
        return startTime;
    }

    /**
     * Returns the end time of the {@code Lesson} object.
     * @return The {@code LocalTime} object representing the end time of the {@code Lesson} object.
     * @see LocalTime
     */
    public LocalTime getEndTime() {
        return endTime;
    }

    /**
     * Returns the day of the week that the {@code Lesson} object is scheduled to be on.
     * @return The {@code DayOfWeek} object representing the day of the week in which the {@code Lesson} is held.
     * @see DayOfWeek
     */
    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    /**
     * Returns the type of lesson in which the {@code Lesson} object represents.
     * @return The {@code LessonType} representing the type of lesson of the {@code Lesson} object.
     * @see LessonType
     */
    public LessonType getLessonType() {
        return lessonType;
    }
}
