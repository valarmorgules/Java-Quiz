//FileManager.java 
// done by Kostiuchenko Mariia (comp mat 2)
// date 28.11.2024

package project.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

// Клас для роботи з файлами: читання та запис текстового вмісту
public class FileManager {
	protected String filename;
	
	// Конструктор, який приймає назву файлу
	public FileManager(String filename) {
		this.filename = filename;
	}
	// Читає весь вміст файлу та повертає його у вигляді рядка
	public String readAll() throws IOException {
		File file = new File(filename);
		FileInputStream fis = new FileInputStream(file);
		byte[] data = new byte[(int)file.length()];
		fis.read(data);
		fis.close();
		
		String str = new String(data, "UTF-8");
		return str;
	}
	
	// Перезаписує весь вміст файлу заданим рядком
	public void writeAll(String str) throws IOException {
		FileWriter fw = new FileWriter(filename); // Відкриває файл для запису
		PrintWriter pw = new PrintWriter(fw); // Забезпечує зручний запис у файл
		pw.write(str); // Записує рядок у файл
		pw.close(); // Закриває потік запису
	}
}
