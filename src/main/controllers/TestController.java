package main.controllers;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.security.RolesAllowed;
import javax.annotation.security.PermitAll;

import main.service.model.user.user.UserService;
import main.model.user.User;
import main.service.mail.MailService;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @Autowired
    private UserService userService;

    @Autowired
    private MailService mailService;

    @RequestMapping( value = "/user", method = RequestMethod.GET, produces = "application/text-plain" )
    @ResponseBody
    @RolesAllowed({"ROLE_USER"})
    public ResponseEntity<String> methodForUser() {
        return new ResponseEntity<>("Uzyskano dostęp do metody dla użytkowników!", HttpStatus.OK);
    }

    @RequestMapping( value = "/admin", method = RequestMethod.GET, produces = "application/text-plain" )
    @ResponseBody
    @RolesAllowed({"ROLE_ADMIN"})
    public ResponseEntity<String> methodForAdmin() {
        return new ResponseEntity<>("Uzyskano dostęp do metody dla administratora!", HttpStatus.OK);
    }

    @RequestMapping( value = "/useradmin", method = RequestMethod.GET, produces = "application/text-plain" )
    @ResponseBody
    @RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
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
        User bill = userService.findUserByUsername("bill");
        String billID = bill.getId();
        bill.setFirstName("Bill");

        userService.updateUser(bill);

        bill = userService.findUserByID(billID);
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
        mailService.sendMail(to, title, message);
        return new ResponseEntity<>("", HttpStatus.OK);
    }

    @RequestMapping( value = "/UUIDgen", method = RequestMethod.GET, produces = "application/text_plain" )
    @PermitAll
    @ResponseBody
    public ResponseEntity<String> generateUUID() {
        return new ResponseEntity<>(new com.eaio.uuid.UUID().toString(), HttpStatus.OK);
    }

}
