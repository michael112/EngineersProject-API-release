package main.controllers.sample;

import java.util.ArrayList;
import java.util.Locale;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import main.util.properties.PropertyProvider;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestPart;

import org.springframework.web.multipart.MultipartFile;

import org.springframework.web.servlet.LocaleResolver;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import javax.annotation.security.RolesAllowed;
import javax.annotation.security.PermitAll;

import main.service.crud.user.user.UserCrudService;

import main.service.file.FileUploadService;

import main.model.user.User;
import main.model.course.File;

import main.util.currentUser.CurrentUserService;
import main.util.mail.MailSender;

import main.constants.urlconstants.GlobalUrlConstants;

@RestController
@RequestMapping(GlobalUrlConstants.GLOBAL_API_URL + "/test")
public class TestController {

    @Autowired
    private UserCrudService userCrudService;

    @Autowired
    private CurrentUserService currentUserService;

    @Autowired
    private MailSender mailSender;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private LocaleResolver localeResolver;

    @Autowired
    private PropertyProvider propertyProvider;

    @Autowired
    private FileUploadService fileUploadService;

    @RequestMapping(value = "/uploadfile", method = RequestMethod.POST, consumes = "multipart/form-data", produces = "application/text-plain; charset=utf-8")
    @ResponseBody
    @RolesAllowed({"USER"})
    public ResponseEntity<String> uploadSampleFile(@RequestPart("input") SampleInput input, @RequestPart("file") MultipartFile file) {
        try {
            User currentUser = this.currentUserService.getCurrentUser();
            File fileInfo = this.fileUploadService.uploadFile(file, currentUser);
            return new ResponseEntity<String>("File uploaded successfully - id: " + fileInfo.getId() + "" + "\nString1 = " + input.getString1() + "\nString2 = " + input.getString2() + "\nInteger = " + input.getInteger() + "\nDouble = " + input.getDbl(), HttpStatus.OK);
        }
        catch( IllegalArgumentException ex ) {
            return new ResponseEntity<String>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/uploadmultiplefile", method = RequestMethod.POST, consumes = "multipart/form-data", produces = "application/text-plain; charset=utf-8")
    @ResponseBody
    @RolesAllowed({"USER"})
    public ResponseEntity<String> uploadMultipleFiles(@RequestPart("input") SampleInput input, @RequestPart("files") List<MultipartFile> files) {
        try {
            User currentUser = this.currentUserService.getCurrentUser();
            String returnMessage = "";
            File fileInfo;
            for (MultipartFile file : files) {
                fileInfo = this.fileUploadService.uploadFile(file, currentUser);
                returnMessage += "File uploaded successfully - id: " + fileInfo.getId() + "" + "\nString1 = " + input.getString1() + "\nString2 = " + input.getString2() + "\nInteger = " + input.getInteger() + "\nDouble = " + input.getDbl();
                returnMessage += "\n";
            }
            return new ResponseEntity<String>(returnMessage, HttpStatus.OK);
        }
        catch( IllegalArgumentException ex ) {
            return new ResponseEntity<String>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping( value = "/getproperty", method = RequestMethod.GET, produces = "application/text-plain; charset=utf-8" )
    @ResponseBody
    @PermitAll
    public ResponseEntity<String> getProperty(@RequestHeader("Property") String property) {
        return new ResponseEntity<String>(this.propertyProvider.getProperty(property), HttpStatus.OK);
    }

    @RequestMapping( value = "/localelng", method = RequestMethod.GET, produces = "application/text-plain; charset=utf-8" )
    @ResponseBody
    @PermitAll
    public ResponseEntity<String> getCurrentLocaleLanguage() {
        Locale locale = this.localeResolver.resolveLocale(this.httpServletRequest);
        String localeLanguageDefault = locale.getDisplayLanguage();
        String localeLanguageEnglish = locale.getDisplayLanguage(new Locale("EN"));
        return new ResponseEntity<>(localeLanguageDefault + "\n" + localeLanguageEnglish + "\n" + (isValidLocale(locale) ? "Język poprawny" : "Język niepoprawny"), HttpStatus.OK);
    }

    private boolean isValidLocale(Locale locale) {
        return java.util.Arrays.asList(Locale.getISOLanguages()).contains(locale.getLanguage());
    }

    @RequestMapping( value = "/user", method = RequestMethod.GET, produces = "application/text-plain" )
    @ResponseBody
    @RolesAllowed({"USER"})
    public ResponseEntity<String> methodForUser() {
        return new ResponseEntity<>("Uzyskano dostęp do metody dla użytkowników!", HttpStatus.OK);
    }

    @RequestMapping( value = "/admin", method = RequestMethod.GET, produces = "application/text-plain" )
    @ResponseBody
    @RolesAllowed({"ADMIN"})
    public ResponseEntity<String> methodForAdmin() {
        return new ResponseEntity<>("Uzyskano dostęp do metody dla administratora!", HttpStatus.OK);
    }

    @RequestMapping( value = "/useradmin", method = RequestMethod.GET, produces = "application/text-plain" )
    @ResponseBody
    @RolesAllowed({"USER"})
    public ResponseEntity<String> methodForUserAndAdmin() {
        return new ResponseEntity<>("Uzyskano dostęp do metody dla użytkownika i administratora!", HttpStatus.OK);
    }

    @RequestMapping( value = "/all", method = RequestMethod.GET, produces = "application/text-plain" )
    @ResponseBody
    @PermitAll
    public ResponseEntity<String> methodForUnauthorizedUsers() {
        return new ResponseEntity<>("Uzyskano dostęp do metody dla wszystkich!", HttpStatus.OK);
    }

    @RequestMapping( value = "/editsample", method = RequestMethod.GET, produces = "application/text_plain" )
    @PermitAll
    @ResponseBody
    public ResponseEntity<String> editSampleUser() {
        User bill = userCrudService.findUserByUsername("bill");
        String billID = bill.getId();
        bill.setFirstName("Bill");

        userCrudService.updateUser(bill);

        bill = userCrudService.findUserByID(billID);
        return new ResponseEntity<>(bill.getFirstName(), HttpStatus.OK);
    }

    @RequestMapping(value = "/encrypt/{password}", method = RequestMethod.GET, produces = "application/text_plain")
    @PermitAll
    @ResponseBody
    public ResponseEntity<String> encryptPassword(@PathVariable("password") String rawPassword) {
        org.springframework.security.crypto.password.PasswordEncoder encoder = new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(rawPassword);
        return new ResponseEntity<>(encodedPassword, HttpStatus.OK);
    }

    @RequestMapping(value = "/sendMail", method = RequestMethod.GET, produces = "application/text_plain")
    @PermitAll
    @ResponseBody
    public ResponseEntity<String> sendSampleMail( @RequestParam String to, @RequestParam String title, @RequestParam String message ) {
        mailSender.sendMail(to, title, message);
        return new ResponseEntity<>("", HttpStatus.OK);
    }

    @RequestMapping( value = "/UUIDgen", method = RequestMethod.GET, produces = "application/text_plain" )
    @PermitAll
    @ResponseBody
    public ResponseEntity<String> generateUUID() {
        return new ResponseEntity<>(new com.eaio.uuid.UUID().toString(), HttpStatus.OK);
    }

}
