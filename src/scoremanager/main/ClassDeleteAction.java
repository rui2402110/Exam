package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Teacher;
import dao.ClassNumDao;
import dao.StudentDao;
import tool.Action;

public class ClassDeleteAction extends Action {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        String classNumStr = req.getParameter("classnum");
        String newClassNumStr = req.getParameter("new_class_num"); // 移動先クラスを取得
        StudentDao studentDao = new StudentDao();
        ClassNumDao classNumDao = new ClassNumDao();
     // セッションからログインユーザー情報を取得
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        int studentCount = studentDao.countStudents(classNumStr);
        System.out.println("studentCount: " + studentCount);

//        req.setAttribute("studentCount", studentCount);


        if (studentCount != 0) {
//            // 生徒を新しいクラスへ移動
//            studentDao.updateClassNum(classNumStr, newClassNumStr);
//            System.out.println("生徒をクラス " + newClassNumStr + " に移動完了");
//
//            // 移動後、クラスを削除
//            classNumDao.delete(classNumStr, teacher.getSchool().getCd());
//            System.out.println("クラス " + classNumStr + " を削除完了");
        	String message =classNumStr+"に生徒が存在するため削除できませんでした";
//
            req.setAttribute("message", message);
            req.getRequestDispatcher("ClassUpdate.action").forward(req, res);
        } else {
            // 生徒がいない場合、そのまま削除
            classNumDao.delete(classNumStr, teacher.getSchool().getCd());
            System.out.println("クラス " + classNumStr + " を削除完了");

            req.setAttribute("message", "クラスを削除しました。");
        }

        req.getRequestDispatcher("ClassList.action").forward(req, res);
    }
}