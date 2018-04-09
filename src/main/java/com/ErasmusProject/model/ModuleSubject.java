package com.ErasmusProject.model;

public class ModuleSubject {

    private String module;
    private String subject;

    public ModuleSubject() {
    }

    public ModuleSubject(String module, String subject) {
        this.module = module;
        this.subject = subject;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
