package kz.singularity.hackaton.backendhackatonvegetables.email;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService{
    private final JavaMailSender emailSender;

    @Override
    public void sendSimpleMessage(
            String to,
            String subject,
            String text) {

        try {
            String from = "alimgaziyevserzhan@yandex.ru";

            Properties properties = System.getProperties();
            properties.setProperty("mail.smtp.host", "smtp.yandex.ru");
            properties.setProperty("mail.smtp.port", "465");
            properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            properties.setProperty("mail.smtp.socketFactory.port", "465");
            properties.setProperty("mail.smtp.auth", "true");

            Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(from, "falxdsdmbpxpbrve");
                }
            });

            session.setDebug(true);
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject);
            message.setText(text);
            Transport.send(message);
            System.out.println("Email Sent successfully....");
        } catch (Exception e) {}
    }
}
