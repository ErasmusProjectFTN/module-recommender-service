package com.ErasmusProject.util;

public class ResponseProgrammeSpecification {

	private String degreeUnitCode;
	private String degreeProgrammeTitle;
	
	public ResponseProgrammeSpecification(String degreeUnitCode, String degreeProgrammeTitle) {
		super();
		this.degreeUnitCode = degreeUnitCode;
		this.degreeProgrammeTitle = degreeProgrammeTitle;
	}

	public String getDegreeUnitCode() {
		return degreeUnitCode;
	}
	
	public String getDegreeProgrammeTitle() {
		return degreeProgrammeTitle;
	}
}
