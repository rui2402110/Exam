package scoremanager.main;
// 何故かsubjectを取ると別のファイルに接続されるわけわからんエラーが出るので一旦放置
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Student;
import bean.Subject;
import bean.Teacher;
import bean.TestListStudent;
import dao.ClassNumDao;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestListStudentDao;
import tool.Action;

public class TestListStudentExecuteAction extends Action {
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		//メソッドとスタブ
		HttpSession session = req.getSession();
		Teacher teacher = (Teacher)session.getAttribute("user");
		LocalDate todaysDate = LocalDate.now();
		int year = todaysDate.getYear();

		//使用するDAOを定義
		TestListStudentDao tesStuDao =new TestListStudentDao();
		StudentDao sDao = new StudentDao();
		SubjectDao subDao =new SubjectDao();
		ClassNumDao cNumDao =new ClassNumDao();

		// JSPから送られてくるデータを定義
		String studentNo = null ;

		// JSPから送られてきたデータを取得
		studentNo =req.getParameter("f4");

		// JSPから送られてきたデータを利用し生徒のデータを取得
		Student student = sDao.get(studentNo);

		//
		List<TestListStudent> testListStudent = tesStuDao.filter(student);

		System.out.println(testListStudent);

		// 表示用の年度リストを作成
		List<Integer> entYearSet = new ArrayList<>();
		for (int i = year - 10; i < year + 1; i++) {
			entYearSet.add(i);
		}

		// ログインユーザーの学校コードをもとにクラス番号の一覧を取得
		List<String> classList = cNumDao.filter(teacher.getSchool());
//
		// ログインユーザーの学校コードをもとに科目の一覧を取得
		List<Subject> subList = subDao.filter(teacher.getSchool());

		req.setAttribute("student_info",student);
		req.setAttribute("score",testListStudent);
		req.setAttribute("subject_set", subList);
		req.setAttribute("ent_year_set",entYearSet);
		req.setAttribute("class_num_set",classList);

		// フォワード
	    req.getRequestDispatcher("test_list_student.jsp").forward(req, res);
	}

}
