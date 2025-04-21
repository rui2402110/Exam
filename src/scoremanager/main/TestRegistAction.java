package scoremanager.main;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Student;
import bean.Teacher;
import dao.ClassNumDao;
import dao.SubjectDao;
import tool.Action;

public class TestRegistAction extends Action {
	@Override
//	セッションからユーザーデータを習得
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
	HttpSession session = req.getSession();
	Teacher teacher = (Teacher)session.getAttribute("user");

	List<Student> students = null;
	ClassNumDao cNumDao = new ClassNumDao();
	SubjectDao sDao = new SubjectDao();


	//DBからデータ取得
//	セッションからユーザーデータからユーザーが所属している学校のクラスデータを習得
	List<String> list = cNumDao.filter(teacher.getSchool());


//	セッションからユーザーデータからユーザーが所属している学校の科目データを習得
	List<Subject> list = sDao.filter(teacher.getSchool());


	}
}
