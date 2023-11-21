package Backend.service;

import Backend.entity.Email;

import javax.mail.MessagingException;

public interface EmailService {
    void sendEmail(Email email) throws MessagingException;

    void resetPassword(String email, String token) throws MessagingException;
}
