// QuizKey.java 
// done by Kostiuchenko Mariia (comp mat 2)
// date 28.11.2024

package project.quiz;

import java.util.ArrayList;
import java.util.List;

public class QuizKey {
	public final List<Key> keys = new ArrayList<Key>(); // Список правильних відповідей для кожного питання
	
	public String toString() {
		return "Questions(" + keys + ")";
	}
}
