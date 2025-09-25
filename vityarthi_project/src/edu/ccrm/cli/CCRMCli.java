package edu.ccrm.cli;

import edu.ccrm.config.AppConfig;
import edu.ccrm.domain.*;
import edu.ccrm.io.BackupService;
import edu.ccrm.io.ImportExportService;
import edu.ccrm.service.CourseService;
import edu.ccrm.service.EnrollmentService;
import edu.ccrm.service.StudentService;

import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

public class CCRMCli {
    private final StudentService studentService;
    private final CourseService courseService;
    private final EnrollmentService enrollmentService;

    public CCRMCli(StudentService studentService, CourseService courseService, EnrollmentService enrollmentService) {
        this.studentService = studentService;
        this.courseService = courseService;
        this.enrollmentService = enrollmentService;
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        AppConfig config = AppConfig.getInstance();
        boolean running = true;
        mainLoop: while (running) {
            System.out.println("=== CCRM Menu ===");
            System.out.println("1. Manage Students");
            System.out.println("2. Manage Courses");
            System.out.println("3. Enrollment & Grades");
            System.out.println("4. Import/Export");
            System.out.println("5. Backup & Show Size");
            System.out.println("6. Transcript & Reports");
            System.out.println("0. Exit");
            System.out.print("Select: ");
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    System.out.println("Students:");
                    studentService.listAll().forEach(System.out::println);
                    break;
                case "2":
                    System.out.println("Courses:");
                    courseService.listAll().forEach(System.out::println);
                    break;
                case "3": {
                    System.out.print("Student ID: ");
                    String sid = scanner.nextLine();
                    System.out.print("Course Code: ");
                    String code = scanner.nextLine();
                    System.out.print("Semester (SPRING/SUMMER/FALL): ");
                    Semester sem = null;
                    try { sem = Semester.valueOf(scanner.nextLine().trim().toUpperCase()); } catch (Exception ignored) {}
                    try {
                        enrollmentService.enroll(sid, code, sem);
                        System.out.println("Enrolled.");
                    } catch (Exception e) {
                        System.out.println("Enroll failed: " + e.getMessage());
                    }
                    break;
                }
                case "4": {
                    try {
                        ImportExportService io = new ImportExportService();
                        Path outDir = Path.of("exports");
                        io.exportStudents(outDir.resolve("students.csv"), studentService.listAll());
                        io.exportCourses(outDir.resolve("courses.csv"), courseService.listAll());
                        System.out.println("Exported to " + outDir.toAbsolutePath());
                    } catch (Exception e) {
                        System.out.println("Export failed: " + e.getMessage());
                    }
                    break;
                }
                case "5":
                    try {
                        BackupService backupService = new BackupService();
                        Path data = config.getDataFolder();
                        Path backups = Path.of("backups");
                        Path created = backupService.backupDirectory(data, backups);
                        long size = backupService.computeTotalSizeRecursive(created);
                        System.out.println("Backup created: " + created);
                        System.out.println("Backup size bytes: " + size);
                    } catch (Exception e) {
                        System.out.println("Backup failed: " + e.getMessage());
                    }
                    break;
                case "6": {
                    System.out.print("Student ID for transcript: ");
                    String sid = scanner.nextLine();
                    System.out.print("Semester (SPRING/SUMMER/FALL): ");
                    Semester sem = null;
                    try { sem = Semester.valueOf(scanner.nextLine().trim().toUpperCase()); } catch (Exception ignored) {}
                    double gpa = enrollmentService.computeGPA(sid, sem);
                    System.out.println("GPA:" + gpa);
                    // Anonymous inner Comparator demo: sort courses by title descending ad-hoc
                    courseService.listAll().stream()
                        .sorted(new java.util.Comparator<Course>() {
                            public int compare(Course a, Course b) {
                                String at = a.getTitle() == null ? "" : a.getTitle();
                                String bt = b.getTitle() == null ? "" : b.getTitle();
                                return -at.compareToIgnoreCase(bt);
                            }
                        })
                        .limit(3)
                        .forEach(c -> System.out.println("Top course: " + c));
                    break;
                }
                case "9":
                    // labeled jump demo once
                    System.out.println("Jumping to exit via labeled break");
                    break mainLoop;
                case "0":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice, try again.");
                    continue;
            }
        }
        System.out.println("Bye.");
    }
}


