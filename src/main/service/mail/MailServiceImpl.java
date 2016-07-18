package main.service.mail;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import lombok.Setter;

public class MailServiceImpl implements MailService {

    @Setter
    private MailSender mailSender;

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
