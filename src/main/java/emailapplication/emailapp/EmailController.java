package emailapplication.emailapp;

import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

@RestController
public class EmailController {

    @RequestMapping("/sendmail")
    public String sendEmail() throws AddressException, MessagingException, IOException {
        sendmail();
        printNO();
        System.out.println("\n*********** END *********\n");
        return "Email sent successfully ! Check your mail box please.";
    }

    void printNO() {
        for(int i=0; i<100; i++) {
            System.out.println("No. is :- " + i);
        }
    }

    void sendmail() throws AddressException, MessagingException, IOException {

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("azzamjafri98@gmail.com", "2992652jafri");
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("azzamjafri98@gmail.com", false));

        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("zakirhassan114@gmail.com"));
        message.setSubject("Demo Email");
        message.setContent("This is just an example email by spring boot", "text/html");
        message.setSentDate(new Date());


        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent("Example email", "text/html");


        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);
        MimeBodyPart attachPart = new MimeBodyPart();

        attachPart.attachFile("/home/azzam/p.java");
        multipart.addBodyPart(attachPart);
        message.setContent(multipart);
        Transport.send(message);

    }

}