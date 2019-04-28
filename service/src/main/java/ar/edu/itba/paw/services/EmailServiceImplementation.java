package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.services.EmailService;
import ar.edu.itba.paw.models.Changa;
import ar.edu.itba.paw.models.User;
import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImplementation implements EmailService{

    @Autowired
    public JavaMailSender emailSender;

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
        sendEmail(changaOwner.getEmail(), "Solicitud", joinRequestEmailBody(changa, changaOwner, requestingUser));
    }


    //TODO INTERNACIONALIZAR
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
