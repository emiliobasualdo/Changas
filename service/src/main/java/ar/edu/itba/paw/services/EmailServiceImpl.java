package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.services.ChangaService;
import ar.edu.itba.paw.interfaces.services.EmailService;
import ar.edu.itba.paw.interfaces.services.InscriptionService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.interfaces.util.Validation;
import ar.edu.itba.paw.models.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import static ar.edu.itba.paw.interfaces.util.Validation.EMAIL_ERROR;
import static ar.edu.itba.paw.interfaces.util.Validation.OK;

@Service
public class EmailServiceImpl implements EmailService {

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

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailServiceImpl.class);


    @Override
    public Validation sendSimpleEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        return sendEmail(to, message);
    }

    @Override
    public Validation sendMimeEmail(String to, String subject, String text) {
        MimeMessage message = emailSender.createMimeMessage();
        try {
            MimeMessageHelper helper;
            helper = new MimeMessageHelper(message,true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text, true);
        } catch (MessagingException e) {
            return EMAIL_ERROR;
        }
        return sendEmail(to, message);
    }

    private Validation sendEmail(String to, Object  message) {
        try {
            if(message instanceof MimeMessage) {
                emailSender.send((MimeMessage) message);
            } else if (message instanceof SimpleMailMessage){
                emailSender.send((SimpleMailMessage) message);
            } else {
                System.out.println("Wrong message type");
                LOGGER.debug("Wrong message type");
            }
            System.out.println("sending email to: " + to);
            LOGGER.debug("Sending email to {}",to);
        } catch (Exception ex){
            System.out.println("failed sending email to: " + to);
            LOGGER.error("Failed to send email to: {}", to);
            return EMAIL_ERROR;
        }
        return OK;
    }

    @Override
    public Validation sendJoinRequestEmail(Changa changa, User changaOwner, User requestingUser) {
        String subject = messageSource.getMessage("sendJoinRequest.Subject", null, LocaleContextHolder.getLocale());
        return sendSimpleEmail(changaOwner.getEmail(), subject, joinRequestEmailBody(changa, changaOwner, requestingUser));
    }

    @Override
    public Validation sendMailConfirmationEmail(User user, String appUrl) {
        Either<VerificationToken, Validation> verificationToken = userService.createVerificationToken(user);
        if(!verificationToken.isValuePresent()) {
           return verificationToken.getAlternative();
        }
        String confirmUrl = appUrl + "/registration-confirm?token=" + verificationToken.getValue().getToken();
        return sendMimeEmail(user.getEmail(), messageSource.getMessage("mailConfirmation.Subject",null, LocaleContextHolder.getLocale()), mailConfirmationEmailBody(user, confirmUrl));
    }


    @Override
    public Validation resendMailConfirmationEmail(String existingToken, String registrationConfirmUrl) {
        Either<VerificationToken, Validation> newVerificationToken = userService.createNewVerificationToken(existingToken);
        if(!newVerificationToken.isValuePresent()) {
            return newVerificationToken.getAlternative();
        }
        Either<User, Validation> user = userService.findById(newVerificationToken.getValue().getUserId());
        if(!user.isValuePresent()) {
            return user.getAlternative();
        }
        String confirmUrl = registrationConfirmUrl + "?token=" + newVerificationToken.getValue().getToken();
        return  sendMimeEmail(user.getValue().getEmail(), messageSource.getMessage("mailConfirmation.Subject",null, LocaleContextHolder.getLocale()), mailConfirmationEmailBody(user.getValue(), confirmUrl));
    }

    @Override
    public Validation sendChangaSettledEmails(long changaId) {
        String subject = messageSource.getMessage("sendChangaSettledEmail.Subject", null, LocaleContextHolder.getLocale());
        Changa changa = changaService.getChangaById(changaId).getValue();
        User changaOwner = userService.findById(changa.getUser_id()).getValue();
        Either<List<Pair<User, Inscription>>, Validation> accpetedUsers = inscriptionService.getAcceptedUsers(changaId);
        Validation val = OK;
        if(accpetedUsers.isValuePresent()) {
            StringBuilder acceptedUsersInfo = new StringBuilder("");
            for (Pair<User, Inscription> userInscription : accpetedUsers.getValue()) {
                if(sendSimpleEmail(userInscription.getKey().getEmail(), subject, changaSettledEmailToInscribedUserBody(changa, changaOwner, userInscription.getKey())) == EMAIL_ERROR){
                    val = EMAIL_ERROR;
                }
                acceptedUsersInfo.append("\n");
                acceptedUsersInfo.append(messageSource.getMessage("name", null, LocaleContextHolder.getLocale())).append(" ").append(userInscription.getKey().getName()).append("\n");
                acceptedUsersInfo.append( messageSource.getMessage("phoneNumber", null, LocaleContextHolder.getLocale())).append(" ").append(userInscription.getKey().getTel()).append("\n");
                acceptedUsersInfo.append(messageSource.getMessage("email", null, LocaleContextHolder.getLocale())).append(" ").append(userInscription.getKey().getEmail()).append("\n");
            }
            if(sendSimpleEmail(changaOwner.getEmail(), subject, changaSettledEmailToChangaOwner(changa, changaOwner, acceptedUsersInfo.toString(), accpetedUsers.getValue().size())) == EMAIL_ERROR) {
                val = EMAIL_ERROR;
            }
        }
        return val;
    }

    @Override
    public Validation sendResetPasswordEmail(User user, String appUrl)  {
        Either<VerificationToken, Validation> verificationToken = userService.createVerificationToken(user);
        if(!verificationToken.isValuePresent()) {
            return verificationToken.getAlternative();
        }
        String resetUrl = appUrl + "/reset-password/validate?id=" + user.getUser_id() + "&token=" + verificationToken.getValue().getToken();
        return sendMimeEmail(user.getEmail(), messageSource.getMessage("resetPassword.Subject",null, LocaleContextHolder.getLocale()), resetPasswordEmailBody(resetUrl));
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
                messageSource.getMessage("changaHasBeenSettled", null, LocaleContextHolder.getLocale()) + " " + changa.getTitle() + ".\n\n" +
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
