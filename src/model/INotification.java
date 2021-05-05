package model;

/**
 * This is an interface which sends an email notification detailing the pending/actual outcome of the student's actions
 * when he or she uses the STARS Planner.
 */
public interface INotification {

    /**
     * This is a method which sends the student a message detailing updates to his actions on the STARS Planner
     * @param recipient The name of the student.
     * @param message The details of the notification.
     */
    void send(String recipient, String message);
}
