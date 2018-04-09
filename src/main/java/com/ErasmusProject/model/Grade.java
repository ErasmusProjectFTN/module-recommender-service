package com.ErasmusProject.model;

public class Grade {

    private int grade;
    private String subject;
    private String student;
    private String module;

    public Grade() {
    }

    public Grade(String student, String subject, int grade) {
        this.grade = grade;
        this.subject = subject;
        this.student = student;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getStudent() {
        return student;
    }

    public void setStudent(String student) {
        this.student = student;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }
}
