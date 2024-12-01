package project.quiz;

public class Key {
	public int id; // Ідентифікатор ключа
	public int correct; // Кількість правильних відповідей
	
	@Override
	public String toString() {
		return "Key(id="+id+", correct="+correct+")"; 
	}
}
