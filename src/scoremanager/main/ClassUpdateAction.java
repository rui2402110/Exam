package scoremanager.main;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.ClassNum;
import bean.Teacher;
import dao.ClassNumDao;
import tool.Action;

public class ClassUpdateAction extends Action {
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		//メソッドとスタブ
		HttpSession session = req.getSession();
		Teacher teacher = (Teacher)session.getAttribute("user");
		ClassNum classNum = new ClassNum();

		// 使用するDAOを定義
		ClassNumDao cNumDao = new ClassNumDao();

		String errors1 ="";
		errors1 = req.getParameter("errors1");

		// classlistから届いたclass_numを取得
		String class_num = (String)req.getParameter("classnum");

		List<String> list = cNumDao.filter(teacher.getSchool());

		Map<String, String> classToSchoolMap ;
		classToSchoolMap = cNumDao.filter2(teacher.getSchool());
		String school_cd = classToSchoolMap.get(class_num);
		List<String> names = cNumDao.findNames(class_num);



//		System.out.println(class_num);
//		System.out.println(school_cd);
		req.setAttribute("names", names);
		req.setAttribute("class_num", list);
		req.setAttribute("class_num_set", class_num);
		req.setAttribute("school_cd_set", school_cd);
		req.setAttribute("errors1", errors1);

		// フォワード
	    req.getRequestDispatcher("class_update.jsp").forward(req, res);
	}
}
