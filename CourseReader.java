import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CourseReader {

    public List<Course> readCourses(String filePath) {

        List<Course> courses = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            String line;
            boolean isHeader = true;

            while ((line = br.readLine()) != null) {
                if (isHeader) {
                    isHeader = false;
                    continue;
                }

                line = line.trim();
                if (line.isEmpty()) continue;

                // Split with ;
                String[] parts = line.split(";");
                String courseCode = parts[0].trim();

                List<String> studentIds = new ArrayList<>();
                if (parts.length > 1 && !parts[1].trim().isEmpty()) {
                    String rawList = parts[1].trim().replace("[", "").replace("]", "");
                    studentIds = Arrays.stream(rawList.split(","))
                            .map(String::trim)
                            .toList();
                }

                courses.add(new Course(courseCode, studentIds));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return courses;
    }
}
