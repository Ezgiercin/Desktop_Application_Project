import java.util.*;

public class DataValidationService {

    //  1. Student validation
    public void validateStudents(List<String> students) {
        Set<String> seen = new HashSet<>();

        for (String student : students) {
            if (student == null || student.isBlank()) {
                throw new IllegalArgumentException("ERROR: Student ID cannot be empty!");
            }

            if (!student.startsWith("Std_ID_")) {
                throw new IllegalArgumentException("ERROR: Invalid student format: " + student);
            }

            if (seen.contains(student)) {
                throw new IllegalArgumentException("ERROR: Duplicate student ID: " + student);
            }

            seen.add(student);
        }
    }

    // 2. Course validation
    public void validateCourses(List<String> courses) {
        Set<String> seen = new HashSet<>();

        for (String course : courses) {
            if (course == null || course.isBlank()) {
                throw new IllegalArgumentException("ERROR: Course code cannot be empty!");
            }

            if (!course.startsWith("CourseCode_")) {
                throw new IllegalArgumentException("ERROR: Invalid course code format: " + course);
            }

            if (seen.contains(course)) {
                throw new IllegalArgumentException("ERROR: Duplicate course code: " + course);
            }

            seen.add(course);
        }
    }

    // 3. Classroom validation
    public void validateClassrooms(Map<String, Integer> classrooms) {
        Set<String> seen = new HashSet<>();
        for (String room : classrooms.keySet()) {
            if (room == null || room.isBlank()) {
                throw new IllegalArgumentException("ERROR: Classroom code cannot be empty!");
            }

            if (!room.startsWith("Classroom_")) {
                throw new IllegalArgumentException("ERROR: Invalid classroom code format: " + room);
            }

            if (seen.contains(room)) {
                throw new IllegalArgumentException("ERROR: Duplicate classroom code: " + room);
            }

            seen.add(room);

            Integer capacity = classrooms.get(room);
            if (capacity == null || capacity <= 0) {
                throw new IllegalArgumentException("ERROR: Classroom capacity must be > 0: " + room);
            }
        }
    }

    // 4. Course registration validation
    public void validateCourseRegistrations(
            Map<String, List<String>> courseToStudents,
            List<String> allStudents,
            List<String> allCourses,
            Map<String, Integer> classrooms
    ) {
        Set<String> studentSet = new HashSet<>(allStudents);
        Set<String> courseSet = new HashSet<>(allCourses);
        int maxRoomCapacity = Collections.max(classrooms.values());

        for (String course : courseToStudents.keySet()) {

            if (!courseSet.contains(course)) {
                throw new IllegalArgumentException("ERROR: Registration includes unknown course: " + course);
            }

            List<String> registered = courseToStudents.get(course);
            if (registered == null) {
                throw new IllegalArgumentException("ERROR: Course " + course + " has no student list!");
            }

            Set<String> studentInCourse = new HashSet<>();
            for (String student : registered) {
                if (!studentSet.contains(student)) {
                    throw new IllegalArgumentException("ERROR: Student " + student +
                            " registered to " + course + " but not in student list!");
                }
                if (studentInCourse.contains(student)) {
                    throw new IllegalArgumentException("ERROR: Duplicate registration for student " + student +
                            " in course " + course);
                }
                studentInCourse.add(student);
            }

            if (registered.size() > maxRoomCapacity) {
                throw new IllegalArgumentException("ERROR: Course " + course +
                        " has " + registered.size() + " students, " +
                        "but max classroom capacity is only " + maxRoomCapacity);
            }
        }
    }

}
