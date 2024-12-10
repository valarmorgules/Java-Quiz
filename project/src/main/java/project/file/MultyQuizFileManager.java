//MultyQuizFileManager.java 
// done by Kostiuchenko Mariia (comp mat 2)
// date 28.11.2024

package project.file;

import java.io.IOException;

import project.quiz.Quiz;

//Клас для роботи з тестами у кількох форматах (JSON та CSV)
public class MultyQuizFileManager extends QuizFileManager {
	private JsonQuizFileManager json; // Менеджер для роботи з JSON-файлами
	private CsvQuizFileManager csv; // Менеджер для роботи з CSV-файлами
	
	// Конструктор, який створює менеджери для обох форматів
	public MultyQuizFileManager(String filename) {
		super(filename);
		json = new JsonQuizFileManager(filename+".json");
		csv = new CsvQuizFileManager(filename+".csv");
	}

	@Override
	// Зберігає тест у форматах JSON та CSV
	public void saveQuiz(Quiz quiz) throws IOException {
		json.saveQuiz(quiz); // Збереження у JSON
		csv.saveQuiz(quiz); // Збереження у CSV
	}

	@Override
	// Завантажує тест з JSON 
	public Quiz loadQuiz() throws IOException {
		return json.loadQuiz(); // Завантаження з JSON
	}
}
