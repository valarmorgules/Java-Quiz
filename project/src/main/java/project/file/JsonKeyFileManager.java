//JsonKeyFileManager.java 
// done by Kostiuchenko Mariia (comp mat 2)
// date 28.11.2024

package project.file;

import java.io.IOException;

import com.google.gson.Gson;

import project.quiz.QuizKey;

//Клас для роботи з JSON-файлами, що зберігають ключі тестів
public class JsonKeyFileManager extends FileManager {
	private Gson gson = new Gson();
	
	// Конструктор, який приймає назву файлу
	public JsonKeyFileManager(String filename) {
		super(filename);
	}

	// Зберігає ключ тесту у форматі JSON
	public void saveKey(QuizKey key) throws IOException {
		String json = gson.toJson(key); // Перетворює об'єкт ключа у JSON
		writeAll(json); // Записує JSON у файл
	}

	// Завантажує ключ тесту з JSON-файлу
	public QuizKey loadKey() throws IOException{
		String json = readAll(); // Зчитує вміст файлу
		QuizKey key = gson.fromJson(json, QuizKey.class); // Перетворює JSON у об'єкт ключа
		
		return key;
	}
}
