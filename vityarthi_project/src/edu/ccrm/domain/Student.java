/*
 * Vityarthi Project
 * MIT License - see LICENSE file in the project root
 */
package edu.ccrm.domain;

public class Student extends Person implements Searchable {
    public Student(String id, String regNo, String fullName, String email) {
        super(id, regNo, fullName, email);
    }

    @Override
    public boolean matches(String query) {
        if (query == null) return false;
        String q = query.toLowerCase();
        return (getId() != null && getId().toLowerCase().contains(q))
            || (getRegistrationNumber() != null && getRegistrationNumber().toLowerCase().contains(q))
            || (getFullName() != null && getFullName().toLowerCase().contains(q))
            || (getEmail() != null && getEmail().toLowerCase().contains(q));
    }

    @Override
    public String getRole() {
        return "STUDENT";
    }
}


