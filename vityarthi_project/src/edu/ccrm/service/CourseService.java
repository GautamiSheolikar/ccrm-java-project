package edu.ccrm.service;

import edu.ccrm.domain.Course;
import edu.ccrm.domain.Semester;

import java.util.*;
import java.util.stream.Collectors;

public class CourseService {
    private final Map<String, Course> codeToCourse = new HashMap<>();

    public static class ByCreditsThenTitleComparator implements java.util.Comparator<Course> {
        @Override
        public int compare(Course a, Course b) {
            int c = Integer.compare(a.getCredits(), b.getCredits());
            if (c != 0) return c;
            String at = a.getTitle() == null ? "" : a.getTitle();
            String bt = b.getTitle() == null ? "" : b.getTitle();
            return at.compareToIgnoreCase(bt);
        }
    }

    public Course create(Course course) { codeToCourse.put(course.getId(), course); return course; }
    public Optional<Course> findByCode(String code) { return Optional.ofNullable(codeToCourse.get(code)); }
    public List<Course> listAll() { return new ArrayList<>(codeToCourse.values()); }

    public List<Course> filterByInstructor(String instructor) {
        return codeToCourse.values().stream()
            .filter(c -> c.getInstructor() != null && c.getInstructor().equalsIgnoreCase(instructor))
            .collect(Collectors.toList());
    }

    public List<Course> filterByDepartment(String department) {
        return codeToCourse.values().stream()
            .filter(c -> c.getDepartment() != null && c.getDepartment().equalsIgnoreCase(department))
            .collect(Collectors.toList());
    }

    public List<Course> filterBySemester(Semester semester) {
        return codeToCourse.values().stream()
            .filter(c -> c.getSemester() == semester)
            .collect(Collectors.toList());
    }
}


