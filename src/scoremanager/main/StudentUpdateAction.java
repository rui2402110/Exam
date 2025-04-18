package scoremanager.main;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Student;
import bean.Teacher;
import dao.ClassNumDao;
import dao.StudentDao;
import tool.Action;

public class StudentUpdateAction extends Action {
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		//必要なものを定義
		HttpSession session = req.getSession();
		Teacher teacher = (Teacher)session.getAttribute("user");
		ResultSet rSet = null;

		//使用するDaoを定義
		StudentDao sDao = new StudentDao();
		ClassNumDao cNumDao = new ClassNumDao();

		//使用する変数を定義
		boolean isAttend = false;
		List<Student> students = null;
		LocalDate todaysDate = LocalDate.now();
		int year = todaysDate.getYear();

		//StudentUpdateExecuteからエラーメッセージを取得 (使わない)
		String errors1 = (String)req.getAttribute("errors1");
		String errors2 = (String)req.getAttribute("errors2");

		// studentlistから届いたnoを取得
		String no = (String)req.getParameter("no");
//		System.out.println(no);

		// noを使用してそのnoの人のデータを取得
		Student student = sDao.get(no);

		// getterを使用してnameを取得
		String name = student.getName();

		// getterを使用してisAttendを取得
        isAttend = student.getIsAttend();
        String isAttendStr = null ;
        // isAttendがtrueだった場合、JSPのチェックボックスを最初からonにするためのメッセージを取得
        if (isAttend ==true){
        	isAttendStr = "checked";
        }

        // getterを使用して年度をしゅとくしたのちStringに変換
        Integer entYear = student.getEntYear();
        String entYearStr = entYear.toString();


        //年度表を取得
		List<String> list = cNumDao.filter(teacher.getSchool());
		students = sDao.filter(teacher.getSchool(), isAttend);



		//JSPにデータをセット
		req.setAttribute("no", no);
		req.setAttribute("name", name);
		req.setAttribute("class_num_set", list);
		req.setAttribute("ent_year", entYearStr);
		req.setAttribute("is_attend", isAttendStr);

		// エラーメッセージをセット
		req.setAttribute("errors1", errors1);
		req.setAttribute("errors2", errors2);

		// フォワード
		req.getRequestDispatcher("student_update.jsp").forward(req, res);
	}

}
