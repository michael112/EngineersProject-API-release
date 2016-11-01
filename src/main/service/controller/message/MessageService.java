package main.service.controller.message;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import main.json.course.message.NewMessageJson;

import main.model.course.Course;
import main.model.user.User;

public interface MessageService {

    void sendUserMessage(User sender, User receiver, NewMessageJson messageJson, List<MultipartFile> attachements);

    void sendGroupMessage(User sender, Course course, boolean sendStudents, boolean sendTeachers, NewMessageJson messageJson, List<MultipartFile> attachements);

}
