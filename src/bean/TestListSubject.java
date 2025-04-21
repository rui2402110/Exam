package bean;

import java.io.Serializable;
import java.util.Map;
public class TestListSubject implements Serializable {
	private int ent_Year ;
	private String studentNo ;
	private String atudentName ;
	private String classNum ;
	private Map<Integer , Integer> points ;
	public int getEnt_Year() {
		return ent_Year;
	}
	public void setEnt_Year(int ent_Year) {
		this.ent_Year = ent_Year;
	}
	public String getStudentNo() {
		return studentNo;
	}
	public void setStudentNo(String studentNo) {
		this.studentNo = studentNo;
	}
	public String getAtudentName() {
		return atudentName;
	}
	public void setAtudentName(String atudentName) {
		this.atudentName = atudentName;
	}
	public String getClassNum() {
		return classNum;
	}
	public void setClassNum(String classNum) {
		this.classNum = classNum;
	}
	public Map<Integer , Integer> getPoints() {
		return points;
	}
	public void setPoints(Map<Integer , Integer> points) {
		this.points = points;
	}

}
