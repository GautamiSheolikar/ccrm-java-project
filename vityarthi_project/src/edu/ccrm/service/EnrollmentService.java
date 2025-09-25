package edu.ccrm.service;

import edu.ccrm.domain.*;

import java.util.*;
import java.util.stream.Collectors;

public class EnrollmentService {
    private final Map<String, Enrollment> idToEnrollment = new HashMap<>();
    private final StudentService studentService;
    private final CourseService courseService;
    private final int maxCreditsPerSemester;

    public EnrollmentService(StudentService studentService, CourseService courseService, int maxCreditsPerSemester) {
        this.studentService = studentService;
        this.courseService = courseService;
        this.maxCreditsPerSemester = maxCreditsPerSemester;
    }

    public Enrollment enroll(String studentId, String courseCode, Semester semester)
        throws DuplicateEnrollmentException, MaxCreditLimitExceededException {
        String key = studentId + ":" + courseCode + ":" + (semester != null ? semester.name() : "NA");
        if (idToEnrollment.containsKey(key)) {
            throw new DuplicateEnrollmentException("Student already enrolled in course for semester");
        }
        Course course = courseService.findByCode(courseCode)
            .orElseThrow(() -> new IllegalArgumentException("Course not found: " + courseCode));
        // Check credit limit for this semester
        int currentCredits = listByStudentAndSemester(studentId, semester).stream()
            .map(e -> courseService.findByCode(e.getCourseCode()).orElse(null))
            .filter(Objects::nonNull)
            .mapToInt(Course::getCredits)
            .sum();
        if (currentCredits + Math.max(0, course.getCredits()) > maxCreditsPerSemester) {
            throw new MaxCreditLimitExceededException("Max credits exceeded for semester");
        }
        Enrollment enrollment = new Enrollment(studentId, courseCode, semester);
        idToEnrollment.put(enrollment.getId(), enrollment);
        return enrollment;
    }

    public void unenroll(String studentId, String courseCode, Semester semester) {
        String key = studentId + ":" + courseCode + ":" + (semester != null ? semester.name() : "NA");
        idToEnrollment.remove(key);
    }

    public List<Enrollment> listByStudent(String studentId) {
        return idToEnrollment.values().stream()
            .filter(e -> e.getStudentId().equals(studentId))
            .collect(Collectors.toList());
    }

    public List<Enrollment> listByStudentAndSemester(String studentId, Semester semester) {
        return idToEnrollment.values().stream()
            .filter(e -> e.getStudentId().equals(studentId) && Objects.equals(e.getSemester(), semester))
            .collect(Collectors.toList());
    }

    public void recordGrade(String studentId, String courseCode, Semester semester, Grade grade) {
        String key = studentId + ":" + courseCode + ":" + (semester != null ? semester.name() : "NA");
        Enrollment e = idToEnrollment.get(key);
        if (e == null) throw new IllegalArgumentException("Enrollment not found");
        e.setGrade(grade);
    }

    public double computeGPA(String studentId, Semester semester) {
        List<Enrollment> list = listByStudentAndSemester(studentId, semester);
        int totalCredits = 0;
        double totalPoints = 0.0;
        for (Enrollment e : list) {
            Course c = courseService.findByCode(e.getCourseCode()).orElse(null);
            if (c == null) continue;
            int cr = Math.max(0, c.getCredits());
            totalCredits += cr;
            totalPoints += cr * e.getGradePointsOrZero();
        }
        return totalCredits == 0 ? 0.0 : totalPoints / totalCredits;
    }
}


