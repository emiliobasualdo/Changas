package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.services.EmailService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.Changa;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.UUID;

@Service
public class EmailServiceImplementation implements EmailService {

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
    public void sendMailConfirmationEmail(User user, String appUrl) throws MessagingException {
        String token = UUID.randomUUID().toString();
        userService.createVerificationToken(user, token);
        String confirmUrl = appUrl + "/registration-confirm?token=" + token;
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper;

        helper = new MimeMessageHelper(message,true);
        helper.setTo(user.getEmail());
        helper.setSubject(messageSource.getMessage("mailConfirmationSubject",null, LocaleContextHolder.getLocale()));
        helper.setText(mailConfirmationEmailBody(user, confirmUrl), true);

        emailSender.send(message);
    }

    @Override
    public void sendResetPasswordEmail(User user, String appUrl) throws MessagingException {
        String token = UUID.randomUUID().toString();
        userService.createVerificationToken(user, token);
        String resetUrl = appUrl + "/reset-password?id=" + user.getUser_id() + "&token=" + token;
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,true);
        helper.setTo(user.getEmail());
        helper.setSubject(messageSource.getMessage("resetPasswordSubject",null, LocaleContextHolder.getLocale()));
        helper.setText(resetPasswordEmailBody(resetUrl), true);
        emailSender.send(message);
    }

    //TODO emails from html templates
    private String mailConfirmationEmailBody(User user, String confirmUrl) {
        StringBuilder sb = new StringBuilder();
        sb.append("Apriete en el link para confirmar el mail\n"); //TODO: problema de idioma no internacional
        sb.append(confirmUrl);
        return sb.toString();
    }

    private String resetPasswordEmailBody(String resetUrl) {
        StringBuilder sb = new StringBuilder();
        sb.append("Apriete en el link para cambiar la contrseña ");
        sb.append(resetUrl);
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
