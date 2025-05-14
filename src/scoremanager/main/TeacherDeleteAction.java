package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Teacher;
import dao.TeacherDao;
import tool.Action;

public class TeacherDeleteAction extends Action {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        // ログイン中の先生情報を取得
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // JSPから送られた科目コードを取得
        String id = req.getParameter("id");

        System.out.println("受け取ったteacherDeleteAction: " + id);
        // 対象のSubjectをDBから取得
        TeacherDao tDao = new TeacherDao();
        Teacher teach = tDao.get(id);

        // subjectが存在しなければエラー処理も追加してよい（ここでは省略）

        // JSPで使えるように属性にセット
        req.setAttribute("id", teach.getId());
        req.setAttribute("teacher_name", teacher.getName());

        // 削除確認画面へフォワード
        req.getRequestDispatcher("teacher_delete.jsp").forward(req, res);
    }
}
