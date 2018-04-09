package com.ErasmusProject.util;

import com.ErasmusProject.model.Student;

public class ResponseSignInFlag {

	public static enum Flag {STUDENT, ADMIN};
	
	
	private Flag flag;
	private Student student;

	public ResponseSignInFlag(Flag flag) {
		super();
		
		this.flag = flag;
		this.setStudent(new Student());
	}
	
	
	public Flag getFlag() {
		return flag;
	}

	public void setFlag(Flag flag) {
		this.flag = flag;
	}


	public Student getStudent() {
		return student;
	}


	public void setStudent(Student student) {
		this.student = student;
	}

	
	
}
