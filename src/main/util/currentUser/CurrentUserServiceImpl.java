package main.util.currentUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.security.core.context.SecurityContextHolder;

import main.service.crud.user.user.UserCrudService;

import main.model.user.User;

@Service("currentUserService")
public class CurrentUserServiceImpl implements CurrentUserService {

    @Autowired
    private UserCrudService userCrudService;

    public User getCurrentUser() {
        return this.userCrudService.findUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
    }

}
