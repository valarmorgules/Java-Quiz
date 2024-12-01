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

public class QuizResultPanel extends JPanel{
	private ResultSender sender;
	private List<QuestionResult> results;
	private JTextField field;
	private JLabel label;
	
	public QuizResultPanel(List<QuestionResult> results, PanelChange mainPanel) {
		this.results = results;
		sender = new ResultSender("quizjava6@gmail.com", "bvoz ikzt mqiy ekdc");
		
		this.setBorder(new EmptyBorder(50, 50, 50, 50));
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		field = new JTextField();
		field.setText("youre@mail.you");
		
		label = new JLabel();
		int right = 0;
		for(QuestionResult result: results) {
			if(result.isCorrect) right++;
		}
		
		label.setText("Score: "+right + "/" + results.size());
		
		JButton sendBtn = new JButton();
		sendBtn.setText("Send");
		
		sendBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				 sendResult();
			}
		});
		
		this.add(label);
		label.setAlignmentX(CENTER_ALIGNMENT);
		this.add(Box.createVerticalStrut(10));
		this.add(field);
		field.setAlignmentX(CENTER_ALIGNMENT);
		this.add(Box.createVerticalStrut(10));
		this.add(sendBtn);
		sendBtn.setAlignmentX(CENTER_ALIGNMENT);
		this.add(Box.createVerticalStrut(10));
		
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
	
	private void sendResult() {
		String email = field.getText();
	
		
		Thread th = new Thread() {
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
