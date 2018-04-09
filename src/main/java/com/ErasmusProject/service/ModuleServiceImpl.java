package com.ErasmusProject.service;

import com.ErasmusProject.model.Grade;
import com.ErasmusProject.model.ModuleSubject;
import com.ErasmusProject.util.QueryResult;
import com.ErasmusProject.util.QueryType;
import com.ErasmusProject.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ErasmusProject.util.QueryUtil.query;

public class ModuleServiceImpl implements ModuleService {

    @Override
    public List<ModuleSubject> getAllModuleSubjects() {
        ArrayList<String> namespaces = new ArrayList<>();

        namespaces.add(StringUtils.namespaceModule);

        ArrayList<QueryResult> retModuleNames = query("module", QueryType.PREDICATE);
        ArrayList<QueryResult> retSubjectNames = query("subject", QueryType.PREDICATE);

        Map<String, String> mapModuleNames = new HashMap<>();
        Map<String, String> mapSubjectNames = new HashMap<>();

        for (QueryResult queryGrades : retModuleNames) {
            mapModuleNames.put(queryGrades.getSubject(), queryGrades.getObject());
//            System.out.println(queryGrades.getSubject());
//            System.out.println(queryGrades.getPredicate());
//            System.out.println(queryGrades.getObject());
        }

        for (QueryResult querySubjects : retSubjectNames) {
            mapSubjectNames.put(querySubjects.getSubject(), querySubjects.getObject());
//            System.out.println(querySubjects.getSubject());
//            System.out.println(querySubjects.getPredicate());
//            System.out.println(querySubjects.getObject());
        }


        List<ModuleSubject> mss = new ArrayList<>();

        for (String key : mapModuleNames.keySet()) {
            ModuleSubject ms = new ModuleSubject(mapModuleNames.get(key), mapSubjectNames.get(key));
            mss.add(ms);
        }

        return mss;
    }
}
