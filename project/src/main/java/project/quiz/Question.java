//Question.java 
// done by Kostiuchenko Mariia (comp mat 2)
// date 28.11.2024
package project.quiz;

import java.util.ArrayList;
import java.util.List;

public class Question{
	public int id;        // Ідентифікатор питання
	public String text;  // Текст питання
	public final List<String> answers = new ArrayList<String>(); // Список можливих відповідей
	
	public String toString() {
		return "Question(text=" + text + ", answer=" + answers + ")"; 
	}
}
