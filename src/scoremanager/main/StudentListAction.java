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
import bean.Teacher;
import dao.ClassNumDao;
import dao.StudentDao;
import tool.Action;

public class StudentListAction extends Action {
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		//メソッドとスタブ
		HttpSession session = req.getSession();
		Teacher teacher = (Teacher)session.getAttribute("user");

		// 値の取得に使う変数を定義
		String entYearStr = "";
		String classNum = "";
		String isAttendStr = "";
		int entYear = 0;
		boolean isAttend = false;

		// 処理に使う変数を定義
		List<Student> students = null;
		LocalDate todaysDate = LocalDate.now();
		int year = todaysDate.getYear();
		StudentDao sDao = new StudentDao();
		ClassNumDao cNumDao = new ClassNumDao();
		Map<String, String> errors = new HashMap<>();

		// 入力された変数を取得
		entYearStr = req.getParameter("f1");
		classNum = req.getParameter("f2");
		isAttendStr = req.getParameter("f3");

//		System.out.println(entYearStr);
//		System.out.println(classNum);
//		System.out.println(isAttendStr);

		// IsAttendStrが非nullだった場合、isAttendにtrueを代入
		if (isAttendStr != null) {
			isAttend = true;
			req.setAttribute("f3", isAttendStr);
		}

		// entYearStrが非nullだった場合、entYearに数値にしたentyearstrを代入
		if (entYearStr != null) {
			entYear = Integer.parseInt(entYearStr);
		}

		// 表示するための年度の表を作成
		List<Integer> entYearSet = new ArrayList<>();
		for (int i = year - 10; i < year + 1; i++) {
			entYearSet.add(i);
		}

		//DBからデータ取得 3
		// ログインユーザーの学校コードをもとにクラス番号の一覧を取得
		List<String> list = cNumDao.filter(teacher.getSchool());

		if (entYear != 0 && !classNum.equals("0")) {
		    // 入学年度とクラス番号を指定
		    students = sDao.filter(teacher.getSchool(), entYear, classNum, isAttend);
		} else if (entYear != 0 && classNum.equals("0")) {
		    // 入学年度のみ指定
		    students = sDao.filter(teacher.getSchool(), entYear, isAttend);
		} else if (entYear == 0 && classNum == null || entYear == 0 && classNum.equals("0")) {
		    // 指定なしの場合
		    // 全学生情報を取得
		    students = sDao.filter(teacher.getSchool(), isAttend);
		} else {
		    errors.put("f1", "クラスを指定する場合は入学年度も指定してください");
		    req.setAttribute("errors", errors);
		    students = sDao.filter(teacher.getSchool(), isAttend);
		}


		// JSPに送るデータをセット
		req.setAttribute("f1", entYear);
		req.setAttribute("f2", classNum);
		req.setAttribute("students", students);
		req.setAttribute("class_num_set", list);
		req.setAttribute("ent_year_set", entYearSet);
		// フォワード
		req.getRequestDispatcher("student_list.jsp").forward(req, res);
	}
}