package bean;

import java.io.Serializable;
public class Subject implements Serializable  {
	private School school ;
	private String cd;
	private String name;
	public String getCd() {
		return cd;
	}
	public void setCd(String cd) {
		this.cd = cd;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public School getSchool() {
		return school;
	}
	public void setSchool(School school) {
		this.school = school;
	}


}

//SELECT  STUDENT.ENT_YEAR ,  STUDENT.CLASS_NUM ,  STUDENT.NO ,  STUDENT.NAME ,TEST.POINT , TEST.SUBJECT_CD
//FROM STUDENT
//JOIN  TEST
//ON STUDENT.CLASS_NUM = TEST.CLASS_NUM

//SELECT STUDENT.ENT_YEAR, STUDENT.CLASS_NUM, TEST.STUDENT_NO, STUDENT.NAME, TEST.POINT, TEST.SUBJECT_CD
//FROM STUDENT
//JOIN TEST
//ON STUDENT.CLASS_NUM = TEST.CLASS_NUM
//AND STUDENT.NO = TEST.STUDENT_NO
//JOIN SUBJECT
//ON
