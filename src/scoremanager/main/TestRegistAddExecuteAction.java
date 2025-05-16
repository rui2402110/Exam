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

		StudentDao stuDao =new StudentDao();
		// フォームから複数データを取得
        String[] students = req.getParameterValues("student[]");
        String[] subjects = req.getParameterValues("subject[]");
        String[] testNumbers = req.getParameterValues("testNumber[]");
        String[] scores = req.getParameterValues("score[]");

        System.out.println(Arrays.toString(students));
        System.out.println(subjects);
        System.out.println(testNumbers);
        System.out.println(scores);

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

                // テストデータオブジェクトを作成
                List<String> list = new ArrayList<String>();
                list.add(students[i]);
                list.add(subjects[i]);
                list.add(testNumbers[i]);
                list.add(scores[i]);
                list.add(teacher.getSchool().getCd());
                list.add(stuDao.get2(students[i]).getClassNum());

                testList.add(list);

                // バリデーション
                if (score < 0 || score > 100) {
                    hasError = true;
                    errorMessage = "点数は0から100の間で入力してください。";
                    break;
                }

	} catch (NumberFormatException e) {
        hasError = true;
        errorMessage = "数値の形式が正しくありません。";
        break;
    }
         // テストデータをデータベースに保存
            TestDao tesdao = new TestDao();
            boolean result = tesdao.save1(testList);

            if (result) {
                // 成功時の処理
                req.setAttribute("message", "テストデータが正常に保存されました。");
                req.getRequestDispatcher("/success.jsp").forward(req, res);
            } else {
                // 失敗時の処理
                req.setAttribute("errorMessage", "データの保存に失敗しました。");
                req.getRequestDispatcher("/error.jsp").forward(req, res);
            }
        }
	}
}
