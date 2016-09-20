package com.revature.mail;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.PasswordAuthentication;


//Logs into an existing GMAIL account and sends email from that account.
//Place JavaMail 1.5 (javax.mail.jar) on the Classpath for the project.
public class SendMailClient {

	//The "From" fields for an existing GMAIL account:
	public static final String from ="lds.system.notification@gmail.com";
	public static final String username ="lds.system.notification";
	public static final String password ="revature";
	
	//The "To" fields:
	public  String to = "recipient email address";
	public  String subject ="recipient SUBJECT";
	public  String text ="recipient MESSAGE";
	
	
	
	public SendMailClient(String to, String subject, String text) {
		super();
		this.to = to;
		this.subject = subject;
		this.text = text;
	}



	public String getTo() {
		return to;
	}



	public void setTo(String to) {
		this.to = to;
	}



	public String getSubject() {
		return subject;
	}



	public void setSubject(String subject) {
		this.subject = subject;
	}



	public String getText() {
		return text;
	}



	public void setText(String text) {
		this.text = text;
	}



	public void sendMail() throws Exception {
		
		//GMAIL server properties
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		Session session = Session.getDefaultInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username,password);
					}
				});

			try {

				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress(from));
				message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
				message.setSubject(subject);
				message.setText(text);

				Transport.send(message);

				System.out.println("Message sent!");

			} catch (MessagingException e) {
				throw new RuntimeException(e);
			}
		}
	
}
		
		
		