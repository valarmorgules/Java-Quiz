//ResultSender.java 
// done by Kostiuchenko Mariia (comp mat 2)
// date 28.11.2024

package project;

import java.util.List;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Transport;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import project.quiz.QuestionResult;

public class ResultSender extends MailSender {
		
//функція для відправлення повідомлення з результататми на пошту
	// Приймає отримувача та список результатів тестування
	
	public ResultSender(String username, String password) {
		super(username, password);
		
	}
	public void sendResults(String reciever, List<QuestionResult> results) throws AddressException, MessagingException {
		StringBuilder builder = new StringBuilder();
		builder.append("<h1>Hello, there are your results!</h1>");
		
		// Підрахунок правильно відповіданих питань
		int right = 0;
		for(QuestionResult result: results) {
			if(result.isCorrect) right++;
		}
		// Додаємо до листа загальну кількість правильних відповідей
		builder.append("<h2>Score: " + right + "/" + results.size() + "</h2>");
		
		// формуємо таблицю з результатами
		builder.append("<table>");
		builder.append("<tr><th>#</th><th>Question</th><th>Your answer</th><th>Correct?</th></tr>");
		
		// Додаємо кожен результат до таблиці
		int index = 1;
		for(QuestionResult result: results) {
			builder.append("<tr><td>"+index+"</td><td>" + result.question.text + "</td>");
			builder.append("<td>" +result.question.answers.get(result.userAnswer)+ "</td>");
			builder.append("<td>" +result.isCorrect + "</td></tr>");
			index++;
		}
		
		// Завершуємо таблицю
		builder.append("</table>");
		
		// Додаємо підпис до листа
		builder.append("<p> - Thank you for taking our quiz!</p>");
		
		// Використовуємо метод send з базового класу для відправки листа
		send(reciever, "Java quiz - Results", builder.toString());
	}
}
