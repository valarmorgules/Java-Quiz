//JsonQuizFileManager.java 
// done by Kostiuchenko Mariia (comp mat 2)
// date 28.11.2024

package project.file;

import java.io.IOException;

import com.google.gson.Gson;

import project.quiz.Quiz;

//Клас для роботи з JSON-файлами, що зберігають тести
public class JsonQuizFileManager extends QuizFileManager {
	private Gson gson = new Gson(); // Інструмент для роботи з JSON
	
	// Конструктор, який приймає назву файлу
	public JsonQuizFileManager(String filename) {
		super(filename);
	}

	@Override
	// Зберігає тест у форматі JSON
	public void saveQuiz(Quiz quiz) throws IOException {
		String json = gson.toJson(quiz);
		writeAll(json);
	}

	@Override
	// Завантажує тест з JSON-файлу
	public Quiz loadQuiz() throws IOException{
		String json = readAll(); // Зчитує JSON з файлу
		Quiz quiz = gson.fromJson(json, Quiz.class); // Десеріалізує JSON у об'єкт тесту
		
		return quiz;
	}

}
