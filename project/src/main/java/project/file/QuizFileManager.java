package project.file;

import java.io.IOException;

import project.quiz.Quiz;

;

//Абстрактний клас для роботи з файлами, що зберігають тести
public abstract class QuizFileManager extends FileManager {

	// Конструктор, який приймає назву файлу
	public QuizFileManager(String filename) {
		super(filename);
	}

	// Абстрактний метод для збереження тесту
	public abstract void saveQuiz(Quiz quiz) throws IOException;
	
	// Абстрактний метод для завантаження тесту
	public abstract Quiz loadQuiz() throws IOException;

}
