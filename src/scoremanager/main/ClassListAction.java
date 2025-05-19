package scoremanager.main;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.ClassNum;
import bean.Teacher;
import dao.ClassNumDao;
import tool.Action;

public class ClassListAction extends Action {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        ClassNumDao cNumDao = new ClassNumDao();

        // teacherの学校情報をもとにクラス情報リストを取得
        List<ClassNum> classList = cNumDao.filter3(teacher.getSchool());

        // JSPに渡すためリクエストスコープにセット
        req.setAttribute("classList", classList);

        // 認証情報をセッションに保存
        session.setAttribute("userAuth", teacher.getAuth());

        // class_list.jspへフォワード
        req.getRequestDispatcher("class_list.jsp").forward(req, res);
    }
}
