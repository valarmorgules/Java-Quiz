//QuizChecker.java 
// done by Kostiuchenko Mariia (comp mat 2)
// date 28.11.2024

package project.quiz;

import java.util.ArrayList;
import java.util.List;


public class QuizChecker{
	private QuizKey key; // Ключ з відповідями
	
	public QuizChecker(QuizKey key) {
		this.key = key;
	}
	
	// Перевіряє відповіді на тест і повертає список результатів
	public List<QuestionResult> check(Quiz quiz, List<Integer> answers) {	
		List<QuestionResult> results = new ArrayList<QuestionResult>(); // Список для збереження результатів перевірки
		
		// Визначаємо мінімальний розмір для перевірки, щоб уникнути помилок за різної кількості питань та відповідей
		int size = Math.min(answers.size(), quiz.questions.size());
		
		// Перевіряємо кожне питання і порівнюємо відповідь користувача з правильною відповіддю
		for(int i = 0; i < size; i++) {
			Question question = quiz.questions.get(i);
			int id = question.id; // Ідентифікатор поточного питання
			
			// Перевіряємо правильність відповіді на це питання
			for(Key k: key.keys) {
				if(k.id == id) {
					QuestionResult result = new QuestionResult(); // Створюємо новий об'єкт для зберігання результату
					result.question = question;
					result.userAnswer = answers.get(i); // Зберігаємо відповідь користувача
					result.isCorrect = k.correct == answers.get(i); // Порівнюємо з правильною відповіддю з ключа
					results.add(result); // Додаємо результат до списку
				}
			}
		}
		
		return results;
	}
}
