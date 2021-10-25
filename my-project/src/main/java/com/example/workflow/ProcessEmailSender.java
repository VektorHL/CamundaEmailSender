package com.example.workflow;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.tools.JavaFileManager;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Component
public class ProcessEmailSender implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) throws Exception {
        String toMail = execution.getVariable("emailAdress").toString(); //получение почты получателя
        String text = execution.getVariable("text").toString(); //получение текста письма, без темы

        final String username = "a.martin.am567@gmail.com"; //почта и пароль отправителя
        final String password = "93847BHJvd34fg7324JKHG"; //

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "465");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.socketFactory.port", "465");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
        });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("a.martin.am567@gmail.com")); //почта отправителя
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(toMail) //почта получателя
            );
            message.setSubject("Testing Gmail SSL"); //тема письма
            message.setText(text); // текст письма

            Transport.send(message); //отправка письма

            System.out.println("Done"); //сообщение в консоль при успешной отправке

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
