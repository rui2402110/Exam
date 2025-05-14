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
        // ログイン中の先生情報を取得
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // フォームから送られた科目コードを取得
        String id = req.getParameter("id");
        TeacherDao tDao =new TeacherDao();

        System.out.println("受け取ったTeacherDeleteActionEX: " + id);
        Teacher teach = new Teacher();
        teach = tDao.get(id);
        System.out.println(teach);
        System.out.println(teach.getName());

        // 削除実行
        boolean success = tDao.delete(teach);

        // メッセージを設定して一覧画面へ
        req.setAttribute("message", teach.getId() + " を削除しました");
        req.getRequestDispatcher("teach_delete_done.jsp").forward(req, res);
    }
}
