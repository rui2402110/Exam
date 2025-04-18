package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.ClassNum;
import bean.Teacher;
import dao.ClassNumDao;
import tool.Action;

public class ClassUpdateExecuteAction extends Action {
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		//メソッドとスタブ
		HttpSession session = req.getSession();
		Teacher teacher = (Teacher)session.getAttribute("user");

		// 使用する変数を定義
		ClassNum classNum = new ClassNum();
		String classNumStr = "";
		String NewClassNumStr = "";
		String schoolCdStr ="" ;

		// 使用するDAOを定義
		ClassNumDao cNumDao = new ClassNumDao();

		// JSPからデータを取得
		classNumStr = req.getParameter("class_num");
		schoolCdStr = req.getParameter("school_cd");
		NewClassNumStr = req.getParameter("new_class_num");

		System.out.println(classNumStr);
		System.out.println(NewClassNumStr);


		if (cNumDao.get(classNumStr, teacher.getSchool()) == null){
			//classNumに取得したデータをsetterでセット
			classNum.setClass_num(classNumStr);
			classNum.setSchool(teacher.getSchool());

			//UPDATEを呼び出し
			cNumDao.save(classNum , NewClassNumStr , schoolCdStr);
			// フォワード
			req.getRequestDispatcher("class_update_done.jsp").forward(req, res);
		}else {
			String errors1 = ("クラスに被りがあります");
			req.setAttribute("errors1", errors1);

			String url = "ClassUpdate.action";
			req.getRequestDispatcher(url).forward(req, res);
		}


	}

}
