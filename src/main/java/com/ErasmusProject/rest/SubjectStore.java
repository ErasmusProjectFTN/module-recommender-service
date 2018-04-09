package com.ErasmusProject.rest;

import com.ErasmusProject.util.OntologyUtils;
import com.ErasmusProject.util.QueryResult;
import com.ErasmusProject.util.QueryType;
import com.ErasmusProject.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/subjects")
public class SubjectStore {

//    @PostConstruct
//    public void addAllSubjects() {
//        for (String subject : readSubjectsFromFile()) {
//            addSubject(subject);
//        }
//    }

    @RequestMapping(method = RequestMethod.POST, value="/add")
    public String addSubject(@RequestParam(value="subjectName", required=true) String subjectName) {

        String identifier = UUID.randomUUID().toString();
        String query = "PREFIX module: <" + StringUtils.namespaceModule + "> "
                +"INSERT DATA"
                +"{"
                +" module:" + identifier + " module:subjectName \"" + subjectName.replaceAll("[\\t\\n\\r]","") + "\" ;"
                +"}";

        System.out.println(query);
        try{
            OntologyUtils.execUpdate(StringUtils.URLupdate, query);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
        return "Successfully created subject";
    }


    /**
     * Get programmes
     * @return programmes from db
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<String> getSubjects()
    {
        ArrayList<String> namespaces = new ArrayList<String>();
        ArrayList<QueryResult> retVal;

        namespaces.add(StringUtils.namespaceModule);

        retVal = query("subjectName", QueryType.PREDICATE);

        ArrayList<String> subjects = new ArrayList<>();

        String subjectName;
        ArrayList<QueryResult> results;


        for (QueryResult queryResult : retVal) {
            subjectName = queryResult.getSubject();
            results = query(subjectName, QueryType.SUBJECT);
            for (QueryResult queryResult2 : results) {
                if (queryResult2.getPredicate().equals("subjectName"))
                    subjectName = queryResult2.getObject();
            }

            subjects.add(subjectName);
        }

        return subjects;
    }

    // generic query
    @RequestMapping(method = RequestMethod.GET, value = "/query")
    public ArrayList<QueryResult> query(@RequestParam("value") String val,
                                        @RequestParam("type") QueryType type)
    {

        ArrayList<String> namespaces = new ArrayList<>();
        ArrayList<QueryResult> retVal = null;
        namespaces.add(StringUtils.namespaceModule);


        switch(type)
        {
            case SUBJECT:
                String subject = "<" + StringUtils.namespaceModule + val + ">";
                retVal = OntologyUtils.formatedSelect(StringUtils.URLquery, String.format(StringUtils.sparqlTemplate,subject,"?p","?o","?p","\""+StringUtils.namespaceModule +"\""), namespaces, type, val);
                break;
            case PREDICATE:
                String predicate = "<" + StringUtils.namespaceModule + val + ">";
                retVal = OntologyUtils.formatedSelect(StringUtils.URLquery, String.format(StringUtils.sparqlTemplate,"?s",predicate,"?o","?s","\""+StringUtils.namespaceModule +"\""), namespaces, type, val);
                break;
            case OBJECT:
                retVal = OntologyUtils.formatedSelect(StringUtils.URLquery, String.format(StringUtils.sparqlTemplate,"?s","?p","\"" + val + "\"","?p","\""+StringUtils.namespaceModule +"\""), namespaces, type, val);
                break;
        }

        return retVal;
    }

}
