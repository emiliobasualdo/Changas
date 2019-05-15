package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.services.ChangaService;
import ar.edu.itba.paw.interfaces.services.EmailService;
import ar.edu.itba.paw.interfaces.services.InscriptionService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.interfaces.util.Validation;
import ar.edu.itba.paw.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.UUID;

@Service
public class EmailServiceImplementation implements EmailService {

    @Autowired
    public JavaMailSender emailSender;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private UserService userService;

    @Autowired
    private InscriptionService inscriptionService;

    @Autowired
    private ChangaService changaService;


    @Override
    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        emailSender.send(message);
        System.out.println("to: " + to);
    }

    @Override
    public void sendJoinRequestEmail(Changa changa, User changaOwner, User requestingUser) {
        String subject = messageSource.getMessage("sendJoinRequest.Subject", null, LocaleContextHolder.getLocale());
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
        helper.setSubject(messageSource.getMessage("mailConfirmation.Subject",null, LocaleContextHolder.getLocale()));
        helper.setText(mailConfirmationEmailBody(user, confirmUrl), true);

        emailSender.send(message);
    }

    @Override
    public void sendChangaSettledEmails(long changaId) {
        String subject = messageSource.getMessage("sendChangaSettledEmail.Subject", null, LocaleContextHolder.getLocale());
        Changa changa = changaService.getChangaById(changaId).getValue();
        User changaOwner = userService.findById(changa.getUser_id()).getValue();
        Either<List<Pair<User, Inscription>>, Validation> accpetedUsers = inscriptionService.getAcceptedUsers(changaId);
        if(accpetedUsers.isValuePresent()) {
            StringBuilder acceptedUsersInfo = new StringBuilder("");
            for (Pair<User, Inscription> userInscription : accpetedUsers.getValue()) {
                sendEmail(userInscription.getKey().getEmail(), subject, changaSettledEmailToInscribedUserBody(changa, changaOwner, userInscription.getKey()));
                acceptedUsersInfo.append("\n");
                acceptedUsersInfo.append(messageSource.getMessage("name", null, LocaleContextHolder.getLocale())).append(" ").append(userInscription.getKey().getName()).append("\n");
                acceptedUsersInfo.append( messageSource.getMessage("phoneNumber", null, LocaleContextHolder.getLocale())).append(" ").append(userInscription.getKey().getTel()).append("\n");
                acceptedUsersInfo.append(messageSource.getMessage("email", null, LocaleContextHolder.getLocale())).append(" ").append(userInscription.getKey().getEmail()).append("\n");
            }
            sendEmail(changaOwner.getEmail(), subject, changaSettledEmailToChangaOwner(changa, changaOwner, acceptedUsersInfo.toString(), accpetedUsers.getValue().size()));
        }
    }


    @Override
    public void sendResetPasswordEmail(User user, String appUrl) throws MessagingException {
        String token = UUID.randomUUID().toString();
        userService.createVerificationToken(user, token);
        String resetUrl = appUrl + "/reset-password/validate?id=" + user.getUser_id() + "&token=" + token;
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,true);
        helper.setTo(user.getEmail());
        helper.setSubject(messageSource.getMessage("resetPassword.Subject",null, LocaleContextHolder.getLocale()));
        helper.setText(resetPasswordEmailBody(resetUrl), true);
        emailSender.send(message);
    }

    //TODO emails from html templates
    private String mailConfirmationEmailBody(User user, String confirmUrl) {
        return messageSource.getMessage("mailConfirmation.Body", null, LocaleContextHolder.getLocale()) +
                "\n" +
                confirmUrl;
    }

    //TODO emails from html templates
    private String resetPasswordEmailBody(String resetUrl) {
        return messageSource.getMessage("resetPassword.Body", null, LocaleContextHolder.getLocale()) +
                ' ' + resetUrl;
    }

    //TODO emails from html templates
    private String joinRequestEmailBody(Changa changa, User changaOwner, User requestingUser){
        return changaOwner.getName() + ",\n" +
                requestingUser.getName() + " " +
                messageSource.getMessage("sendJoinRequest.Body", null, LocaleContextHolder.getLocale()) + " " +
                changa.getDescription();
    }

    //TODO emails from html templates
    private String changaSettledEmailToInscribedUserBody(Changa changa, User changaOwner, User inscriptedUser){
        return inscriptedUser.getName() + ",\n" +
                changaOwner.getName() + " " +
                messageSource.getMessage("changaHasBeenSettled", null, LocaleContextHolder.getLocale()) + " " + changa.getTitle() + ".\n" +
                messageSource.getMessage("changaOwnerInfo", null, LocaleContextHolder.getLocale()) + "\n" +
                messageSource.getMessage("name", null, LocaleContextHolder.getLocale()) + " " + changaOwner.getName() + "\n" +
                messageSource.getMessage("phoneNumber", null, LocaleContextHolder.getLocale()) + " " + changaOwner.getTel() + "\n" +
                messageSource.getMessage("email", null, LocaleContextHolder.getLocale()) + " " + changaOwner.getEmail() + "\n" +
                messageSource.getMessage("pleaseContactChangaOwner", null, LocaleContextHolder.getLocale())
                ;
    }

    private String changaSettledEmailToChangaOwner(Changa changa, User changaOwner, String acceptedUsersInfo, int nUsersInscripted) {
        String text =  changaOwner.getName() + ",\n";
        if(nUsersInscripted > 1){
            text += messageSource.getMessage("inscriptedUsersInfo", null, LocaleContextHolder.getLocale()) + " ";
        } else {
            text += messageSource.getMessage("inscriptedUserInfo", null, LocaleContextHolder.getLocale()) + " ";
        }
        text +=  changa.getTitle() + " " +
                messageSource.getMessage("areTheFollowing", null, LocaleContextHolder.getLocale()) + "\n" +
                acceptedUsersInfo;
        return  text;

    }
}
