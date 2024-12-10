// AnswerCallable.java 
// done by Kostiuchenko Mariia (comp mat 2)
// date 06.12.2024

package project.ui;

import java.util.List;

//Інтерфейс для обробки відповідей користувача
public interface AnswerCallable {
	// Метод, який викликається для передачі списку відповідей
	void call(List<Integer> answers);
}
