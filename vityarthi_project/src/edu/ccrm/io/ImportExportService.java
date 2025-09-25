/*
 * Vityarthi Project
 * MIT License - see LICENSE file in the project root
 */
package edu.ccrm.io;

import edu.ccrm.domain.Course;
import edu.ccrm.domain.Semester;
import edu.ccrm.domain.Student;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.BufferedWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ImportExportService {
    public List<Student> loadStudents(Path csvPath) throws IOException {
        List<Student> students = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(csvPath, StandardCharsets.UTF_8)) {
            String line;
            boolean isFirst = true;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                // Skip header if present
                if (isFirst && line.toLowerCase().contains("id") && line.toLowerCase().contains("reg")) {
                    isFirst = false;
                    continue;
                }
                isFirst = false;
                String[] parts = line.split(",");
                if (parts.length < 4) continue;
                String id = parts[0].trim();
                String regNo = parts[1].trim();
                String fullName = parts[2].trim();
                String email = parts[3].trim();
                students.add(new Student(id, regNo, fullName, email));
            }
        }
        return students;
    }

    public List<Course> loadCourses(Path csvPath) throws IOException {
        List<Course> courses = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(csvPath, StandardCharsets.UTF_8)) {
            String line;
            boolean isFirst = true;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                // Skip header if present
                if (isFirst && line.toLowerCase().contains("code") && line.toLowerCase().contains("title")) {
                    isFirst = false;
                    continue;
                }
                isFirst = false;
                String[] parts = line.split(",");
                if (parts.length < 6) continue;
                String code = parts[0].trim();
                String title = parts[1].trim();
                int credits;
                try {
                    credits = Integer.parseInt(parts[2].trim());
                } catch (NumberFormatException e) {
                    credits = 0;
                }
                String instructor = parts[3].trim();
                Semester semester = null;
                try {
                    semester = Semester.valueOf(parts[4].trim().toUpperCase());
                } catch (Exception ignored) {}
                String department = parts[5].trim();
                courses.add(new Course.Builder(code)
                    .title(title)
                    .credits(credits)
                    .instructor(instructor)
                    .semester(semester)
                    .department(department)
                    .build());
            }
        }
        return courses;
    }

    public void exportStudents(Path csvPath, List<Student> students) throws IOException {
        Files.createDirectories(csvPath.getParent());
        try (BufferedWriter w = Files.newBufferedWriter(csvPath, StandardCharsets.UTF_8)) {
            w.write("id,regNo,fullName,email\n");
            for (Student s : students) {
                String line = String.join(",",
                    safe(s.getId()),
                    safe(s.getRegistrationNumber()),
                    safe(s.getFullName()),
                    safe(s.getEmail()));
                w.write(line);
                w.write("\n");
            }
        }
    }

    public void exportCourses(Path csvPath, List<Course> courses) throws IOException {
        Files.createDirectories(csvPath.getParent());
        try (BufferedWriter w = Files.newBufferedWriter(csvPath, StandardCharsets.UTF_8)) {
            w.write("code,title,credits,instructor,semester,department\n");
            for (Course c : courses) {
                String sem = c.getSemester() == null ? "" : c.getSemester().name();
                String line = String.join(",",
                    safe(c.getId()),
                    safe(c.getTitle()),
                    Integer.toString(Math.max(0, c.getCredits())),
                    safe(c.getInstructor()),
                    safe(sem),
                    safe(c.getDepartment()));
                w.write(line);
                w.write("\n");
            }
        }
    }

    private static String safe(String v) { return v == null ? "" : v; }
}


