package main.controllers;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

import main.util.properties.PropertyProvider;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.web.servlet.LocaleResolver;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import javax.annotation.security.RolesAllowed;
import javax.annotation.security.PermitAll;

import main.service.crud.user.user.UserCrudService;
import main.model.user.User;
import main.util.mail.MailSender;

import main.constants.urlconstants.GlobalUrlConstants;

@RestController
@RequestMapping(GlobalUrlConstants.GLOBAL_API_URL + "/test")
public class TestController {

    @Autowired
    private UserCrudService userCrudService;

    @Autowired
    private MailSender mailSender;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private LocaleResolver localeResolver;

    @Autowired
    private PropertyProvider propertyProvider;

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
