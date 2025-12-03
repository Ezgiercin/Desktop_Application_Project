package SamProd_Desktop_Application_Project;

import SamProd_Desktop_Application_Project.exception.DataImportException;
import SamProd_Desktop_Application_Project.gui.SchedulerGUI;
import SamProd_Desktop_Application_Project.model.DomainModels.Classroom;
import SamProd_Desktop_Application_Project.model.DomainModels.Course;
import SamProd_Desktop_Application_Project.model.DomainModels.Student;
import SamProd_Desktop_Application_Project.parser.Parser;
import SamProd_Desktop_Application_Project.parser.impl.CoreParsers;
import SamProd_Desktop_Application_Project.service.DataValidator;
import SamProd_Desktop_Application_Project.service.FinalWriter; // Import the new Writer

import javax.swing.SwingUtilities;
import java.io.File;
import java.util.Collections;
import java.util.List;

public class UniversitySchedulerApp {

    public static void main(String[] args) {
        System.out.println("--- Starting University Exam Scheduler Import ---");

        File studentFile = new File("C:\\Users\\samal\\Desktop\\SE 302 Project Code\\SamProd_Desktop_Application_Project\\CSV_Files\\students.csv");
        File courseFile = new File("C:\\Users\\samal\\Desktop\\SE 302 Project Code\\SamProd_Desktop_Application_Project\\CSV_Files\\courses.csv");
        File classroomFile = new File("C:\\Users\\samal\\Desktop\\SE 302 Project Code\\SamProd_Desktop_Application_Project\\CSV_Files\\clasrooms.csv");
        File attendanceFile = new File("C:\\Users\\samal\\Desktop\\SE 302 Project Code\\SamProd_Desktop_Application_Project\\CSV_Files\\attendance.csv");
        
        String outputPath = "output/result.txt";

        try {
            // [1] Parse Students
            System.out.println("\n[1] Parsing Students...");
            List<Student> students = Collections.emptyList();
            if (studentFile.exists()) {
                Parser<Student> studentParser = new CoreParsers.StudentParser();
                students = studentParser.parse(studentFile);
                System.out.println("    Successfully loaded " + students.size() + " students.");
            } else {
                System.out.println("    Student file not found at: " + studentFile.getPath());
            }

            // [2] Parse Classrooms
            System.out.println("\n[2] Parsing Classrooms...");
            List<Classroom> classrooms = Collections.emptyList();
            if (classroomFile.exists()) {
                Parser<Classroom> roomParser = new CoreParsers.ClassroomParser();
                classrooms = roomParser.parse(classroomFile);
                System.out.println("    Successfully loaded " + classrooms.size() + " classrooms.");
            } else {
                System.out.println("    Classroom file not found.");
            }

            // [3] Parse Course List
            System.out.println("\n[3] Parsing Master Course List...");
            List<Course> masterCourses = Collections.emptyList();
            if (courseFile.exists()) {
                Parser<Course> courseParser = new CoreParsers.CourseParser();
                masterCourses = courseParser.parse(courseFile);
                System.out.println("    Successfully loaded " + masterCourses.size() + " master courses.");
            } else {
                System.out.println("    Master Course file not found.");
            }

            // [4] Parse Attendance 
            System.out.println("\n[4] Parsing Attendances...");
            List<Course> enrolledCourses = Collections.emptyList();
            if (attendanceFile.exists()) {
                Parser<Course> attendanceParser = new CoreParsers.AttendanceParser();
                enrolledCourses = attendanceParser.parse(attendanceFile);
                System.out.println("    Successfully loaded attendance for " + enrolledCourses.size() + " courses.");
            } else {
                System.out.println("    Attendance file not found.");
            }

            // [5] Data Validation 
            System.out.println("\n[5] Validating Referential Integrity...");
            DataValidator validator = new DataValidator();
            List<String> errors = validator.validate(students, classrooms, masterCourses, enrolledCourses);

            if (errors.isEmpty()) {
                System.out.println("    SUCCESS: All data is valid.");
                
                // [6] Write Output
                System.out.println("\n[6] Writing Final Output...");
                FinalWriter writer = new FinalWriter();
                writer.writeOutput(classrooms, students, masterCourses, enrolledCourses, outputPath);
                
                // [7] Launch GUI
                System.out.println("\n[7] Launching GUI...");
                
                List<Student> finalStudents = students;
                List<Classroom> finalClassrooms = classrooms;
                List<Course> finalMasterCourses = masterCourses;
                List<Course> finalEnrolledCourses = enrolledCourses;

                SwingUtilities.invokeLater(() -> {
                    SchedulerGUI gui = new SchedulerGUI(
                        finalStudents, 
                        finalClassrooms, 
                        finalMasterCourses, 
                        finalEnrolledCourses
                    );
                    gui.setVisible(true);
                });

            } else {
                System.out.println("    WARNING: Found " + errors.size() + " inconsistencies. Files will NOT be written.");
                errors.forEach(e -> System.out.println("      - " + e));
            }

        } catch (DataImportException e) {
            e.printStackTrace();
        }
    }
}