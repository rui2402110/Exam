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
        SubjectDao subDao =new SubjectDao();

        System.out.println("受け取ったSubjectDeleteActionEX: " + subjectCd);
        Subject subject = new Subject();
        subject = subDao.get(subjectCd,teacher.getSchool());
        System.out.println(subject);
        System.out.println(subject.getName());

        // 削除対象のSubjectオブジェクトを作成

        subject.setCd(subjectCd);
        subject.setName(subject.getName());
        subject.setSchool(teacher.getSchool());

        // 削除実行
        SubjectDao dao = new SubjectDao();
        boolean success = dao.delete(subject);

        // メッセージを設定して一覧画面へ
        req.setAttribute("message", subjectCd + " を削除しました");
        req.getRequestDispatcher("subject_delete_done.jsp").forward(req, res);
    }
}
