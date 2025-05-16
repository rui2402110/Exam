package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.ClassNum;
import bean.Teacher;
import dao.ClassNumDao;
import tool.Action;

public class ClassUpdateExecuteAction extends Action {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        // セッションからログインユーザー情報を取得
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // パラメータ取得
        String classNumStr = req.getParameter("class_num");
        String schoolCdStr = req.getParameter("school_cd");
        String newClassNumStr = req.getParameter("new_class_num");

        // DAOの準備
        ClassNumDao cNumDao = new ClassNumDao();

        // 新しいクラスが既に存在しないかチェック
        if (cNumDao.get(newClassNumStr, teacher.getSchool()) == null) {
            // クラス情報の更新処理を実行
            ClassNum classNum = new ClassNum();
            classNum.setClass_num(classNumStr);
            classNum.setSchool(teacher.getSchool());

            cNumDao.save(classNum, newClassNumStr, schoolCdStr);

            // 更新完了後、遷移ページへフォワード
            req.setAttribute("message", "クラス " + classNumStr + " を " + newClassNumStr + " に更新しました。");
            req.getRequestDispatcher("class_update_done.jsp").forward(req, res);
        } else {
            // クラス番号が重複している場合、エラーを設定して再入力画面へ
            req.setAttribute("errors1", "クラス番号が既に存在します。");
            req.getRequestDispatcher("ClassUpdate.action").forward(req, res);
        }
    }
}