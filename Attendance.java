import java.util.List;

public class Attendance {
    private String courseCode;
    private List<String> studentIds;

    public Attendance(String courseCode, List<String> studentIds) {
        this.courseCode = courseCode;
        this.studentIds = studentIds;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public List<String> getStudentIds() {
        return studentIds;
    }

    @Override
    public String toString() {
        return courseCode + " -> " + studentIds;
    }
}
