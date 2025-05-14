package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Teacher;
import dao.TeacherDao;
import tool.Action;

public class TeacherUpdateAction extends Action {
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		//必要なものを定義
		HttpSession session = req.getSession();
		Teacher teacher = (Teacher)session.getAttribute("user");

		//使用するDaoを定義
		TeacherDao tDao = new TeacherDao();


		// tacher_listから届いたcdを取得
		String id = req.getParameter("id");
		System.out.println(id);

		// idを使用してそのcdの人のデータを取得
		Teacher teacherSet = tDao.get(id);


		//JSPにデータをセット
		req.setAttribute("id", id);
		req.setAttribute("pass", teacherSet.getPassword());
		req.setAttribute("name", teacherSet.getName());
		req.setAttribute("auth", teacherSet.getAuth());

		// フォワード
		req.getRequestDispatcher("teacher_update.jsp").forward(req, res);

	}

}
