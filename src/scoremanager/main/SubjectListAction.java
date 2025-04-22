package scoremanager.main;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import tool.Action;

public class SubjectListAction extends Action {
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		//メソッドとスタブ
		HttpSession session = req.getSession();
		Teacher teacher = (Teacher)session.getAttribute("user");

		// 値の取得に使う変数を定義
		String Cd = "";
		String Name = "";

		// 処理に使う変数を定義
		List<Subject> subject = null;
		SubjectDao subDao = new SubjectDao();
//		Map<String, String> errors = new HashMap<>();

		//DBからデータ取得
		// ログインユーザーの学校コードをもとに科目の一覧を取得
		subject = subDao.filter(teacher.getSchool());
		System.out.print(subject);


		// JSPに送るデータをセット
		req.setAttribute("subject", subject);
		// フォワード
		req.getRequestDispatcher("subject_list.jsp").forward(req, res);
	}
}