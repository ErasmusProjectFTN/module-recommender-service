package com.ErasmusProject.model;

import java.util.List;

public class ModuleGrades {

    private String module;
    private List<Grade> grades;

    public ModuleGrades() {
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public List<Grade> getGrades() {
        return grades;
    }

    public void setGrades(List<Grade> grades) {
        this.grades = grades;
    }

    public void addToGrades(Grade grade) {
        this.grades.add(grade);
    }
}
