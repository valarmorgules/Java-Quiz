package project.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class MainPanel extends JPanel{
	private final Font titleFont = new Font("Arial", Font.BOLD, 30);
	private JButton takeQuiz;
	private JButton admin;
	
	public MainPanel() {
		this.setBorder(new EmptyBorder(50, 50, 50, 50));
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JLabel title = new JLabel();
		title.setText("Java quiz");
		title.setFont(titleFont);
		title.setAlignmentX(CENTER_ALIGNMENT);
		
		this.add(title);
		this.add(Box.createVerticalStrut(10));
		
		takeQuiz = new JButton();
		takeQuiz.setText("Take quiz");
		takeQuiz.setAlignmentX(CENTER_ALIGNMENT);
		
		
		this.add(takeQuiz);
		this.add(Box.createVerticalStrut(10));
		
		admin = new JButton();
		admin.setText("Admin mode");
		admin.setAlignmentX(CENTER_ALIGNMENT);
		
		this.add(admin);
		this.add(Box.createVerticalStrut(10));
		
	}
	
	public void addTakerChange(PanelChange takePanel) {
		takeQuiz.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				takePanel.goToPanel();
			}
		});
	}
	
	public void addAdminChange(PanelChange adminPanel) {
		admin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				adminPanel.goToPanel();
			}
		});
	}
}
