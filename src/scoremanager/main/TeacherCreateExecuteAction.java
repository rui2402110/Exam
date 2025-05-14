package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Teacher;
import dao.TeacherDao;
import tool.Action;

public class TeacherCreateExecuteAction extends Action{
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		//メソッドとスタブ
		HttpSession session = req.getSession();
		Teacher teacher = (Teacher)session.getAttribute("user");
		Teacher teacherSet =new Teacher();

		String id = "";//ユーザーID
		String pass1 = "" ;//パスワード
		String pass2 = "" ;//パスワード確認用
		String name = "" ;//教員名
		String authStr ="" ;//管理者権限確認
		boolean auth = false ;//管理者権限

		// 使用するDAOを定義
		TeacherDao tDao = new TeacherDao();

		//JSPから送られたデータを取得
		id = req.getParameter("id"); // ユーザーID
		pass1 = req.getParameter("pass1"); // パスワード
		pass2 = req.getParameter("pass2"); // パスワード確認用
		name = req.getParameter("name"); // 教員名
		authStr = req.getParameter("auth"); // 管理者権限

		if (authStr != null){
			auth = true;
		}
//		// 取得した値をコンソールに出力
//		System.out.println("ユーザーID: " + id);
//		System.out.println("パスワード: " + pass1);
//		System.out.println("パスワード確認用: " + pass2);
//		System.out.println("教員名: " + name);
//		System.out.println("管理者権限: " + authStr);
//		System.out.println("管理者権限: " + auth);

		//パスワードチェック
		if(pass1 != null && pass1.equals(pass2)){
			//idの重複チェック
			if (tDao.get(id)==null){
				// エラーの表示が必要ない場合データをセット
				teacherSet.setId(id);
				teacherSet.setPassword(pass1);
				teacherSet.setName(name);
				teacherSet.setSchool(teacher.getSchool());
				teacherSet.setAuth(auth);

				// INSERTを呼び出し
				tDao.save(teacherSet);

				//　フォワード
				req.getRequestDispatcher("teacher_create_done.jsp").forward(req, res);
			}else{
				// 認証失敗の場合
				// エラーメッセージをセット
				String errors2 = ("IDが重複しています");
				req.setAttribute("errors2", errors2);

				String url = "teacher_create.jsp";
				req.setAttribute("pass1_set", pass1);
				req.setAttribute("pass2_set", pass2);
				req.setAttribute("name_set", name);
				req.getRequestDispatcher(url).forward(req, res);
			}
		}else{
			// 認証失敗の場合
			// エラーメッセージをセット
			String errors2 = ("パスワードが同じではありません");
			req.setAttribute("errors2", errors2);

			String url = "teacher_create.jsp";
			req.setAttribute("id_set", id);
			req.setAttribute("pass1_set", pass1);
			req.setAttribute("pass2_set", pass2);
			req.setAttribute("name_set", name);
			req.getRequestDispatcher(url).forward(req, res);
		}

	}

}
