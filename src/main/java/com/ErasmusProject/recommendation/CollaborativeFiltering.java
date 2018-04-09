package com.ErasmusProject.recommendation;

import com.ErasmusProject.model.Grade;
import com.ErasmusProject.model.ModuleGrades;
import com.ErasmusProject.service.GradeService;
import com.ErasmusProject.service.GradeServiceImpl;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;
import org.apache.jena.base.Sys;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class CollaborativeFiltering {

    private GradeService gradeService = new GradeServiceImpl();

    public Map<String, Double> calculatePearsonCorrelation(Map<String, Integer> studentGrades) {
        Map<String, Double> output = new HashMap<>();

        Map<String, ModuleGrades> students = gradeService.getGrades();

        Map<String, Integer> subjects = new HashMap<>();
        int i=0;
        for (String subject : studentGrades.keySet()) {
            if (!subjects.containsKey(subject)) {
                subjects.put(subject, i++);
            }
        }

        List<Double> myGrades = new ArrayList<>(Collections.nCopies(subjects.size(), 0.0));
        for (String subjectName : studentGrades.keySet()) {
            Integer subjectIndex = subjects.get(subjectName);
            Integer myGrade = studentGrades.get(subjectName);
            myGrades.set(subjectIndex, (double) myGrade);
        }

        double[] myGradesArray = ArrayUtils.toPrimitive(myGrades.toArray(new Double[myGrades.size()]));

        for (String student : students.keySet()) {
            ModuleGrades moduleGrades = students.get(student);
            List<Double> grades = new ArrayList<>(Collections.nCopies(subjects.size(), 0.0));
            double average = 0.0;
            int avgCounter = 0;
            for (Grade grade : moduleGrades.getGrades()) {
                if (subjects.containsKey(grade.getSubject())) {
                    grades.set(subjects.get(grade.getSubject()), (double) grade.getGrade());
                    average += (double) grade.getGrade();
                    avgCounter++;
                }
            }

            double[] gradesArray = ArrayUtils.toPrimitive(grades.toArray(new Double[myGrades.size()]));

            double value = new PearsonsCorrelation().correlation(myGradesArray, gradesArray);
            value = (value + 1) * 5 * (average/avgCounter);

            if (!output.containsKey(moduleGrades.getModule()) ) {
                output.put(moduleGrades.getModule(), value);
            } else if (output.containsKey(moduleGrades.getModule()) && output.get(moduleGrades.getModule()) < value) {
                output.put(moduleGrades.getModule(), value);
            }
        }

        return sortByValue(output);

    }

    private <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> unsortMap) {

        List<Map.Entry<K, V>> list =
                new LinkedList<Map.Entry<K, V>>(unsortMap.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
            public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });

        Map<K, V> result = new LinkedHashMap<K, V>();
        Collections.reverse(list);
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }

        return result;

    }

    private List<Map<String, String>> readStudentsFromFile() {

        try {
            File file = ResourceUtils.getFile("classpath:students.csv");

            //Read File Content
            String content = new String(Files.readAllBytes(file.toPath()));

            List<String> rows = Arrays.asList(content.split("\n"));

            List<Map<String, String>> students = new ArrayList<>();

            List<String> subjects = new ArrayList<>();
            int index = 0;
            for (String row: rows) {
                if (index++ == 0) {
                    subjects = Arrays.asList(row.trim().split(","));
                } else if (!"".equals(row.trim())) {
                    List<String> student = Arrays.asList(row.trim().split(","));
//                    students.add(student);
                    Map<String, String> map = new HashMap<>();
                    for (int i = 0; i < subjects.size(); i++) {
                        map.put(subjects.get(i), student.get(i));
                    }

                    students.add(map);
                }
            }

            return students;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    private Map<String, Integer> readSubjectsFromFile() {

        try {
            File file = ResourceUtils.getFile("classpath:predmeti.txt");

            //Read File Content
            String content = new String(Files.readAllBytes(file.toPath()));

            List<String> rows = Arrays.asList(content.split("\n"));

            Map<String, Integer> subjects = new HashMap<>();

            int index = 0;
            for (String row: rows) {
               if (!row.contains("Godina:") && !row.contains("Izborni")) {
                   if (!subjects.containsKey(row.trim()))
                       subjects.put(row.trim(), index++);
               }
            }

            return subjects;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return new HashMap<>();
    }
}