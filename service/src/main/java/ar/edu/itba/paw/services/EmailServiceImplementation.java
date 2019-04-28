package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.services.EmailService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.Changa;
import ar.edu.itba.paw.models.User;
import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.util.UUID;

@Service
public class EmailServiceImplementation implements EmailService{

    @Autowired
    public JavaMailSender emailSender;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private UserService userService;

    @Override
    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        emailSender.send(message);
    }

    @Override
    public void sendJoinRequestEmail(Changa changa, User changaOwner, User requestingUser) {
        String subject = messageSource.getMessage("sendJoinRequestSubject", null, LocaleContextHolder.getLocale());
        sendEmail(changaOwner.getEmail(), subject, joinRequestEmailBody(changa, changaOwner, requestingUser));
    }

    @Override
    public void sendMailConfirmationEmail(User user, String appUrl) {
        String token = UUID.randomUUID().toString();
        userService.createVerificationToken(user, token);
        String confirmUrl = appUrl + "/registrationConfirm?token=" + token;
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper;

        try {
            helper = new MimeMessageHelper(message,true);
            helper.setTo(user.getEmail());
            helper.setSubject(messageSource.getMessage("mailConfirmationSubject",null, LocaleContextHolder.getLocale()));
            helper.setText(mailConfirmationEmailBody(user, confirmUrl), true);
        } catch (javax.mail.MessagingException e) {
            e.printStackTrace();
        }

        emailSender.send(message);
    }



    //TODO emails from html templates
    private String mailConfirmationEmailBody(User user, String confirmUrl) {
        StringBuilder sb = new StringBuilder();
        sb.append("Apriete en el link para confirmar el mail\n");
        sb.append(confirmUrl);
        return sb.toString();
    }


    //TODO emails from html templates
    private String joinRequestEmailBody(Changa changa, User changaOwner, User currentUser){
        StringBuilder sb = new StringBuilder();
        sb.append(changaOwner.getName());
        sb.append(",\n");
        sb.append(currentUser.getName());
        sb.append(" quiere unirse a la changa ");
        sb.append(changa.getDescription());
        return sb.toString();
    }
}
