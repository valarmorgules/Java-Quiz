package project.ui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import project.file.JsonKeyFileManager;
import project.file.JsonQuizFileManager;
import project.file.MultyQuizFileManager;
import project.quiz.Key;
import project.quiz.Question;
import project.quiz.Quiz;
import project.quiz.QuizKey;

public class AdminPanel extends JPanel{
	private final Font titleFont = new Font("Arial", Font.BOLD, 30);
	private Question currentQuestion;
	private int currentIndex = 0;
	
	private List<JTextField> answerFileds = new ArrayList<JTextField>();
	private List<JRadioButton> answerRadios = new ArrayList<JRadioButton>();
	
	private JPanel formPanel = new JPanel();
	private JTextField questionField = new JTextField();
	private ButtonGroup radioGroup = new ButtonGroup();
	private int correctAnswer = 0;
	
	private JComboBox<String> box = new JComboBox<String>();
	private Quiz quiz = new Quiz();
	private QuizKey quizKey = new QuizKey();
	private int nextId = 0;
	
	private MultyQuizFileManager manager;
	private JsonKeyFileManager keyManager;
	
	private void turnAnswerRow(int index, boolean condition) {
		answerFileds.get(index).setEnabled(condition);
		JRadioButton btn = answerRadios.get(index);
		btn.setEnabled(condition);
		if(btn.isSelected() && !condition) {
			answerRadios.get(index-1).setSelected(true);
		}
	}
	
