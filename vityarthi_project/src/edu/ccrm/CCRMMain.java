/*
 * Vityarthi Project
 * MIT License - see LICENSE file in the project root
 */
package edu.ccrm;

import edu.ccrm.cli.CCRMCli;
import edu.ccrm.domain.Course;
import edu.ccrm.domain.Student;
import edu.ccrm.io.ImportExportService;
import edu.ccrm.service.CourseService;
import edu.ccrm.service.EnrollmentService;
import edu.ccrm.service.StudentService;
import edu.ccrm.domain.Semester;

import java.nio.file.Path;
import java.util.List;

public class CCRMMain {
    public static void main(String[] args) {
        try {
            Path studentsCsv = Path.of("test_data", "students.csv");
            Path coursesCsv = Path.of("test_data", "courses.csv");

            ImportExportService io = new ImportExportService();
            List<Student> students = io.loadStudents(studentsCsv);
            List<Course> courses = io.loadCourses(coursesCsv);

            System.out.println("Loaded students: " + students.size());
            System.out.println("Loaded courses:  " + courses.size());

            StudentService studentService = new StudentService();
            students.forEach(studentService::create);
            CourseService courseService = new CourseService();
            courses.forEach(courseService::create);
            EnrollmentService enrollmentService = new EnrollmentService(studentService, courseService, 24);

            // quick demo: enroll first student to first course in FALL
            if (!students.isEmpty() && !courses.isEmpty()) {
                try { enrollmentService.enroll(students.get(0).getId(), courses.get(0).getId(), Semester.FALL); } catch (Exception ignored) {}
            }

            new CCRMCli(studentService, courseService, enrollmentService).start();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}


