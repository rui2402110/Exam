package scoremanager.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Student;
import bean.Teacher;
import bean.Test;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestDao;
import tool.Action;

public class TestRegistExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        // セッションからログイン中の教師情報を取得
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // DAO 初期化
        StudentDao studentDao = new StudentDao();
        SubjectDao subjectDao = new SubjectDao();
        TestDao testDao = new TestDao();

        // パラメータ取得
        String[] studentNos = req.getParameterValues("regist"); // 学生番号一覧
        String entYear = req.getParameter("f1");
        String classNum = req.getParameter("f2");
        String subjectCd = req.getParameter("subject");
        String countStr = req.getParameter("count");

        Map<String, String> errorMap = new HashMap<>();
        List<Test> testList = new ArrayList<>();

        // 各学生の点数処理
        for (String studentNo : studentNos) {
            String pointStr = req.getParameter("point_" + studentNo);

            if (pointStr == null || pointStr.trim().isEmpty()) {
                errorMap.put(studentNo, "点数を入力してください");
                continue;
            }

            try {
                int point = Integer.parseInt(pointStr);

                if (point < 0 || point > 100) {
                    errorMap.put(studentNo, "0～100の範囲で入力してください");
                    continue;
                }

                // 学生情報の取得
                Student student = studentDao.get(studentNo);
                student.setEntYear(Integer.parseInt(entYear));
                student.setClassNum(classNum);

                // テスト情報の作成
                Test test = new Test();
                test.setStudent(student);
                test.setSubject(subject);
                test.setNo(count);
                test.setPoint(point);
                test.setClassNum(classNum);

                testList.add(test);

            } catch (NumberFormatException e) {
                errorMap.put(studentNo, "数値で入力してください");
            }
        }

        // 処理分岐
        if (!errorMap.isEmpty()) {
            // エラーがある場合はエラーマップと入力データを保持してJSPへ返す
            req.setAttribute("errors", errorMap);
            req.setAttribute("points", testList);
        } else {
            // 成績を保存
            testDao.save(testList);
            req.setAttribute("points", testList);
        }

        // 再表示
        req.setAttribute("subject_name", subject.getName());
        req.setAttribute("subject_cd", subjectCd);
        req.setAttribute("count", count);
        req.getRequestDispatcher("TestRegist.action").forward(req, res);
    }
}
