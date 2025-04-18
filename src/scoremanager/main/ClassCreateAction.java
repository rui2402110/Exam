package scoremanager.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Teacher;
import dao.ClassNumDao;
import tool.Action;

public class ClassCreateAction extends Action {
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		//メソッドとスタブ
		HttpSession session = req.getSession();
		Teacher teacher = (Teacher)session.getAttribute("user");

		// 使用するDAOを定義
		ClassNumDao cNumDao = new ClassNumDao();

		//DBからデータ取得 3
		// ログインユーザーの学校コードをもとにクラス番号と学校コードの一覧を取得
		Map<String, String> classToSchoolMap ;
		classToSchoolMap = cNumDao.filter2(teacher.getSchool());

		// 学校コードのみをListに格納し、データをJSPに送る
		List<String> schoolCdList = new ArrayList<>(classToSchoolMap.values());
		req.setAttribute("school_cd_list", schoolCdList);

		// フォワード
	    req.getRequestDispatcher("class_create.jsp").forward(req, res);
	}
}