	private void addAnswer() {
		final int index = answerFileds.size();
		
		JLabel answerLabel = new JLabel();
		answerLabel.setText("Answer text " + (answerFileds.size()+1));
		formPanel.add(answerLabel);
		
		JTextField field = createAnswerField(index);
		answerFileds.add(field);
		formPanel.add(field);
		
		addRadioButton(index, field);
	}
	private JTextField createAnswerField(int index) {
		JTextField field = new JTextField();
		
		field.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void insertUpdate(DocumentEvent e) {
				if(index + 1 >= answerFileds.size()) return;
				
				boolean condition = answerFileds.get(index).getText().length() > 0;
				
				if(condition) {
					for(int i = index+1; i < answerFileds.size(); i++) {
						boolean hasText = answerFileds.get(i-1).getText().length() > 0;
						turnAnswerRow(i, hasText);
					}
					return;
				}
				
				for(int i = answerFileds.size() - 1; i > index; i--) {
					turnAnswerRow(i, false);
				}
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				insertUpdate(e);
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
			}	
		});
		
		field.setEnabled(false);
		
		return field;
	}
	
	private void addRadioButton(int index, JTextField field) {
		JRadioButton radio = new JRadioButton();
		radio.setEnabled(false);
		answerRadios.add(radio);
		radioGroup.add(radio);
		formPanel.add(radio);
		
		if(index == 0) {
			radio.setSelected(true);
			field.setEnabled(true);
			radio.setEnabled(true);
		}
		
		radio.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				correctAnswer = index;
			}
		});
	}
	
	private void updateForm(int index, Question question) {
		int correct = quizKey.keys.get(index).correct;
		correctAnswer = correct;
		
		for(int i = 0; i < question.answers.size() && i < answerFileds.size(); i++) {
			answerFileds.get(i).setText(question.answers.get(i));
		}
		
		for(int i = question.answers.size(); i < answerFileds.size(); i++) {
			answerFileds.get(i).setText("");
		}
		
		questionField.setText(question.text);
		answerRadios.get(correct).setSelected(true);
	}
	
	private void updateQuestion() {
		Question question = quiz.questions.get(currentIndex);
		Key key = quizKey.keys.get(currentIndex);
		key.correct = correctAnswer;
		
		question.text = questionField.getText();
		question.answers.clear();
		
		for(JTextField field: answerFileds) {			
			if(field.isEnabled() && field.getText().length() > 0) {
				question.answers.add(field.getText());
			}
		}
		
		saveAll();
		
		DefaultComboBoxModel<String> model = (DefaultComboBoxModel<String>) box.getModel();

		model.removeElementAt(currentIndex);
		model.insertElementAt(question.text, currentIndex+1);
		
		box.setSelectedIndex(currentIndex+1);
	}
	
	private void saveAll() {
		try {			
			manager.saveQuiz(quiz);
			keyManager.saveKey(quizKey);
			JOptionPane.showMessageDialog(null, "Saved!");
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error during saving!");
		}
	}
	
	private Question addNewQuestion() {
		Question question = new Question();
		question.text = "New question";
		question.id = nextId;
		
		for(Question qu: quiz.questions) {
			if(qu.text.equals(question.text)) {
				question.text = "" + nextId + "." + question.text;
			}
		}
		
		quiz.questions.add(question);
		
		
		Key key = new Key();
		key.id = nextId++;
		quizKey.keys.add(key);
		
		return question;
	}
	
	private void loadQuestions() {
		try {
			quiz = manager.loadQuiz();
			quizKey = keyManager.loadKey();
		} catch (IOException e) {}
		
		if(quiz.questions.size() > 0) {
			nextId = quiz.questions.get(quiz.questions.size()-1).id+1;
		}
		else {
			addNewQuestion();
		}
	}
	
	public AdminPanel(MultyQuizFileManager manager, JsonKeyFileManager keyManager, PanelChange mainPanel) {
		this.manager = manager;
		this.keyManager = keyManager;
		
		loadQuestions();
		
		this.setBorder(new EmptyBorder(20, 20, 20, 20));
		this.setLayout(new GridLayout(1, 2));
		JPanel panel = new JPanel();
		this.add(panel);
		
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBorder(new EmptyBorder(0, 0, 0, 20));
		
		panel.add(createTitle());
		
		setBox();
		panel.add(box);

		setForm();
		this.add(formPanel);
		
		panel.add(createButtons(mainPanel));
		
		if(quiz.questions.size()>0) updateForm(0, quiz.questions.get(0));
	}
	
	private JLabel createTitle() {
		JLabel title = new JLabel();
		title.setText("Admin mode");
		title.setFont(titleFont);
		title.setAlignmentX(CENTER_ALIGNMENT);
		
		return title;
	}
	
	private void setBox() {
		for(int i = 0; i < quiz.questions.size(); i++) {
			box.addItem(quiz.questions.get(i).text);
		}
		
		box.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				currentIndex = box.getSelectedIndex();
				if(currentIndex < 0 || currentIndex >= quiz.questions.size()) return;
				
				updateForm(currentIndex, quiz.questions.get(currentIndex));
			}
			
		});
	}
	
	private void setForm() {
		formPanel.setLayout(new GridLayout(0, 3, 5, 5));
		
		JLabel questionLabel = new JLabel("Question text");
		formPanel.add(questionLabel);
		
		formPanel.add(questionField);
		
		JLabel correctLabel = new JLabel("Is Correct?");
		formPanel.add(correctLabel);
		for(int i = 0; i < 6; i++)
			addAnswer();
	}
	
	private JPanel createButtons(PanelChange mainPanel) {
		JPanel btns = new JPanel();
		
		
		btns.add(createSave());
		btns.add(createNew());
		btns.add(createRemove());
		btns.add(createBack(mainPanel));
		
		return btns;
	}
	
	private JButton createSave() {
		JButton saveBtn = new JButton();
		saveBtn.setText("Save");
		saveBtn.setAlignmentX(CENTER_ALIGNMENT);
		saveBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				updateQuestion();
			}
		});
		
		return saveBtn;
	}
	
	private JButton createNew() {//create button to add new question
		JButton newBtn = new JButton();
		newBtn.setText("New");
		newBtn.setAlignmentX(CENTER_ALIGNMENT);
		
		newBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				Question question = addNewQuestion();
				box.addItem(question.text);
				box.setSelectedIndex(quiz.questions.size()-1);
				
			}
		});
		
		return newBtn;
	}
	
	private JButton createRemove() {//create button to remove question
		JButton removeBtn = new JButton();
		removeBtn.setText("Remove");
		removeBtn.setAlignmentX(CENTER_ALIGNMENT);
		
		removeBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				int response = JOptionPane.showConfirmDialog(null, 
						"Do you really want to remove question?");
				
				if(response == JOptionPane.YES_OPTION) {
					quiz.questions.remove(currentIndex);
					quizKey.keys.remove(currentIndex);
					DefaultComboBoxModel<String> model = (DefaultComboBoxModel<String>) box.getModel();

					model.removeElementAt(currentIndex);
					saveAll();
					
					if(quiz.questions.size() == 0) {
						Question question = addNewQuestion();
						box.addItem(question.text);
					}
					
					box.setSelectedIndex(0);
				}
				
			}
		});
		
		return removeBtn;
	}
	
	private JButton createBack(PanelChange mainPanel) {//create button to back to main screen
		JButton backBtn = new JButton();
		backBtn.setText("Back");
		
		backBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				mainPanel.goToPanel();
			}
		});
		backBtn.setAlignmentX(CENTER_ALIGNMENT);
		
		return backBtn;
	}
}