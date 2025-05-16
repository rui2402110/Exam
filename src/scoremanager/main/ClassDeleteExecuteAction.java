package scoremanager.main;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Teacher;
import dao.ClassNumDao;
import tool.Action;
public class ClassDeleteExecuteAction extends Action {
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		//メソッドとスタブ
		HttpSession session = req.getSession();
		Teacher teacher = (Teacher)session.getAttribute("user");

	    String classNumStr = req.getParameter("classnum");

	    System.out.println("削除処理開始: classNum=" + classNumStr + ", schoolCd=" + teacher.getSchool().getCd());

	    ClassNumDao classNumDao = new ClassNumDao();
	    boolean result = classNumDao.delete(classNumStr, teacher.getSchool().getCd());

	    if (result) {
	        System.out.println("削除成功: クラス " + classNumStr + " を削除しました。");
	        req.setAttribute("message", "クラス " + classNumStr + " を削除しました。");
	    } else {
	        System.out.println("削除失敗: クラス " + classNumStr + " の削除に失敗しました。");
	        req.setAttribute("message", "クラスの削除に失敗しました。");
	    }

	    System.out.println("処理完了。リダイレクト: ClassList.action");
	    req.getRequestDispatcher("ClassList.action").forward(req, res);
	}
}