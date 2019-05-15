package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.Changa;
import ar.edu.itba.paw.models.User;

import javax.mail.MessagingException;

public interface EmailService {
    void sendEmail(String to, String subject, String body);
    void sendJoinRequestEmail(Changa changa, User changaOwner, User requestingUser);
    void sendMailConfirmationEmail(User user, String appUrl) throws MessagingException;
    void sendResetPasswordEmail(User user, String appUrl) throws MessagingException;
    void sendChangaSettledEmails(long changaId);
}
