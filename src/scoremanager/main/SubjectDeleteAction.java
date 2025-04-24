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
        // ログイン中の先生情報を取得
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // JSPから送られた科目コードを取得
        String subjectCd = req.getParameter("cd");

        System.out.println("受け取ったSubjectDeleteAction: " + subjectCd);
        // 対象のSubjectをDBから取得
        SubjectDao dao = new SubjectDao();
        Subject subject = dao.get(subjectCd, teacher.getSchool());

        // subjectが存在しなければエラー処理も追加してよい（ここでは省略）

        // JSPで使えるように属性にセット
        req.setAttribute("subject_name", subject.getName());
        req.setAttribute("cd", subjectCd);

        // 削除確認画面へフォワード
        req.getRequestDispatcher("subject_delete.jsp").forward(req, res);
    }
}
