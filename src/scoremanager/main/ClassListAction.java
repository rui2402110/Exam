package scoremanager.main;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Teacher;
import dao.ClassNumDao;
import tool.Action;

public class ClassListAction extends Action {
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		//メソッドとスタブ
		HttpSession session = req.getSession();
		Teacher teacher = (Teacher)session.getAttribute("user");

		// 使用するDAOを定義
		ClassNumDao cNumDao = new ClassNumDao();

		// 値の取得に使う変数を定義 (まだ使わない)
//		String schoolCd = "";
//		String classNum = "";

		// 処理に使う変数を定義

		// 入力された変数を取得
//		schoolCd = req.getParameter("school_cd");
//		classNum = req.getParameter("class_num");

		//DBからデータ取得 3
		// ログインユーザーの学校コードをもとにクラス番号と学校コードの一覧を取得
		Map<String, String> classToSchoolMap ;
		classToSchoolMap = cNumDao.filter2(teacher.getSchool());

//		for (Map.Entry<String, String> entry : classToSchoolMap.entrySet()) {
//		    System.out.println("クラス番号: " + entry.getKey() + ", 学校コード: " + entry.getValue());
//		}

		// JSPに送るデータをセット
		req.setAttribute("classMap", classToSchoolMap);
		// セッションにも保存 (JSP で使いやすい形に)
        session.setAttribute("userAuth", teacher.getAuth());
		// フォワード
	    req.getRequestDispatcher("class_list.jsp").forward(req, res);
	}

}
