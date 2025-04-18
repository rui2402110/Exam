package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.ClassNum;
import bean.Teacher;
import dao.ClassNumDao;
import tool.Action;

public class ClassCreateExecuteAction extends Action {
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		//メソッドとスタブ
		HttpSession session = req.getSession();
		Teacher teacher = (Teacher)session.getAttribute("user");
		ClassNum classNum = new ClassNum();
		String classNumStr = "" ;

		// 使用するDAOを定義
		ClassNumDao cNumDao = new ClassNumDao();

		//JSPから送られたデータを取得
		classNumStr = req.getParameter("class_num");

		if (cNumDao.get(classNumStr ,teacher.getSchool())==null){
			classNum.setClass_num(classNumStr);
			classNum.setSchool(teacher.getSchool());

			// INSERTを呼び出し
			cNumDao.save(classNum);
			req.getRequestDispatcher("class_create_done.jsp").forward(req, res);

		}else{
			// 認証失敗の場合
			// エラーメッセージをセット
			String errors2 = ("クラスに被りがあります");
			req.setAttribute("errors2", errors2);

			String url = "ClassCreate.action";
			req.getRequestDispatcher(url).forward(req, res);
		}
	}

}
