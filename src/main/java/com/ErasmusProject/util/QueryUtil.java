package com.ErasmusProject.util;

import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

public class QueryUtil {

    public static ArrayList<QueryResult> query(String val, QueryType type) {

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
