package scoremanager.main;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import dao.ClassNumDao;
import dao.SubjectDao;
import tool.Action;

public class TestRegistAction extends Action {
	@Override

	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
//		セッションからユーザーデータを習得
	HttpSession session = req.getSession();
	Teacher teacher = (Teacher)session.getAttribute("user");

	//処理に使う変数を定義
	List<String> classNum = null;
	List<Subject> subject = null;
	ClassNumDao cNumDao = new ClassNumDao();
	SubjectDao subDao =new SubjectDao();

	//セッションのユーザーからユーザーが所属している学校の...
	//クラスデータを取得
	classNum = cNumDao.filter(teacher.getSchool());
	//科目データを取得
	subject =subDao.filter(teacher.getSchool());

	// JSPに送るデータをセット
	req.setAttribute("classNum", classNum);
	req.setAttribute("subject", subject);
	// フォワード
	req.getRequestDispatcher("test_regist.jsp").forward(req, res);
	}
}
