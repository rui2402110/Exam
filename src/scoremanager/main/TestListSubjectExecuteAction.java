package scoremanager.main;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import bean.TestListSubject;
import dao.SubjectDao;
import dao.TestListSubjectDao;
import tool.Action;

public class TestListSubjectExecuteAction extends Action {
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		//メソッドとスタブ
		HttpSession session = req.getSession();
		Teacher teacher = (Teacher)session.getAttribute("user");

		//使用するDAOを定義
		TestListSubjectDao tesSubDao =new TestListSubjectDao();
		SubjectDao subDao =new SubjectDao();


		// JSPから送られてくるデータを定義
		int entYear = 0 ;
		String classNum = null ;
		String subjectCd = null ;

		// JSPから送られてきたデータを取得
		entYear =Integer.parseInt(req.getParameter("f1"));
		classNum =req.getParameter("f2");
		subjectCd =req.getParameter("f3");

		System.out.println(" TestListSubjectExecuteAction");
		System.out.println("entYear="+ entYear);
		System.out.println("classNum="+ classNum);
		System.out.println("cd="+subjectCd);

		Subject subject =subDao.get(subjectCd, teacher.getSchool());

		List<TestListSubject> testListSubject =tesSubDao.filter(entYear, classNum, subject, teacher.getSchool());


		req.setAttribute("ent_year_set",entYear);
		req.setAttribute("class_num_set",classNum);
		req.setAttribute("subject_set", subject);
		req.setAttribute("test_list",testListSubject);

		// フォワード
	    req.getRequestDispatcher("test_list_student.jsp").forward(req, res);
	}

}
