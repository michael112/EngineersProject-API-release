package main.service.controller;

import main.util.locale.LocaleCodeProvider;

public abstract class AbstractService {

    protected LocaleCodeProvider localeCodeProvider;

    protected AbstractService(LocaleCodeProvider localeCodeProvider) {
        this.localeCodeProvider = localeCodeProvider;
    }

}
