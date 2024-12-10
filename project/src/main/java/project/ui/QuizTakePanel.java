// QuizTakePanel.java 
// done by Kostiuchenko Mariia (comp mat 2)
// date 06.12.2024

package project.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import project.quiz.Question;
import project.quiz.Quiz;

//Панель для проходження тесту
public class QuizTakePanel extends JPanel {
	private final Quiz quiz; // Об'єкт тесту
	private final List<Integer> answers = new ArrayList<Integer>(); // Відповіді користувача
	private int currentQuestion = 0; // Індекс поточного питання
	
	private final JLabel label = new JLabel(); // Лейбл для відображення тексту питання
	private final JPanel buttonsPanel = new JPanel(); // Панель для кнопок відповідей
	
	private final List<JButton> buttons = new ArrayList<JButton>(); // Список кнопок відповідей
	private final Color[] bgColors = {new Color(187, 143, 206), new Color(171, 235, 198)}; // Кольори для кнопок
	private final Font btnFont = new Font("Arial", Font.PLAIN, 20); // Шрифт для кнопок

	
	private final AnswerCallable finish; // Callback для завершення тесту
	
	// Конструктор, що ініціалізує панель
	public QuizTakePanel(Quiz quiz, AnswerCallable finish) {
		this.quiz = quiz;
		this.finish = finish;
	
		
		this.setBorder(new EmptyBorder(50, 50, 50, 50)); // Встановлення відступів
		this.setLayout(new BorderLayout()); // Використання BorderLayout
		
		label.setFont(new Font("Arial", Font.BOLD, 30)); // Налаштування шрифту питання
		label.setHorizontalAlignment(JLabel.CENTER); // Вирівнювання тексту по центру
		label.setBorder(new EmptyBorder(0, 0, 10, 0)); // Відступи для лейбла
		
		this.add(label, BorderLayout.NORTH); // Додавання лейбла
		this.add(buttonsPanel, BorderLayout.CENTER); // Додавання панелі кнопок
		
		buttonsPanel.setLayout(new GridLayout(0, 2, 5, 5)); // Грід для розташування кнопок

		updateQuestion(); // Оновлення відображення для першого питання
		
	}
	
	// Оновлює текст питання і кнопки
	private void updateQuestion() {
		Question question = quiz.questions.get(currentQuestion);
		label.setText(question.text); // Встановлення тексту питання
		updateButtons(question); // Оновлення кнопок відповідей
	}
	
	// Перехід до наступного питання
	private void nextQuestion() {
		currentQuestion++;
		if(currentQuestion >= quiz.questions.size()) {
			
			finish.call(answers); // Виклик callback, якщо питання закінчились
			return;
		}
		updateQuestion();
	}
	
	// Оновлення кнопок відповідей
	private void updateButtons(Question question) {
		for(int i = 0; i < question.answers.size(); i++) {
			final int answer = i; // Індекс відповіді
			
			if(i < buttons.size()) {
				buttons.get(i).setVisible(true); // Відображення існуючої кнопки
				buttons.get(i).setText(question.answers.get(i)); // Оновлення тексту кнопки
			}else {
				JButton btn = new JButton(question.answers.get(i)); // Створення нової кнопки
				btn.setFocusPainted(false);
				btn.setFont(btnFont);
				btn.setBackground(bgColors[((i+1)/2)%bgColors.length]); // Встановлення кольору кнопок 
				
				btn.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						answers.add(answer); // Збереження відповіді
						nextQuestion(); // Перехід до наступного питання
					}
				});
				
				buttons.add(btn);
				buttonsPanel.add(btn, BorderLayout.NORTH); // Додавання кнопки на панель
			}
		}
		
		for(int i = question.answers.size(); i < buttons.size(); i++) {
			buttons.get(i).setVisible(false); // Сховати зайві кнопки
		}
	}
}
