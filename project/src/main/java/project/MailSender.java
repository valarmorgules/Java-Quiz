//MailSender.java 
// done by Kostiuchenko Mariia (comp mat 2)
// date 28.11.2024

package project;

import java.util.List;
import java.util.Properties;

import jakarta.mail.*;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import project.quiz.QuestionResult;

public class MailSender {
	private Properties properties = new Properties();
	private Session session;
	private String username;
	
	// Конструктор класу MailSender
    // Приймає логін та пароль для аутентифікації на сервері
	
	public MailSender(String username, String password) {
		properties.put("mail.smtp.host", "smtp.gmail.com"); // Хост Gmail SMTP
		properties.put("mail.smtp.port", "587"); // Порт для TLS-з'єднання
		properties.put("mail.smtp.auth", "true"); // Увімкнення аутентифікації
		properties.put("mail.smtp.starttls.enable", "true"); // Увімкнення TLS
		
		session = Session.getInstance(properties, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication () {
				return new PasswordAuthentication(username, password);
			}
		});
		
		this.username = username;
	}
	
	// Метод для відправки електронного листа
	public void send(String reciever, String subject, String text) throws AddressException, MessagingException {
		Message msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress(username));
		msg.setRecipient(Message.RecipientType.TO, new InternetAddress(reciever));
		
		msg.setSubject(subject);
		msg.setContent(text, "text/html");
		
		Transport.send(msg);
	}
}
