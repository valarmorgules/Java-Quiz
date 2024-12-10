package project.ui;

import java.awt.Dimension;
import com.apple.eawt.Application;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import project.file.JsonKeyFileManager;
import project.file.JsonQuizFileManager;
import project.file.MultyQuizFileManager;
import project.quiz.Question;
import project.quiz.QuestionResult;
import project.quiz.Quiz;
import project.quiz.QuizChecker;
import project.quiz.QuizKey;

public class Menu {
	private MultyQuizFileManager manager; // Менеджер для роботи з файлами квізів у кількох форматах
	private JsonKeyFileManager keyManager; // Менеджер для роботи з файлом ключів 
	private QuizChecker checker; // Клас для перевірки відповідей
	private Quiz quiz = new Quiz();
	
	// Конструктор класу, ініціалізує менеджери та оновлює квіз
	public Menu() {
		manager = new MultyQuizFileManager("quiz");
		keyManager = new JsonKeyFileManager("key.json");
		
		UpdateQuiz();
	}
	// Метод для оновлення даних квізу та ключів
	private void UpdateQuiz() {
		QuizKey key = new QuizKey();
		try {
			key = keyManager.loadKey(); // Завантаження ключів
			quiz = manager.loadQuiz(); // Завантаження квізу
		} catch (IOException e) {
			e.printStackTrace(); // Вивід помилки у випадку невдачі
		}
		
		checker = new QuizChecker(key); // Ініціалізація перевіряльника з оновленими ключами
	}
	// Метод для генерації випадкового квізу з обмеженою кількістю питань
	private Quiz getRandomQuiz(int count){
		try {
			Quiz fullQuiz = manager.loadQuiz(); // Завантаження повного списку питань
			fullQuiz.questions.removeIf(new Predicate<Question>() { // Видалення питань без відповідей
				@Override
				public boolean test(Question t) {
					return t.answers.size()==0;
				}
			});
			
			Random rnd = new Random();
			Quiz quiz = new Quiz();
			
			// Вибір випадкових питань
			for(int i = 0; i < count && fullQuiz.questions.size()>0; i++) {
				int index = rnd.nextInt(0, fullQuiz.questions.size());
				quiz.questions.add(fullQuiz.questions.get(index));
				fullQuiz.questions.remove(index); // Видалення вибраного питання зі списку
			}
		
			return quiz;
		} catch (IOException e) {

		}
		return new Quiz(); // Повернення порожнього квізу у випадку помилки
	}
	
	// метод для запуску програми
	public void run() {
		JFrame frame = new JFrame(); // Головне вікно програми
		MainPanel mainPanel = new MainPanel(); // Головна панель
		frame.setContentPane(mainPanel);
		
		// Оновлення квізу та повернення до головного меню
		PanelChange mainChange = new PanelChange() {
			@Override
			public void goToPanel() {
				UpdateQuiz();
				
				frame.setContentPane(mainPanel);
				frame.validate();
			}
		};
		
		AnswerCallable answerClick = new AnswerCallable() {
			@Override
			public void call(List<Integer> answers) {	
				List<QuestionResult> results = checker.check(quiz, answers); // Перевірка відповідей
				QuizResultPanel resPanel = new QuizResultPanel(results, mainChange); // Відображення результатів
				frame.setContentPane(resPanel);
				frame.validate();
			}
		};
			
		
		// Обробка натискання "Take Quiz"
		mainPanel.addTakerChange(new PanelChange() {
					@Override
					public void goToPanel() {
						if(quiz.questions.size() == 0) {
							JOptionPane.showMessageDialog(null, "There aren't any questions!");
							return;
						}
						quiz = getRandomQuiz(12); // Отримання 12 випадкових питань
						QuizTakePanel panel = new QuizTakePanel(quiz,  answerClick);
						frame.setContentPane(panel);
						frame.validate();
					}
				} );
		
		// Обробка натискання "Admin Mode"
		mainPanel.addAdminChange(new PanelChange() {
			@Override
			public void goToPanel() {
				
				String password = JOptionPane.showInputDialog(null, "Enter password"); // Введення пароля
				if(password == null) return;
				
				if(!password.equals("admin")) { 
					JOptionPane.showMessageDialog(null, "Invalid Password!");
					return;
				}
				
				// Відображення панелі адміністратора
				AdminPanel panel = new AdminPanel(manager, keyManager, mainChange);
				
				frame.setContentPane(panel);
				frame.validate();
			}
		} );
	
		// Налаштування вікна
		frame.setMinimumSize(new Dimension(600, 300));
		frame.setTitle("Java Quiz");
		Image icon = Toolkit.getDefaultToolkit().getImage("icon.png");
        frame.setIconImage(icon);

    
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	
}
