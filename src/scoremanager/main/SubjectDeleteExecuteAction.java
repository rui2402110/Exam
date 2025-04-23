package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import tool.Action;

public class SubjectDeleteExecuteAction extends Action {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        // ログイン中の先生情報を取得
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // フォームから送られた科目コードを取得
        String subjectCd = req.getParameter("subject_cd");

        // 削除対象のSubjectオブジェクトを作成
        Subject subject = new Subject();
        subject.setCd(subjectCd);
        subject.setSchool(teacher.getSchool());

        // 削除実行
        SubjectDao dao = new SubjectDao();
        dao.delete(subject);

        // メッセージを設定して一覧画面へ
        req.setAttribute("message", subjectCd + " を削除しました");
        req.getRequestDispatcher("SubjectList.action").forward(req, res);
    }
}
