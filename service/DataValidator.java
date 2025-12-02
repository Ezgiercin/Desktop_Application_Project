package SamProd_Desktop_Application_Project.service;

import SamProd_Desktop_Application_Project.model.DomainModels.Course;
import SamProd_Desktop_Application_Project.model.DomainModels.Student;
import SamProd_Desktop_Application_Project.model.DomainModels.Classroom;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;

public class DataValidator {

    public List<String> validate(List<Student> students, 
                                 List<Classroom> classrooms, 
                                 List<Course> masterCourses, 
                                 List<Course> coursesWithEnrollment) {
        
        List<String> errors = new ArrayList<>();

        Set<String> validStudentIds = new HashSet<>();
        Set<String> validCourseCodes = new HashSet<>();
        Set<String> duplicateCheck = new HashSet<>();
        
        // Validate Students
        for (Student s : students) {
            if (!duplicateCheck.add(s.getId())) {
                errors.add("Duplicate Student ID found in master list: " + s.getId());
            }
            validStudentIds.add(s.getId());
        }

        //  Validate Classrooms
        duplicateCheck.clear();
        for (Classroom c : classrooms) {
            if (!duplicateCheck.add(c.getName())) {
                errors.add("Duplicate Classroom Name found: " + c.getName());
            }
        }

        // Validate  Courses
        duplicateCheck.clear();
        for (Course c : masterCourses) {
            if (!duplicateCheck.add(c.getCourseCode())) {
                errors.add("Duplicate Course Code found in master list: " + c.getCourseCode());
            }
            validCourseCodes.add(c.getCourseCode());
        }

        //  Validate Enrollments & Course Validity
        for (Course course : coursesWithEnrollment) {
            // Check if the course exists in the master list 
            if (!validCourseCodes.contains(course.getCourseCode())) {
                errors.add("Unknown Course detected in Attendance file: " + course.getCourseCode());
            }

            // Check if students exist in the master list
            for (Student enrolled : course.getEnrolledStudents()) {
                if (!validStudentIds.contains(enrolled.getId())) {
                    errors.add("Integrity Error: Course " + course.getCourseCode() + 
                               " contains unknown student " + enrolled.getId());
                }
            }
        }

        return errors;  // Try this return statement whether is returning the correct Error List - Sam
    }
}