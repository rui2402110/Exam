package scoremanager.main;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Teacher;
import dao.StudentDao;
import dao.TestDao;
import tool.Action;
public class TestRegistAddExecuteAction extends Action {
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		HttpSession session = req.getSession();
		Teacher teacher = (Teacher)session.getAttribute("user");

		StudentDao stuDao = new StudentDao();
		// フォームから複数データを取得
        String[] students = req.getParameterValues("f1");
        String[] subjects = req.getParameterValues("f2");
        String[] testNumbers = req.getParameterValues("f3");
        String[] scores = req.getParameterValues("f4");

        System.out.println(Arrays.toString(students));
        System.out.println(Arrays.toString(subjects));
        System.out.println(Arrays.toString(testNumbers));
        System.out.println(Arrays.toString(scores));

        // データが存在するか確認
        if (students == null || subjects == null || testNumbers == null || scores == null) {
            // エラー処理
            req.setAttribute("errorMessage", "データが正しく送信されませんでした。");
            req.getRequestDispatcher("TestRegistAdd.action").forward(req, res);
            return;
        }

        // データの長さが一致するか確認
        if (students.length != subjects.length ||
            students.length != testNumbers.length ||
            students.length != scores.length) {
            // エラー処理
            req.setAttribute("errorMessage", "送信されたデータの数が一致しません。");
            req.getRequestDispatcher("TestRegistAdd.action").forward(req, res);
            return;
        }

        // テストデータのリストを作成
        List<List<String>> testList = new ArrayList<List<String>>();
        boolean hasError = false;
        String errorMessage = "";

        for (int i = 0; i < students.length; i++) {
            try {
                // スコアを数値に変換
                int score = Integer.parseInt(scores[i]);

                // バリデーション
                if (score < 0 || score > 100) {
                    hasError = true;
                    errorMessage = "点数は0から100の間で入力してください。";
                    break;
                }

                // テストデータオブジェクトを作成
                List<String> list = new ArrayList<String>();
                System.out.println(students[i]);
                System.out.println(subjects[i]);
                System.out.println(testNumbers[i]);
                System.out.println(scores[i]);
                System.out.println(teacher.getSchool().getCd());
                System.out.println(stuDao.get(students[i]).getClassNum());

                list.add(students[i]);
                list.add(subjects[i]);
                list.add(testNumbers[i]);
                list.add(scores[i]);
                list.add(teacher.getSchool().getCd());
                list.add(stuDao.get(students[i]).getClassNum());

                testList.add(list);
            } catch (NumberFormatException e) {
                hasError = true;
                errorMessage = "数値の形式が正しくありません。";
                break;
            }
        }

        // エラーがあった場合の処理
        if (hasError) {
            req.setAttribute("errorMessage", errorMessage);
            req.getRequestDispatcher("TestRegistAdd.action").forward(req, res);
            return;
        }

        // テストデータをデータベースに保存
        TestDao tesdao = new TestDao();
        boolean result = tesdao.save1(testList);

        if (result) {
            // 成功時の処理
            req.setAttribute("message", "テストデータが正常に保存されました。");
            req.getRequestDispatcher("test_regist_done.jsp").forward(req, res);
        } else {
            // 失敗時の処理
            req.setAttribute("errorMessage", "データの保存に失敗しました。");
            req.getRequestDispatcher("TestRegistAdd.action").forward(req, res);
        }
	}
}