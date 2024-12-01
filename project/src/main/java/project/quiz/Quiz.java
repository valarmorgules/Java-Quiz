//Quiz.java 
// done by Kostiuchenko Mariia (comp mat 2)
// date 28.11.2024

package project.quiz;

import java.util.ArrayList;
import java.util.List;

public class Quiz{
	public final List<Question> questions = new ArrayList<Question>(); // Список питань в тесті

	
	public String toString() {
		return "Questions(" + questions + ")";
	}
}
