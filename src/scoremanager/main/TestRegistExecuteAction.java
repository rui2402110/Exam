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
        Student students =new Student();

        // DAO 初期化
        StudentDao stuDao = new StudentDao();
        SubjectDao subDao = new SubjectDao();
        TestDao testDao = new TestDao();
//        ClassNumDao classDao =new ClassNumDao();

        // 各学生の点数処理
        String[] regists = req.getParameterValues("regist");  // すべての学生番号を取得
        for(String studentNo:regists){ //学生番号をstudentNoに代入
        	// パラメータ取得
        	String pointStr = req.getParameter("point_" + studentNo);  // 学生ごとの点数
        	String No = req.getParameter("count");  // 試験回数
            String subjectCd = req.getParameter("subject");  // 科目コード

            //未入力
            if (pointStr == null || pointStr.trim().isEmpty()) {
//                errorMap.put(studentNo, "点数を入力してください");
                System.out.print("点数を入力してください");
                continue;
            }
            try{
	          //pointSrtをpointに変換
	            int point=Integer.parseInt(pointStr);
	            if (point < 0 || point > 100) {
	                errorMap.put(studentNo, "0～100の範囲で入力してください");
	                continue;
	            }else{
	            	//データを格納
	            	Test test =new Test();
	            	students=stuDao.get(studentNo);
	            	test.setStudent(students);
	            	test.setSubject(subDao.get(subjectCd, teacher.getSchool()));
	            	test.setSchool(teacher.getSchool());
	            	test.setNo(Integer.parseInt(No));
	            	test.setPoint(point);
	            	test.setClassNum(students.getClassNum());
	            	//testlistに追加
	            	testList.add(test);

	            }
//            	セーブ
            	testDao.save(testList);
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

        // 表示
//        エラー
        req.setAttribute("errors", errorMap);
        req.getRequestDispatcher("test_regist_done.jsp").forward(req, res);
    }
}
