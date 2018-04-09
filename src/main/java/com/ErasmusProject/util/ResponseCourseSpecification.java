package com.ErasmusProject.util;

public class ResponseCourseSpecification {

	private String courseUnitCode;
	private String courseUnitTitle;
	
	public ResponseCourseSpecification(String courseUnitCode, String courseUnitTitle) {
		super();
		this.courseUnitCode = courseUnitCode;
		this.courseUnitTitle = courseUnitTitle;
	}

	public String getCourseUnitCode() {
		return courseUnitCode;
	}

	public String getCourseUnitTitle() {
		return courseUnitTitle;
	}
}
