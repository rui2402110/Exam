package scoremanager.main;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Student;
import bean.Subject;
import bean.Teacher;
import dao.StudentDao;
import dao.SubjectDao;
import tool.Action;

public class TestRegistAddAction extends Action{

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		// メソッドとスタブ
		HttpSession session = req.getSession();
		Teacher teacher = (Teacher)session.getAttribute("user");

		List<Student> students = null;
		List<Subject> subjects = null;
		ArrayList<Integer> testNumbers = new ArrayList<Integer>();
		testNumbers.add(1);
		testNumbers.add(2);

		StudentDao sDao = new StudentDao();
		SubjectDao subDao =new SubjectDao();

		students = sDao.filter(teacher.getSchool(), true);
		subjects = subDao.filter(teacher.getSchool());

		req.setAttribute("studentList", students);
		req.setAttribute("subjectList", subjects);
		req.setAttribute("testNumbers", testNumbers);
		// フォワード
		req.getRequestDispatcher("test_regist_add.jsp").forward(req, res);
	}

}
