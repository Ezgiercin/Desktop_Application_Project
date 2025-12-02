
import java.util.List;

public class Course {

    private String courseCode;
    private List<String> studentIds;

    public Course() {}

    public Course(String courseCode, List<String> studentIds) {
        this.courseCode = courseCode;
        this.studentIds = studentIds;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public List<String> getStudentIds() {
        return studentIds;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public void setStudentIds(List<String> studentIds) {
        this.studentIds = studentIds;
    }
}
