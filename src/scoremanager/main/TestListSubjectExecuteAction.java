package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import bean.TestListSubject;
import dao.ClassNumDao;
import dao.SubjectDao;
import dao.TestListSubjectDao;
import tool.Action;

public class TestListSubjectExecuteAction extends Action {
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		//メソッドとスタブ
		HttpSession session = req.getSession();
		Teacher teacher = (Teacher)session.getAttribute("user");
		LocalDate todaysDate = LocalDate.now();
		int year = todaysDate.getYear();

		//使用するDAOを定義
		TestListSubjectDao tesSubDao =new TestListSubjectDao();
		SubjectDao subDao =new SubjectDao();
		ClassNumDao cNumDao =new ClassNumDao();

		// JSPから送られてくるデータを定義
		int entYear = 0 ;
		String classNum = null ;
		String subjectCd = null ;

		// JSPから送られてきたデータを取得
		String entYearStr = req.getParameter("f1");
		if (entYearStr != null && !entYearStr.equals("0")) {
			entYear = Integer.parseInt(entYearStr);
		}
		classNum = req.getParameter("f2");
		subjectCd = req.getParameter("f3");

		// 入力値を保持するためにリクエスト属性にセット
		req.setAttribute("f1", entYear == 0 ? null : entYear);
		req.setAttribute("f2", classNum);
		req.setAttribute("f3", subjectCd);

		String error1 = "入学年度とクラスと科目を選択してください";

		int errorCount = 0;

		if (entYear == 0 || classNum == null || classNum.equals("0") || subjectCd == null || subjectCd.equals("0")) {
			System.out.println("エラー通ってます");
			req.setAttribute("error1", error1);
			errorCount += 1;
		}

		System.out.println("TestListSubjectExecuteAction");
		System.out.println("entYear = " + entYear);
		System.out.println("classNum = " + classNum);
		System.out.println("cd = " + subjectCd);

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

		if (errorCount >= 1) {
			String url = "test_list.jsp";
			req.getRequestDispatcher(url).forward(req, res);
		} else {
			Subject subject = subDao.get(subjectCd, teacher.getSchool());

			List<TestListSubject> testListSubject = tesSubDao.filter(entYear, classNum, subject, teacher.getSchool());

			System.out.println(entYearSet);
			System.out.println(classList);
			System.out.println(subList);
			System.out.println(testListSubject);

			req.setAttribute("test_list", testListSubject);
			req.setAttribute("subjectCd", subject.getName());

			// フォワード
			req.getRequestDispatcher("test_list_subject.jsp").forward(req, res);
		}
	}
}