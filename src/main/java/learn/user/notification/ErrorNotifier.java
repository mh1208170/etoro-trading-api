package learn.user.notification;

import learn.monitoring.PositionError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Component
@Slf4j
public class ErrorNotifier {

    @Autowired
    ApplicationArguments applicationArguments;

    private String username = "oleh_22_08@outlook.com";
    private String password;

    @PostConstruct
    public void init() {

        password = applicationArguments.getSourceArgs()[2];

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "outlook.office365.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS
        session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
    }

    private Session session;


    public void sendErrorNotification(PositionError error) {
        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("oleh_22_08@outlook.com"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse("oleh_22_08@outlook.com")
            );
            message.setSubject("EtoroBot position error");
            message.setText(error.toString());

            Transport.send(message);

        } catch (MessagingException e) {
            log.error("Failed to send message!");
        }
    }
}
