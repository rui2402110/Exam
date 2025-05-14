package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Teacher;
import dao.TeacherDao;
import tool.Action;

public class TeacherUpdateExecuteAction extends Action {
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		//メソッドとスタブ
		HttpSession session = req.getSession();
		Teacher teacher = (Teacher)session.getAttribute("user");


		// 使用する変数を定義
		Teacher teacherSet =new Teacher();
		String id = "" ;
		String pass = "" ;
		String name = "" ;
		String authStr = "" ;
		boolean auth =false;

		// 使用するDaoを定義
		TeacherDao tDao = new TeacherDao();

		// JSPからデータを取得
		id  = req.getParameter("id");
		pass  = req.getParameter("pass");
		name = req.getParameter("name");
		authStr  = req.getParameter("auth");

		if (authStr != null){
			auth = true;
		}

		System.out.println(" TeacherUpdateExecuteAction");
		System.out.println("id="+ id);
		System.out.println("auth="+auth);

		//subjectsに取得したデータをまとめてsetterでセット
		teacherSet.setId(id);
		teacherSet.setPassword(pass);
		teacherSet.setName(name);
		teacherSet.setSchool(teacher.getSchool());
		teacherSet.setAuth(auth);

		//UPDATEを呼び出し
		tDao.save(teacherSet);

		// フォワード
		req.getRequestDispatcher("teacher_update_done.jsp").forward(req, res);

	}

}
