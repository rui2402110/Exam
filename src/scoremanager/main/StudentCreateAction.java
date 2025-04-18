package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Student;
import bean.Teacher;
import dao.ClassNumDao;
import dao.StudentDao;
import tool.Action;

public class StudentCreateAction extends Action {
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		// メソッドとスタブ
		HttpSession session = req.getSession();
		Teacher teacher = (Teacher)session.getAttribute("user");

		// 変数を定義
		boolean isAttend = false;
		List<Student> students = null;
		LocalDate todaysDate = LocalDate.now();
		int year = todaysDate.getYear();

		// 使用するDAOを定義
		StudentDao sDao = new StudentDao();
		ClassNumDao cNumDao = new ClassNumDao();


		// 表示用の年度リストを作成
		List<Integer> entYearSet = new ArrayList<>();
		for (int i = year - 10; i < year + 1; i++) {
			entYearSet.add(i);
		}

		//StudentCreateExecuteActionからエラー処理用のデータを取得
		String errors1 = (String)req.getAttribute("errors1");
		String errors2 = (String)req.getAttribute("errors2");

		//教師データなどを取得
		List<String> list = cNumDao.filter(teacher.getSchool());
		students = sDao.filter(teacher.getSchool(), isAttend);
//		System.out.println(list);
//		System.out.println(entYearSet);

		//JSPに送るデータをセット
		req.setAttribute("class_num_set", list);
		req.setAttribute("ent_year_set", entYearSet);
		//エラー文章をセット
		req.setAttribute("errors1", errors1);
		req.setAttribute("errors2", errors2);
		//フォワード
		req.getRequestDispatcher("student_create.jsp").forward(req, res);

	}

}
