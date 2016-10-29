package main.service.controller.homework;

import org.springframework.beans.factory.annotation.Autowired;

import main.util.locale.LocaleCodeProvider;

import main.service.controller.AbstractService;

public class HomeworkServiceImpl extends AbstractService implements HomeworkService {

    @Autowired
    public HomeworkServiceImpl(LocaleCodeProvider localeCodeProvider) {
        super(localeCodeProvider);
    }

}
