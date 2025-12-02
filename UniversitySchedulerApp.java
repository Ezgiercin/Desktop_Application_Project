package SamProd_Desktop_Application_Project;

import SamProd_Desktop_Application_Project.exception.DataImportException;
import SamProd_Desktop_Application_Project.gui.SchedulerGUI; // Import the GUI
import SamProd_Desktop_Application_Project.model.DomainModels.Classroom;
import SamProd_Desktop_Application_Project.model.DomainModels.Course;
import SamProd_Desktop_Application_Project.model.DomainModels.Student;
import SamProd_Desktop_Application_Project.parser.Parser;
import SamProd_Desktop_Application_Project.parser.impl.CoreParsers;
import SamProd_Desktop_Application_Project.service.DataValidator;

import javax.swing.SwingUtilities;
import java.io.File;
import java.util.Collections;
import java.util.List;

public class UniversitySchedulerApp {

    public static void main(String[] args) {
        System.out.println("--- Starting University Exam Scheduler Import ---");

        File studentFile = new File("C:\\Users\\samal\\Desktop\\SE 302 Project Code\\SamProd_Desktop_Application_Project\\CSV_Files\\sampleData_AllStudents(1).csv");
        File courseFile = new File("C:\\Users\\samal\\Desktop\\SE 302 Project Code\\SamProd_Desktop_Application_Project\\CSV_Files\\sampleData_AllCourses(2).csv");
        File classroomFile = new File("C:\\Users\\samal\\Desktop\\SE 302 Project Code\\SamProd_Desktop_Application_Project\\CSV_Files\\sampleData_AllClassroomsAndTheirCapacities(1).csv");
        File attendanceFile = new File("C:\\Users\\samal\\Desktop\\SE 302 Project Code\\SamProd_Desktop_Application_Project\\CSV_Files\\sampleData_AllAttendanceLists(1).csv");

        try {
            // Parse Students
            System.out.println("\n[1] Parsing Students...");
            List<Student> students = Collections.emptyList();
            if (studentFile.exists()) {
                Parser<Student> studentParser = new CoreParsers.StudentParser();
                students = studentParser.parse(studentFile);
                System.out.println("    Successfully loaded " + students.size() + " students.");
            } else {
                System.out.println("    Student file not found.");
            }

            //  Parse Classrooms
            System.out.println("\n[2] Parsing Classrooms...");
            List<Classroom> classrooms = Collections.emptyList();
            if (classroomFile.exists()) {
                Parser<Classroom> roomParser = new CoreParsers.ClassroomParser();
                classrooms = roomParser.parse(classroomFile);
                System.out.println("    Successfully loaded " + classrooms.size() + " classrooms.");
            } else {
                System.out.println("    Classroom file not found.");
            }

            //  Parse Course List
            System.out.println("\n[3] Parsing Master Course List...");
            List<Course> masterCourses = Collections.emptyList();
            if (courseFile.exists()) {
                Parser<Course> courseParser = new CoreParsers.CourseParser();
                masterCourses = courseParser.parse(courseFile);
                System.out.println("    Successfully loaded " + masterCourses.size() + " master courses.");
            } else {
                System.out.println("    Master Course file not found.");
            }

            //  Parse Attendance 
            System.out.println("\n[4] Parsing Attendances...");
            List<Course> enrolledCourses = Collections.emptyList();
            if (attendanceFile.exists()) {
                Parser<Course> attendanceParser = new CoreParsers.AttendanceParser();
                enrolledCourses = attendanceParser.parse(attendanceFile);
                System.out.println("    Successfully loaded attandances for " + enrolledCourses.size() + " courses.");
            } else {
                System.out.println("    Attendance file not found.");
            }

            // Data Validation  --> DataValidation methods are located in the service folder - Sam
            System.out.println("\n[5] Validating Referential Integrity...");
            DataValidator validator = new DataValidator();
            List<String> errors = validator.validate(students, classrooms, masterCourses, enrolledCourses);

            if (errors.isEmpty()) {
                System.out.println("    SUCCESS: All data is valid.");
                
                // A scrap gui starter xD - Sam
                System.out.println("\n[6] Launching GUI...");
                
                // final vars are for lambda expression below, model lists can be used as it is on other classes, for more info look to the impl/CoreParsers or text me - Sam
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
                System.out.println("    WARNING: Found " + errors.size() + " inconsistencies. GUI will not launch.");
                errors.stream().limit(5).forEach(e -> System.out.println("      - " + e));
            }

        } catch (DataImportException e) {
            e.printStackTrace();
        }
    }
}