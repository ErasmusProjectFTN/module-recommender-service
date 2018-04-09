package com.ErasmusProject.service;

import com.ErasmusProject.model.Grade;
import com.ErasmusProject.model.ModuleGrades;

import java.util.List;
import java.util.Map;

public interface GradeService {

    Grade addGrade(String student, String subject, int grade, String module);

    Grade removeGrade(Grade grade);

    Map<String, ModuleGrades> getGrades();
}
