package scoremanager.main;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Teacher;
import dao.TeacherDao;
import dto.TeacherDTO;
import tool.Action;

public class TeacherListAction extends Action {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        // セッションからログインユーザー情報を取得
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // 未ログインならログインページへリダイレクト
        if (teacher == null) {
            res.sendRedirect("login.jsp");
            return;
        }

        // DAOを利用して教師データを取得
        TeacherDao tDao = new TeacherDao();
        List<TeacherDTO> teacherList = tDao.filter(teacher.getSchool())
            .stream()
            .map(TeacherDTO::new)
            .collect(Collectors.toList());

        // JSPにデータをセット
        req.setAttribute("teacherList", teacherList);
        req.setAttribute("school", teacher.getSchool());

//        System.out.println( teacher.getAuth());
//        System.out.println( teacher.getName());

        // セッションにも保存 (JSP で使いやすい形に)
        session.setAttribute("userAuth", teacher.getAuth());

        req.getRequestDispatcher("teacher_list.jsp").forward(req, res);
    }
}