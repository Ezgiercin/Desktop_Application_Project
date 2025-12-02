import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AttendanceReader {

    public List<Attendance> readAttendance(String filePath) {
        List<Attendance> attendanceList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            String line;
            String currentCourse = null;

            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                // If starts with CourseCode starts new course
                if (line.startsWith("CourseCode_")) {
                    currentCourse = line;
                }
                // If starts with [ student list
                else if (line.startsWith("[")) {
                    if (currentCourse == null) {
                        System.out.println("Hatalı satır (skip): " + line);
                        continue;
                    }

                    String rawList = line.replace("[", "").replace("]", "").replace("'", "");

                    List<String> studentIds = Arrays.stream(rawList.split(","))
                            .map(String::trim)
                            .toList();

                    attendanceList.add(new Attendance(currentCourse, studentIds));

                    // Ready for next course
                    currentCourse = null;
                } else {
                    System.out.println("Hatalı satır (skip): " + line);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return attendanceList;
    }
}
