package model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import exception.InvalidAccessTimeException;

/**
 * This class holds the start and end date of a student's access period.
 */
public class AccessTime implements Serializable {
    /**
     * Stores the start date and time of the access period.
     */
    private LocalDateTime start;

    /**
     * Stores the end date and time of the access period.
     */
    private LocalDateTime end;

    /**
     * Constructs a new AccessPeriod object with the start and end date and time.
     * @param start {@code LocalDateTime} object representing start date and time of access period.
     * @param end {@code LocalDateTime} object representing end date and time of access period.
     * @throws InvalidAccessTimeException if the start {@code LocalDateTime} object is equal to or after the end
     * {@code LocalDateTime} object.
     */
    public AccessTime(LocalDateTime start, LocalDateTime end) throws InvalidAccessTimeException {
        if (start.isAfter(end) || start.isEqual(end)) {
            throw new InvalidAccessTimeException();
        }
        this.start = start;
        this.end = end;
    }

    /**
     * Checks if the current date and time is within the access period allotted for a particular student.
     * @param localDateTime {@code LocalDateTime} object representing the current date and time.
     * @return true if the current date and time is within the access period; false otherwise.
     */
    public boolean isWithinAccessTime(LocalDateTime localDateTime) {
        if (localDateTime.isAfter(start) && localDateTime.isBefore(end)) {
            return true;
        }

        return false;
    }

    /**
     * Returns the start date and time of the start of the access period.
     * @return A {@code LocalDateTime} object representing the start date and time of the access period.
     */
    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    /**
     * The String representation of a {@code AccessTime} object.
     * @return a concatenated String object representing the start and end date and time of the access period allotted
     * for a particular student.
     */
    @Override
    public String toString() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        return dtf.format(start) + " to " + dtf.format(end);
    }
}
