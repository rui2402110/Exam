package scoremanager.main;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.ClassNum;
import bean.Teacher;
import dao.ClassNumDao;
import tool.Action;
public class ClassDeleteAction extends Action {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        //メソッドとスタブ
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher)session.getAttribute("user");

        ClassNum classNum = new ClassNum();

        //JSPから送られたデータを取得
        String classNumStr = req.getParameter("classnum");
        // 使用するDAOを定義
        ClassNumDao cNumDao = new ClassNumDao();
        Map<String, String> classToSchoolMap;
        classToSchoolMap = cNumDao.filter2(teacher.getSchool());
        String schoolCd = classToSchoolMap.get(classNumStr);
        cNumDao.delete(classNumStr, schoolCd);
        String message = classNumStr + "を削除しました";
        req.setAttribute("message", message);


        req.getRequestDispatcher("ClassList.action").forward(req, res);
    }
}