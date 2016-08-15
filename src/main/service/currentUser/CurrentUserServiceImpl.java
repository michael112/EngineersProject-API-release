package main.service.currentUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.security.core.context.SecurityContextHolder;

import main.service.model.user.user.UserService;

import main.model.user.User;

@Service("currentUserService")
public class CurrentUserServiceImpl implements CurrentUserService {

    @Autowired
    private UserService userService;

    public User getCurrentUser() {
        return this.userService.findUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
    }

}
