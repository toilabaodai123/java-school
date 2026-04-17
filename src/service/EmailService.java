package service;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class EmailService {
    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    private final Session session;

    public EmailService(String host, int port) {
        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", String.valueOf(port));
        props.put("mail.smtp.auth", "false");
        props.put("mail.smtp.starttls.enable", "false");

        session = Session.getInstance(props);
    }

    public void sendEmail(String from, String to, String subject, String body) throws MessagingException {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        message.setSubject(subject);
        message.setText(body);

        Transport.send(message);
        logger.info("Email sent to {}", to);
    }
}
