//CsvQuizFileManager.java 
// done by Kostiuchenko Mariia (comp mat 2)
// date 28.11.2024

package project.file;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;

import project.quiz.Question;
import project.quiz.Quiz;

//Клас для роботи з CSV-файлами для збереження та завантаження тестів
public class CsvQuizFileManager extends QuizFileManager {
	private int headerCount = 2;
	
	// Конструктор, що приймає назву файлу
	public CsvQuizFileManager(String filename) {
		super(filename);

	}
	/**
	 * 
	 * @param questions - list of question
	 * @return max count of answers
	 */
	
	// Знаходить максимальну кількість варіантів відповіді серед усіх питань
	private int findMaxAnswerCount(List<Question> questions) {
		int max = 0;
		for(Question question: questions) {
			if(question.answers.size() > max) {
				max = question.answers.size();
			}
		}
		
		return max;
	}
	
	// Створює заголовки для CSV-файлу, враховуючи кількість відповідей
	private String[] getHeaders(Quiz quiz){
		int maxCount = findMaxAnswerCount(quiz.questions);
		
		String[] headers = new String[headerCount+maxCount];
		headers[0] = "Id";
		headers[1] = "Text";
		
		
		for(int i = 0; i < maxCount; i++) {
			headers[i+headerCount] = "Answer_"+i;
		}
		
		return headers;
	}

	@Override
	 // Зберігає тест у CSV-файл
	public void saveQuiz(Quiz quiz) throws IOException {
		CSVWriter writer = new CSVWriter(new FileWriter(filename));
		
		String[] headers = getHeaders(quiz);
		writer.writeNext(headers);
		
		for(Question question: quiz.questions) {
			String[] quest = new String[headerCount+question.answers.size()];
			quest[0] = ""+question.id;
			quest[1] = question.text;
			
			for(int i = 0; i < question.answers.size(); i++) {
				quest[i+headerCount] = question.answers.get(i);
			}
			writer.writeNext(quest);
		}
		
		writer.close();
	}
	
	 // Перетворює рядок у ціле число з обробкою помилок
	private int toInt(String str) {
		try {
			return Integer.parseInt(str);
		}catch(NumberFormatException e) {
			return 0;
		}
	}

	@Override
	// Завантажує тест із CSV-файлу
	public Quiz loadQuiz() throws IOException {
		Quiz quiz = new Quiz();
		CSVReader reader = new CSVReader(new FileReader(filename));
		
		
		try {
			String[] row = reader.readNext();
			
			while((row = reader.readNext()) != null) {
				Question question = new Question();
				question.id = toInt(row[0]);
				question.text = row[1];
				
				for(int i = headerCount; i < row.length; i++) {
					question.answers.add(row[i]);
				}
				
				quiz.questions.add(question);
			}
			
		} catch (CsvValidationException e) {
			reader.close();
			throw new IOException(e.getMessage());
		}
	
		reader.close();
		
		return quiz;
	}

}
