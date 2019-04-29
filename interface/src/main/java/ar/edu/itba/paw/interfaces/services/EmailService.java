package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.Changa;
import ar.edu.itba.paw.models.User;
import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;

import java.util.Locale;

public interface EmailService {
    void sendEmail(String to, String subject, String body);
    void sendJoinRequestEmail(Changa changa, User changaOwner, User requestingUser);
    void sendMailConfirmationEmail(User user, String appUrl);
}
