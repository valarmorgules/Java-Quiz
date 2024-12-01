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
	private MultyQuizFileManager manager;
	private JsonKeyFileManager keyManager;
	private QuizChecker checker;
	private Quiz quiz = new Quiz();
	
	public Menu() {
		manager = new MultyQuizFileManager("quiz");
		keyManager = new JsonKeyFileManager("key.json");
		
		UpdateQuiz();
	}
	private void UpdateQuiz() {
		QuizKey key = new QuizKey();
		try {
			key = keyManager.loadKey();
			quiz = manager.loadQuiz();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		checker = new QuizChecker(key);
	}
	private Quiz getRandomQuiz(int count){
		try {
			Quiz fullQuiz = manager.loadQuiz();
			fullQuiz.questions.removeIf(new Predicate<Question>() {
				@Override
				public boolean test(Question t) {
					return t.answers.size()==0;
				}
			});
			
			Random rnd = new Random();
			Quiz quiz = new Quiz();
			
			for(int i = 0; i < count && fullQuiz.questions.size()>0; i++) {
				int index = rnd.nextInt(0, fullQuiz.questions.size());
				quiz.questions.add(fullQuiz.questions.get(index));
				fullQuiz.questions.remove(index);
			}
		
			return quiz;
		} catch (IOException e) {

		}
		return new Quiz();
	}
	
	public void run() {
		JFrame frame = new JFrame();
		MainPanel mainPanel = new MainPanel();
		frame.setContentPane(mainPanel);
		
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
				List<QuestionResult> results = checker.check(quiz, answers);
				QuizResultPanel resPanel = new QuizResultPanel(results, mainChange);
				frame.setContentPane(resPanel);
				frame.validate();
			}
		};
			
		
		
		mainPanel.addTakerChange(new PanelChange() {
					@Override
					public void goToPanel() {
						if(quiz.questions.size() == 0) {
							JOptionPane.showMessageDialog(null, "There aren't any questions!");
							return;
						}
						quiz = getRandomQuiz(12);
						QuizTakePanel panel = new QuizTakePanel(quiz,  answerClick);
						frame.setContentPane(panel);
						frame.validate();
					}
				} );
		
		mainPanel.addAdminChange(new PanelChange() {
			@Override
			public void goToPanel() {
				
				String password = JOptionPane.showInputDialog(null, "Enter password");
				if(password == null) return;
				
				if(!password.equals("admin")) {
					JOptionPane.showMessageDialog(null, "Invalid Password!");
					return;
				}
				
				AdminPanel panel = new AdminPanel(manager, keyManager, mainChange);
				
				frame.setContentPane(panel);
				frame.validate();
			}
		} );
	
		frame.setMinimumSize(new Dimension(600, 300));
		frame.setTitle("Java Quiz");
		Image icon = Toolkit.getDefaultToolkit().getImage("icon.png");
        frame.setIconImage(icon);

    
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	
}
