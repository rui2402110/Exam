package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Student;
import bean.Subject;
import bean.Teacher;
import bean.Test;
import dao.ClassNumDao;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestDao;
import tool.Action;

public class TestRegistAction extends Action {
	@Override

	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		//メソッドとスタブ
		HttpSession session = req.getSession();
		Teacher teacher = (Teacher)session.getAttribute("user");
		LocalDate todaysDate = LocalDate.now();
		int year = todaysDate.getYear();
		Test test =new Test();
//		Student student =new Student();
//		Subject subject =new Subject();


		// 値の取得に使う変数を定義
		String entYearStr = "";
		String classNum = "";
		String subjectCd = "";
		String countStr ="";
		int entYear = 0;
		boolean isAttend =false;

		Map<String, String> errorMap = new HashMap<>();


		// 使用するDAOを定義
		ClassNumDao cNumDao =new ClassNumDao();
		SubjectDao subDao =new SubjectDao();
		StudentDao stuDao =new StudentDao();
		TestDao testDao =new TestDao();

		// 入力された変数を取得
		entYearStr = req.getParameter("f1");//入学年度
		classNum = req.getParameter("f2");//クラス
		subjectCd = req.getParameter("f3");//科目コード
		countStr = req.getParameter("f4");//回数

		System.out.println(entYearStr);
		System.out.println(classNum);
		System.out.println(subjectCd);
		System.out.println(countStr);

		// 表示用の年度リストを作成
		List<Integer> entYearSet = new ArrayList<>();
		for (int i = year - 10; i < year + 1; i++) {
		    entYearSet.add(i);
		}

		if (entYearStr != null && !entYearStr.isEmpty()) {
		    entYear = Integer.parseInt(entYearStr);
		}
		// ログインユーザーの学校コードをもとにクラス番号の一覧を取得
		List<String> classList = cNumDao.filter(teacher.getSchool());

		// ログインユーザーの学校コードをもとに科目の一覧を取得
		List<Subject> subList = subDao.filter(teacher.getSchool());

		// ログインユーザーの学校コードをもとに一覧を取得
		List<Student> stuList = stuDao.filter(teacher.getSchool(), entYear, classNum, isAttend);
		// 入学年度とクラス番号を指定

		System.out.println("stuList: " +teacher.getSchool()+ " : "+entYear+ " : "+ classNum+ " : "+ isAttend);
		// JSPに送るデータをセット
		req.setAttribute("ent_year_set",entYearSet);
		req.setAttribute("class_num_set",classList);
		req.setAttribute("subject_set", subList);
		req.setAttribute("student_set", stuList);

		System.out.println("取得した学生数: " + stuList.size());

		// 入力検証
        if (entYearStr == null || classNum == null || subjectCd == null || countStr == null
                || entYearStr.isEmpty() || classNum.isEmpty() || subjectCd.isEmpty() || countStr.isEmpty()) {

            req.setAttribute("errors", "入学年度とクラスと科目と回数を選択してください");
            req.getRequestDispatcher("test_regist.jsp").forward(req, res);
            return;
        }
        //listをStudent studentに変換
        for (Student student : stuList) {
        	test.setStudent(student);
        }

        test.setSubject(subDao.get(subjectCd,teacher.getSchool()));
        req.setAttribute("points", test);
		// フォワード
	    req.getRequestDispatcher("test_regist.jsp").forward(req, res);
	}

}
