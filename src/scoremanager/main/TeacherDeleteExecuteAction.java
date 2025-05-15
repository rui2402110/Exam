package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Teacher;
import dao.TeacherDao;
import tool.Action;

public class TeacherDeleteExecuteAction extends Action {
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		HttpSession session = req.getSession();
	    // フォームから送られた教師IDを取得
	    String id = req.getParameter("id");
	    TeacherDao tDao = new TeacherDao();

	    System.out.println("受け取ったTeacherDeleteActionEX: " + id);

	    // 該当教師を取得
	    Teacher teach = tDao.get(id);

	    if (teach == null) {
	        req.setAttribute("error", "指定されたIDの教師は存在しません。");
	        req.getRequestDispatcher("teach_delete_done.jsp").forward(req, res);
	        return;
	    }

	    System.out.println(teach);
	    System.out.println(teach.getName());

	    // 削除実行
	    boolean success = tDao.delete(teach);

	    System.out.println("success=" + success);

	    if (success) {
	        String message = "教員「" + teach.getName() + "」を削除しました";
	        req.setAttribute("message", message);
	    } else {
	        String message = "削除に失敗しました";
	        req.setAttribute("message", message);
	    }

	    req.getRequestDispatcher("TeacherList.action").forward(req, res);
	}

}
