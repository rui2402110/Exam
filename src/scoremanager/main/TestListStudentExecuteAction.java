package scoremanager.main;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Student;
import bean.Teacher;
import bean.TestListStudent;
import dao.StudentDao;
import dao.TestListStudentDao;
import tool.Action;

public class TestListStudentExecuteAction extends Action {
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		//メソッドとスタブ
		HttpSession session = req.getSession();
		Teacher teacher = (Teacher)session.getAttribute("user");

		//使用するDAOを定義
		TestListStudentDao tesStuDao =new TestListStudentDao();
		StudentDao sDao = new StudentDao();

		// JSPから送られてくるデータを定義
		String studentNo = null ;

		// JSPから送られてきたデータを取得
		studentNo =req.getParameter("f4");

		// JSPから送られてきたデータを利用し生徒のデータを取得
		Student student = sDao.get(studentNo);

		//
		List<TestListStudent> testListStudent = tesStuDao.filter(student);

		System.out.println(testListStudent);

		req.setAttribute("student_info",student);
		req.setAttribute("score",testListStudent);

		// フォワード
	    req.getRequestDispatcher("test_list_student.jsp").forward(req, res);
	}

}
