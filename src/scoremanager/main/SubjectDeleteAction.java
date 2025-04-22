package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import tool.Action;

public class SubjectDeleteAction extends Action {
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		//メソッドとスタブ
		HttpSession session = req.getSession();
		Teacher teacher = (Teacher)session.getAttribute("user");

		//JSPから送られたデータを取得
        String subjectCd = req.getParameter("subject_cd");

        Subject subject = new Subject();
        subject.setCd(subjectCd);
        subject.setSchool(teacher.getSchool());


     // 使用するDAOを定義
        SubjectDao subDao = new SubjectDao();
//        削除
        boolean deleted = subDao.delete(subject);
		String message =  subjectCd + "を削除しました";
        req.setAttribute("message", message);

		req.getRequestDispatcher("SubjectList.action").forward(req, res);
	}
}
