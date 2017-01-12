package main.service.controller.admin.language;

import java.util.Set;

import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;

import main.util.locale.LocaleCodeProvider;

import main.service.controller.AbstractService;

import main.service.crud.language.LanguageCrudService;

import main.service.crud.user.user.UserCrudService;

import main.error.exception.IllegalRemovalEntityException;

import main.json.admin.language.view.LanguageListJson;
import main.json.admin.language.view.LanguageJson;

import main.json.admin.language.NewLanguageJson;

import main.json.admin.language.EditLanguageJson;

import main.json.admin.language.LanguageNameJson;

import main.json.admin.language.teacher.TeacherLanguageListJson;

import main.json.course.CourseUserJson;

import main.model.language.Language;
import main.model.language.LanguageName;

import main.model.user.User;

@Service("adminLanguageService")
public class AdminLanguageServiceImpl extends AbstractService implements AdminLanguageService {

    private LanguageCrudService languageCrudService;
    private UserCrudService userCrudService;

    public LanguageListJson getLanguageList() {
        try {
            Set<Language> languages = this.languageCrudService.findAllLanguages();
            LanguageListJson result = new LanguageListJson();
            for( Language language : languages ) {
                LanguageJson languageJson = new LanguageJson(language.getId());
                for( LanguageName languageName : language.getLanguageNames() ) {
                    languageJson.addLanguageName(languageName.getNamedLanguage().getId(), languageName.getNamingLanguage().getId(), languageName.getLanguageName());
                }
                result.addLanguage(languageJson);
            }
            return result;
        }
        catch( NullPointerException ex ) {
            throw new IllegalArgumentException();
        }
    }

    public void addLanguage(NewLanguageJson languageJson) {
        try {
            Language language = new Language(languageJson.getLanguageID());
            for( LanguageNameJson languageNameJson : languageJson.getLanguageNames() ) {
                language.addLanguageName(new LanguageName(language, languageNameJson.getLanguageName()));
            }
            this.languageCrudService.saveLanguage(language);
        }
        catch( NullPointerException ex ) {
            throw new IllegalArgumentException();
        }
    }

    public void editLanguageNames(Language language, EditLanguageJson languageJson) {
        try {
            // erasing language name list
            for( LanguageName languageName : language.getLanguageNames() ) {
                language.removeLanguageName(languageName);
            }
            for( LanguageNameJson languageNameJson : languageJson.getLanguageNames() ) {
                language.addLanguageName(new LanguageName(language, this.languageCrudService.findLanguageByID(languageNameJson.getLanguageID()), languageNameJson.getLanguageName()));
            }
            this.languageCrudService.updateLanguage(language);
        }
        catch( NullPointerException ex ) {
            throw new IllegalArgumentException();
        }
    }

    public void editSingleLanguageName(Language language, LanguageNameJson languageNameJson) {
        try {
            for( LanguageName languageName : language.getLanguageNames() ) {
                if( ( languageName.getNamingLanguage().getId().equals(languageNameJson.getLanguageID()) ) && ( languageName.getNamedLanguage().getId().equals(language.getId()) ) ) {
                    languageName.setLanguageName(languageNameJson.getLanguageName());
                    this.languageCrudService.updateLanguage(language);
                }
            }
        }
        catch( NullPointerException ex ) {
            throw new IllegalArgumentException();
        }
    }

    public void addLanguageName(Language language, LanguageNameJson languageNameJson) {
        try {
            language.addLanguageName(new LanguageName(language, this.languageCrudService.findLanguageByID(languageNameJson.getLanguageID()), languageNameJson.getLanguageName()));
            this.languageCrudService.updateLanguage(language);
        }
        catch( NullPointerException ex ) {
            throw new IllegalArgumentException();
        }
    }

    public void removeLanguage(Language language) {
        try {
            if( language.hasActiveCourses() ) {
                throw new IllegalRemovalEntityException();
            }
            else {
                this.languageCrudService.deleteLanguage(language);
            }
        }
        catch( NullPointerException ex ) {
            throw new IllegalArgumentException();
        }
    }

    public TeacherLanguageListJson getTeacherLanguageList(Language language) {
        try {
            Set<User> teacherLanguageList = language.getTeachers();
            TeacherLanguageListJson result = new TeacherLanguageListJson(language.getId(), language.getLanguageName(this.localeCodeProvider.getLocaleCode()));
            for( User teacher : teacherLanguageList ) {
                result.addTeacher(new CourseUserJson(teacher.getId(), teacher.getFullName()));
            }
            return result;
        }
        catch( NullPointerException ex ) {
            throw new IllegalArgumentException();
        }
    }

    public void addTeacherLanguage(Language language, User teacher) {
        try {
            language.addTeacher(teacher);
            this.userCrudService.updateUser(teacher);
        }
        catch( NullPointerException ex ) {
            throw new IllegalArgumentException();
        }
    }

    public void removeTeacherLanguage(Language language, User teacher) {
        try {
            if( teacher.hasActiveCourses(language) ) {
                throw new IllegalRemovalEntityException();
            }
            else {
                language.removeTeacher(teacher);
                this.userCrudService.updateUser(teacher);
            }
        }
        catch( NullPointerException ex ) {
            throw new IllegalArgumentException();
        }
    }

    @Autowired
    public AdminLanguageServiceImpl(LocaleCodeProvider localeCodeProvider, LanguageCrudService languageCrudService, UserCrudService userCrudService) {
        super(localeCodeProvider);
        this.languageCrudService = languageCrudService;
        this.userCrudService = userCrudService;
    }

}
