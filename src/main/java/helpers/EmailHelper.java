package helpers;

import org.apache.logging.log4j.LogManager;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.security.Security;
import java.util.Properties;

/**
 * Created by makri on 14/06/2017.
 */
public class EmailHelper {

    public void getEmail() {
        Properties props = new Properties();
        props.put("mail.smtp.host", "127.0.0.1");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "5587");

        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("username", "password");
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("from@no-spam.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse("to@no-spam.com"));
            message.setSubject("Testing Subject");
            message.setText("Dear Mail Crawler," +
                    "\n\n No spam to my email, please!");

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }


    public void receiveEmails() {
        try {

            Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
            final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";

            // Get a Properties object
            Properties props = System.getProperties();
            props.setProperty("proxySet", "true");
            props.setProperty("http.proxyHost", "syd-proxy-dmz.au.fcl.internal");
            props.setProperty("http.proxyPort", "3128");

            props.setProperty("mail.pop3.socketFactory.class", SSL_FACTORY);
            props.setProperty("mail.pop3.socketFactory.fallback", "false");
            props.setProperty("mail.pop3.port", "995");
            props.setProperty("mail.pop3.socketFactory.port", "995");

            Session session = Session.getDefaultInstance(props, null);


            URLName urln = new URLName("pop3", "pop.gmail.com", 995, null,
                    "fcl.online.automation@gmail.com", "fcL0nlin3");
            Store store = session.getStore(urln);

            //create properties field
//            Properties properties = new Properties();
//
//            properties.put("mail.pop3.host", host);
//            properties.put("mail.pop3.port", "995");
//            //  properties.setProperty("mail.store.protocol", "imaps");
//            //   properties.put("mail.pop3.starttls.enable", "true");
//            // host ="imap.gmail.com";//for imap protocol
//
//
//            Authenticator authenticator = new javax.mail.Authenticator() {
//                protected PasswordAuthentication getPasswordAuthentication() {
//                    return new PasswordAuthentication(
//                            username, password);
//                }
//
//            }    ;
//
//                Session emailSession;
//                Store store;
//            try
//
//                {
//                    emailSession = Session.getDefaultInstance(properties, null);
//                    store = emailSession.getStore("pop3");
//                } catch(
//                Exception e)
//
//                {
//                    logger.info(e.toString());
//                    return;
//                }

            //create the POP3 store object and connect with the pop server

            store.connect();
            // store.connect(this.host,this.username,this.password);

            //create the folder object and open it
            Folder emailFolder = store.getFolder("INBOX");
            emailFolder.open(Folder.READ_ONLY);

            // retrieve the messages from the folder in an array and print it
            Message[] messages = emailFolder.getMessages();
            logger.info("messages.length---" + messages.length);

            for (
                    int i = 0, n = messages.length;
                    i < n; i++)

            {
                Message message = messages[i];
                logger.info("---------------------------------");
                logger.info("Email Number " + (i + 1));
                logger.info("Subject: " + message.getSubject());
                logger.info("From: " + message.getFrom()[0]);
                logger.info("Text: " + message.getContent().toString());

            }

            //close the store and folder objects
            emailFolder.close(false);
            store.close();

        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public EmailHelper() {

        host = "pop.gmail.com";// change accordingly
        mailStoreType = "pop3";
        username = "fcl.online.automation@gmail.com";// change accordingly
        password = "fcL0nlin3";// change accordingly


    }

    public EmailHelper(String host, String mailStoreType, String username, String password) {

        host = "pop.gmail.com";// change accordingly
        mailStoreType = "pop3";
        username = "fcl.online.automation@gmail.com";// change accordingly
        password = "fcL0nlin3";// change accordingly


    }

    private String host = "";
    private String mailStoreType = "";
    private String username = "";
    private String password = "";
    private org.apache.logging.log4j.Logger logger = LogManager.getRootLogger();


}
