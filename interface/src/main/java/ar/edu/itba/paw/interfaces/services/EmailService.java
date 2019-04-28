package ar.edu.itba.paw.interfaces.services;

import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;

public interface EmailService {
    void sendEmail(String to, String subject, String body);
}
