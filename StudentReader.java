import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StudentReader {
    public List<Student> readStudents(String filePath) {
        List<Student> students = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isHeader = true;
            while ((line = br.readLine()) != null) {
                if (isHeader) { isHeader = false; continue; }
                line = line.trim();
                if (!line.isEmpty()) students.add(new Student(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return students;
    }
}
