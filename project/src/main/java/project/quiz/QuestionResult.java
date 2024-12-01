//QuestionResults.java 
// done by Kostiuchenko Mariia (comp mat 2)
// date 28.11.2024

package project.quiz;

public class QuestionResult {
	public Question question;  // Питання, на яке була дана відповідь
	public int userAnswer;     // Індекс вибраної користувачем відповіді
	public boolean isCorrect;  // Чи є відповідь правильною
	
	@Override
	public String toString() {
		return "QuestionResult(question="+question + ", userAnswer=" + 
				userAnswer + ", isCorrect=" + isCorrect + ")"; 
	}
}
