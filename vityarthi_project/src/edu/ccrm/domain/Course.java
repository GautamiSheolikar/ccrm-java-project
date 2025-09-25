/*
 * Vityarthi Project
 * MIT License - see LICENSE file in the project root
 */
package edu.ccrm.domain;

import java.util.Objects;

public class Course implements Persistable, Searchable {
    private final String id; // course code
    private String title;
    private int credits;
    private String instructor; // simple string for now
    private Semester semester;
    private String department;

    private Course(Builder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.credits = builder.credits;
        this.instructor = builder.instructor;
        this.semester = builder.semester;
        this.department = builder.department;
    }

    public static class Builder {
        private final String id;
        private String title;
        private int credits;
        private String instructor;
        private Semester semester;
        private String department;

        public Builder(String id) {
            this.id = id;
        }

        public Builder title(String title) { this.title = title; return this; }
        public Builder credits(int credits) { this.credits = credits; return this; }
        public Builder instructor(String instructor) { this.instructor = instructor; return this; }
        public Builder semester(Semester semester) { this.semester = semester; return this; }
        public Builder department(String department) { this.department = department; return this; }

        public Course build() { return new Course(this); }
    }

    @Override
    public String getId() { return id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public int getCredits() { return credits; }
    public void setCredits(int credits) { this.credits = credits; }

    public String getInstructor() { return instructor; }
    public void setInstructor(String instructor) { this.instructor = instructor; }

    public Semester getSemester() { return semester; }
    public void setSemester(Semester semester) { this.semester = semester; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    @Override
    public boolean matches(String query) {
        if (query == null) return false;
        String q = query.toLowerCase();
        return (id != null && id.toLowerCase().contains(q))
            || (title != null && title.toLowerCase().contains(q))
            || (instructor != null && instructor.toLowerCase().contains(q))
            || (department != null && department.toLowerCase().contains(q));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Course)) return false;
        Course course = (Course) o;
        return Objects.equals(id, course.id);
    }

    @Override
    public int hashCode() { return Objects.hash(id); }

    @Override
    public String toString() {
        return "Course{" +
            "code='" + id + '\'' +
            ", title='" + title + '\'' +
            ", credits=" + credits +
            ", instructor='" + instructor + '\'' +
            ", semester=" + semester +
            ", department='" + department + '\'' +
            '}';
    }
}


