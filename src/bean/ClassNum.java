package bean;
import java.io.Serializable;

public class ClassNum implements Serializable {
	private String class_num ;
	private School school ;
	private boolean attribute ;
	private int studentCount;

	public String getClass_num() {
		return class_num;
	}
	public void setClass_num(String class_num) {
		this.class_num = class_num;
	}
	public School getSchool() {
		return school;
	}
	public void setSchool(School school) {
		this.school = school;
	}
	public boolean isAttribute() {
		return attribute;
	}
	public void setAttribute(boolean attribute) {
		this.attribute = attribute;
	}
	public int getStudentCount() {
		return studentCount;
	}
	public void setStudentCount(int studentCount) {
		this.studentCount = studentCount;
	}



}
