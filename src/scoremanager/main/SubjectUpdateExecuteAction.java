package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import tool.Action;

public class SubjectUpdateExecuteAction extends Action {
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		//メソッドとスタブ
		HttpSession session = req.getSession();
		Teacher teacher = (Teacher)session.getAttribute("user");

		// 使用する変数を定義
		Subject subject = new Subject();
		String cd = "" ;
		String name = "" ;

		// 使用するDaoを定義
		SubjectDao subDao = new SubjectDao();

		// JSPからデータを取得
		cd  = req.getParameter("cd");
		name = req.getParameter("name");

		System.out.println(" SubjectUpdateExecuteAction");
		System.out.println("cd="+ cd);
		System.out.println("name="+ name);

		//subjectsに取得したデータをまとめてsetterでセット
		subject.setCd(cd);
		subject.setName(name);
		subject.setSchool(teacher.getSchool());

		//UPDATEを呼び出し
		subDao.save(subject);

		// フォワード
		req.getRequestDispatcher("subject_update_done.jsp").forward(req, res);

	}

}
