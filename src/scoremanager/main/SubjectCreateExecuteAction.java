package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import tool.Action;

public class SubjectCreateExecuteAction extends Action{
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		//メソッドとスタブ
		HttpSession session = req.getSession();
		Teacher teacher = (Teacher)session.getAttribute("user");
		Subject subject = new Subject() ;
		String cd = "";
		String name = "" ;

		// 使用するDAOを定義
		SubjectDao subDao = new SubjectDao();

		//JSPから送られたデータを取得
		cd  = req.getParameter("cd");
		name = req.getParameter("name");

//		System.out.println(cd);
//		System.out.println(name);

		// 科目コード(CD)が3文字以外だった場合は通常の処理を行わずにエラーデータを取得しSubjectCreateActionにフォワード
		if (cd.length() == 3 ) {
			// CDの被りがあった場合は通常の処理を行わずにエラーデータを取得しSubjectCreateActionにフォワード
			if( subDao.get(cd,teacher.getSchool()) ==null){
				// エラーの表示が必要ない場合データをセット
				subject.setSchool(teacher.getSchool());
				subject.setCd(cd);
				subject.setName(name);


				// INSERTを呼び出し
				subDao.save(subject);

				//　フォワード
				req.getRequestDispatcher("subject_create_done.jsp").forward(req, res);
			}
			else{
				// 認証失敗の場合
				// エラーメッセージをセット
				String errors2 = ("科目コードが重複しています");
				req.setAttribute("errors2", errors2);

				String url = "subject_create.jsp";
				req.setAttribute("cd_set", cd);
				req.setAttribute("name_set", name);
				req.getRequestDispatcher(url).forward(req, res);
			}
		} else {
			// 認証失敗の場合
			// エラーメッセージをセット
			String errors1 = ("科目コードは3文字で入力してください");
			req.setAttribute("errors1", errors1);

			String url = "subject_create.jsp";
			req.getRequestDispatcher(url).forward(req, res);
		}

	}

}
