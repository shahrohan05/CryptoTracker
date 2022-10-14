package com.rohandev.cryptotracker.service;

import io.github.cdimascio.dotenv.Dotenv;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class EmailNotificationService {

    private static Dotenv DOTENV;

    private static final String MSG_TEMPLATE = "Hi there, \n\n%s price just went %s %.2f %s. Time to %s! \n\n "
            + "\nCurrent price - %.2f %s"
            + "\n\nRegards,\n%s";

    static {
        DOTENV = Dotenv.configure().load();
    }

    @Value("${crypto-tracker.from-email}")
    private String fromEmail;

    @Value("${crypto-tracker.coin-id}")
    private String coinId;

    @Value("${crypto-tracker.currency}")
    private String currency;

    @Value("${crypto-tracker.project-name}")
    private String projectName;

    private Logger logger = LoggerFactory.getLogger(EmailNotificationService.class);

    public void sendEmailNotification(Double price) {
        Double minPrice = Double.valueOf(DOTENV.get("crypto_price_min"));
        Double maxPrice = Double.valueOf(DOTENV.get("crypto_price_max"));
        String to = DOTENV.get("send_to_email");

        if (price < minPrice || price > maxPrice) {

            Session session = getSMTPSession();

            try {

                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(fromEmail));
                message.setRecipients(Message.RecipientType.TO,
                        InternetAddress.parse(to));

                message.setSubject(getSubject(price, minPrice, maxPrice));
                message.setText(getMessage(price, minPrice, maxPrice));

                Transport.send(message);
                logger.info("email sent!");
            } catch (Exception e) {
                logger.error("Error sending email!", e);
            }
        }
    }

    private String getMessage(Double price, Double minPrice, Double maxPrice) {
        if (price < minPrice) {
            return String.format(MSG_TEMPLATE, coinId.toUpperCase(), "below", minPrice, currency.toUpperCase(), "buy", price, currency.toUpperCase(), projectName);

        } else if (price > maxPrice) {
            return String.format(MSG_TEMPLATE, coinId.toUpperCase(), "above", maxPrice, currency.toUpperCase(), "buy", price, currency.toUpperCase(), projectName);

        }
        return null;
    }

    private Session getSMTPSession() {

        String host = DOTENV.get("mail_host");
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "2525");
        props.put("mail.smtp.ssl.trust", host);

        final String username = DOTENV.get("mail_username");
        final String password = DOTENV.get("mail_password");

        return Session.getInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
    }

    private String getSubject(Double price, Double minPrice, Double maxPrice) {
        if (price < minPrice) {
            return "Crypto Tracker - low price alert on " + coinId.toUpperCase();
        } else if (price > maxPrice) {
            return "Crypto Tracker - high price alert on " + coinId.toUpperCase();
        }
        return null;
    }


}
