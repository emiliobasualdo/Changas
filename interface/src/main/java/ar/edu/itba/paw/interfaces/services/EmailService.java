package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.interfaces.util.Validation;
import ar.edu.itba.paw.models.Changa;
import ar.edu.itba.paw.models.User;

import javax.mail.MessagingException;

public interface EmailService {
    Validation sendSimpleEmail(String to, String subject, String body);
    Validation sendJoinRequestEmail(Changa changa, User changaOwner, User requestingUser);
    Validation sendMailConfirmationEmail(User user, String appUrl);
    Validation sendResetPasswordEmail(User user, String appUrl);
    Validation sendChangaSettledEmails(long changaId);
    Validation resendMailConfirmationEmail(String existingToken, String registrationConfirmUrl);
    Validation sendMimeEmail(String to, String subject, String text);
}
