package com.ErasmusProject.controller;

import com.ErasmusProject.model.Grade;
import com.ErasmusProject.model.ModuleGrades;
import com.ErasmusProject.recommendation.CollaborativeFiltering;
import com.ErasmusProject.service.GradeServiceImpl;
import com.ErasmusProject.util.*;
import org.apache.jena.rdf.model.Model;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/grades")
public class GradeStore {

    @Autowired
    private Conf conf;

    private GradeServiceImpl gradeService = new GradeServiceImpl();
    private CollaborativeFiltering collaborativeFiltering = new CollaborativeFiltering();

    @PostConstruct
    public void initFuseki()
    {
        System.out.println("\n\n"+conf.getInitialize()+"\n\n");
        if(!conf.getInitialize()) return;
        System.out.println("\n\nPROSAO\n\n");
        try {
//            Model student = OntologyUtils.createOntModel(StringUtils.studentFile);
//            Model ects = OntologyUtils.createOntModel(StringUtils.ectsFile);
//            Model application = OntologyUtils.createOntModel(StringUtils.applicationFile);
            Model e2 = OntologyUtils.createOntModel(StringUtils.e2File);
//            ects.add(student);
//            ects.add(application);
//            ects.add(e2);
            OntologyUtils.reloadModel(e2,StringUtils.URL);

            // create similarity matrix

//            DegreeProgrammeRecommendation dpr = new DegreeProgrammeRecommendation();
//            similarityMatrix = dpr.generateSimilarityMatrix();
//            System.out.println(similarityMatrix);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        dataBinder.registerCustomEditor(QueryType.class, new QueryTypeConverter());
    }



    @RequestMapping(method = RequestMethod.POST, value = "/collaborative")
    public Map<String, Double> collaborative(@RequestBody String studentGradesJSON)   {

        JSONObject json = new JSONObject(studentGradesJSON);
        Map<String, Integer> studentGrades = new HashMap<>();

        Set<String> subjects = json.keySet();
        for (String subject : subjects) {
            if (!"student".equals(subject)) {
                int grade;
                if ((json.getString(subject)).equals("undefined")) {
                    grade = 0;
                } else {
                    grade = Integer.parseInt(json.getString(subject));
                }

                if (grade != 0)
                    studentGrades.put(subject, grade);
            }
        }

        return collaborativeFiltering.calculatePearsonCorrelation(studentGrades);
    }

    @RequestMapping(method = RequestMethod.POST, value="/add")
    public Grade addGrade(@RequestParam(value="grade", required=true) String grade,
                           @RequestParam(value="student", required=true) String student,
                           @RequestParam(value="subject", required=true) String subject,
                           @RequestParam(value="module", required=true) String module) {

        return gradeService.addGrade(student, subject, Integer.parseInt(grade), module);

//        String identifier = UUID.randomUUID().toString();
//        String query = "PREFIX module: <" + StringUtils.namespaceModule + "> "
//                +"INSERT DATA"
//                +"{"
//                +" module:" + identifier + " module:grade \"" + Integer.parseInt(grade.replaceAll("[\\t\\n\\r]","")) + "\" ;"
//                + "						     module:student \"" + username.replaceAll("[\\t\\n\\r]","") + "\" ;"
//                + "						     module:subject \"" + subjectName.replaceAll("[\\t\\n\\r]","") + "\" ;"
//                +"}";
//
//        System.out.println(query);
//        try{
//            OntologyUtils.execUpdate(StringUtils.URLupdate, query);
//        }catch(Exception e){
//            e.printStackTrace();
//            return null;
//        }
//        return "Successfully added grade";
    }


    /**
     * Get programmes
     * @return programmes from db
     */
    @RequestMapping(method = RequestMethod.GET)
    public Map<String, ModuleGrades> getGrades() {
        return gradeService.getGrades();
    }


//    private List<String> readSubjectsFromFile() {
//
//        try {
//            File file = ResourceUtils.getFile("classpath:predmeti.txt");
//
//            //Read File Content
//            String content = new String(Files.readAllBytes(file.toPath()));
//
//            List<String> predmeti = Arrays.asList(content.split("\n"));
//
//            List<String> outputPredmeti = new ArrayList<>();
//
//            for (String predmet : predmeti) {
//                if (predmet.contains("\t") && !predmet.contains("Izborni")) {
//                    outputPredmeti.add(predmet.trim());
//                }
//            }
//
//            return outputPredmeti;
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return new ArrayList<>();
//    }

}
