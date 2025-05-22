package scoremanager.main;

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
		TestListStudentDao tesStuDao = new TestListStudentDao();
		StudentDao sDao = new StudentDao();
		SubjectDao subDao = new SubjectDao();
		ClassNumDao cNumDao = new ClassNumDao();

		// JSPから送られてくるデータを定義
		String studentNo = null;

		// JSPから送られてきたデータを取得
		studentNo = req.getParameter("f4");

		// 入力値を保持するためにリクエスト属性にセット
		req.setAttribute("f4", studentNo);

		// 表示用の年度リストを作成
		List<Integer> entYearSet = new ArrayList<>();
		for (int i = year - 10; i < year + 1; i++) {
			entYearSet.add(i);
		}

		// ログインユーザーの学校コードをもとにクラス番号の一覧を取得
		List<String> classList = cNumDao.filter(teacher.getSchool());

		// ログインユーザーの学校コードをもとに科目の一覧を取得
		List<Subject> subList = subDao.filter(teacher.getSchool());

		// 共通データをセット
		req.setAttribute("ent_year_set", entYearSet);
		req.setAttribute("class_num_set", classList);
		req.setAttribute("subject_set", subList);

		// JSPから送られてきたデータを利用し生徒のデータを取得
		Student student = sDao.get(studentNo);

		if (student == null) {
			String error1 = "成績情報が存在しませんでした";
			req.setAttribute("error1", error1);
			String url = "test_list.jsp";
			req.getRequestDispatcher(url).forward(req, res);
		} else {
			List<TestListStudent> testListStudent = tesStuDao.filter(student);

			System.out.println(testListStudent);

			req.setAttribute("student_info", student);
			req.setAttribute("score", testListStudent);

			// フォワード
			req.getRequestDispatcher("test_list_student.jsp").forward(req, res);
		}
	}
}