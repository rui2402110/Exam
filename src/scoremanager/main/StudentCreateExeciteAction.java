package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Student;
import bean.Teacher;
import dao.ClassNumDao;
import dao.StudentDao;
import tool.Action;

public class StudentCreateExeciteAction extends Action{
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		//メソッドとスタブ
		HttpSession session = req.getSession();
		Teacher teacher = (Teacher)session.getAttribute("user");
		Student students = new Student() ;
		String entYearStr = "";
		int entYear ;
		String no = "" ;
		String name = "" ;
		String classNum = "";
//		boolean isAttend =true ;

		// 使用するDAOを定義
		StudentDao sDao = new StudentDao();
		ClassNumDao cDao =new ClassNumDao();

		//JSPから送られたデータを取得
		entYearStr = req.getParameter("f1");
		no  = req.getParameter("no");
		name = req.getParameter("name");
		classNum  = req.getParameter("f2");

		entYear = Integer.parseInt(entYearStr);

//		System.out.println(entYearStr);
//		System.out.println(no);
//		System.out.println(name);
//		System.out.println(classNum);

		// entYearが0だった場合は通常の処理を行わずにentYearが0だった場合のエラーデータを取得しStudentCreateActionにフォワード
		if (entYear != 0 ) {
			// noに被りがあった場合は通常の処理を行わずにnoが被りだった場合のエラーデータを取得しStudentCreateActionにフォワード
			if( sDao.get(no) ==null){
				if (classNum != "0"){
					// エラーの表示が必要ない場合データをセット
					students.setEntYear(entYear);
					students.setNo(no);
					students.setName(name);
					students.setClassNum(classNum);
					//よく見たら仕様書に最初は在籍してないところから始まる(違約)って書いてあった
//					students.setAttend(isAttend);
					students.setSchool(teacher.getSchool());

					// INSERTを呼び出し
					sDao.save(students);

					//　フォワード
					req.getRequestDispatcher("student_create_done.jsp").forward(req, res);

				}else{
					String errors3 = ("クラスを入力してください");
					req.setAttribute("errors3", errors3);

					//値をセット
					req.setAttribute("ent_year", entYear);
					req.setAttribute("no", no);
					req.setAttribute("name", name);
					req.setAttribute("class_num", classNum);

					String url = "StudentCreate.action";
					req.getRequestDispatcher(url).forward(req, res);
				}
			}
			else{
				// 認証失敗の場合
				// エラーメッセージをセット
				String errors2 = ("学生番号が重複しています");
				req.setAttribute("errors2", errors2);

				//値をセット
				req.setAttribute("ent_year", entYear);
				req.setAttribute("no", no);
				req.setAttribute("name", name);
				req.setAttribute("class_num", classNum);

				String url = "StudentCreate.action";
				req.getRequestDispatcher(url).forward(req, res);
			}
		} else {
			// 認証失敗の場合
			// エラーメッセージをセット
			String errors1 = ("入学年度を選択してください");
			req.setAttribute("errors1", errors1);

			//値をセット
			req.setAttribute("ent_year", entYear);
			req.setAttribute("no", no);
			req.setAttribute("name", name);
			req.setAttribute("class_num", classNum);

			String url = "StudentCreate.action";
			req.getRequestDispatcher(url).forward(req, res);
		}

	}

}
