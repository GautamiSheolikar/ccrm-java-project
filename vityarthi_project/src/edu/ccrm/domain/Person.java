package edu.ccrm.domain;

import java.time.LocalDateTime;
import java.util.Objects;

public abstract class Person implements Persistable {
    private final String id;
    private String registrationNumber;
    private String fullName;
    private String email;
    private boolean active;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    protected Person(String id, String registrationNumber, String fullName, String email) {
        assert id != null && !id.isBlank() : "id must be non-null";
        this.id = id;
        this.registrationNumber = registrationNumber;
        this.fullName = fullName;
        this.email = email;
        this.active = true;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
    }

    @Override
    public String getId() {
        return id;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
        touch();
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
        touch();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        touch();
    }

    public boolean isActive() {
        return active;
    }

    public void deactivate() {
        this.active = false;
        touch();
    }

    public void activate() {
        this.active = true;
        touch();
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    protected void touch() {
        this.updatedAt = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;
        Person person = (Person) o;
        return Objects.equals(id, person.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
            "id='" + id + '\'' +
            ", regNo='" + registrationNumber + '\'' +
            ", fullName='" + fullName + '\'' +
            ", email='" + email + '\'' +
            ", active=" + active +
            ", createdAt=" + createdAt +
            ", updatedAt=" + updatedAt +
            '}';
    }

    public abstract String getRole();
}



