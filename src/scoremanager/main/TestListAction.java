package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import dao.ClassNumDao;
import dao.StudentDao;
import dao.SubjectDao;
import tool.Action;

public class TestListAction extends Action {
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		//メソッドとスタブ
		HttpSession session = req.getSession();
		Teacher teacher = (Teacher)session.getAttribute("user");
		LocalDate todaysDate = LocalDate.now();
		int year = todaysDate.getYear();


		// 使用するDAOを定義
		ClassNumDao cNumDao =new ClassNumDao();
		SubjectDao subDao =new SubjectDao();
		StudentDao stuDao =new StudentDao();

		// 表示用の年度リストを作成
		List<Integer> entYearSet = new ArrayList<>();
		for (int i = year - 10; i < year + 1; i++) {
			entYearSet.add(i);
		}

		//StudentCreateExecuteActionからエラー処理用のデータを取得
		String errors1 = (String)req.getAttribute("errors1");
		String errors2 = (String)req.getAttribute("errors2");

		// ログインユーザーの学校コードをもとにクラス番号の一覧を取得
		List<String> classList = cNumDao.filter(teacher.getSchool());

		// ログインユーザーの学校コードをもとに科目の一覧を取得
		List<Subject> subList = subDao.filter(teacher.getSchool());

		// ログインユーザーの学校コードをもとに学生の一覧を取得する必要はなかったです
//		List<Student> stuList =stuDao.filter(teacher.getSchool(),true);

//		System.out.println(entYearSet);
//		System.out.println(classList);
//		System.out.println(subList);
		// JSPに送るデータをセット
		req.setAttribute("ent_year_set",entYearSet);
		req.setAttribute("class_num_set",classList);
		req.setAttribute("subject_set", subList);
//		req.setAttribute("student_set", stuList);
		req.setAttribute("errors1",errors1);
		req.setAttribute("errors2",errors2);

		// フォワード
	    req.getRequestDispatcher("test_list.jsp").forward(req, res);
	}

}
