// QuizResultPanel.java 
// done by Kostiuchenko Mariia (comp mat 2)
// date 06.12.2024

package project.ui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import jakarta.mail.MessagingException;
import project.MailSender;
import project.ResultSender;
import project.quiz.QuestionResult;

//Панель для відображення результатів тесту та можливості надсилання результатів на електронну пошту
public class QuizResultPanel extends JPanel{
	private ResultSender sender; // Відправник результатів
	private List<QuestionResult> results; // Результати тесту
	private JTextField field; // Поле для введення електронної адреси
	private JLabel label; // Лейбл для відображення рахунку
	
	// Конструктор, який ініціалізує панель результатів
	public QuizResultPanel(List<QuestionResult> results, PanelChange mainPanel) {
		this.results = results;
		sender = new ResultSender("quizjava6@gmail.com", "bvoz ikzt mqiy ekdc");
		
		this.setBorder(new EmptyBorder(50, 50, 50, 50)); // Встановлення відступів
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // Вертикальне розташування компонентів
		
		field = new JTextField();
		field.setText("youre@mail.you"); // Значення за замовчуванням для електронної адреси
		
		label = new JLabel();
		int right = 0;
		for(QuestionResult result: results) {
			if(result.isCorrect) right++; // Підрахунок правильних відповідей
		}
		
		label.setText("Score: "+right + "/" + results.size()); // Встановлення тексту з рахунком
		
		// Кнопка для надсилання результатів
		JButton sendBtn = new JButton();
		sendBtn.setText("Send");
		
		sendBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				 sendResult();
			}
		});
		
		// Додавання компонентів до панелі
		this.add(label);
		label.setAlignmentX(CENTER_ALIGNMENT);
		this.add(Box.createVerticalStrut(10));
		this.add(field);
		field.setAlignmentX(CENTER_ALIGNMENT);
		this.add(Box.createVerticalStrut(10));
		this.add(sendBtn);
		sendBtn.setAlignmentX(CENTER_ALIGNMENT);
		this.add(Box.createVerticalStrut(10));
		
		// Кнопка для повернення назад
		JButton backBtn = new JButton();
		backBtn.setText("Back");
		
		backBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				mainPanel.goToPanel();
			}
		});
		this.add(backBtn);
		backBtn.setAlignmentX(CENTER_ALIGNMENT);
	}
	
	// Метод для надсилання результатів на електронну пошту
	private void sendResult() {
		String email = field.getText();
	
		
		Thread th = new Thread() { // Використання окремого потоку для виконання операції надсилання
			public void run() {
				try {
					sender.sendResults(email, results);
					JOptionPane.showMessageDialog(null, "Sent successfully!");
				} catch (MessagingException e) {
					JOptionPane.showMessageDialog(null, ""+e.getMessage());
					e.printStackTrace();
				}
			}
		};
		th.start();
		JOptionPane.showMessageDialog(null, "Try to send!");
	}
}
