package project.ui;

import java.util.List;

//Інтерфейс для обробки відповідей користувача
public interface AnswerCallable {
	// Метод, який викликається для передачі списку відповідей
	void call(List<Integer> answers);
}
