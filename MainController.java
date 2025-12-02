import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

public class MainController {

    public void run() {

        // Create Reader  Writer objects
        StudentReader studentReader = new StudentReader();
        CourseReader courseReader = new CourseReader();
        ClassroomReader classroomReader = new ClassroomReader();
        AttendanceReader attendanceReader = new AttendanceReader();
        FinalWriter writer = new FinalWriter();

        // Read classrooms
        List<Classroom> classrooms = classroomReader.readClassrooms("input/classrooms.csv");

        // Read students
        List<Student> students = studentReader.readStudents("input/students.csv");

        // Read courses
        List<Course> courses = courseReader.readCourses("input/courses.csv");

        // Read Attendance list
        List<Attendance> attendanceList = attendanceReader.readAttendance("input/attendance.csv");

        // for Validator create String lists
        List<String> studentIds = students.stream()
                .map(Student::getStudentId)
                .toList();
        List<String> courseCodes = courses.stream()
                .map(Course::getCourseCode)
                .toList();
        Map<String, Integer> classroomMap = classrooms.stream()
                .collect(Collectors.toMap(Classroom::getName, Classroom::getCapacity));

        //  Attendance list to Map
        Map<String, List<String>> courseToStudentsMap = new HashMap<>();
        for (Attendance a : attendanceList) {
            courseToStudentsMap.put(a.getCourseCode(), a.getStudentIds());
        }

        // Validator and control
        DataValidationService validator = new DataValidationService();
        validator.validateStudents(studentIds);
        validator.validateCourses(courseCodes);
        validator.validateClassrooms(classroomMap);
        validator.validateCourseRegistrations(courseToStudentsMap, studentIds, courseCodes, classroomMap);

        // output
        writer.writeOutput(classrooms, students, courses, attendanceList, "output/result.txt");

        System.out.println("Program is completed → output/result.txt");
    }
}
