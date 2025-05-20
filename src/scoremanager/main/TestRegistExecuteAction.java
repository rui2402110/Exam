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
        Map<String, String> errorMap = new HashMap<>();
        List<Test> testList = new ArrayList<>();

        // DAO 初期化
        StudentDao stuDao = new StudentDao();
        SubjectDao subDao = new SubjectDao();
        TestDao testDao = new TestDao();

        System.out.println("TestRegistExecute開始-------------------------");

        // フォームからパラメータを取得
        String subjectCd = req.getParameter("subject");  // 科目コード
        String countStr = req.getParameter("count");    // 試験回数

        System.out.println("受け取ったパラメータ:");
        System.out.println("科目コード: " + subjectCd);
        System.out.println("回数: " + countStr);

        // TestRegist.actionで使用する属性をセット
        req.setAttribute("f3", subjectCd);
        req.setAttribute("f4", countStr);

        // 各学生の点数処理
        String[] regists = req.getParameterValues("regist");  // すべての学生番号を取得
        System.out.println("registsのサイズ: " + (regists != null ? regists.length : 0));

        String error1 = "点数を入力してください";
        String error2 = "0～100の範囲で入力してください";

        int errorCount = 0;

        if (regists != null) {
            for(String studentNo : regists) { // 学生番号をstudentNoに代入
                // パラメータ取得
                String pointStr = req.getParameter("point_" + studentNo);  // 学生ごとの点数

                // 未入力
                if (pointStr == null || pointStr.trim().isEmpty()) {
                    System.out.println("点数を入力してください");
                    req.setAttribute("error1", error1);
                    errorCount += 1;
                    break;
                }

                try {
                    // pointStrをpointに変換
                    int point = Integer.parseInt(pointStr);
                    if (point < 0 || point > 100) {
                        System.out.println("0～100の範囲で入力してください");
                        req.setAttribute("error2", error2);
                        errorCount += 1;
                        break;
                    } else {
                        // データを格納
                        Test test = new Test();

                        // 学生情報の取得（この学生オブジェクトにはクラス情報も含まれている）
                        Student student = stuDao.get(studentNo);
                        test.setStudent(student);

                        // 科目情報のセット
                        test.setSubject(subDao.get(subjectCd, teacher.getSchool()));

                        // その他の情報をセット
                        test.setSchool(teacher.getSchool());
                        test.setNo(Integer.parseInt(countStr));
                        test.setPoint(point);

                        // 学生オブジェクトからクラス情報を取得
                        test.setClassNum(student.getClassNum());

                        System.out.println("Student: " + test.getStudent().getNo() + " " + test.getStudent().getName());
                        System.out.println("Subject: " + test.getSubject().getCd() + " " + test.getSubject().getName());
                        System.out.println("School: " + test.getSchool());
                        System.out.println("No: " + test.getNo());
                        System.out.println("Point: " + test.getPoint());
                        System.out.println("ClassNum: " + test.getClassNum());
                        System.out.println("入学年度: " + test.getStudent().getEntYear());
                        System.out.println("---------------------");

                        // testlistに追加
                        testList.add(test);
                    }

                } catch (NumberFormatException e) {
                    errorMap.put(studentNo, "数値で入力してください");
                    errorCount += 1;
                    break;
                }
            }
        }

        // 処理分岐
        if (errorCount >= 1) {
            // エラーがある場合はエラーマップと入力データを保持してJSPへ返す
            System.out.println("エラーあります");

            // Test一覧画面に戻る
            // hiddenフィールドから入学年度とクラスを取得しておく（エラー時のリダイレクト用）
            String entYearStr = req.getParameter("entYear");
            String classNumStr = req.getParameter("classNum");

            String url = "TestRegist.action?f1=" + entYearStr + "&f2=" + classNumStr + "&f3=" + subjectCd + "&f4=" + countStr;
            req.getRequestDispatcher(url).forward(req, res);
        } else {
            // 成績を保存
            System.out.println("エラー無いです");
            testDao.save(testList);
            req.setAttribute("points", testList);
            req.setAttribute("errors", errorMap);
            req.getRequestDispatcher("test_regist_done.jsp").forward(req, res);
        }
        System.out.println("TestRegistExecute終了-------------------------");
    }
}