// package com.gradescope.cs201;

public class FUV_student_hw1 {
    private String firstName;
    private String lastName;
    private String studentId;

    // Constructor
    public FUV_student_hw1(String email) {
        if (!isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid FUV student email");
        }

        // Split email to extract first name, last name, and student id
        String[] parts = email.split("@")[0].split("\\.");
        this.firstName = parts[0];
        this.lastName = parts[1];
        this.studentId = parts[2];
    }

    // Helper method to check if the email is valid
    private boolean isValidEmail(String email) {
        String regex = "[a-zA-Z]+\\.[a-zA-Z]+\\.\\d{6}@student\\.fulbright\\.edu\\.vn";
        return email.matches(regex);
    }

    // Getter method for first name
    public String get_first_name() {
        return firstName;
    }

    // Getter method for last name
    public String get_last_name() {
        return lastName;
    }

    // Getter method for student id
    public String get_student_id() {
        return studentId;
    }
}
