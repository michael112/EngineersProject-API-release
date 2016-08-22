package main.util.mail;

import org.springframework.mail.SimpleMailMessage;

import lombok.Setter;

public class MailSenderImpl implements MailSender {

    @Setter
    private org.springframework.mail.MailSender mailSender;

    @Setter
    private SimpleMailMessage message;

    @Setter
    private String from;

    public void sendMail(String to, String subject, String message) {
        this.message.setFrom(this.from);
        this.message.setTo(to);
        this.message.setSubject(subject);
        this.message.setText(message);
        this.mailSender.send(this.message);
    }

}
