package model;

import java.io.Serializable;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * This is a Singleton class that is responsible for handling the logic of sending notification via E-mail implementing INotification.
 */
public class EmailNotification implements INotification, Serializable {
    /**
     * String representing the username of the email to be used to send the notification to the particular students.
     */
    private static String username = "cz2002.ss3.group3@gmail.com";

    /**
     * String representing the password of the E-mail to be used to send the notification to the particular students.
     */
    private static String password = "cz2002ss3group3";

    /**
     * Represents the single instance of the EmailNotification object.
     */
    private static EmailNotification instance = new EmailNotification();

    /**
     * Sets the E-mail to be used for sending the E-mail notification.
     * @param username  String representing the username of the E-mail to be used to send the notification to the particular students.
     * @param password  String representing the password of the E-mail to be used to send the notification to the particular students.
     */
    public static void setEmailAccount(String username, String password) {
        EmailNotification.username = username;
        EmailNotification.password = password;
    }

    /**
     * Static method that returns the single instance of the EmailNotification object.
     * @return EmailNotification object.
     */
    public static EmailNotification getInstance() {
        return instance;
    }

    /**
     * Mark the constructor as private as this class is a Singleton class.
     */
    private EmailNotification() { }

    /**
     * This method is required to ensure that the same instance of EmailNotification will be retrieved when we
     * de-serialize the object.
     * @return The EmailNotification object that is being de-serialized.
     */
    protected Object readResolve() {
        return getInstance();
    }

    /**
     * Send the particular message to the particular student's provided E-mail.
     * @param recipientEmail String representing the E-mail address to send the message to.
     * @param messageToSend String representing the message to send.
     */
    @Override
    public void send(String recipientEmail, String messageToSend) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
            new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject("MyStars Notification");
            message.setText(messageToSend);

            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
