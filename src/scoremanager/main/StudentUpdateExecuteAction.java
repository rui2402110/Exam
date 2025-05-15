package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Student;
import bean.Teacher;
import dao.StudentDao;
import tool.Action;

public class StudentUpdateExecuteAction extends Action {
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		//メソッドとスタブ
		HttpSession session = req.getSession();
		Teacher teacher = (Teacher)session.getAttribute("user");

		// 使用する変数を定義
		Student students = new Student() ;
		String entYearStr = "";
		int entYear ;
		String no = "" ;
		String name = "" ;
		String classNum = "";
		String isAttendStr ="" ;
		String errors1 =null;

		// 使用するDaoを定義
		StudentDao sDao = new StudentDao();

		// JSPからデータを取得
		entYearStr = req.getParameter("ent_year");
		no  = req.getParameter("no");
		name = req.getParameter("name");
		classNum  = req.getParameter("class_num");
		boolean isAttend = req.getParameter("is_attend")!=null;

//		System.out.println("entYear="+ entYearStr);
//		System.out.println("no="+ no);
//		System.out.println("name="+ name);
//		System.out.println("classnum="+ classNum);
//		System.out.println("isAttend="+ isAttendStr);
		entYear = Integer.parseInt(entYearStr);


		if (classNum.equals("0")){
			errors1 = "class_numの入力が済んでいません";
			req.setAttribute("errors1", errors1);
			String url = "StudentUpdate.action";
			req.getRequestDispatcher(url).forward(req, res);
		} else{
			//studentsに取得したデータをまとめてsetterでセット
			students.setEntYear(entYear);
			students.setNo(no);
			students.setName(name);
			students.setClassNum(classNum);
			students.setAttend(isAttend);
			students.setSchool(teacher.getSchool());


			//UPDATEを呼び出し
			sDao.save(students);

			// フォワード
			req.getRequestDispatcher("student_update_done.jsp").forward(req, res);
		}

	}

}
