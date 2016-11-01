package main.util.mail;

import java.util.Collection;
import org.springframework.web.multipart.MultipartFile;

public interface MailSender {
    void sendMail(String to, String subject, String message);
    void sendMail(String to, String subject, String message, Collection<MultipartFile> attachements);
}
