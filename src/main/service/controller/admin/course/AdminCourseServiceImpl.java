package main.service.controller.admin.course;

import org.springframework.stereotype.Service;

import main.util.locale.LocaleCodeProvider;

import main.service.controller.AbstractService;

@Service("adminCourseService")
public class AdminCourseServiceImpl extends AbstractService implements AdminCourseService {

    public AdminCourseServiceImpl(LocaleCodeProvider localeCodeProvider) {
        super(localeCodeProvider);
    }

}
