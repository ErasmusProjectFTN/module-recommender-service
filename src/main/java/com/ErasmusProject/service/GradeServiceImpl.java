package com.ErasmusProject.service;

import com.ErasmusProject.model.Grade;
import com.ErasmusProject.model.ModuleGrades;
import com.ErasmusProject.util.OntologyUtils;
import com.ErasmusProject.util.QueryResult;
import com.ErasmusProject.util.QueryType;
import com.ErasmusProject.util.StringUtils;

import java.util.*;

import static com.ErasmusProject.util.QueryUtil.query;

public class GradeServiceImpl implements GradeService {
    @Override
    public Grade addGrade(String student, String subject, int grade, String module) {

        Grade gradeObj = new Grade(student, subject, grade);
        String identifier = UUID.randomUUID().toString();
        String query = "PREFIX module: <" + StringUtils.namespaceModule + "> "
                +"INSERT DATA"
                +"{"
                +" module:" + identifier + " module:grade \"" + grade + "\" ;"
                + "						     module:student \"" + student.replaceAll("[\\t\\n\\r]","") + "\" ;"
                + "						     module:subject \"" + subject.replaceAll("[\\t\\n\\r]","") + "\" ;"
                + "						     module:module \"" + module.replaceAll("[\\t\\n\\r]","") + "\" ;"
                +"}";

        System.out.println(query);

        try{
            OntologyUtils.execUpdate(StringUtils.URLupdate, query);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }

        return gradeObj;
    }

    @Override
    public Grade removeGrade(Grade grade) {
        return null;
    }

    @Override
    public Map<String, ModuleGrades> getGrades() {
        ArrayList<String> namespaces = new ArrayList<String>();
        ArrayList<QueryResult> retGrades, retSubjects, retStudents, retModule;

        namespaces.add(StringUtils.namespaceModule);

        Map<String, Integer> moduleLevel = new HashMap<>();
        moduleLevel.put("Osnovni", 0);
        moduleLevel.put("RT-RK", 1);
        moduleLevel.put("Automatika", 1);
        moduleLevel.put("PRNI", 1);
        moduleLevel.put("Internet i elektronsko poslovanje", 2);
        moduleLevel.put("Informacioni sistemi", 2);
        moduleLevel.put("Inteligentni sistemi", 2);
        moduleLevel.put("Softversko inzenjerstvo", 2);


        retGrades = query("grade", QueryType.PREDICATE);
        retSubjects = query("subject", QueryType.PREDICATE);
        retStudents = query("student", QueryType.PREDICATE);
        retModule = query("module", QueryType.PREDICATE);

        Map<String, Integer> mapGrades = new HashMap<>();
        Map<String, String> mapSubjects = new HashMap<>();
        Map<String, String> mapStudents = new HashMap<>();
        Map<String, String> mapModules = new HashMap<>();

        for (QueryResult queryGrades : retGrades) {
            mapGrades.put(queryGrades.getSubject(), Integer.parseInt(queryGrades.getObject()));
//            System.out.println(queryGrades.getSubject());
//            System.out.println(queryGrades.getPredicate());
//            System.out.println(queryGrades.getObject());
        }

        for (QueryResult querySubjects : retSubjects) {
            mapSubjects.put(querySubjects.getSubject(), querySubjects.getObject());
//            System.out.println(querySubjects.getSubject());
//            System.out.println(querySubjects.getPredicate());
//            System.out.println(querySubjects.getObject());
        }

        for (QueryResult queryStudents : retStudents) {
            mapStudents.put(queryStudents.getSubject(), queryStudents.getObject());
//            System.out.println(queryStudents.getSubject());
//            System.out.println(queryStudents.getPredicate());
//            System.out.println(queryStudents.getObject());
        }

        for (QueryResult queryModules : retModule) {
            mapModules.put(queryModules.getSubject(), queryModules.getObject());
//            System.out.println(queryStudents.getSubject());
//            System.out.println(queryStudents.getPredicate());
//            System.out.println(queryStudents.getObject());
        }

        Map<String, ModuleGrades> studentGrades = new HashMap<>();

        for (String key : mapGrades.keySet()) {
            Grade grade = new Grade();
            grade.setGrade(mapGrades.get(key));
            grade.setSubject(mapSubjects.get(key));
            grade.setStudent(mapStudents.get(key));
            grade.setModule(mapModules.get(key));

            if (studentGrades.get(mapStudents.get(key)) == null) {
                ModuleGrades moduleGrades = new ModuleGrades();
                List<Grade> temp = new ArrayList<>();
                temp.add(grade);
                moduleGrades.setGrades(temp);
                moduleGrades.setModule("Osnovni");
                if (moduleLevel.get(grade.getModule()) > moduleLevel.get(moduleGrades.getModule())) {
                    moduleGrades.setModule(grade.getModule());
                }
                studentGrades.put(mapStudents.get(key), moduleGrades);
            } else {
                studentGrades.get(mapStudents.get(key)).addToGrades(grade);
                if (moduleLevel.get(grade.getModule()) > moduleLevel.get(studentGrades.get(mapStudents.get(key)).getModule())) {
                    studentGrades.get(mapStudents.get(key)).setModule(grade.getModule());
                }
            }
        }

        return studentGrades;
    }
}
