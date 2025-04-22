package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import tool.Action;

public class SubjectUpdateAction extends Action {
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		//必要なものを定義
		HttpSession session = req.getSession();
		Teacher teacher = (Teacher)session.getAttribute("user");
//		ResultSet rSet = null;

		//使用するDaoを定義
		SubjectDao subDao = new SubjectDao();

//		//使用する変数を定義
//		List<Student> subjects = null;

		//StudentUpdateExecuteからエラーメッセージを取得 (使わない)
		String errors1 = (String)req.getAttribute("errors1");
		String errors2 = (String)req.getAttribute("errors2");

		// subject_listから届いたcdを取得
		String cd = req.getParameter("cd");
		System.out.println(cd);

		// cdを使用してそのcdの人のデータを取得
		Subject subject = subDao.get(cd, teacher.getSchool());

		// getterを使用してnameを取得
		String name = subject.getName();

		//JSPにデータをセット
		req.setAttribute("cd", cd);
		req.setAttribute("name", name);

		// エラーメッセージをセット
		req.setAttribute("errors1", errors1);
		req.setAttribute("errors2", errors2);

		// フォワード
		req.getRequestDispatcher("subject_update.jsp").forward(req, res);
	}

}
