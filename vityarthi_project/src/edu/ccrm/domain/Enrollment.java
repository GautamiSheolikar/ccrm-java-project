package edu.ccrm.domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class Enrollment implements Persistable {
    private final String id; // studentId + ":" + courseCode
    private final String studentId;
    private final String courseCode;
    private final Semester semester;
    private final LocalDateTime enrolledAt;
    private Grade grade; // nullable until assigned

    public Enrollment(String studentId, String courseCode, Semester semester) {
        assert studentId != null && !studentId.isBlank();
        assert courseCode != null && !courseCode.isBlank();
        this.studentId = studentId;
        this.courseCode = courseCode;
        this.semester = semester;
        this.id = studentId + ":" + courseCode + ":" + (semester != null ? semester.name() : "NA");
        this.enrolledAt = LocalDateTime.now();
    }

    @Override
    public String getId() { return id; }

    public String getStudentId() { return studentId; }
    public String getCourseCode() { return courseCode; }
    public Semester getSemester() { return semester; }
    public LocalDateTime getEnrolledAt() { return enrolledAt; }

    public Grade getGrade() { return grade; }
    public void setGrade(Grade grade) { this.grade = grade; }

    public double getGradePointsOrZero() { return grade == null ? 0.0 : grade.getPoints(); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Enrollment)) return false;
        Enrollment that = (Enrollment) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() { return Objects.hash(id); }

    @Override
    public String toString() {
        return "Enrollment{" +
            "id='" + id + '\'' +
            ", studentId='" + studentId + '\'' +
            ", courseCode='" + courseCode + '\'' +
            ", semester=" + semester +
            ", grade=" + grade +
            ", enrolledAt=" + enrolledAt +
            '}';
    }
}


