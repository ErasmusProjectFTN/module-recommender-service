package com.ErasmusProject.util;

/**
 * Created by Komp on 13.2.2017.
 */
public class Response {

    private String name;
    private String surname;
    

    public Response(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }
    
    public Response(String signInFlag){
    	this.name = signInFlag;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }
}
