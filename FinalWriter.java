import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.List;

public class FinalWriter {

    public void writeOutput(
            List<Classroom> classrooms,
            List<Student> students,
            List<Course> courses,
            List<Attendance> attendanceList,
            String filePath) {

        try {
            File file = new File(filePath);
            file.getParentFile().mkdirs(); // creat and output file(result)
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));

            bw.write("=== CLASSROOMS ===");
            bw.newLine();
            for (Classroom c : classrooms) {
                bw.write(c.getName() + " | Capacity: " + c.getCapacity());
                bw.newLine();
            }

            bw.newLine();
            bw.write("=== STUDENTS ===");
            bw.newLine();
            for (Student s : students) {
                bw.write(s.getStudentId());
                bw.newLine();
            }

            bw.newLine();
            bw.write("=== COURSES ===");
            bw.newLine();
            for (Course c : courses) {
                bw.write(c.getCourseCode() + " -> " + String.join(", ", c.getStudentIds()));
                bw.newLine();
            }
            bw.newLine();
            bw.write("=== ATTENDANCE LIST ===");
            bw.newLine();
            for (Attendance a : attendanceList) {
                bw.write(a.getCourseCode() + " -> " + String.join(", ", a.getStudentIds()));
                bw.newLine();
            }


            bw.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

