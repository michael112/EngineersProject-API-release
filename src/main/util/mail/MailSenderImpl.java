package main.util.mail;

import java.util.Collection;

import javax.mail.internet.MimeMessage;
import javax.mail.MessagingException;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.MailParseException;

import org.springframework.web.multipart.MultipartFile;

import lombok.Setter;

public class MailSenderImpl implements MailSender {

    @Setter
    private JavaMailSender mailSender;

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

    public void sendMail(String to, String subject, String message, Collection<MultipartFile> attachements) {
        this.mailSender.send(createMimeMessage(to, subject, message, attachements));
    }

    private MimeMessage createMimeMessage(String to, String subject, String content, Collection<MultipartFile> attachements) {
        try {
            MimeMessage message = mailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(this.from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, false);

            for( MultipartFile attachement : attachements ) {
                helper.addAttachment(attachement.getOriginalFilename(), attachement);
            }

            return message;
        }
        catch( MessagingException ex ) {
            throw new MailParseException(ex);
        }
    }

}
