package main.service.controller.admin.course.user;

import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;

import main.util.locale.LocaleCodeProvider;

import main.service.controller.AbstractService;

@Service("adminCourseUserService")
public class AdminCourseUserServiceImpl extends AbstractService implements AdminCourseUserService {

    @Autowired
    public AdminCourseUserServiceImpl(LocaleCodeProvider localeCodeProvider) {
        super(localeCodeProvider);
    }

}
