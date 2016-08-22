package main.util.mail;

public interface MailSender {
    void sendMail(String to, String subject, String message);
}
