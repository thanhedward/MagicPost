package Backend.controller;

import Backend.entity.Email;
import Backend.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api")
@CrossOrigin(origins = "*", maxAge = 3600)

public class EmailController {

    private EmailService emailService;

    @Autowired
    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping(value = "/send-email")
    public void sendEmail(@Valid @RequestBody Email email) throws Exception {
        try {
            emailService.sendEmail(email);
        } catch (Exception e) {
            throw new Exception();
        }

    }
}
