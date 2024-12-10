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

//Головна панель з вибором режиму (користувач чи адміністратор)
public class MainPanel extends JPanel{
	private final Font titleFont = new Font("Arial", Font.BOLD, 30); // Шрифт для заголовка
	private JButton takeQuiz; // Кнопка для початку тесту
	private JButton admin; // Кнопка для входу в режим адміністратора
	
	// Конструктор створює графічний інтерфейс
	public MainPanel() {
		this.setBorder(new EmptyBorder(50, 50, 50, 50));
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		// Заголовок
		JLabel title = new JLabel();
		title.setText("Java quiz"); // Текст заголовка
		title.setFont(titleFont); // Застосування шрифту
		title.setAlignmentX(CENTER_ALIGNMENT); // Вирівнювання по центру
		
		this.add(title); // Додавання заголовка
		this.add(Box.createVerticalStrut(10)); // Вертикальний відступ
		
		// Кнопка для початку тесту
		takeQuiz = new JButton();
		takeQuiz.setText("Take quiz");
		takeQuiz.setAlignmentX(CENTER_ALIGNMENT); // Вирівнювання по центру
		
		
		this.add(takeQuiz); // Додавання кнопки
		this.add(Box.createVerticalStrut(10)); // Вертикальний відступ
		
		// Кнопка для режиму адміна
		admin = new JButton();
		admin.setText("Admin mode");
		admin.setAlignmentX(CENTER_ALIGNMENT); // Вирівнювання по центру
		
		this.add(admin); // Додавання кнопки
		this.add(Box.createVerticalStrut(10)); // Вертикальний відступ
		
	}
	
	// Додавання обробника для переходу до панелі тестування
	public void addTakerChange(PanelChange takePanel) {
		takeQuiz.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				takePanel.goToPanel();
			}
		});
	}
	
	// Додавання обробника для переходу до панелі адміністратора
	public void addAdminChange(PanelChange adminPanel) {
		admin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				adminPanel.goToPanel();
			}
		});
	}
}
