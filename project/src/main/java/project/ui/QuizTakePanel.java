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

public class QuizTakePanel extends JPanel {
	private final Quiz quiz;
	private final List<Integer> answers = new ArrayList<Integer>();
	private int currentQuestion = 0;
	
	private final JLabel label = new JLabel();
	private final JPanel buttonsPanel = new JPanel();
	
	private final List<JButton> buttons = new ArrayList<JButton>();
	private final Color[] bgColors = {new Color(187, 143, 206), new Color(171, 235, 198)};
	private final Font btnFont = new Font("Arial", Font.PLAIN, 20);
	
	private final AnswerCallable finish;
	
	public QuizTakePanel(Quiz quiz, AnswerCallable finish) {
		this.quiz = quiz;
		this.finish = finish;
	
		
		this.setBorder(new EmptyBorder(50, 50, 50, 50));
		this.setLayout(new BorderLayout());
		
		label.setFont(new Font("Arial", Font.BOLD, 30));
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setBorder(new EmptyBorder(0, 0, 10, 0));
		
		this.add(label, BorderLayout.NORTH);
		this.add(buttonsPanel, BorderLayout.CENTER);
		
		buttonsPanel.setLayout(new GridLayout(0, 2, 5, 5));

		updateQuestion();
		
	}
	
	private void updateQuestion() {
		Question question = quiz.questions.get(currentQuestion);
		label.setText(question.text);
		updateButtons(question);
	}
	
	private void nextQuestion() {
		currentQuestion++;
		if(currentQuestion >= quiz.questions.size()) {
			
			finish.call(answers);
			return;
		}
		updateQuestion();
	}
	
	private void updateButtons(Question question) {
		for(int i = 0; i < question.answers.size(); i++) {
			final int answer = i;
			
			if(i < buttons.size()) {
				buttons.get(i).setVisible(true);
				buttons.get(i).setText(question.answers.get(i));
			}else {
				JButton btn = new JButton(question.answers.get(i));
				btn.setFocusPainted(false);
				btn.setFont(btnFont);
				btn.setBackground(bgColors[((i+1)/2)%bgColors.length]);
				
				btn.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						answers.add(answer);
						nextQuestion();
					}
				});
				
				buttons.add(btn);
				buttonsPanel.add(btn, BorderLayout.NORTH);
			}
		}
		
		for(int i = question.answers.size(); i < buttons.size(); i++) {
			buttons.get(i).setVisible(false);
		}
	}
}
