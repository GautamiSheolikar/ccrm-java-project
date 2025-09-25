package edu.ccrm.service;

import edu.ccrm.domain.Student;

import java.util.*;
import java.util.stream.Collectors;

public class StudentService {
    private final Map<String, Student> idToStudent = new HashMap<>();

    public Student create(Student student) {
        idToStudent.put(student.getId(), student);
        return student;
    }

    public Optional<Student> findById(String id) { return Optional.ofNullable(idToStudent.get(id)); }

    public List<Student> listAll() { return new ArrayList<>(idToStudent.values()); }

    public List<Student> search(String query) {
        return idToStudent.values().stream().filter(s -> s.matches(query)).collect(Collectors.toList());
    }

    public void deactivate(String id) { Optional.ofNullable(idToStudent.get(id)).ifPresent(Student::deactivate); }
}


