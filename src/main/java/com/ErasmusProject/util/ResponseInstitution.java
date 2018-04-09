package com.ErasmusProject.util;

/**
 * 
 * @author Nina
 *
 */

public class ResponseInstitution {

	private String identifier;
	private String institutionName;
	
	public ResponseInstitution(String identifier, String institutioName){
		this.identifier = identifier;
		this.institutionName = institutioName;
	}
	
	public String getIdentifier() {
		return identifier;
	}
	public String getInstitutionName() {
		return institutionName;
	}	
}
