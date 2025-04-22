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
import dao.SubjectDao;
import tool.Action;

public class TestRegistAction extends Action {
//	メモ：入学年度,クラス番号,科目,回数　が必要
	@Override
//	セッションからユーザーデータを習得
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

	HttpSession session = req.getSession();
	Teacher teacher = (Teacher)session.getAttribute("user");

	// 入力された変数を取得
	String entYearStr = req.getParameter("ent_year");//入学年度
    String classNum = req.getParameter("class_num");//クラス
	String subjectCd = req.getParameter("subject_cd");//科目
    String testNumStr = req.getParameter("test_num"); //回数
    boolean isAttend = true;


	// 処理に使う変数を定義
	List<Student> students = null;
	LocalDate todaysDate = LocalDate.now();
	int year = todaysDate.getYear();
	
	StudentDao sDao = new StudentDao();
	ClassNumDao cNumDao = new ClassNumDao();
	SubjectDao subDao = new SubjectDao();
	
	Map<String, String> errors = new HashMap<>();
	int entYear = 0;
    int testNum = 0;

// entYearStrが非nullだった場合、entYearに数値にしたentyearstrを代入
	if (entYearStr != null) {
		entYear = Integer.parseInt(entYearStr);
	}


	// 表示するための年度の表を作成
	List<Integer> entYearSet = new ArrayList<>();
	for (int i = year - 10; i < year + 1; i++) {
		entYearSet.add(i);
	}

//
//	//DBからデータ取得
////	セッションからユーザーデータからユーザーが所属している学校のクラスデータを習得
//	List<String> list = cNumDao.filter(teacher.getSchool());
//
//
////	セッションからユーザーデータからユーザーが所属している学校の科目データを習得
//	List<Subject> subjects = subDao.filter(teacher.getSchool());
//
//
//	if (entYear != 0 && !classNum.equals("0")) {
//	    // 入学年度とクラス番号を指定
//	    students = sDao.filter(teacher.getSchool(), entYear, classNum, isAttend);
//	} else if (entYear != 0 && classNum.equals("0")) {
//	    // 入学年度のみ指定
//	    students = sDao.filter(teacher.getSchool(), entYear, isAttend);
//	} else if (entYear == 0 && classNum == null || entYear == 0 && classNum.equals("0")) {
//	    // 指定なしの場合
//	    // 全学生情報を取得
//	    students = sDao.filter(teacher.getSchool(), isAttend);
//	} else {
//	    errors.put("f1", "クラスを指定する場合は入学年度も指定してください");
//	    req.setAttribute("errors", errors);
//	    students = sDao.filter(teacher.getSchool(), isAttend);
//	}
//
//	// JSPに送るデータをセット
//	req.setAttribute("f1", entYear);
//	req.setAttribute("f2", classNum);
//	req.setAttribute("students", students);
//	req.setAttribute("class_num_set", list);
//	req.setAttribute("ent_year_set", entYearSet);
//	// フォワード
//	req.getRequestDispatcher("test_regist.jsp").forward(req, res);

	}
}
